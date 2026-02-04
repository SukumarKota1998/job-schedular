package airtribe.job_schedular.repository;

import airtribe.job_schedular.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailIdAndActiveTrue(String emailId);
}
