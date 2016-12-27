package chaos.core.web.email.service.impl;

import chaos.core.web.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender mailSender;//spring配置中定义

//    @Autowired
//    VelocityConfigurer velocityConfigurer;//spring配置中定义
    VelocityConfigurer velocityConfigurer = new VelocityConfigurer();

    @Autowired
    SimpleMailMessage simpleMailMessage;//spring配置中定义

    /**
     * 发送模板邮件
     */
    @Async
    public void emailSendTextTemplate(Map model, String vmName, String subject, String con, String... to) {
        simpleMailMessage.setTo(to); //接收人
        simpleMailMessage.setFrom(simpleMailMessage.getFrom()); //发送人,从配置文件中取得  
        simpleMailMessage.setSubject(subject);
        String result = null;
        try {
//            Template template = new Template();
//            template.setName(vmName);
//            template.setEncoding("UTF-8");

//            FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
            result = VelocityEngineUtils.mergeTemplateIntoString(velocityConfigurer.getVelocityEngine(), vmName, "UTF-8", model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        simpleMailMessage.setText(result);
        mailSender.send(simpleMailMessage);
    }

    /**
     * 发送模板邮件
     */
    @Async
    public void emailSendHtmlTemplate(Map model, String vmName, String subject, String con, String... to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(subject);

            String result = null;
            try {
                result = VelocityEngineUtils.mergeTemplateIntoString(velocityConfigurer.getVelocityEngine(), vmName, "UTF-8", model);
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageHelper.setText(result, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 发送普通文本邮件
     */
    @Async
    public void emailSendText(String subject, String con, String... to) {
        try {
            System.out.println("async...");
            simpleMailMessage.setTo(to); //接收人
            simpleMailMessage.setFrom(simpleMailMessage.getFrom()); //发送人,从配置文件中取得
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(con);
            mailSender.send(simpleMailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发送普通Html邮件
     */
    @Async
    public void emailSendHtml(String subject, String con, String... to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(subject);
            messageHelper.setText(con, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 发送普通带一张图片的Html邮件
     */
    @Async
    public void emailSendHtmlWithImage(String imagePath, String subject, String con, String... to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(subject);
            messageHelper.setText(con, true);
//          Content="<html><head></head><body><img src=\"cid:image\"/></body></html>";  
            //图片必须这样子：<img src='cid:image'/>  
            FileSystemResource img = new FileSystemResource(new File(imagePath));
            messageHelper.addInline("image", img);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 发送普通带附件的Html邮件
     */
    @Async
    public void emailSendHtmlWithAttachment(String filePath, String subject, String con, String... to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(subject);
            messageHelper.setText(con, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
//          System.out.println("file.getFilename==="+file.getFilename());  
            messageHelper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }
}