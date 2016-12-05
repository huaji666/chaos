package chaos.core.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import chaos.core.model.BaseModel;

/**
 * 这个方法主要用了接受参数没有其他功能
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-11
 */
class ParamsModel extends BaseModel {
    /**
     * 短信验证码
     */
    protected String sCode;

    /**
     * 获取短信验证码
     *
     * @return
     */
    @JsonIgnore
    public String getsCode() {
        return sCode;
    }

    @JsonSetter
    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    /**
     * 图片验证码
     */
    protected String iCode;

    /**
     * 获取图片验证码
     *
     * @return
     */
    @JsonIgnore
    public String getiCode() {
        return iCode;
    }

    @JsonSetter
    public void setiCode(String iCode) {
        this.iCode = iCode;
    }
}
