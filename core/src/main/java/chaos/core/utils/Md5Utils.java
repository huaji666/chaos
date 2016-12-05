package chaos.core.utils;

import chaos.core.utils.object.ObjectUtils;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-09
 */
public class Md5Utils {
    /**
     *
     * @param str
     * @return ""
     */
    public static  String toMd5(String str){
        String def = "";
        if (ObjectUtils.isEmpty(str)) return def;
       return DigestUtils.md5Hex(str);
    }
}
