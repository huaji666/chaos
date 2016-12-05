package chaos.core.web.model;

import chaos.core.utils.ConvertUtil;
import chaos.core.utils.object.ObjectUtils;

/**
 * Created by 王健 on 2016-11-26.
 * qq:1413221142
 */
public class SearchBST {

    private String newValue;
    private String field;

    public SearchBST() {
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getField() {
        String s = this.field;
        if (ObjectUtils.isEmpty(s)) return "";
        s = s.replace("Str", "");
        s = s.replace("All", "");
        return ConvertUtil.toTableStyle(s);
    }

    public void setField(String field) {
        this.field = field;
    }

//    public Map toMap() {
//        HashMap<String, String> map = new HashMap<>();
//        String s = ConvertUtil.toTableStyle(field);
//        map.put(s, newValue);
//        return map;
//    }
}
