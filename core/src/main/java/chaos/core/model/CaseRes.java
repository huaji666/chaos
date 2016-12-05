package chaos.core.model;

import chaos.core.utils.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(aupp)
 * 时间：2016-02-18
 */
public class CaseRes<E> {
    public CaseRes() {

    }
    /**
     *
     */
//    public CaseRes<E>() {
//
//    }

    /**
     * 分割code_msg
     *
     * @param message
     * @return
     */
    public <T extends CaseRes<E>> CaseRes<E> setMessage(String message) {
        if (StringUtils.isEmpty(message)) return this;
        if (!(message.indexOf("_") > 0)) {
            this.eMsg = message;
            return this;
        }
        String[] temp = {"0", ""};
        try {
            temp = message.split("_");
            if (!StringUtils.isNumeric(temp[0])) {//内容包含'_'直接返回。
                this.eMsg = message;
                return this;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.eCode = Integer.parseInt(temp[0]);
        this.eMsg = temp[1];
        return this;
    }


    /**
     * 返回数据
     *
     * @param data
     */
    public CaseRes(E data) {
        this.data = data;
    }

    /**
     * 异常消息
     *
     * @param eMsg
     */
    public CaseRes(String eMsg) {
        setMessage(eMsg);
    }

    /**
     * 异常消息和code
     *
     * @param eMsg
     * @param eCode
     */
    @Deprecated
    public CaseRes(String eMsg, int eCode) {
        this.eMsg = eMsg;
        this.eCode = eCode;
    }

    /**
     *
     */
    private E data;
    /**
     * 是否执行成功
     */
    private boolean success = true;
    /**
     * 错误码
     */
    private int eCode = 0;
    /**
     * 错误消息
     */
    private String eMsg;
    /**
     * 当前时间戳
     */
    public long time = System.currentTimeMillis();


    public int geteCode() {
        return eCode;
    }

    public String geteMsg() {
        return eMsg;
    }

    public CaseRes seteMsg(String eMsg) {
        return setMessage(eMsg);
//        this.eMsg = eMsg;
//        return this;
    }

    public CaseRes seteCode(int eCode) {
        this.eCode = eCode;
        return this;
    }

    /**
     * 执行正常
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        if (geteCode() != 0 || !StringUtils.isEmpty(geteMsg())) {
            success = false;
        }
        return success;
    }

    public CaseRes setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public E getData() {
        return data;
    }

    public CaseRes<E> setData(E data) {
        this.data = data;
        return this;
    }

    public static CaseRes getInstance() {
        return new CaseRes<Object>();
    }

    public String toJson() {
        return JsonUtils.tojSON(this);
    }
}
