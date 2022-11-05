package ownTest;

import com.alibaba.fastjson2.JSON;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.ExpenseSheet;
import object.Homeowner;
import object.People;
import object.Tenant;
import service.ExpenseSheetServlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.alibaba.fastjson2.JSONObject.toJSONString;

public class TempTry {
//    public static void main(String[] args) {
//        boolean tb=true;
//        String jsstr=toJSONString(tb);
//        System.out.println(jsstr);
//
//        List<Homeowner> l = new ArrayList<>();
//        Homeowner homeowner1=new Homeowner("Lucy","999","Moon","17634341234");
//        Homeowner homeowner2=new Homeowner("cy","99","oon","17634");
//        l.add(homeowner1);
//        l.add(homeowner2);
//        jsstr= JSON.toJSONString(l);
//        System.out.println(jsstr);
//    }

//    public static void main(String[] args) {
//        People people=new People();
//        tf(people);
//        System.out.println(people.getID());
//    }
//
//    static void tf(People people){
//        people=new People("123");
//    }

    public static void main(String[] args) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        System.out.println(dateNowStr);
    }
}
