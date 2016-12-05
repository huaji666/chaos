package chaos.core.web.context;

import chaos.core.utils.object.ObjectUtils;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-03-23
 */
public class CommonRequestHelper {

    final Logger log = Logger.getLogger(CommonRequestHelper.class);
    private ApplicationContext context;

    private static CommonRequestHelper requestHelper = null;

    private CommonRequestHelper(ApplicationContext context) {
        this.context = context;
    }

    public static CommonRequestHelper getInstance() {
        Assert.notNull(requestHelper, "RequestHelper 请先初始化....");
        return requestHelper;
    }

    public static CommonRequestHelper init(ApplicationContext context) {
        if (requestHelper == null) requestHelper = new CommonRequestHelper(context);
        return requestHelper;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }


    public String getIp() {
        HttpServletRequest request = getRequest();

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("PRoxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

//    public static String getIp_(HttpServletRequest request) {
//        String ip = request.getHeader("X-Forwarded-For");
//        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = ip.indexOf(",");
//            if (index != -1) {
//                return ip.substring(0, index);
//            } else {
//                return ip;
//            }
//        }
//        ip = request.getHeader("X-Real-IP");
//        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
//            return ip;
//        }
//        return request.getRemoteAddr();
//    }

    /**
     * 是否移动设备
     *
     * @return
     */
    public boolean isMobile() {
        UserAgent userAgent = UserAgent.parseUserAgentString(getRequest().getHeader("user-agent"));
        if (userAgent.getOperatingSystem().getDeviceType().equals(DeviceType.MOBILE)) return true;
        return false;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public String getDevice() {

        StringBuffer sb = new StringBuffer();
        UserAgent userAgent = UserAgent.parseUserAgentString(getRequest().getHeader("user-agent"));

        userAgent.getBrowser().getBrowserType().getName();
        userAgent.getOperatingSystem().getDeviceType();
        sb.append(userAgent.getOperatingSystem().getDeviceType().getName());
        sb.append("-");
        sb.append(userAgent.getOperatingSystem().getManufacturer().getName());
        sb.append("-");
        sb.append(userAgent.getBrowser().getBrowserType().getName());
        sb.append("-");
        sb.append(userAgent.getBrowserVersion());
        sb.append("-");
        sb.append(userAgent.getBrowser().getRenderingEngine().name());
        return sb.toString();
    }

    /**
     * 限制请求次数
     *
     * @param event
     * @param count
     * @return
     */
    public boolean stintCount(String event, int count) {

        if (ObjectUtils.isEmpty(event)) {
            log.error("event 不能为空！");
            return true;
        }
        int number = ObjectUtils.toInt(getRequest().getSession().getAttribute(event));
        if (number >= count) return true;
        getRequest().getSession().setAttribute(event, ++number);
        return false;
    }

    /**
     * 限制请求次数
     *
     * @param event
     * @return
     */
    public int stintCount(String event) {
        int number = ObjectUtils.toInt(getRequest().getSession().getAttribute(event));
        getRequest().getSession().setAttribute(event, ++number);
        return number;
    }
}
