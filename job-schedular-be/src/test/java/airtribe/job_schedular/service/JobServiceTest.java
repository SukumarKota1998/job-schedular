package airtribe.job_schedular.service;

import airtribe.job_schedular.dto.JobRequest;
import airtribe.job_schedular.entity.Job;
import airtribe.job_schedular.enums.ExecutionStatus;
import airtribe.job_schedular.enums.JobType;
import airtribe.job_schedular.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    void shouldCreateOneTimeJob() {

        JobRequest request = new JobRequest();
        request.setName("test-job");
        request.setJobType(JobType.ONE_TIME);
        request.setRunAt(LocalDateTime.now().plusMinutes(1));
        request.setMaxRetries(3);

        when(jobRepository.save(any(Job.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Job job = jobService.createJob(request);

        assertEquals(ExecutionStatus.PENDING, job.getStatus());
        assertEquals(0, job.getRetryCount());
        assertEquals(3, job.getMaxRetries());
        assertNotNull(job.getNextRunTime());
    }
}
