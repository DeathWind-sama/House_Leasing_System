import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class HttpServer {
    private static final Integer port = 8080;//HTTP默认端口80
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            //建立服务器Socket,监听客户端请求
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port:" + serverSocket.getLocalPort());
            //死循环不间断监听客户端请求
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("biuld a new tcp link with client,the cient address:" +
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
                String uri="";
                String contentType ="";
                if(request.length() > 0){
                    firstLineOfRequest = request.substring(0,request.indexOf("\r\n"));
                    heads = firstLineOfRequest.split(" ");
                    uri = heads[1];
                    if(Objects.equals(uri, "/") || Objects.equals(uri, "")){
                        uri = "/index.html";
                    }
                    System.out.println("-------------uri: "+uri);
                    if(uri.contains("html")){
                        contentType = "text/html";
                    }else{
                        contentType = "application/octet-stream";
                    }
                }

                //回复
                OutputStream outSocket = socket.getOutputStream();
                //将响应头发送给客户端
                String responseFirstLine = "HTTP/1.1 200 OK\r\n";
                String responseHead = "Content-Type:" + contentType +"\r\n";
                System.out.println("ServerResponse:\n"+responseFirstLine+"\n"+responseHead+"\n"
                        + "--------------------------------------------------------------------");
                outSocket.write(responseFirstLine.getBytes());
                outSocket.write(responseHead.getBytes());

                //通过HTTP请求中的uri读取相应文件发送给客户端
                FileInputStream writehtml = new FileInputStream("d:/webroot"+uri);
                outSocket.write("\r\n".getBytes());
                byte[] htmlbuffer = new byte[writehtml.available()];
                int len = 0;
                System.out.println("writeHtml");
                while((len = writehtml.read(htmlbuffer)) != -1){
                    outSocket.write(htmlbuffer, 0,len);
                }
                outSocket.close();

                //结束通信
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}