package com.oserret.ocrulus.service;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void sendEmail(Boolean mailSmtpAuth,
                   String mailSmtpSslProtocols,
                   String mailSmtpStarttlsEnable,
                   String mailSmtpHost,
                   String mailSmtpPort,
                   String mailSmtpSslTrust,
                   String mailUsername,
                   String mailPwd,
                   String zipFile,
                   String mailForAddress,
                   String week,
                   String year) throws MessagingException, IOException;

    void sendEmailUser(Boolean mailSmtpAuth,
                   String mailSmtpSslProtocols,
                   String mailSmtpStarttlsEnable,
                   String mailSmtpHost,
                   String mailSmtpPort,
                   String mailSmtpSslTrust,
                   String mailUsername,
                   String mailPwd,
                   String mailForAddress,
                   String generatedPwd,
                   String userName,
                   String internalConverterUri) throws MessagingException;


}
