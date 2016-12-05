package chaos.core.web.constant;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-05
 */
public interface CommonMessage {


    interface upFile {
        String isEmpty = "200_没有要上传的文件！";
    }

    interface sms {
        String error_send = "100_发送失败！";
        String empty_type = "101_类型不能为空！";
        String empty_sCode = "102_短信验证码不能为空！";
        String error_verfy = "103_短信验证码错误！";
    }

    interface captcha {
        String error = "301_验证码错误！";
        String empty_iCode = "303_图片验证码不能为空！";
        String empty_sCode = "304_短信验证码不能为空！";
    }


    interface service {
        String error_insert = "501_保存失败！";
        String error_insert_or_update = "502_保存或者更新失败！";
        String error_update = "503_更新失败！";
    }

    interface controller {
        String empty_param = "801_参数不能为空";
    }
}
