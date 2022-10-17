package Socket;

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
                    //输出
                    System.out.println("接收到： "+msg);
                    clientSocket.shutdownInput();

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //返回消息给socket客户端
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    writer.println(clientSocket.getPort() + " 服务器返回： " + msg);
                    writer.flush();

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //返回消息给socket客户端
                    writer.println(clientSocket.getPort() + " 服务器返回2： " + msg);
                    writer.flush();

                    //收到此消息退出socket服务器端
                    if (msg.equals("exit")) {
                        server.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            executorService.submit(runnable);
        }
    }

    public static void main(String[] args) throws IOException {
        SocketServer.turnOnServer();
    }
}
