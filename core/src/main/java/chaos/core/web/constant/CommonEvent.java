package chaos.core.web.constant;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-12
 */
public interface CommonEvent {

    interface user {
        String login = "登陆";
        String logout = "退出";
        String register = "注册";
        String resetPass = "重置密码";

        String delete = "删除用户";
        String passErrorNumber = "密码错误次数";
    }

    interface sms{
        String sendNumber ="短信发送";
    }

}
