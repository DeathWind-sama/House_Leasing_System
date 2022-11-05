package service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.CommunicationAuthority;
import object.ExpenseSheet;
import object.People;
import object.VisitRecord;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * searchCommunication: 通过ID找到该人的所有CA
 * confirmAgreement: 不论是谁，按下确定后调用，通过communicationid进行确认以生成看房记录
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

    private void modifyMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void confirmAgreement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("confirmAgreement");

        String communicationID=request.getParameter("communicationid");

        ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
        CommunicationAuthority communicationAuthority=serviceToDaoInterface.getCommunicationAuthority(communicationID);
        //删除当前交流许可
        serviceToDaoInterface.delCommunicationAuthority(communicationID);

        //添加看房记录，将交流结果保存
        String payTime=getFormatCurrentTime();
        VisitRecord visitRecord=new VisitRecord(communicationAuthority,payTime);
        serviceToDaoInterface.addVisitRecord(visitRecord);

        JSONObject responseJS=new JSONObject();
        responseJS.put("result", "true");
        String reponseJSStr= responseJS.toJSONString();

        //response
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(reponseJSStr);
    }

    //tools
    private String getFormatCurrentTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }
}
