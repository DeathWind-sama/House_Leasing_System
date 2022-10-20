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
                    service(socket);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void service(Socket socket) {
        String responseCode = "200 OK";
        String fun = "";
        String uri = "";
        String contentType = "";
        //接收
        try {
            socket.setSoTimeout(3000);//设置超时
            InputStream soIS = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(soIS));

            //读取请求行+头存入heads
            ArrayList<String> heads = new ArrayList<>();
            String lineStr;
            System.out.println("ClientBrowser:");
            while (!Objects.equals(lineStr = br.readLine(), "")) {
                heads.add(lineStr);
                System.out.println(socket.getPort() + "| " + lineStr);
            }
            System.out.println(heads + "\n"
                    + "------------------------------------------------------------------");

            //解析首行
            String[] firstLineParts=heads.get(0).split(" ");
            fun=firstLineParts[0];
            uri=firstLineParts[1];
            if (Objects.equals(uri, "/") || Objects.equals(uri, "")) {
                uri = "/index.html";
            }
            if (uri.contains("html")) {
                contentType = "text/html";
            } else {
                contentType = "application/octet-stream";
            }
        }catch (SocketTimeoutException e) {
            responseCode="408 Time Out";
            System.err.println("ERROR: Time Out. Maybe an empty HTTP request.");
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //回复
        try {
            OutputStream outSocket = socket.getOutputStream();
            switch (fun) {
                case "GET":
                    //寻找文件
                    FileInputStream fileIS = null;
                    try {
                        fileIS = new FileInputStream("d:/webroot" + uri);
                    } catch (FileNotFoundException e) {
                        responseCode = "404 Not Found";
                    }

                    //写响应头
                    String responseFirstLine = "HTTP/1.1 " + responseCode + "\r\n";
                    String responseHead = "Content-Type:" + contentType + "\r\n";
                    System.out.println("ServerResponse:\n" + responseFirstLine + "\n" + responseHead + "\n"
                            + "--------------------------------------------------------------------");
                    outSocket.write(responseFirstLine.getBytes());
                    outSocket.write(responseHead.getBytes());

                    //通过HTTP请求中的uri读取相应文件发送给客户端
                    if (!(fileIS == null)) {
                        outSocket.write("\r\n".getBytes());
                        byte[] fileBuffer = new byte[fileIS.available()];
                        int len;
                        System.out.println("writeHtml");
                        while ((len = fileIS.read(fileBuffer)) != -1) {
                            outSocket.write(fileBuffer, 0, len);
                        }
                    }
                    break;
                case "POST":

                    break;
            }
            outSocket.close();

            //结束通信
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
