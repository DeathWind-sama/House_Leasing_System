package Network;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private static Socket socket;

    public static void turnOnClient() {
        try {
            //绑定服务器端IP与端口并发送消息
            socket = new Socket("127.0.0.1", 8888);

            //向服务器端发送消息
            Scanner scan=new Scanner(System.in);
            String strIn="";
            if (scan.hasNextLine()) {
                strIn = scan.nextLine();
            }
            OutputStream out = socket.getOutputStream();
            out.write(strIn.getBytes());
            socket.shutdownOutput();

            //获取socket服务器端返回的消息
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("接收到回复： " + reader.readLine());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketClient.turnOnClient();
    }
}
