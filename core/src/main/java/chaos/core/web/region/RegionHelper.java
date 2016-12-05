package chaos.core.web.region;

import chaos.core.web.region.service.RegionService;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * ©土土网 app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-26
 */
public class RegionHelper {

    private static RegionHelper instance;
    private ApplicationContext context;
    private RegionService service;


    public static RegionHelper getInstance() {
        Assert.notNull(instance, "RegionHelper 还未初始化！");
        return instance;
    }

    private RegionHelper(ApplicationContext context) {
        this.context = context;
        this.service = this.context.getBean(RegionService.class);
    }

    public static void init(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        if (instance != null) return;
        instance = new RegionHelper(context);
    }

    public RegionService getService() {
        return service;
    }
}
