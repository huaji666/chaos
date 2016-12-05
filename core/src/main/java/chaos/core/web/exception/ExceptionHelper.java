package chaos.core.web.exception;

import chaos.core.web.exception.service.ExceptionService;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-23
 */
public class ExceptionHelper {
    private static ExceptionHelper instance;

    public static ExceptionHelper getInstance() {
        Assert.notNull(instance, "ExcptionHelper 还未初始化！");
        return instance;
    }

    private ExceptionHelper(ApplicationContext context) {
        this.context = context;
        this.exceptionService = this.context.getBean(ExceptionService.class);
    }

    private ApplicationContext context;
    private ExceptionService exceptionService;

    public static void init(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        if (instance != null) return;
        instance = new ExceptionHelper(context);
    }

    public ExceptionService getExceptionService() {
        return exceptionService;
    }
}
