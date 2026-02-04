package airtribe.job_schedular.entity;

import airtribe.job_schedular.enums.ExecutionStatus;
import airtribe.job_schedular.enums.JobType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    // Cron expression for recurring jobs
    private String cronExpression;

    // Payload / metadata for execution
    @Column(columnDefinition = "JSON")
    private String payload;

    private Integer retryCount;
    private Integer maxRetries;

    private LocalDateTime nextRunTime;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        status = ExecutionStatus.PENDING;
        retryCount = 0;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
