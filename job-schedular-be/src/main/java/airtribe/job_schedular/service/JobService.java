package airtribe.job_schedular.service;

import airtribe.job_schedular.dto.JobRequest;
import airtribe.job_schedular.entity.Job;
import airtribe.job_schedular.entity.JobExecution;
import airtribe.job_schedular.enums.JobType;
import airtribe.job_schedular.repository.JobExecutionRepository;
import airtribe.job_schedular.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExecutionRepository jobExecutionRepository;

    public Job createJob(JobRequest request) {

        Job job = new Job();
        job.setName(request.getName());
        job.setJobType(request.getJobType());
        job.setCronExpression(request.getCronExpression());
        job.setPayload(request.getPayload());
        job.setMaxRetries(
                request.getMaxRetries() != null ? request.getMaxRetries() : 3
        );

        if (request.getJobType() == JobType.ONE_TIME) {
            job.setNextRunTime(request.getRunAt());
        } else {
            // TEMP: schedule immediately (cron parsing comes later)
            job.setNextRunTime(LocalDateTime.now());
        }

        return jobRepository.save(job);
    }

    public List<JobExecution> getJobStatus(Long jobId) {
        return jobExecutionRepository.findByJobId(jobId);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
