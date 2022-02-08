package com.cave.tools;

import com.cave.beans.Erreur;
import com.cave.beans.Utilisateur;
import com.cave.dao.ErreurDao;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


public class MailUtils {
    
    public boolean sendMail(ErreurDao erreurDao, Utilisateur utilisateur, String[] recipients, String[] bccRecipients, String subject, String message, String page) {
        Long userId = utilisateur != null ? utilisateur.getId() : null;
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(props, new SocialAuth());
            Message msg = new MimeMessage(session);

            InternetAddress from = new InternetAddress(Const.EMAIL_TEAM, Const.NOM_TEAM);
            msg.setFrom(from);

            InternetAddress[] toAddresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                toAddresses[i] = new InternetAddress(recipients[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, toAddresses);


            InternetAddress[] bccAddresses = new InternetAddress[bccRecipients.length];
            for (int j = 0; j < bccRecipients.length; j++) {
                bccAddresses[j] = new InternetAddress(bccRecipients[j]);
            }
            msg.setRecipients(Message.RecipientType.BCC, bccAddresses);

            msg.setSubject(subject);
            msg.setContent(message, "text/plain");
            Transport.send(msg);
            return true;
        } catch (UnsupportedEncodingException ex) {
            erreurDao.creer(new Erreur(userId, page, ex.toString()));
            //Logger.getLogger(MailUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } catch (MessagingException ex) {
            erreurDao.creer(new Erreur(userId, page, ex.toString()));
            //Logger.getLogger(MailUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}


