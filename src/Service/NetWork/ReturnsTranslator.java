package Service.NetWork;

import Service.Enums.RequestEnum;

import java.util.ArrayList;

/**
 * transform the returns(request & objects) to string for sending them back to client
 */
public class ReturnsTranslator {
    //如果参数上遇到困难，可能直接删掉这个方法，把功能给整合进handleRequest
    public static ArrayList<String> translateReturnsToString(){//参数要多加考虑
        ArrayList<String> msgToReturn=new ArrayList<>();
        //待开发----------
        msgToReturn.add("a handled server message");
        return msgToReturn;
    }
}
