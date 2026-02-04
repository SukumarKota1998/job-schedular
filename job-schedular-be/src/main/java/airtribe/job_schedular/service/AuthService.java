package airtribe.job_schedular.service;

import airtribe.job_schedular.constants.CommonConstants;
import airtribe.job_schedular.dto.UserCreateBean;
import airtribe.job_schedular.entity.Users;
import airtribe.job_schedular.execption.ResourceNotFoundException;
import airtribe.job_schedular.repository.UsersRepository;
import airtribe.job_schedular.utills.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtil;

    /**
     * This function is used to register a user into our project so that user can read articles
     * If the user already exist and active in database then throw error to avoid duplicate users.
     *
     * @return User: will return the saved user.
     * @param: UserCreateBean with necessary details like username, email id and password.
     */
    public Users registerUser(UserCreateBean userCreateBean) {
        Optional<Users> user = usersRepository.findByEmailIdAndActiveTrue(userCreateBean.getEmailId());
        if (user.isPresent()) {
            LOGGER.error("User {} already registered", userCreateBean.getEmailId());
            return user.get();
        }

        Users newUser = new Users();
        newUser.setFirstName(userCreateBean.getFirstName());
        newUser.setLastName(userCreateBean.getLastName());
        newUser.setDisplayName(userCreateBean.getFirstName() + " " + userCreateBean.getLastName());
        newUser.setEmailId(userCreateBean.getEmailId());
        newUser.setRole(CommonConstants.ROLES_NAMES.USER);
        newUser.setContactNumber(userCreateBean.getContactNumber());
        newUser.setAddress(userCreateBean.getAddress());
        newUser.setPassword(passwordEncoder.encode(userCreateBean.getPassword()));
        newUser.setIsVerified(CommonConstants.TRUE);
        newUser.setActive(CommonConstants.TRUE);
        newUser.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        newUser.setModifiedAt(LocalDateTime.now(ZoneOffset.UTC));
        return usersRepository.save(newUser);
    }

    /**
     * This function is used to check the login,
     * If user was existed and active then return JWT token.
     *
     * @param: Takes email id and password to validate the user.
     * @return: JWT token.
     */
    public String login(String emailId, String password) {
        Optional<Users> user = usersRepository.findByEmailIdAndActiveTrue(emailId);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return TokenUtils.generateToken(user.get());
        }

        LOGGER.error("User {} not found", emailId);
        throw new ResourceNotFoundException("user not found");
    }
}
