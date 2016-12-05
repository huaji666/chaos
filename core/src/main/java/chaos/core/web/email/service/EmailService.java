package chaos.core.web.email.service;

import java.util.Map;

/**
 * © shop
 * qq:1413221142
 * 作者：王健(aupp)
 * 时间：2016-02-28
 */
public interface EmailService {
    /**
     * 发送text模板邮件
     */
    void emailSendTextTemplate(Map model, String vmName, String subject, String con, String... to);

    /**
     * 发送Html模板邮件
     */
    void emailSendHtmlTemplate(Map model, String vmName, String subject, String con, String... to);

    /**
     * 发送普通文本邮件
     */
    void emailSendText(String subject, String con, String... to);

    /**
     * 发送普通Html邮件
     */
    void emailSendHtml(String subject, String con, String... to);

    /**
     * 发送普通带一张图片的Html邮件
     */
    void emailSendHtmlWithImage(String imagePath, String subject, String con, String... to);

    /**
     * 发送普通带附件的Html邮件
     */
    void emailSendHtmlWithAttachment(String filePath, String subject, String con, String... to);
}
