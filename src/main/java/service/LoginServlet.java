package service;

import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.alibaba.fastjson2.JSONObject.toJSONString;

@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: GET to login");
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: POST to login");
        Map<String,String> responseMap=new HashMap<>();
        try{
            String id = request.getParameter("id");
            String password = request.getParameter("password");
            String identity = request.getParameter("identity");
            System.out.println("id：" + id);
            System.out.println("password：" + password);
            System.out.println("identity：" + identity);

            boolean isHomeowner= Objects.equals(identity, "homeowner");

            //login
            ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
            boolean isSuccess = serviceToDaoInterface.matchPeopleToLogin(id, password,isHomeowner);

            if(isSuccess){
                System.out.println("Login Succeed.");
                responseMap.put("result","true");
            }else{
                System.out.println("Login Fail.");
                responseMap.put("result","false");
            }

            String responseJSStr=toJSONString(responseMap);
            System.out.println("Response JS: " + responseJSStr);

            //response
            PrintWriter responseWriter = response.getWriter();
            responseWriter.write(responseJSStr);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
