package chaos.core.web.context;

import chaos.core.web.email.service.EmailService;
import chaos.core.web.kaptcha.service.CaptchaService;
import chaos.core.web.region.service.RegionService;
import chaos.core.web.sms.service.SmsServer;
import chaos.core.utils.json.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommonController {
    /**
     * The Request.
     *
     * @see CommonController#request BaseController#request request
     */
    @Autowired
    protected HttpServletRequest request;
    /**
     * response
     */
    @Autowired
    protected HttpServletResponse response;

    /**
     * 邮箱
     */
    @Autowired
    protected EmailService emailService;

    /**
     * 验证码
     */
    @Autowired
    protected CaptchaService captchaService;

    /**
     * 短信
     */
    @Autowired
    protected SmsServer smsServer;

    /**
     * 地区
     */
    @Autowired
    protected RegionService regionService;


    /**
     * The Body map.
     */
    private Map bodyMap = new HashMap<>();

    /**
     * Gets string param.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the string param
     */
    protected String getStringParam(String key, String defaultValue) {
        if (StringUtils.isEmpty(key)) return defaultValue;

        Object o = findParam(key);
        if (!ObjectUtils.isEmpty(o)) {
            try {
                return String.valueOf(o).equals("null") ? defaultValue : String.valueOf(o);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets string param.
     *
     * @param key the key
     * @return the string param
     */
    protected String getStringParam(String key) {
        return getStringParam(key, "");
    }

    /**
     * Gets long param.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the long param
     */
    protected long getLongParam(String key, long defaultValue) {
        if (StringUtils.isEmpty(key)) return defaultValue;

        Object o = findParam(key);
        if (!ObjectUtils.isEmpty(o)) {
            try {
                return (long) o;
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets long param.
     *
     * @param key the key
     * @return the long param
     */
    protected long getLongParam(String key) {
        return getLongParam(key, 0);
    }

    /**
     * Gets int param.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the int param
     */
    protected int getIntParam(String key, int defaultValue) {
        if (StringUtils.isEmpty(key)) return defaultValue;

        Object o = findParam(key);
        if (!ObjectUtils.isEmpty(o)) {
            try {
                return Integer.parseInt(String.valueOf(o));
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets int param.
     *
     * @param key the key
     * @return the int param
     */
    protected int getIntParam(String key) {
        return getIntParam(key, 0);
    }

    /**
     * Gets bool param.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the bool param
     */
    protected boolean getBoolParam(String key, boolean defaultValue) {
        if (StringUtils.isEmpty(key)) return defaultValue;
        Object o = findParam(key);
        if (!ObjectUtils.isEmpty(o)) {
            return BooleanUtils.toBoolean(String.valueOf(o));
        }
        return defaultValue;
    }

    /**
     * Gets bool param.
     *
     * @param key the key
     * @return the bool param
     */
    protected boolean getBoolParam(String key) {
        return getBoolParam(key, false);
    }

    /**
     * Find param object.
     *
     * @param key the key
     * @return the object
     */
    private Object findParam(String key) {
        if (StringUtils.isEmpty(key)) return null;

        Object o;

        o = request.getParameter(key);
        if (!ObjectUtils.isEmpty(o)) return o;

        o = request.getAttribute(key);
        if (!ObjectUtils.isEmpty(o)) return o;

        if (request.getContentType() != null && request.getContentType().equals(MediaType.APPLICATION_JSON.toString())) {
            try {
                String body = IOUtils.toString(request.getInputStream());
                return findBoodyMap(key, body);
//                if (StringUtils.isEmpty(body)) return null;
//                Map<String, Object> tmepMap = JsonUtil.toMap(body, String.class, Object.class);
//                return tmepMap.getOrDefault(key, null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * Find boody map object.
     *
     * @param key  the key
     * @param body the body
     * @return the object
     */
    private Object findBoodyMap(String key, String body) {
//        if (StringUtils.isEmpty(body)) return null;

        if (!StringUtils.isEmpty(body)) {
            bodyMap.clear();
            bodyMap = JsonUtils.toMap(body, String.class, Object.class);
        }
        return bodyMap.getOrDefault(key, null);
    }

}
