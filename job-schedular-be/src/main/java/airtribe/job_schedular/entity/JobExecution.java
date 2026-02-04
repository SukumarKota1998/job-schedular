package airtribe.job_schedular.entity;

import airtribe.job_schedular.enums.ExecutionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_executions")
@Data
public class JobExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(length = 2000)
    private String errorMessage;
}
