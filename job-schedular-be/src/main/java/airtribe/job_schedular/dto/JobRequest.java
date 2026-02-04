package airtribe.job_schedular.dto;

import airtribe.job_schedular.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobRequest {
    @NotBlank
    private String name;

    @NotNull
    private JobType jobType;

    // For ONE_TIME jobs
    private LocalDateTime runAt;

    // For RECURRING jobs
    private String cronExpression;

    private Integer maxRetries;

    private String payload;
}
