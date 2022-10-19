import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
            InputStream inSocket;
            try {
                //接收
                //获取HTTP请求头
                inSocket = socket.getInputStream();
                int size = inSocket.available();
                byte[] buffer = new byte[1024];
                inSocket.read(buffer);
                String request = new String(buffer);
                System.out.println("ClientBrowser:\n"+request+"\n"
                        + "------------------------------------------------------------------");
                String firstLineOfRequest = "";
                String[] heads;
                String fun="";
                String uri="";
                String contentType ="";
                if(request.length() > 1){
                    firstLineOfRequest = request.substring(0,request.indexOf("\r\n"));
                    heads = firstLineOfRequest.split(" ");
                    fun=heads[0];
                    uri = heads[1];
                    if(Objects.equals(uri, "/") || Objects.equals(uri, "")){
                        uri = "/index.html";
                    }
                    if(uri.contains("html")){
                        contentType = "text/html";
                    }else{
                        contentType = "application/octet-stream";
                    }
                }

                //回复
                OutputStream outSocket = socket.getOutputStream();
                switch (fun) {
                    case "GET":
                        String responseCode = "200 OK";
                        //寻找文件
                        FileInputStream fileIS = null;
                        try {
                            fileIS = new FileInputStream("d:/webroot" + uri);
                        } catch (FileNotFoundException e) {
                            responseCode = "404 Not Found";
                        }

                        //响应头
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
                        //handle-------------
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
