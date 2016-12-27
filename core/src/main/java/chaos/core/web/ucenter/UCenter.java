package chaos.core.web.ucenter;

/**
 *
 * commons 通用用户接口
 * 当前会话用户
 * ©chaos-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-12
 */
public interface UCenter {
    /**
     * 获取用户id
     *
     * @return
     */
    long getUcenterId();

    /**
     * 获取用户名称
     *
     * @return
     */
    String getUcenterName();

    /**
     * @return
     */
    String getUcenterPhone();
}
