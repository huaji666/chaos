package chaos.core.web.sms.interfaces;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-08
 */
public interface SmsSend {

    /**
     * 发送实现传统方式
     *
     * @param phone
     * @param con
     * @return
     */
    boolean send(String phone, String con);

    /**
     * 发送实现模板方式
     *
     * @param con
     * @return
     */
    boolean send(String... con);

    /**
     * 接口测试
     *
     * @return
     */
    boolean testSend();
}
