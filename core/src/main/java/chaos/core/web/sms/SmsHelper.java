package chaos.core.web.sms;

import chaos.core.web.sms.service.SmsServer;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * ©土土网 app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-26
 */
public class SmsHelper {
    private static SmsHelper instance;
    private ApplicationContext context;
    private SmsServer smsServer;


    public static SmsHelper getInstance() {
        Assert.notNull(instance, "SmsHelper 还未初始化！");
        return instance;
    }

    private SmsHelper(ApplicationContext context) {
        this.context = context;
        this.smsServer = this.context.getBean(SmsServer.class);
    }

    public static void init(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        if (instance != null) return;
        instance = new SmsHelper(context);
    }

    public SmsServer getSmsServer() {
        return smsServer;
    }
}
