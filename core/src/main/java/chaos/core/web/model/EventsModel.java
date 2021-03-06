package chaos.core.web.model;

import java.io.Serializable;

public class EventsModel implements Serializable {
    /**
     * 事件id
     */
    private Integer eventId;

    /**
     * 触发时间
     */
    private String createDate;

    /**
     * 总数
     */
    private Long eventCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table common_events
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * 事件id
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * 事件id
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 触发时间
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * 触发时间
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    /**
     * 总数
     */
    public Long getEventCount() {
        return eventCount;
    }

    /**
     * 总数
     */
    public void setEventCount(Long eventCount) {
        this.eventCount = eventCount;
    }
}