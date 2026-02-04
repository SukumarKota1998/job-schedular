package airtribe.job_schedular.worker;

import airtribe.job_schedular.constants.CommonConstants;
import airtribe.job_schedular.entity.Job;
import airtribe.job_schedular.entity.JobExecution;
import airtribe.job_schedular.enums.ExecutionStatus;
import airtribe.job_schedular.enums.JobType;
import airtribe.job_schedular.queue.JobExecutionQueue;
import airtribe.job_schedular.repository.JobExecutionRepository;
import airtribe.job_schedular.repository.JobRepository;
import airtribe.job_schedular.utills.CommonUtils;
import airtribe.job_schedular.utills.DistributedLockService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobWorker {

    @Autowired
    private JobExecutionQueue executionQueue;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExecutionRepository executionRepository;

    @Autowired
    private DistributedLockService lockService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobWorker.class);

    @PostConstruct
    public void start() {
        Thread workerThread = new Thread(this::processJobs);
        workerThread.setDaemon(true);
        workerThread.start();
    }

    private void processJobs() {
        while (true) {
            try {
                LOGGER.info("Worker waiting for job...");
                JobExecutionEvent event = executionQueue.take();
                LOGGER.info("Worker picked up job {}", event.getJobId());
                executeJob(event.getJobId());
            } catch (Exception e) {
                LOGGER.error("Worker error", e);
            }
        }
    }

    @RabbitListener(queues = "job.execution.queue")
    private void executeJob(Long jobId) {

        Job job = jobRepository.findById(jobId).orElseThrow();
        LOGGER.info("Starting execution for job {}", jobId);

        JobExecution execution = new JobExecution();
        execution.setJobId(jobId);
        execution.setStatus(ExecutionStatus.RUNNING);
        execution.setStartTime(LocalDateTime.now());
        executionRepository.save(execution);

        try {
            LOGGER.info("Executing job {}", jobId);
            Thread.sleep(2000);

            if (job.getRetryCount() > job.getMaxRetries()) {
                throw new RuntimeException("Simulated job failure");
            }

            Thread.sleep(2000);

            execution.setStatus(ExecutionStatus.SUCCESS);
            job.setStatus(ExecutionStatus.SUCCESS);
            if (JobType.RECURRING.equals(job.getJobType())) {
                job.setNextRunTime(CommonUtils.getNextExecutionTime(
                        job.getCronExpression()
                ));
                job.setStatus(ExecutionStatus.PENDING);
            }

        } catch (Exception e) {

            execution.setStatus(ExecutionStatus.FAILED);
            execution.setErrorMessage(e.getMessage());

            handleRetry(job);

        } finally {

            execution.setEndTime(LocalDateTime.now());
            executionRepository.save(execution);
            jobRepository.save(job);
            lockService.releaseLock(job.getId());

        }
    }

    private void handleRetry(Job job) {

        int retries = job.getRetryCount() + 1;
        job.setRetryCount(retries);

        if (retries > job.getMaxRetries()) {
            job.setStatus(ExecutionStatus.FAILED);
            LOGGER.error("Job {} failed permanently after {} retries",
                    job.getId(), retries);
            return;
        }

        long delaySeconds = (long) Math.pow(2, retries);
        job.setNextRunTime(LocalDateTime.now().plusSeconds(delaySeconds));
        job.setStatus(ExecutionStatus.PENDING);

        LOGGER.warn("Retrying job {} in {} seconds", job.getId(), delaySeconds);
    }


}
