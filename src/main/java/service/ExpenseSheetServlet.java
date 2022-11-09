package service;

import com.alibaba.fastjson2.JSON;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.CommunicationAuthority;
import object.ExpenseSheet;
import object.House;
import object.People;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * searchSheet: 通过id获取一个人的所有费用单
 * getPayView: 跳转到Sheet的支付界面pay.jsp
 * completePaySheet: 付完钱了，通过sheet信息让此Sheet标记成已支付并给予对应权限，然后从支付界面回到原界面
 */
@WebServlet(name = "SheetManager", value = "/SheetManager")
public class ExpenseSheetServlet extends HttpServlet {
    //set price
    public static final double registerHousePrice=20.5;
    public static final double communicationPrice=5.24;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---Servlet: POST to SheetManager---");
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

    private static void searchSheet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("searchSheet");
        ArrayList<ExpenseSheet> sheets = new ArrayList<>();//result

        String id = request.getParameter("id");
        //search
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();

        People people=new People(id);//-------temp--------
        boolean isSucceed = serviceToDaoInterface.getOwnExpenseSheets(people, sheets);

        //没有满足要求的房子
        if (sheets.size() == 0) {
            response.setStatus(404);
        }

        //response
        String responseJSStr = JSON.toJSONString(sheets);
        System.out.println("Response JS: " + responseJSStr);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    private static void getPayView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("getPayView");
        response.sendRedirect("pay.jsp");
    }

    private static void completePaySheet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("completePaySheet");

        String sheetID=request.getParameter("sheetid");
        boolean isHomeowner= Objects.equals(request.getParameter("ishomeowner"), "true");
        String payerID=request.getParameter("payerid");
        String houseID=request.getParameter("houseid");

        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        boolean isSucceed = serviceToDaoInterface.payExpenseSheet(sheetID);

        if(isSucceed){
            //解锁房屋或者增加交流许可
            if(isHomeowner){//-----------------bad
                ExpenseSheet expenseSheet=new ExpenseSheet(payerID,"",true,0,houseID);
                serviceToDaoInterface.unlockHouse(expenseSheet);
            }else{
                //通过houseID找到homeownerID
                House house=serviceToDaoInterface.getHouse(houseID);
                String homeownerID=house.getOwnerID();
                CommunicationAuthority communicationAuthority=new CommunicationAuthority(homeownerID,payerID,"",houseID);
                serviceToDaoInterface.addCommunicationAuthority(communicationAuthority);
            }

            //跳转
            response.sendRedirect("index.jsp");
        }else{
            response.setStatus(404);//付完钱却无法改变数据库，白付，自行联系客服吧（笑）
        }
    }
}
