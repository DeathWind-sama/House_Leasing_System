package netWork;

import service.enums.RequestEnum;
import service.ServiceMainLogic;

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
    public static ArrayList<String> handleMsg(ArrayList<String> msg){
        RequestEnum request;
        try {
            request=translateMsgToRequest(msg);
        } catch (UnknownRequestException e) {
            String errorStr="ERROR: Unknown Request: "+msg.get(0);
            System.err.println(errorStr);
            ArrayList<String> es=new ArrayList<>();
            es.add(errorStr);
            return es;
        }
        return ServiceMainLogic.handleRequest(request,msg);
    }

    private static RequestEnum translateMsgToRequest(ArrayList<String> analysedMsg) throws UnknownRequestException{
        try {
            switch (analysedMsg.get(0)){
                case "LOGIN":
                    return RequestEnum.LOGIN;
            }
        }catch (ArrayIndexOutOfBoundsException ignored){}
        //Can find the corresponding request
        throw new UnknownRequestException("REQUEST: "+analysedMsg.get(0));
    }
}
