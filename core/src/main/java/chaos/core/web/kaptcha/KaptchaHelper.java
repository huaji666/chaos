package chaos.core.web.kaptcha;

import chaos.core.web.kaptcha.service.CaptchaService;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * ©土土网 app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-26
 */
public class KaptchaHelper {

    private static KaptchaHelper instance;
    private ApplicationContext context;
    private CaptchaService captchaService;


    public static KaptchaHelper getInstance() {
        Assert.notNull(instance, "KaptchaHelper 还未初始化！");
        return instance;
    }

    private KaptchaHelper(ApplicationContext context) {
        this.context = context;
        this.captchaService = this.context.getBean(CaptchaService.class);
    }

    public static void init(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        if (instance != null) return;
        instance = new KaptchaHelper(context);
    }

    public CaptchaService getCaptchaService() {
        return captchaService;
    }
}
