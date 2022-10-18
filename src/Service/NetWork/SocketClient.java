package Service.NetWork;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class SocketClient {
    private static Socket socket;

    /**
     * 请异步调用
     * @param msg 传向服务器的消息
     * @param strReturn 服务器随后返回的消息，通过传参赋值
     * @return 是否成功连接服务器
     */
    public static boolean SendMsgToServer(String msg, ArrayList<String> strReturn) {
        try {
            //绑定服务器端IP与端口
            socket = new Socket("127.0.0.1", 8888);
            System.out.println("Connect Success.");

            //向服务器端发送消息
            OutputStream out = socket.getOutputStream();
            out.write(msg.getBytes());
            socket.shutdownOutput();

            //获取socket服务器端返回的消息
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            int lineNum=Integer.parseInt(reader.readLine());//get lineNum
            //read lines
            for(int i=0;i<lineNum-1;i++){
                strReturn.add(reader.readLine());
            }
            String endStr=reader.readLine();//last line
            //end communication
            socket.close();
            //check end
            System.out.println("接收到回复： " + strReturn);
            if(!endStr.equals("MSG_END")){
                throw new SocketReceivedDataErrorException();
            }
        }catch (ConnectException e){
            System.err.println("ERROR: Fail To Connect.");
            return false;
        }catch (SocketReceivedDataErrorException e){
            System.err.println("ERROR: Received Data Error.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayList<String> str=new ArrayList<>();
//        SocketClient.SendMsgToServer("dasd\n123sd\rttttt\n\rsda", str);
        String strToSend;
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNextLine()) {
            str.clear();
            strToSend = scanner.nextLine();
            SocketClient.SendMsgToServer(strToSend, str);
        }
    }
}
