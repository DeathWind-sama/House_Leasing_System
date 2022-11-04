package service;

import com.alibaba.fastjson2.JSON;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.CommunicationAuthority;
import object.ExpenseSheet;
import object.People;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * searchCommunication: 通过ID找到该人的所有CA
 */
@WebServlet(name = "CommunicationManager", value = "/CommunicationManager")
public class CommunicationAuthorityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---Servlet: POST to CommunicationManager---");
        response.setContentType("text/html;charset=utf-8");

        String methodName = request.getParameter("function");
        //use reflection to judge which method to call
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            response.sendError(404,"Failed To Call Method: "+methodName);
            throw new RuntimeException("ERROR: Failed To Call Method: "+methodName);
        }
    }

    private void searchCommunication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("searchCommunication");
        ArrayList<CommunicationAuthority> communicationAuthorities = new ArrayList<>();//result

        String id = request.getParameter("id");

        //search
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        boolean isSucceed = serviceToDaoInterface.getOwnCommunicationAuthorities(id, communicationAuthorities);

        //没有满足要求的房子
        if (communicationAuthorities.size() == 0) {
            response.setStatus(404);
        }

        String responseJSStr = JSON.toJSONString(communicationAuthorities);
        System.out.println("Response JS: " + responseJSStr);

        //response
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

}
