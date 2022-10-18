package Service.NetWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
                            ArrayList<String> msg=new ArrayList<>();
                            int lineNum=Integer.parseInt(br.readLine());//get lineNum
                            //read lines
                            for(int i=0;i<lineNum-1;i++){
                                msg.add(br.readLine());
                            }
                            String endStr=br.readLine();//last line
//                            clientSocket.shutdownInput();
                            System.out.println("接收到： " + msg);
                            if(!endStr.equals("MSG_END")){
                                System.err.println("end: "+endStr);
                                throw new SocketReceivedDataErrorException();
                            }

                            //返回消息给socket客户端（客户端通过阻塞来等待回复）
                            String msgToReturn = getMsgToReturn(msg);
                            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                            writer.println(msgToReturn);
                            writer.flush();
                            writer.close();
                        }catch (SocketReceivedDataErrorException e){
                            String msgToReturn = "ERROR: Received Data Error.";
                            System.err.println(msgToReturn);
                            PrintWriter writer = null;
                            try {
                                writer = new PrintWriter(clientSocket.getOutputStream());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
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

    private static String getMsgToReturn(ArrayList<String> msg) {
        ArrayList<String> msgToReturnArray = MessageTranslator.handleMsg(msg);
        //处理msg使其符合传输格式
        StringBuilder msgToReturn = new StringBuilder(msgToReturnArray.size()+1 + "\n");//开头一行数字表示之后总共有多少行
        for(String s:msgToReturnArray){
            msgToReturn.append(s).append("\n");
        }
        msgToReturn.append("MSG_END\n");//表示结束。可以标注序列号
        return msgToReturn.toString();
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

//        msgToReturn=msgToReturn.replace("\r\n","\n").replace("\r","\n");
//        if (!(msgToReturn.endsWith("\n"))) {
//        msgToReturn += "\n";
//        }
//        System.out.println("msgToReturn: "+msgToReturn);
//        msgToReturn+="MSG_END\n";//表示结束。可以添加序列号
//        String[] msgToReturnArray=msgToReturn.split("\\n");//分行
//        System.out.println("TEST: " + Arrays.toString(msgToReturnArray));
//        int lineNum = msgToReturnArray.length;//计算行数
//        msgToReturn = String.valueOf(lineNum) + "\n" + msgToReturn;//开头一行数字表示之后总共有多少行
