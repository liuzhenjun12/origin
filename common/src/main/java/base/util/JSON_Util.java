package base.util;

import base.api.SocketCode;
import base.vo.ReturnJson;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JSON_Util {
    public static String setResult(ReturnJson json) {
        JSONObject pers = new JSONObject();
        pers.put("code", json.getCode());
        pers.put("sn",json.getSn());
        pers.put("date",json.getDete());
        pers.put("content",json.getContent());
        pers.put("openCode",json.getOpenCode());
        pers.put("psk",json.getPsk());
        return pers.toJSONString();
    }
    public static ReturnJson getResult(String info){
        JSONObject jsonObject = JSONObject.parseObject(info);
        String code=jsonObject.getString("code");
        String date=jsonObject.getString("date");
        String sn=jsonObject.getString("sn");
        String content=jsonObject.getString("content");
        String openCode=jsonObject.getString("openCode");
        String psk=jsonObject.getString("psk");
        return new ReturnJson(code,date,sn,content,openCode,psk);
    }
}
