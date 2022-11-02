package legacy.netWork;

import service.enums.RequestEnum;

import java.util.ArrayList;
import java.util.List;

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
        return handleRequest(request,msg);
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

    public static ArrayList<String> handleRequest(RequestEnum request, List<String> msg){
        ArrayList<String> msgToReturn=new ArrayList<>();
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
        //待开发----------
        msgToReturn.add("a handled server message");
        return msgToReturn;
    }
}
