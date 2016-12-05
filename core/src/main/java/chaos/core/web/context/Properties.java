package chaos.core.web.context;


import chaos.core.utils.PropertiesUtils;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-05
 */
public interface Properties {

    PropertiesUtils mysql = PropertiesUtils.init("mysql.properties");
    PropertiesUtils file = PropertiesUtils.init("file.properties");
    PropertiesUtils email = PropertiesUtils.init("email.properties");
    PropertiesUtils sms = PropertiesUtils.init("sms.properties");
    PropertiesUtils kaptcha = PropertiesUtils.init("kaptcha.properties");
    PropertiesUtils im4java = PropertiesUtils.init("im4java.properties");
    PropertiesUtils wechat4j = PropertiesUtils.init("wechat4j.properties");
}
