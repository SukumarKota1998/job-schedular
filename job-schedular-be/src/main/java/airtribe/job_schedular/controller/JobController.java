package airtribe.job_schedular.controller;

import airtribe.job_schedular.dto.JobRequest;
import airtribe.job_schedular.dto.ResponseBean;
import airtribe.job_schedular.entity.Job;
import airtribe.job_schedular.entity.JobExecution;
import airtribe.job_schedular.service.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Job API's", description = "APIs related to Job Scheduling and Management")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<ResponseBean> createJob(@RequestBody @Valid JobRequest jobRequest) {
        Job job = jobService.createJob(jobRequest);
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("Job created successfully", job)
                , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseBean> getJobStatus(@RequestParam Long jobId) {
        List<JobExecution> jobs = jobService.getJobStatus(jobId);
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("Job status retrieved successfully", jobs)
                , HttpStatus.OK);
    }

    @GetMapping("/all-jobs")
    public ResponseEntity<ResponseBean> getAllJobs() {
        List<Job> job = jobService.getAllJobs();
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("Job status retrieved successfully", job)
                , HttpStatus.OK);
    }
}
