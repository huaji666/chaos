package chaos.core.web.context;

import chaos.core.web.constant.CommonKey;
import chaos.core.web.ucenter.UCenter;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 暂时不用
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-03-21
 */
@Deprecated
public class CommonSessionHelper {

    private static CommonSessionHelper commonSessionHelper;

    public static void init(ApplicationContext context) {
        if (commonSessionHelper == null) commonSessionHelper = new CommonSessionHelper(context);
    }

    private ApplicationContext context;

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    //    private HttpServletRequest request;
//    private HttpServletResponse response;

    private CommonSessionHelper(ApplicationContext context) {
        this.context = context;
    }

    public static CommonSessionHelper getInstance() {
        Assert.notNull(commonSessionHelper, "commonSessionHelper 还没有初始化！");
        return commonSessionHelper;
    }

    public void login(UCenter userModel) {
        if (ObjectUtils.isEmpty(userModel) || ObjectUtils.isEmpty(userModel.getUcenterId())) return;
        getRequest().getSession().setAttribute(CommonKey.session.user, userModel);
    }


    public void logout() {
        getRequest().getSession().setAttribute(CommonKey.session.user, null);
    }


    public boolean isLogin() {
        UCenter uCenter = (UCenter) getRequest().getSession().getAttribute(CommonKey.session.user);
        if (uCenter == null) return false;
        return true;
    }


    public static void main(String[] args) {

    }
}
