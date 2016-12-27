package chaos.core.web.kaptcha.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ©chaos
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-05
 */
public interface CaptchaService {

    /**
     * 获取图像验证码base64
     *
     * @return
     */
    String getICode();

    /**
     * 获取图像验证码
     *
     * @return
     */
    byte[] getICodeImg();

    /**
     * 判断验证码是否正确
     *
     * @param str
     * @return
     */
    boolean isVerify(String str);

    /**
     * 清除记录
     */
    void clear();

    void getICodeImg(HttpServletRequest request, HttpServletResponse response);
}
