import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final int port = 8080;
    private static final String printDivideMark="----------------------------------------------------------------------------";

    private static boolean isWorking=false;

    public static void main(String[] args) {
        turnOnHttpServer();
    }

    /**
     * 若想异步监听请求，请异步调用
     */
    public static void turnOnHttpServer(){
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            isWorking=true;
            System.out.println("Server is running on port:" + serverSocket.getLocalPort());
            ExecutorService serviceThreadPool = Executors.newFixedThreadPool(100);//线程池管理
            //监听客户端请求
            while (isWorking) {
                final Socket socket = serverSocket.accept();
                System.out.println("New Link with Client:" +
                        socket.getInetAddress() + ":" + socket.getPort());
                //并发处理HTTP客户端请求
                Thread t= new Thread(() -> {
                    service(socket);
                });
                serviceThreadPool.submit(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void service(Socket socket) {
        InetAddress clientAddress=socket.getInetAddress();
        int clientPort= socket.getPort();
        String clientMark=clientAddress+":"+clientPort;

        String responseCode = "200 OK";
        String fun = "";
        String uri = "";
        String requestContentType = "";
        int requestContentLength=0;

        BufferedReader br = null;

        System.out.println("Start to Serve: "+clientMark);

        //
        //接收
        try {
            socket.setSoTimeout(3000);//设置超时
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //读取请求行+头存入heads
            ArrayList<String> heads = new ArrayList<>();
            String lineStr;
            //逐行读，直到遇到空行
            while (!Objects.equals(lineStr = br.readLine(), "")) {
                //Socket被中途关闭才会出现null，此时直接无视此次请求，退出处理
                if(lineStr==null){
                    System.err.println("ERROR: Socket Closed, Fail To Handle Request of "+socket.getInetAddress() + ":" + socket.getPort());
                    return;
                }
                //按行加入列表
                heads.add(lineStr);
            }
            System.out.println(printDivideMark+"\n"
                    +"Client "+clientMark+ " Request Head:\n"
                    +"\t"+String.join("\n\t",heads)+"\n"
                    +printDivideMark);

            //解析首行
            String[] firstLineParts=heads.get(0).split(" ");
            fun=firstLineParts[0];
            uri=firstLineParts[1];
            System.out.println(clientMark + " | "+"FUN: "+fun);
            System.out.println(clientMark + " | "+"URI: "+uri);

            //解析head各项
            for(String s:heads) {
                //读取Content-Type
                if (s.startsWith("Content-Type:")) {
                    requestContentType = s.substring(14);//16 is the beginning index of number
                    System.out.println(clientMark + " | "+"Content-Type: "+requestContentType);
                }
                //读取contentLength
                if (s.startsWith("Content-Length:")) {
                    requestContentLength = Integer.parseInt(s.substring(16));//16 is the beginning index of number
                    System.out.println(clientMark + " | "+"Content-Length: "+requestContentLength);
                }
            }
        }catch (SocketTimeoutException e) {
            responseCode="408 Time Out";
            System.err.println("ERROR: "+clientMark+ " Time Out. Maybe an empty HTTP request.");
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
        //处理与回复
        try {
            OutputStream soOS = socket.getOutputStream();
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
                    soOS.write(ServerResponse.getBytes());
                    System.out.println(printDivideMark+"\n"
                            + "ServerResponse to "+clientMark + ":\n" + ServerResponse
                            + printDivideMark);

                    //通过uri读取相应文件以发送
                    if (!(fileIS == null)) {
                        byte[] fileBuffer = new byte[fileIS.available()];
                        int len;
                        System.out.println("File \""+ uri +"\" Sending...");
                        while ((len = fileIS.read(fileBuffer)) != -1) {
                            soOS.write(fileBuffer, 0, len);
                        }
                        System.out.println("File \""+ uri +"\" Sending Complete.");
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
                    soOS.write(ServerResponse.getBytes());
                    //写响应体
                    String body=msgToReturn+"\r\n";
                    soOS.write((body).getBytes());
                    System.out.println(printDivideMark+"\n"
                            + "ServerResponse to "+clientMark + ":\n" + ServerResponse + body
                            + printDivideMark);
                    break;
            }
            soOS.close();

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
