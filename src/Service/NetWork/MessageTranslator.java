package Service.NetWork;

import Service.Enums.RequestEnum;
import Service.ServiceMainLogic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * transform the received string to request for handling it
 */
public class MessageTranslator {
    /**
     * analyse, transform into request enum and call request handler
     * @param msg the message from client
     * @return message to return to client
     */
    public static ArrayList<String> handleMsg(String msg){
        ArrayList<String> analysedMsg=analyseMsg(msg);
        RequestEnum request;
        try {
            request=translateMsgToRequest(analysedMsg);
        } catch (UnknownRequestException e) {
            String errorStr="ERROR: Unknown Request: "+msg;
            System.err.println(errorStr);
            ArrayList<String> es=new ArrayList<>();
            es.add("ERROR: Unknown Request: "+analysedMsg.get(0));
            return es;
        }
        return ServiceMainLogic.handleRequest(request);
    }

    /**
     * transform string into ArrayList<String>
     * @param msg msg in string
     * @return
     */
    private static ArrayList<String> analyseMsg(String msg){
        String[] analysedMsgArray=msg.split("\\n");
        return new ArrayList<>(Arrays.asList(analysedMsgArray));
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
