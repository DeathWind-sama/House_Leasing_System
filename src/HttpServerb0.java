import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpServerb0 {

    public static final String WEB_ROOT = "d:/webroot";
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;
    public static void main(String[] args) {
        HttpServerb0 server = new HttpServerb0();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

// Loop waiting for a request

        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

// create Request object and parse
                Request request = new Request(input);
                request.parse();

// create Response object
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

// Close the socket;
                socket.close();

// check if the revious URI is a shutdown command
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}

//

class Request {
    private InputStream input;
    private String uri;
    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
// Read a set of characters from the socket
        StringBuffer request = new StringBuffer(2018);
        int i;
        byte[] buffer = new byte[2048];
//        InputStreamReader isr = new InputStreamReader(is,"UTF-8");-----------
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }

        for (int j = 0; j < i; j ++) {
            request.append((char)buffer[j]);
        }

        System.out.println("-----------------request----------------");
        System.out.print(request.toString());
        uri = parseUri(request.toString());
    }

    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            return requestString.substring(index1 + 1, index2);
        }
        return "NULL";
    }

    public String getUri() {
        return uri;
    }
}

//

class Response {
    private static final int BUFFER_SIZE = 1024;
    private Request request;
    private OutputStream output;
    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        boolean errorFlag = true;
        if (request.getUri() != null) {
            File file = new File(HttpServerb0.WEB_ROOT, request.getUri());
            if (file.exists()) {
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
                errorFlag = false;
            }
        }
        if (errorFlag) {
// file not found
            String errorMessage = "HTTP/1.1 404 File NOT Fount\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "File Not Found";
            output.write(errorMessage.getBytes());
        }

        if (fis != null) {
            fis.close();
        }
    }
}

