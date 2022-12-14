package service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.ExpenseSheet;
import object.House;
import object.People;
import object.enums.HouseTypeEnum;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * searchHouse: 查找房子，自己的或所有的。searchtype为"own"时返回id房主的所有房子；为"all"时返回num个所有可被检索的房子信息。没找到就404
 * registerHouse: 登记房子
 * delHouse: 删除房子。houseid
 */
@WebServlet(name = "HouseManager", value = "/HouseManager")
public class HouseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---Servlet: POST to HouseManager---");
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

    private void searchHouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("searchHouse");
        ArrayList<House> houses = new ArrayList<>();//result

        String searchType = request.getParameter("searchtype");
        if (Objects.equals(searchType, "own")) {
            String id = request.getParameter("id");
            //search
            ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
            houses = serviceToDaoInterface.getOwnHouses(id);
        } else {
            //get num
            int num = 0;
            try {
                num = Integer.parseInt(request.getParameter("num"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //search
            ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
            houses = serviceToDaoInterface.getSomePayedHouses(num);
        }

        //没有满足要求的房子
        if(houses.size()==0){
            response.setStatus(404);
        }

        //response
        String responseJSStr = JSON.toJSONString(houses);
        System.out.println("Response JS: " + responseJSStr);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    private void registerHouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("registerHouse");
        JSONObject responseJS=new JSONObject();

        String ownerID=request.getParameter("ownerID");
        String address=request.getParameter("address");
        HouseTypeEnum houseType= HouseTypeEnum.judgeHouseTypeFromString(request.getParameter("houseType"));
        int maxTenantsNum= Integer.parseInt(request.getParameter("maxTenantsNum"));
        double monthlyRent= Double.parseDouble(request.getParameter("monthlyRent"));

        House house=new House(ownerID,address,houseType,maxTenantsNum,monthlyRent);

        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        boolean isSuccess = serviceToDaoInterface.addHouse(house);

        if (isSuccess) {
            System.out.println("Succeed.");
            responseJS.put("result", "true");

            //添加费用单
            People homeowner = serviceToDaoInterface.getPeople(ownerID,true);
            ExpenseSheet expenseSheet=new ExpenseSheet(ownerID,homeowner.getName(),true,ExpenseSheetServlet.registerHousePrice, house.getHouseID());
            serviceToDaoInterface.addExpenseSheet(expenseSheet);
        } else {
            System.out.println("Fail.");
            responseJS.put("result", "false");
        }

        //response
        String responseJSStr = JSON.toJSONString(responseJS);
        System.out.println("Response JS: " + responseJSStr);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    private void delHouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("delHouse");
        JSONObject responseJS=new JSONObject();

        String houseID=request.getParameter("houseid");
        //search
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();

        boolean isSuccess = serviceToDaoInterface.delHouse(houseID);

        responseJS.put("result", "true");

        //response
        String responseJSStr = JSON.toJSONString(responseJS);
        System.out.println("Response JS: " + responseJSStr);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }
}
