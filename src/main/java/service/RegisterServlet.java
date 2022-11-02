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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.alibaba.fastjson2.JSONObject.toJSONString;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: POST to register");
        Map<String,String> responseMap=new HashMap<>();
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
