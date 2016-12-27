package chaos.core.model;


import chaos.core.utils.constant.UtilsConstant;
import chaos.core.utils.json.JsonUtils;
import chaos.core.utils.object.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ©chaos
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-04
 */
public class BaseModel {
    /**
     * @see JsonUtils#tojSON(Object)
     */
    public String toJson() {
        return JsonUtils.tojSON(this);
    }


    /**
     * @param json
     * @param cls
     * @return
     */
    protected List toList(String json, Class<?> cls) {
        return JsonUtils.toList(json, cls);
    }

    /**
     * @param json
     * @param key
     * @param value
     * @return
     */
    protected Map toMap(String json, Class<?> key, Class<?> value) {
        return JsonUtils.toMap(json, key, value);
    }

    /**
     * 时间转成时间戳
     *
     * @param data
     * @return
     */
    protected String toTimestamp(String data) {
        if (ObjectUtils.isEmpty(data)) {
            return "";
        }
        DateFormat format = new SimpleDateFormat(UtilsConstant.DateFormat.yyyy_MM_dd_HH_mm_ss);
        Date date = new Date();
        try {
            date = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data.trim();
    }

    protected String dataFormat(String timestamp) {
        return "";
    }
}
