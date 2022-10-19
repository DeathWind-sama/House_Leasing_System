package Service.NetWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 调用turnOnServer以打开服务器。
 * 调用shutOffServer以关闭服务器。
 */
public class SocketServer {
    private static ServerSocket server;

    /**
     * 开启服务端。无需异步调用。
     * @throws IOException 开ServerSocket失败时弹出
     */
    public static void turnOnServer() throws IOException {
//         ServerSocket server;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        //设定服务器端口，ip不设置表示默认的本机ip
        server = new ServerSocket(8888);
        System.out.println("Server Turned On.");
        //开始接受并处理消息，使用独立线程
        new Thread(() -> {
            while (true) {
                //阻塞，等待访问
                try {
                    if(server==null){
                        break;
                    }
                    Socket clientSocket = server.accept();
                    Runnable runnable = () -> {
                        try {
                            //获取socket客户端发送进来的消息
                            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                            ArrayList<String> msg=new ArrayList<>();
                            String msgToReturn;
                            try {
                                int lineNum = Integer.parseInt(br.readLine());//get lineNum
                                //read lines
                                for (int i = 0; i < lineNum - 1; i++) {
                                    msg.add(br.readLine());
                                }
                                String endStr = br.readLine();//last line
//                            clientSocket.shutdownInput();
                                System.out.println("接收到： " + msg);
                                //ebd检测
                                if (!endStr.equals("MSG_END")) {
                                    System.err.println("end: " + endStr);
                                    throw new SocketReceivedDataErrorException();
                                }
                                msgToReturn = getMsgToReturn(msg);//处理消息并求取返回值
                            }catch (SocketReceivedDataErrorException e){
                                msgToReturn = "ERROR: Received Data Error.";
                                System.err.println(msgToReturn);
                            }catch (NumberFormatException e){
                                msgToReturn = "ERROR: Received Head Error.";
                                System.err.println(msgToReturn);
                            }

                            //返回消息给socket客户端（客户端通过阻塞来等待回复）
                            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                            writer.println(msgToReturn);
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                    executorService.submit(runnable);
                }catch (SocketException e){
                    if(e.getMessage().equals("socket closed")){
                        break;
                    }else{
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 关闭服务器
     * @return 是否成功关闭服务器
     */
    public static boolean shutOffServer(){
        if(server!=null){
            try {
                server.close();
                server=null;
                System.out.println("Shut Down Server Success.");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.err.println("ERROR: Shut Down Server Failed.");
        return false;
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
            turnOnServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        shutOffServer();
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
