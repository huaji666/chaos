package chaos.core.web.model;

import chaos.core.web.api.annoatation.ApiField;

import java.io.Serializable;

public class ExceptionModel extends ParamsModel implements Serializable {
    @ApiField("ip///")
    private String ip;

    /**
     * 用户id
     */
    @ApiField("userId/用户id//")
    private Long userId;


    private String userName;

    /**
     * 异常标题
     */
    @ApiField("title/异常标题//")
    private String title;

    /**
     * 设备信息
     */
    @ApiField("device/设备信息//")
    private String device;

    /**
     * 创建时间
     */
    @ApiField("createDate/创建时间//")
    private String createDate;

    /**
     * 异常内容
     */
    @ApiField("context/异常内容//")
    private String context;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table common_exceptions
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getIp() {
        return ip;
    }

    /**
     * 
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 异常标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 异常标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 设备信息
     */
    public String getDevice() {
        return device;
    }

    /**
     * 设备信息
     */
    public void setDevice(String device) {
        this.device = device == null ? null : device.trim();
    }

    /**
     * 创建时间
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    /**
     * 异常内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 异常内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}