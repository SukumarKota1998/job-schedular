package airtribe.job_schedular.service;

import airtribe.job_schedular.entity.Job;
import airtribe.job_schedular.enums.ExecutionStatus;
import airtribe.job_schedular.enums.JobType;
import airtribe.job_schedular.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JobExecutionIntegrationTest {

    @Autowired
    private JobRepository jobRepository;

    @Test
    void shouldExecuteJobEndToEnd() throws Exception {

        Job job = new Job();
        job.setName("e2e-job");
        job.setJobType(JobType.ONE_TIME);
        job.setNextRunTime(LocalDateTime.now().minusSeconds(5));
        job.setStatus(ExecutionStatus.PENDING);
        job.setMaxRetries(1);

        job = jobRepository.save(job);

        // wait for scheduler + rabbit + worker
        Thread.sleep(7000);

        Job updated = jobRepository.findById(job.getId()).orElseThrow();
        assertEquals(ExecutionStatus.SUCCESS, updated.getStatus());
    }
}
