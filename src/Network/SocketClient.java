package Network;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class SocketClient {
    private static Socket socket;

    /**
     * 请异步调用
     * @param msg 传向服务器的消息
     * @param strReturn 服务器随后返回的消息，通过传参赋值
     * @return 是否成功连接服务器
     */
    public static boolean SendMsgToServer(String msg,String strReturn) {
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
            strReturn=reader.readLine();
            System.out.println("接收到回复： " + strReturn);
            socket.close();
        }catch (ConnectException e){
            System.err.println("ERROR: Fail To Connect.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        String str="";
        SocketClient.SendMsgToServer("msg from client",str);
    }
}
