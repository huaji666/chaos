package chaos.core.web.context;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-04-08
 */
public class CommonsMultiparResolver extends CommonsMultipartResolver {
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        //过滤富文本文件上传
        if (request.getRequestURL().toString().contains("ueditor/upLoad")) {
            return false;
        }
        return super.isMultipart(request);
    }
}
