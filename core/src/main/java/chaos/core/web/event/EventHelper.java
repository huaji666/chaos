package chaos.core.web.event;

import chaos.core.web.event.service.EventService;
import chaos.core.web.model.EventCacheModel;
import chaos.core.web.model.EventInfoModel;
import chaos.core.web.ucenter.UCenter;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-13
 */
public class EventHelper {

    private static EventHelper instance;

    public static EventHelper getInstance() {
        Assert.notNull(instance, "EventHelper 还未初始化！");
        return instance;
    }

    private EventHelper(ApplicationContext context) {
        this.context = context;
        this.eventService = this.context.getBean(EventService.class);
    }

    private ApplicationContext context;
    private EventService eventService;

    public static void init(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        if (instance != null) return;
        instance = new EventHelper(context);
    }

    public void insertEvent(String title, UCenter uCenter) {
//        eventService.insertEventsModel(model);
    }

    public void insertEvent(String title) {

        EventInfoModel model = eventService.getEventInfoByTitle(title);

        EventCacheModel cacheModel = new EventCacheModel();
        cacheModel.setEventId(model.getId());
        eventService.insertEventCache(cacheModel);


    }

    public void insertEvent(EventInfoModel title, UCenter uCenter) {
//        eventService.insertEventsModel(model);
    }

    public void insertEvent(EventInfoModel title) {

    }

    public void insertEvent(String title, UCenter uCenter, long uniqueId) {
    }


}
