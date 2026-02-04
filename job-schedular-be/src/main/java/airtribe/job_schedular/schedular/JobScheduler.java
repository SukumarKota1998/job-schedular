package airtribe.job_schedular.schedular;

import airtribe.job_schedular.entity.Job;
import airtribe.job_schedular.enums.ExecutionStatus;
import airtribe.job_schedular.repository.JobRepository;
import airtribe.job_schedular.utills.DistributedLockService;
import airtribe.job_schedular.worker.JobPublisher;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class JobScheduler {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private DistributedLockService lockService;

    @Autowired
    private JobPublisher jobPublisher;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void scheduleJobs() {
        List<Job> jobs = jobRepository.findDueJobs();

        if (jobs.isEmpty()) {
            log.info("No jobs to execute at this time.");
            return;
        }

        for (Job job : jobs) {
            boolean locked = lockService.acquireLock(job.getId(), 60);
            if (!locked) {
                log.info("Job with ID: {} is already being processed by another instance.", job.getId());
                continue;
            }
            log.info("Executing job with ID: {}", job.getId());
            job.setStatus(ExecutionStatus.RUNNING);
            jobRepository.save(job);
            jobPublisher.publish(job.getId());
        }
    }
}
