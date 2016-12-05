package chaos.core.web.sms.service;

import chaos.core.model.CaseRes;
import chaos.core.model.CaseRes;
import chaos.core.web.ucenter.UCenter;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-10
 */
public interface SmsServer {
    /**
     * 模板短信
     *
     * @param con
     * @return
     */
    boolean sendSms(String... con);

    /**
     * 普通短信
     *
     * @param target
     * @param con
     * @return
     */
    boolean sendSms(UCenter target, String con);

    /**
     * @param source
     * @param target
     * @param con
     * @return
     */
    CaseRes sendSms(UCenter source, UCenter target, String con);

    /**
     * 获取验证码，会存入session
     *
     * @param count
     * @return
     */
    String getCode(int count);

    /**
     * 验证验证码
     * sCode 需通过 getCode(int)
     *
     * @param sCode
     * @return CaseRes
     * @see SmsServer#getCode(int)
     */
    CaseRes isSmsCodeVerify(String sCode);
}
