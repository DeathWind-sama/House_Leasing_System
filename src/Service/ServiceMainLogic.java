package Service;

import Dao.ServiceToDaoInterface;
import Service.Enums.RequestEnum;
import Service.NetWork.ReturnsTranslator;

import java.util.ArrayList;

public class ServiceMainLogic {
    public static ServiceToDaoInterface serviceToDaoInterface;//接口入口，在整个项目的主逻辑里赋值

    public static ArrayList<String> handleRequest(RequestEnum request){
        switch (request){
            case LOGIN:
                System.out.println("Login request received.");
        }
        return ReturnsTranslator.translateReturnsToString();
    }
}
