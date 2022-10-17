package Service.NetWork;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    public static void turnOnServer() throws IOException {
        ServerSocket server;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        //设定服务器端口，ip不设置表示默认的本机ip
        server = new ServerSocket(8888);
        while (true) {
            //阻塞，等待访问
            Socket clientSocket = server.accept();
            Runnable runnable = () -> {
                try {
                    //获取socket客户端发送进来的消息
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                    String msg = br.readLine();
//                    br.close();
                    clientSocket.shutdownInput();
                    System.out.println("接收到： "+msg);

                    //返回消息给socket客户端（客户端通过阻塞来等待回复）
                    String msgToReturn=getMsgToReturn(msg);
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    writer.println(msgToReturn);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            executorService.submit(runnable);
        }
    }

    private static String getMsgToReturn(String msg){
        String msgToReturn=MessageTranslator.handleMsg(msg);
        //处理结果使其符合传输规范
        return msgToReturn;
    }

    public static void main(String[] args) throws IOException {
        SocketServer.turnOnServer();
    }
}
