package com.expert.ai.ocrulus.controller;

import ch.qos.logback.classic.Logger;
import com.expert.ai.ocrulus.bean.LoginRequestBean;
import com.expert.ai.ocrulus.bean.Message;
import com.expert.ai.ocrulus.bean.UserBean;
import com.expert.ai.ocrulus.entity.UserEntity;
import com.expert.ai.ocrulus.service.EmailService;
import com.expert.ai.ocrulus.service.UserService;
import com.expert.ai.ocrulus.utils.Globals;
import com.expert.ai.ocrulus.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;


@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    ObjectMapper mapper = new ObjectMapper();
    Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;


    @Value("${mail.SmtpAuth}")
    private String smtpAuth;
    @Value("${mail.SmtpStarttlsEnable}")
    private String smtpStarttlsEnable;
    @Value("${mail.SmtpHost}")
    private String smtpHost;
    @Value("${mail.SmtpPort}")
    private String smtpPort;
    @Value("${mail.SmtpSslTrust}")
    private String smtpSslTrust;
    @Value("${mail.Username}")
    private String username;
    @Value("${mail.Pwd}")
    private String pwd;
    @Value("${mail.SmtpSslProtocols}")
    private String smtpSslProtocols;
    @Value("${expert.ai.ic.uri}")
    private String internalConverterUri;

    /**
     * Post request to create customer information int the system.
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewUser(@RequestParam(name = "firstName") String firstName,
                      @RequestParam(name = "lastName") String lastName,
                      @RequestParam(name = "email") String email,
                      @RequestParam(name = "userName") String userName,
                      @RequestParam(name = "password") String password) {

        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(userName);
        return userService.save(user);

    }

    /**
     * Post request to create customer information int the system.
     *
     * @param userBean
     * @return
     */
    @PostMapping("/addUser")
    public @ResponseBody
    Object saveNewUser(final @RequestBody UserBean userBean) throws JsonProcessingException {
        String generatedPwd = Utilities.generateDicPassword();
        logger.info("Call: /api/user/addUser POST SERVICE");

        if (userBean.getPassword() == null || userBean.getPassword().isEmpty()) {
            userBean.setPassword(bCryptPasswordEncoder.encode(generatedPwd));
        } else {
            userBean.setPassword(bCryptPasswordEncoder.encode(userBean.getPassword()));
        }
        logger.info("Storing the user into the database");
        UserBean createdUser = userService.saveUser(userBean);

        if (userBean.isSendEmail()) {
            try {
                emailService.sendEmailUser(smtpAuth.endsWith(Globals.TRUE_STRING), smtpSslProtocols, smtpStarttlsEnable, smtpHost,
                        smtpPort, smtpSslTrust, username, pwd, userBean.getEmail(), generatedPwd, createdUser.getUserName(), internalConverterUri);
            } catch (MessagingException e) {
                logger.error(e.getMessage());
            }
        }

        logger.info("Response: /api/user/addUser POST SERVICE");
        if (createdUser != null) {
            return createdUser;
        } else {
            return Utilities.toJson(new Message(Globals.USER_ALREADY_PRESENT, Globals.STATUS_KO));
        }
    }

    /**
     * Get request to retrieve all the users present into the Database
     *
     * @return List<UserData>
     */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<UserBean> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Post request to validate user credentials.
     *
     * @param loginRequestBean
     * @return
     */
    @PostMapping("/login")
    public @ResponseBody
    Object login(final @RequestBody LoginRequestBean loginRequestBean) throws JsonProcessingException {
        UserEntity userLogued = userService.login(loginRequestBean);
        if (userLogued != null) {
            return userLogued;
        } else {
            return Utilities.toJson(new Message(Globals.USER_DOES_NOT_EXIST, Globals.STATUS_KO));
        }
    }

}