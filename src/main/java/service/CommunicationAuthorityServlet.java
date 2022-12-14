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
import java.util.*;

/**
 * addCommunication: 租赁者点击房屋申请新建交流许可
 * searchCommunication: 通过id找到该人的所有CA。注意，CA的isHomeownerModifyAvailable为false时，房主无法对它进行更改；为true时，租赁者无法对它进行更改。这须在前端有体现
 * modifyMessage 通过communicationid确定CA，提供time和place来修改对应值，通过ishomeowner确保拥有修改权限以保障安全。若无权限，则404
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
        } catch (NoSuchMethodException | IllegalAccessException e) {
            response.sendError(404,"Failed To Call Method: "+methodName);
            throw new RuntimeException("ERROR: Failed To Call Method: "+methodName);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        }
    }

    private void addCommunication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("addCommunication");

        String id=request.getParameter("id");
        String houseID=request.getParameter("houseid");

        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        People tenant = serviceToDaoInterface.getPeople(id,false);
        //添加费用单
        ExpenseSheet expenseSheet=new ExpenseSheet(id,tenant.getName(),false,ExpenseSheetServlet.communicationPrice,houseID);
        serviceToDaoInterface.addExpenseSheet(expenseSheet);
    }

    private void searchCommunication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("searchCommunication");
        ArrayList<CommunicationAuthority> communicationAuthorities = new ArrayList<>();//result

        String id = request.getParameter("id");
        boolean isHomeowner= Objects.equals(request.getParameter("ishomeowner"), "true");

        //search
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        communicationAuthorities = serviceToDaoInterface.getOwnCommunicationAuthorities(id,isHomeowner);

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
        System.out.println("modifyMessage");
        JSONObject responseJS=new JSONObject();

        String communicationID=request.getParameter("communicationid");
        boolean isHomeowner= Objects.equals(request.getParameter("ishomeowner"), "true");

        ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
        CommunicationAuthority communicationAuthority=serviceToDaoInterface.getCommunicationAuthority(communicationID);

        //判断权限
        if(isHomeowner != communicationAuthority.getIsHomeownerModifyAvailable()){
            response.setStatus(404);//无权修改
            responseJS.put("result", "false");
        }else{
            //修改内容（权限转换由modifyCommunicationAuthority自动完成）
            String appointedTime=request.getParameter("time");
            String appointedPlace=request.getParameter("place");
            serviceToDaoInterface.modifyCommunicationAuthority(communicationID,appointedTime,appointedPlace);

            responseJS.put("result", "true");
        }

        //response
        String responseJSStr= responseJS.toJSONString();
        System.out.println("Response JS: " + responseJSStr);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    private void confirmAgreement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("confirmAgreement");
        JSONObject responseJS=new JSONObject();

        String communicationID=request.getParameter("communicationid");

        ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
        CommunicationAuthority communicationAuthority=serviceToDaoInterface.getCommunicationAuthority(communicationID);
        if(communicationAuthority==null){
            response.setStatus(404);
            return;
        }
        //删除当前交流许可
        serviceToDaoInterface.delCommunicationAuthority(communicationID);

        //添加看房记录，将交流结果保存
        String payTime=getFormatCurrentTime();
        VisitRecord visitRecord=new VisitRecord(communicationAuthority,payTime);
        serviceToDaoInterface.addVisitRecord(visitRecord);

        responseJS.put("result", "true");

        //response
        String responseJSStr= responseJS.toJSONString();
        System.out.println("Response JS: " + responseJSStr);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    //tools
    private String getFormatCurrentTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }
}
