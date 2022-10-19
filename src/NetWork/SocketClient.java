package NetWork;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SocketClient {
    private static Socket socket;

    /**
     * 请异步调用
     * @param msgArray 传向服务器的消息
     * @param strReturn 服务器随后返回的消息，通过传参赋值
     * @return 是否成功连接服务器
     */
    public static boolean SendMsgToServer(ArrayList<String> msgArray, ArrayList<String> strReturn) {
        try {
            //绑定服务器端IP与端口
            socket = new Socket("127.0.0.1", 8888);
            System.out.println("Connect Success.");

            //向服务器端发送消息
            OutputStream out = socket.getOutputStream();
            String msg=transformArrayToString(msgArray);
            out.write(msg.getBytes());
//            socket.shutdownOutput();

            //获取socket服务器端返回的消息
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            int lineNum=Integer.parseInt(reader.readLine());//get lineNum
            //read lines
            strReturn.clear();
            for(int i=0;i<lineNum-1;i++){
                strReturn.add(reader.readLine());
            }
            String endStr=reader.readLine();//last line
            //end communication
            socket.close();
//            System.out.println("接收到回复： " + strReturn);
            //check end
            if(!endStr.equals("MSG_END")){
                System.err.println("end: "+endStr);
                throw new SocketReceivedDataErrorException();
            }
        }catch (ConnectException e){
            System.err.println("ERROR: Fail To Connect.");
            return false;
        }catch (SocketReceivedDataErrorException e){
            System.err.println("ERROR: Received Data End Error.");
            return false;
        }catch (NumberFormatException e){
            System.err.println("ERROR: Received Data Head Error.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Send message by String
     * @param msg
     * @param strReturn
     * @return
     */
    public static boolean SendMsgToServer(String msg, ArrayList<String> strReturn) {
        msg = msg.replace("\r\n", "\n").replace("\r", "\n");
        if (!(msg.endsWith("\n"))) {
            msg += "\n";
        }
        String[] msgList = msg.split("\\n");//分行
        ArrayList<String> msgArray = new ArrayList<>(Arrays.asList(msgList));
        return SendMsgToServer(msgArray,strReturn);
    }

    /**
     * 把ArrayList转化成符合格式的String
     * @param msgArray
     * @return
     */
    private static String transformArrayToString(ArrayList<String> msgArray){
        String msg=msgArray.size()+1+"\n";//开头记录行数
        msg+=String.join("\n",msgArray);
        msg+="\nMSG_END\n";//结尾表示结束
        return msg;
    }

    public static void main(String[] args) {
        ArrayList<String> str=new ArrayList<>();
        ArrayList<String> strArray=new ArrayList<>();
        strArray.add("LOGIN");
        strArray.add("DeathWind");
        strArray.add("1919810");
        SocketClient.SendMsgToServer(strArray, str);
        System.out.println("Get: "+str);
        SocketClient.SendMsgToServer("LOGIN\nSanae\n0721", str);
        System.out.println("Get: "+str);
        SocketClient.SendMsgToServer("LOGIN\nBakaDontHavePassword", str);
        System.out.println("Get: "+str);
        SocketClient.SendMsgToServer("LOGIN\n中文测试\nhomo114514", str);
        System.out.println("Get: "+str);
        //
        String strToSend;
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNextLine()) {
            str.clear();
            strToSend = scanner.nextLine();
            SocketClient.SendMsgToServer(strToSend, str);
        }
    }
}
