package chaos.core.web.email;

import chaos.core.web.email.service.EmailService;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * ©chaos-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-13
 */
public class EmailHelper {

    private static EmailHelper instance;

    public static EmailHelper getInstance() {
        Assert.notNull(instance, "EmailHelper 还未初始化！");
        return instance;
    }

    private EmailHelper(ApplicationContext context) {
        this.context = context;
        this.eventService = this.context.getBean(EmailService.class);
    }

    private ApplicationContext context;

    public EmailService getEventService() {
        return eventService;
    }

    private EmailService eventService;

    public static void init(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        if (instance != null) return;
        instance = new EmailHelper(context);
    }


}
