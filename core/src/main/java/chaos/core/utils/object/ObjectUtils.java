package chaos.core.utils.object;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;

/**
 * ©
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-06-02
 */
public class ObjectUtils {

    /**
     * 有一个内容为空，即返回true
     * <pre>
     *     null = true
     *     "null" = true
     *     [,,a,b] = true
     *     [a,,b,c] = true
     * </pre>
     *
     * @param objects
     * @return
     */
    public static boolean isEmpty(Object... objects) {
        if (objects == null) return true;
        if (objects.length==0) return true;
        for (int i = 0; i < objects.length; i++) {
            Object object;
            try {
                object = objects[i];
                if (object instanceof CharSequence) if (object.equals("null")) return true;
            } catch (Exception ignored) {
                return true;
            }
            if (org.springframework.util.ObjectUtils.isEmpty(object)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        if (org.apache.commons.lang3.StringUtils.isNumeric(str)) return true;
        return false;
    }

    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    public static int toInt(Object obj, int def) {
        if (obj == null) return def;
        try {
            if (obj instanceof Integer) {
                def  = (Integer) obj;
            }else if (obj instanceof Long){
                def  = (int) obj;
            }else if (obj instanceof Short){
                def  = (int) obj;
            }else {
                def = NumberUtils.toInt(obj.toString(), def);
            }
        } catch (Exception e) {
            return def;
        }

        return def;
    }

    public static String toString(Object object, String def) {
        if (ObjectUtils.isEmpty(object)) return def;
        def = String.valueOf(object);
        if (def.equals("null")) def = "";
        return def;
    }

    public static String toString(Object object) {
        return toString(object, "");
    }

    /**
     * 任意符号分割字符串
     *　<pre>
     *     a b c = [a,b,c]
     *     a !@#$%^&*()_+bc = [a,bc]
     *     23 k abc !@#$%^&*()_ ss = [23,k,abc,ss]
     *</pre>
     * @param str
     * @return
     */
    public static String[] split(String str) {
        String[] def = {};
        StringBuilder sb = new StringBuilder();
        if (ObjectUtils.isEmpty(str)) return def;

        for (char c : str.toCharArray()) {
            if (isValidChar(c)) {
                sb.append(c);
            } else {
                if (sb.length() <= 0) continue;

                if (sb.codePointAt(sb.length() - 1) != "-".codePointAt(0)) {
                    sb.append("-");
                }
            }
        }
        def = sb.toString().split("-");
        return def;
    }

    /**
     * 有效字符串，非符号
     *
     * @param c
     * @return
     */
    public static boolean isValidChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        // 中文汉字
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
//                标点符号
//                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
//                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                ) {
            return true;
        }
        //字母和数字[0-9 a-Z]
        if (Character.isLetterOrDigit(c)) {
            return true;
        }
        return false;
    }


//    public static String[] split(String str, String regex) {
//        String[] def = {};
//
//
//        return def;
//    }

    public static void main(String[] args) {
//        for (char c : "123ssv1　S　S　SS345678!@#$$%%^%^^&*(*()_)_+9ha拉开距离那快递费就按了空间，。路口24346！@#￥%……&*（）——+".toCharArray()) {
//            System.out.println(c + " = " + isValidChar(c));
//        }


//        String[] temp = split("!!lakf hahah%%ah 3.2.3 理,论,考,试 卡空,间来看");
//        for (String s : temp) {
////            System.out.println(s);
//        }

        System.out.println(ObjectUtils.isEmpty("adfasf"));
        System.out.println(ObjectUtils.isEmpty(""));
        System.out.println(ObjectUtils.isEmpty("null"));
        System.out.println(ObjectUtils.isEmpty(new ArrayList<>()));
        String[] strings = new String[1];
        System.out.println(ObjectUtils.isEmpty((Object) strings));
        System.out.println(ObjectUtils.isEmpty((Object) new String[]{"ad"}));

    }
}
