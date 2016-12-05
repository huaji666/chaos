package chaos.core.web.controller;

import java.util.*;

/**
 * Created by tutu-ycb on 2016/9/21.
 * 作者：余陈斌
 * ©土土网
 */
public class TextData {


    public static TextData textData = new TextData();
    private List<Map<String, String>> list = new ArrayList<>();

    public static TextData getInstance() {
        return textData;
    }

    private TextData() {
        for (int i = 1; i < 22; i++) {
            Map<String, String> temp = new HashMap<>();
            temp.put("id", String.valueOf(i));
            temp.put("username", "企鹅啊" + i);
            temp.put("phone", "15236541254");
            temp.put("nickname", "小齐1");
            temp.put("sex", "0");
            temp.put("userType", "0");
            temp.put("email", "ydfihcxzfa@qq.com");
            temp.put("realname", "王博龙");
            list.add(temp);
        }
    }

    public List getList() {
        return list;
    }


    public List editAjax(int id, String field, String newVaule) {

        selectById(id).put(field, newVaule);
        return list;
    }

    public List addList(Map<String, String> temp) {

        int random = (int)(1+Math.random()*(1000000));
        for (Map<String, String> map : list) {
            if (map.get("id").equals(random + "")){
                random = (int)(1+Math.random()*(1000000));
            }
        }
        temp.put("id", String.valueOf(random));
        list.add(temp);
        return list;
    }

    public Map<String, String> selectById(int id) {

        for (Map<String, String> map : list) {
            if (!map.get("id").equals(id + "")) continue;
            return map;
        }
        return null;
    }

    public List delectById(int id) {
        list.remove(selectById(id));
        return list;
    }

}
