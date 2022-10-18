package Service.NetWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    public static void turnOnServer() throws IOException {
        final ServerSocket server;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        //设定服务器端口，ip不设置表示默认的本机ip
        server = new ServerSocket(8888);
        //开始接受并处理消息，使用独立线程
        new Thread(() -> {
            while (true) {
                //阻塞，等待访问
                try {
                    Socket clientSocket = server.accept();
                    Runnable runnable = () -> {
                        try {
                            //获取socket客户端发送进来的消息
                            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                            String msg = br.readLine();
                            clientSocket.shutdownInput();
                            System.out.println("接收到： " + msg);

                            //返回消息给socket客户端（客户端通过阻塞来等待回复）
                            String msgToReturn = getMsgToReturn(msg);
                            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                            writer.println(msgToReturn);
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                    executorService.submit(runnable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static String getMsgToReturn(String msg) {
        String msgToReturn = MessageTranslator.handleMsg(msg);
        msgToReturn="aaa\n2124231\rwewewewe\r4333zzzz";
        if (!(msgToReturn.endsWith("\n") || msgToReturn.endsWith("\r"))) {
            msgToReturn += "\n";
        }
        msgToReturn+="MSG_END\n";//表示结束。可以添加序列号
        int lineNum = msgToReturn.split("\n\r|\n|\r").length;//计算行数
        msgToReturn = String.valueOf(lineNum) + "\n" + msgToReturn;//开头一行数字表示之后总共有多少行
        return msgToReturn;
    }

    public static void main(String[] args) {
        try {
            SocketServer.turnOnServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Turned On.");
    }
}
