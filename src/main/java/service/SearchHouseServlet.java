package service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.House;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * searchHouse: 查找房子，自己的或所有的。searchtype为"own"时返回id房主的所有房子；为"all"时返回num个所有可被检索的房子信息
 * registerHouse: 登记房子
 */
@WebServlet(name = "HouseManager", value = "/HouseManager")
public class SearchHouseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: POST to SearchHouse");
        String methodName = request.getParameter("function");
        //use reflection to judge which method to call
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            response.setStatus(404);
            throw new RuntimeException("ERROR: Failed To Call Method: " + methodName);
        }
    }

    private void searchHouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchType = request.getParameter("searchtype");
        ArrayList<House> houses = new ArrayList<>();//result

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

        String responseJSStr = JSON.toJSONString(houses);
        System.out.println("Response JS: " + responseJSStr);

        //response
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    private void requestHousePayment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
