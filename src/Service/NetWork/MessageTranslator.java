package Service.NetWork;

import Service.Enums.RequestEnum;
import Service.ServiceMainLogic;

import java.util.ArrayList;

/**
 * transform the received string to request for handling it
 */
public class MessageTranslator {
    /**
     * analyse, transform into request enum and call request handler
     * @param msg the message from client
     * @return message to return to client
     */
    public static String handleMsg(String msg){
        ArrayList<String> analysedMsg=analyseMsg(msg);
        RequestEnum request;
        try {
            request=translateMsgToRequest(analysedMsg);
        } catch (UnknownRequestException e) {
            String errorStr="ERROR: Unknown Request: "+msg;
            System.err.println(errorStr);
            return errorStr;
        }
        return ServiceMainLogic.handleRequest(request);
    }

    private static ArrayList<String> analyseMsg(String msg){
        ArrayList<String> analysedMsg=new ArrayList<>();
        analysedMsg.add(msg);
        //待开发------------
        return analysedMsg;
    }

    private static RequestEnum translateMsgToRequest(ArrayList<String> analysedMsg) throws UnknownRequestException{
        try {
            if(analysedMsg.get(0).equals("LOGIN")){
                return RequestEnum.LOGIN;
            }
        }catch (ArrayIndexOutOfBoundsException ignored){}
        //Can find the corresponding request
        throw new UnknownRequestException("REQUEST: "+analysedMsg.get(0));
    }
}
