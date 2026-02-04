package airtribe.job_schedular.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String DisplayName;

    private String emailId;

    private String password;

    private String contactNumber;

    private String role;

    private String address;

    private Boolean isVerified;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
