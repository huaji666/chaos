package chaos.core.utils;


import chaos.core.utils.object.ObjectUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 王健 on 2016-11-24.
 * qq:1413221142
 */
public class StringUtils {

    public static String[] getImgs(String content) {

        if (ObjectUtils.isEmpty(content)) return new String[]{};

        String img = "";
        Pattern p_image;
        Matcher m_image;
//        String str = "";
        String[] images = null;
        String regEx_img = "(<img.*src\\s*=\\s*(.*?)[^>]*?>)";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(content);
        ArrayList<String> list = new ArrayList<>();
        while (m_image.find()) {
            img = m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String tempSelected = m.group(1);
                list.add(tempSelected);
            }
        }
        if (!list.isEmpty()) return list.toArray(new String[list.size()]);
        return images;
    }
}
