package service;

import com.alibaba.fastjson2.JSON;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.ExpenseSheet;
import object.House;
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
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            response.sendError(404,"Failed To Call Method: "+methodName);
            throw new RuntimeException("ERROR: Failed To Call Method: " + methodName);
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
            boolean isSuccess = serviceToDaoInterface.getOwnHouses(id, houses);
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
            boolean isSuccess = serviceToDaoInterface.getSomePayedHouses(num, houses);
        }

        //没有满足要求的房子
        if(houses.size()==0){
            response.setStatus(404);
        }

        String responseJSStr = JSON.toJSONString(houses);
        System.out.println("Response JS: " + responseJSStr);

        //response
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    private void registerHouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("registerHouse");
        Map<String, String> responseMap = new HashMap<>();

        String houseID=request.getParameter("houseID");
        String ownerID=request.getParameter("ownerID");
        String address=request.getParameter("address");
        HouseTypeEnum houseType= HouseTypeEnum.judgeHouseTypeFromString(request.getParameter("houseType"));
        int maxTenantsNum= Integer.parseInt(request.getParameter("maxTenantsNum"));
        double monthlyRent= Double.parseDouble(request.getParameter("monthlyRent"));

        House house=new House(houseID,ownerID,false,false,address,houseType,maxTenantsNum,monthlyRent);

        //search
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        boolean isSuccess = serviceToDaoInterface.addHouse(house);

        if (isSuccess) {
            System.out.println("Succeed.");
            responseMap.put("result", "true");

            //添加费用单
            ExpenseSheet expenseSheet=new ExpenseSheet(ownerID,true,ExpenseSheetServlet.registerHousePrice,houseID);
            serviceToDaoInterface.addExpenseSheet(expenseSheet);
        } else {
            System.out.println("Fail.");
            responseMap.put("result", "false");
        }

        String responseJSStr = JSON.toJSONString(responseMap);
        System.out.println("Response JS: " + responseJSStr);

        //response
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }
}
