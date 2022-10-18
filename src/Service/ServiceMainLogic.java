package Service;

import Dao.ServiceToDaoInterface;
import Service.Enums.RequestEnum;
import Service.NetWork.ReturnsTranslator;

import java.util.ArrayList;
import java.util.List;

public class ServiceMainLogic {
    public static ServiceToDaoInterface serviceToDaoInterface;//接口入口，在整个项目的主逻辑里赋值

    public static ArrayList<String> handleRequest(RequestEnum request, List<String> msg){
        try {
            switch (request) {
                case LOGIN:
                    System.out.println("Login request received.\n\tAccount: " + msg.get(1)+"\n\tPassword: " + msg.get(2));
                    break;
            }
        }catch (IndexOutOfBoundsException e){
            String errorStr="ERROR: Format Error: "+msg.get(0);
            System.err.println(errorStr);
            ArrayList<String> es=new ArrayList<>();
            es.add(errorStr);
            return es;
        }
        return ReturnsTranslator.translateReturnsToString();
    }
}
