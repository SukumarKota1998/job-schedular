package airtribe.job_schedular.repository;

import airtribe.job_schedular.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("""
        SELECT j FROM Job j
        WHERE j.status = 'PENDING'
        AND j.nextRunTime <= now()
    """)
    List<Job> findDueJobs();
}
