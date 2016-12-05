package chaos.core.web.event.service;


import chaos.core.web.model.EventInfoModel;
import chaos.core.web.model.EventsModel;
import chaos.core.web.model.EventCacheModel;

/**
 *
 * 事件操作
 *
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-12
 */
public interface EventService {
    /**
     * 事件名称获取事件model
     *
     * @param model
     * @return
     */
    EventsModel selectEventsByInfo(EventInfoModel model);

    /**
     * 添加统计
     *
     * @param model
     * @return
     */
    boolean insertEventsModel(EventInfoModel model);

    /**
     * 当前事件是否已经存在
     *
     * @return
     */
    boolean isExistEventInfo(String title);

    /**
     * 添加事件
     *
     * @return
     */
    boolean insertEventInfo(EventInfoModel model);

    /**
     * 添加事件记录
     *
     * @return
     */
    boolean insertEventCache(EventCacheModel model);

    /**
     * 获取EventInfo
     *
     * @param title
     * @return
     */
    EventInfoModel getEventInfoByTitle(String title);
}
