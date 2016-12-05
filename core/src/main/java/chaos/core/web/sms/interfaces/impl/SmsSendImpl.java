package chaos.core.web.sms.interfaces.impl;

import chaos.core.web.constant.CommonKey;
import chaos.core.utils.json.JsonUtils;
import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.context.Properties;
import chaos.core.web.sms.interfaces.SmsSend;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-08
 */
public class SmsSendImpl implements SmsSend {

    /**
     * 发送实现
     *
     * @param phone
     * @param con
     * @return
     */
    @Override
    public boolean send(String phone, String con) {
        return false;
    }

    /**
     * 发送实现模板方式
     *
     * @param con
     * @return
     */
    @Override
    public boolean send(String... con) {
        if (!isSend) return false;
        if (_send(con)) return true;
        return false;
    }


    boolean isSend = Properties.sms.getBoolean(CommonKey.Propertie.sms.send, false);
    String url = Properties.sms.getString(CommonKey.Propertie.sms.url);

    /**
     * 接口测试
     *
     * @return
     */
    @Override
    public boolean testSend() {
        if (!isSend) return false;
        if (_send("14559", "15257171749", "123456")) return true;
        return false;
    }


    private boolean _send(String... con) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            url = MessageFormat.format(url, URLEncoder.encode(con[0], "utf-8"), URLEncoder.encode(con[1], "utf-8"), URLEncoder.encode(con[2], "utf-8"));

            HttpGet get = new HttpGet(url);

            CloseableHttpResponse response = httpclient.execute(get);
            //输出网页源码
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            Map<String, Object> map = JsonUtils.toMap(result, String.class, Object.class);
            int error_code = ObjectUtils.toInt(map.get("error_code"), -1);
            if (error_code == 0) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
