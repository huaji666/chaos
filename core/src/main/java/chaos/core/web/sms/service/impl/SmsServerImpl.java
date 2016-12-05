package chaos.core.web.sms.service.impl;

import chaos.core.model.CaseRes;
import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.constant.CommonKey;
import chaos.core.web.constant.CommonMessage;
import chaos.core.web.dao.SmsModelMapper;
import chaos.core.web.model.SmsModel;
import chaos.core.web.sms.interfaces.impl.SmsSendImpl;
import chaos.core.web.sms.service.SmsServer;
import chaos.core.web.context.CommonRequestHelper;
import chaos.core.web.sms.interfaces.SmsSend;
import chaos.core.web.ucenter.UCenter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-10
 */
@Service
public class SmsServerImpl implements SmsServer {

    @Autowired
    SmsModelMapper smsModelMapper;

    public void setSmsSend(SmsSend smsSend) {
        this.smsSend = smsSend;
    }

    private SmsSend smsSend = new SmsSendImpl();

    @Override
    public boolean sendSms(String... con) {
        return smsSend.send(con);
    }

    /**
     * 普通短信
     *
     * @param target
     * @param con
     * @return
     */
    @Override
    public boolean sendSms(UCenter target, String con) {
        SmsModel model = new SmsModel();
//        model.s
//        smsModelMapper.insert()
        return false;
    }

    /**
     * @param source
     * @param target
     * @param con
     * @return
     */
    @Override
    public CaseRes sendSms(UCenter source, UCenter target, String con) {
        return null;
    }

    /**
     * 获取验证码
     * @return
     */
    @Override
    public String getCode(int count) {
        String sCode = RandomStringUtils.randomNumeric(count);
        CommonRequestHelper.getInstance().getRequest().getSession().setAttribute(CommonKey.session.sCode, sCode);
        return sCode;
    }

    @Override
    public CaseRes isSmsCodeVerify(String sCode) {
        if (ObjectUtils.isEmpty(sCode)) {
            return new CaseRes(CommonMessage.sms.empty_sCode);
        }
        String code = String.valueOf(CommonRequestHelper.getInstance().getRequest().getSession().getAttribute(CommonKey.session.sCode));

        if (code.equals(sCode)) {
            return null;
        }
        return CaseRes.getInstance().setMessage(CommonMessage.sms.error_verfy);
    }

}
