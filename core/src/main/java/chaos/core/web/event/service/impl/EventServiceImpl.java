package chaos.core.web.event.service.impl;

import chaos.core.web.dao.EventInfoModelMapper;
import chaos.core.web.dao.EventCacheModelMapper;
import chaos.core.web.dao.EventsModelMapper;
import chaos.core.web.event.service.EventService;
import chaos.core.web.model.EventCacheModel;
import chaos.core.web.model.EventInfoModel;
import chaos.core.web.model.EventsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-12
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventInfoModelMapper eventInfoModelMapper;
    @Autowired
    private EventsModelMapper eventsModelMapper;
    @Autowired
    private EventCacheModelMapper eventCacheMapper;


    /**
     * 创建事件model
     *
     * @param name
     * @return
     */
    private EventInfoModel insertEventInfo(String name) {
        EventInfoModel model = new EventInfoModel();
        model.setName(name);
        eventInfoModelMapper.insert(model);
        return model;
    }

    /**
     * 事件名称获取事件model
     *
     * @param model
     * @return
     */
    @Override
    public EventsModel selectEventsByInfo(EventInfoModel model) {
        return null;
    }

    /**
     * 添加统计
     *
     * @param model
     * @return
     */
    @Override
    public boolean insertEventsModel(EventInfoModel model) {
        return false;
    }

    /**
     * 当前事件是否已经存在
     *
     * @param title
     * @return
     */
    @Override
    public boolean isExistEventInfo(String title) {
        return false;
    }

    /**
     * 添加事件
     *
     * @param model
     * @return
     */
    @Override
    public boolean insertEventInfo(EventInfoModel model) {
        return false;
    }

    /**
     * 添加事件记录
     *
     * @param model
     * @return
     */
    @Override
    public boolean insertEventCache(EventCacheModel model) {
        return eventCacheMapper.insert(model) > 0;
    }

    @Override
    public EventInfoModel getEventInfoByTitle(String title) {

        EventInfoModel model = eventInfoModelMapper.selectByName(title);
        if (model == null) {
            model = new EventInfoModel();
            model.setName(title);
            eventInfoModelMapper.insert(model);
        }
        return model;
    }
}
