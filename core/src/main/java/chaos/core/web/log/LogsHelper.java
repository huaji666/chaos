package chaos.core.web.log;

import chaos.core.web.context.CommonRequestHelper;
import chaos.core.web.log.service.LogService;
import chaos.core.web.model.LogModel;
import chaos.core.web.ucenter.UCenter;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * ©chaos-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-13
 */
public class LogsHelper {

    private static LogsHelper instance;

    public static LogsHelper getInstance() {
        Assert.notNull(instance, "LogsHelper 还未初始化！");
        return instance;
    }

    private LogsHelper(ApplicationContext context) {
        this.context = context;
        this.logService = this.context.getBean(LogService.class);
    }

    private ApplicationContext context;

    public LogService getLogService() {
        return logService;
    }

    private LogService logService;

    public static void init(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        if (instance != null) return;
        instance = new LogsHelper(context);
    }

    /**
     * @param type
     * @param uCenter
     * @param target
     * @param description
     */
    public boolean insertLog(Type type, UCenter uCenter, String target, String description) {
        LogModel model = new LogModel();
        model.setType(type.ordinal());
        model.setDescription(description);
        model.setIp(CommonRequestHelper.getInstance().getIp());
        model.setTargettable(target);
        model.setUserId(uCenter.getUcenterId());
        model.setUserName(uCenter.getUcenterName());
        model.setCreateDate(String.valueOf(System.currentTimeMillis()));
        return logService.insert(model) > 0;
    }

    /**
     * @param type
     * @param uCenter
     * @param target
     */
    public boolean insertLog(Type type, UCenter uCenter, String target) {

        if (uCenter == null) return insertLog(type, target);

        LogModel model = new LogModel();
        model.setType(type.ordinal());
        model.setIp(CommonRequestHelper.getInstance().getIp());
        model.setTargettable(target);
        model.setUserId(uCenter.getUcenterId());
        model.setUserName(uCenter.getUcenterName());
        model.setCreateDate(String.valueOf(System.currentTimeMillis()));
        return logService.insert(model) > 0;
    }

    /**
     * @param type
     * @param target
     */
    public boolean insertLog(Type type, String target) {
        LogModel model = new LogModel();
        model.setType(type.ordinal());
        model.setIp(CommonRequestHelper.getInstance().getIp());
        model.setTargettable(target);
        model.setCreateDate(String.valueOf(System.currentTimeMillis()));
        return logService.insert(model) > 0;
    }


}
