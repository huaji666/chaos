package chaos.core.web.api.model;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-22
 */
public class ApiConfig {

    private String[] excludeParams = {};

    public String[] getExcludeParams() {
        return excludeParams;
    }

    /**
     * 需要全局忽略的字段
     *
     * @param excludeParams
     * @return
     */
    public ApiConfig setExcludeParams(String[] excludeParams) {
        this.excludeParams = excludeParams;
        return this;
    }
}
