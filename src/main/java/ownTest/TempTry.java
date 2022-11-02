package ownTest;

import com.alibaba.fastjson2.JSON;
import object.Homeowner;
import object.Tenant;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson2.JSONObject.toJSONString;

public class TempTry {
    public static void main(String[] args) {
        boolean tb=true;
        String jsstr=toJSONString(tb);
        System.out.println(jsstr);

        List<Homeowner> l = new ArrayList<>();
        Homeowner homeowner1=new Homeowner("Lucy","999","Moon","17634341234");
        Homeowner homeowner2=new Homeowner("cy","99","oon","17634");
        l.add(homeowner1);
        l.add(homeowner2);
        jsstr= JSON.toJSONString(l);
        System.out.println(jsstr);
    }
}
