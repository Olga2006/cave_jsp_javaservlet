package com.cave.tools;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class SocialAuth extends Authenticator {

    //https://www.google.com/settings/security/lesssecureapps


    @Override
    protected PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(Const.EMAIL_TEAM, Const.PASSWORD_TEAM);

    }

}
