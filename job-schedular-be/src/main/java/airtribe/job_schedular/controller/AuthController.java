package airtribe.job_schedular.controller;

import airtribe.job_schedular.dto.ResponseBean;
import airtribe.job_schedular.dto.UserCreateBean;
import airtribe.job_schedular.entity.Users;
import airtribe.job_schedular.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API's", description = "APIs related to User Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseBean> registerUser(@Valid @RequestBody UserCreateBean userBean) {
        Users user = authService.registerUser(userBean);
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("User Created successfully", user), HttpStatus.OK);
    }

    @PostMapping("/logins")
    public ResponseEntity<ResponseBean> login(@RequestParam String emailId,
                                              @RequestParam String password) {
        String token = authService.login(emailId, password);
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("Logged successfully", token), HttpStatus.OK);
    }
}
