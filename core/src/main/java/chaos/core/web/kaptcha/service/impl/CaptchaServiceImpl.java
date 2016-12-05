package chaos.core.web.kaptcha.service.impl;

import chaos.core.web.kaptcha.service.CaptchaService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.context.CommonRequestHelper;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-05
 */

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private Producer captchaProducer;

    @Override
    public String getICode() {
        String capText = encodeBase64String(getCapText(CommonRequestHelper.getInstance().getRequest()));
        return capText;
    }

    @Override
    public byte[] getICodeImg() {
        return getCapText(CommonRequestHelper.getInstance().getRequest());
    }

    @Override
    public boolean isVerify(String str) {
        if (ObjectUtils.isEmpty(str)) return false;

        String code = (String) CommonRequestHelper.getInstance().getRequest().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (ObjectUtils.isEmpty(code)) return false;
        if (code.equals(str)) return true;
        return false;
    }

    @Override
    public void clear() {
        CommonRequestHelper.getInstance().getRequest().getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, "");
    }

    private byte[] getCapText(HttpServletRequest request) {
        byte[] bytes = new byte[0];
        String capText = captchaProducer.createText();
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ByteArrayOutputStream baos = null;
        baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", baos);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                    baos = null;
                }
            } catch (Exception e) {
                System.out.println("关闭文件流发生异常: " + e);
            }
        }
        return bytes;
    }


}
