package service;

import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.Homeowner;
import object.People;
import object.Tenant;
import object.enums.GenderEnum;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.alibaba.fastjson2.JSONObject.toJSONString;

@WebServlet(name = "LoginManager", value = "/LoginManager")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: GET to login");
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: POST to LoginManager");
        String methodName = request.getParameter("function");
        //use reflection to judge which method to call
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            throw new RuntimeException("ERROR: Failed To Call Method: "+methodName);
        }
        login(request,response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("Login");
        Map<String, String> responseMap = new HashMap<>();
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String identity = request.getParameter("identity");
        System.out.println("id：" + id);
        System.out.println("password：" + password);
        System.out.println("identity：" + identity);

        boolean isHomeowner = Objects.equals(identity, "homeowner");

        //login
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        boolean isSuccess = serviceToDaoInterface.matchPeopleToLogin(id, password, isHomeowner);

        if (isSuccess) {
            System.out.println("Login Succeed.");
            responseMap.put("result", "true");
        } else {
            System.out.println("Login Fail.");
            responseMap.put("result", "false");
        }

        String responseJSStr = toJSONString(responseMap);
        System.out.println("Response JS: " + responseJSStr);

        //response
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("Register");
        Map<String,String> responseMap=new HashMap<>();//?----
        try{
            //get identity
            String identity = request.getParameter("identity");
            boolean isHomeowner= Objects.equals(identity, "homeowner");
            //create certain object
            People people;
            if(isHomeowner){
                String name = request.getParameter("name");
                String id = request.getParameter("id");
                String address = request.getParameter("address");
                String telNumber=request.getParameter("tel");
                people=new Homeowner(name,id,address,telNumber);
            }else{
                String name = request.getParameter("name");
                String id = request.getParameter("id");
                String address = request.getParameter("address");
                String telNumber=request.getParameter("tel");
                String birthday=request.getParameter("birthday");
                String genderStr=request.getParameter("gender");

                people=new Tenant(name,id,address,telNumber,birthday, GenderEnum.judgeGenderFromString(genderStr));
            }
            String password = request.getParameter("password");

            System.out.println("identity：" + identity);
            System.out.println("People：" + people);
            System.out.println("password：" + password);

            //register
            ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
            boolean isSuccess = serviceToDaoInterface.registerPeople(people, password);

            if(isSuccess){
                System.out.println("Register Succeed.");
                responseMap.put("result","true");
            }else{
                System.out.println("Register Fail.");
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
