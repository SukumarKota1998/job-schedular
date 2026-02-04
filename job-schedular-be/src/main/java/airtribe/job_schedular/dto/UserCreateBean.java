package airtribe.job_schedular.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateBean {

    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @Email
    private String emailId;

    @NotNull
    @NotBlank
    private String password;

    @NotBlank
    @NotNull
    @Length(min = 10, max = 10)
    private String contactNumber;

    @NotBlank
    @NotNull
    @Length(min = 10, max = 1000)
    private String address;

}
