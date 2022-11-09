package service;

import com.alibaba.fastjson2.JSONArray;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.People;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.alibaba.fastjson2.JSONObject.toJSONString;

@WebServlet(name = "PeopleMessage", value = "/PeopleMessage")
public class PeopleMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---Servlet: POST to PeopleMessage---");
        response.setContentType("text/html;charset=utf-8");

        boolean isHomeowner = Objects.equals(request.getParameter("identity"), "homeowner");
        String id = request.getParameter("id");

        ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
        People people=serviceToDaoInterface.getPeople(id, isHomeowner);

        if(people!=null){
            System.out.println("Succeed");

            String responseJSStr = toJSONString(people);
            System.out.println("Response JS: " + responseJSStr);

            //response
            PrintWriter responseWriter = response.getWriter();
            responseWriter.write(responseJSStr);
        }else{
            System.out.println("Fail");
            response.setStatus(404);
        }
    }
}
