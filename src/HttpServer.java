import NetWork.MessageTranslator;
import sun.security.util.Length;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

public class HttpServer {
    private static final Integer port = 8080;
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port:" + serverSocket.getLocalPort());
            //监听客户端请求
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("New Link with Client:" +
                        socket.getInetAddress() + ":" + socket.getPort());
                //并发处理HTTP客户端请求
                new Thread(() -> {
                    System.out.println("Serve: "+socket.getPort());
                    service(socket);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void service(Socket socket) {
        int clientPort= socket.getPort();

        String responseCode = "200 OK";
        String fun = "";
        String uri = "";
        String requestContentType = "";
        int requestContentLength=0;

        BufferedReader br = null;

        //接收
        try {
            socket.setSoTimeout(3000);//设置超时
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //读取请求行+头存入heads
            ArrayList<String> heads = new ArrayList<>();
            String lineStr;
            System.out.println("ClientBrowser:");
            //逐行读，直到遇到空行
            while (!Objects.equals(lineStr = br.readLine(), "")) {
                //Socket被中途关闭才会出现null，此时直接无视此次请求，退出处理
                if(lineStr==null){
                    System.err.println("ERROR: Socket Closed, Fail To Handle Request of "+socket.getInetAddress() + ":" + socket.getPort());
                    return;
                }
                //按行加入列表
                heads.add(lineStr);
                System.out.println(clientPort + "| " + lineStr);
            }
            System.out.println("\n"+"------------------------------------------------------------------");

            //解析首行
            String[] firstLineParts=heads.get(0).split(" ");
            fun=firstLineParts[0];
            uri=firstLineParts[1];
            System.out.println(clientPort + "| "+"FUN: "+fun);
            System.out.println(clientPort + "| "+"URI: "+uri);

            //解析head各项
            for(String s:heads) {
                //读取Content-Type
                if (s.startsWith("Content-Type:")) {
                    requestContentType = s.substring(14);//16 is the beginning index of number
                    System.out.println(clientPort + "| "+"Content-Type: "+requestContentType);
                }
                //读取contentLength
                if (s.startsWith("Content-Length:")) {
                    requestContentLength = Integer.parseInt(s.substring(16));//16 is the beginning index of number
                    System.out.println(clientPort + "| "+"Content-Length: "+requestContentLength);
                }
            }
        }catch (SocketTimeoutException e) {
            responseCode="408 Time Out";
            System.err.println("ERROR: Time Out. Maybe an empty HTTP request.");
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //处理与回复
        try {
            OutputStream outSocket = socket.getOutputStream();
            String ServerResponse;
            String contentType="";

            switch (fun) {
                case "GET":
                    //获取uri
                    if (Objects.equals(uri, "/") || Objects.equals(uri, "")) {
                        uri = "/index.html";
                    }
                    //判断需求类型
                    if (uri.contains("html")) {
                        contentType = "text/html";
                    } else {
                        contentType = "application/octet-stream";
                    }

                    //寻找文件
                    FileInputStream fileIS = null;
                    try {
                        fileIS = new FileInputStream("d:/webroot" + uri);
                    } catch (FileNotFoundException e) {
                        responseCode = "404 Not Found";
                    }

                    //写响应头
                    ServerResponse=getResponseHead(responseCode,contentType);
                    outSocket.write(ServerResponse.getBytes());
                    System.out.println("--------------------------------------------------------------------\n"
                            + "ServerResponse:\n" + ServerResponse
                            + "--------------------------------------------------------------------");

                    //通过uri读取相应文件以发送
                    if (!(fileIS == null)) {
                        byte[] fileBuffer = new byte[fileIS.available()];
                        int len;
                        System.out.println("writeHtml");
                        while ((len = fileIS.read(fileBuffer)) != -1) {
                            outSocket.write(fileBuffer, 0, len);
                        }
                    }
                    break;
                case "POST":
                    if(br==null){
                        return;
                    }
                    //读请求体
                    char[] buffer = new char[requestContentLength];
                    br.read(buffer);
                    String msg = new String(buffer);
                    System.out.println(clientPort + "| "+"MSG: "+msg);

                    //处理请求并获取回复信息
                    String msgToReturn="msg to return";

                    //回复
                    //写响应头
                    ServerResponse=getResponseHead(responseCode,contentType);
                    outSocket.write(ServerResponse.getBytes());
                    //写响应体
                    String body=msgToReturn+"\r\n";
                    outSocket.write((body).getBytes());
                    System.out.println("--------------------------------------------------------------------\n"
                            + "ServerResponse:\n" + ServerResponse + body
                            + "--------------------------------------------------------------------");
                    break;
            }
            outSocket.close();

            //结束通信
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getResponseHead(String responseCode,String requestContentType){
        String responseFirstLine = "HTTP/1.1 " + responseCode + "\r\n";
        String responseHead = "Content-Type:" + requestContentType + "\r\n";
        return responseFirstLine+responseHead+"\r\n";
    }
}


//中文乱码解决
//try {
//        System.out.println("------------uri: "+new String(uri.getBytes(StandardCharsets.ISO_8859_1),"GBK"));
//        } catch (UnsupportedEncodingException e) {
//        e.printStackTrace();
//        }


//接收
//获取HTTP请求头
//            inSocket = socket.getInputStream();
//                    int size = inSocket.available();
//                    byte[] buffer = new byte[1024];
//                    inSocket.read(buffer);
//                    String request = new String(buffer);
//                    System.out.println("ClientBrowser:\n" + request + "\n"
//                    + "------------------------------------------------------------------");
//                    String firstLineOfRequest = "";
//                    String[] heads;
//                    String fun = "";
//                    String uri = "";
//                    String contentType = "";
//                    if (request.length() > 1) {
//                    firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));
//                    heads = firstLineOfRequest.split(" ");
//                    fun = heads[0];
//                    uri = heads[1];
//                    if (Objects.equals(uri, "/") || Objects.equals(uri, "")) {
//                    uri = "/index.html";
//                    }
//                    if (uri.contains("html")) {
//                    contentType = "text/html";
//                    } else {
//                    contentType = "application/octet-stream";
//                    }
//                    }
