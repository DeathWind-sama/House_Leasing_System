package service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.Contract;
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
import java.util.Objects;

@WebServlet(name = "ContractManager", value = "/ContractManager")
public class ContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---Servlet: POST to ContractManager---");
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

    private void searchContract(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("searchContract");
        ArrayList<Contract> contracts = new ArrayList<>();//result

        String id = request.getParameter("id");
        boolean isHomeOwner= Objects.equals(request.getParameter("ishomeowner"), "true");

        //search
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        contracts = serviceToDaoInterface.getOwnContracts(id,isHomeOwner);

        //没有满足要求的合同
        if(contracts.size()==0){
            response.setStatus(404);
        }

        //response
        String responseJSStr = JSON.toJSONString(contracts);
        System.out.println("Response JS: " + responseJSStr);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }

    private void registerContract(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("registerContract");
        JSONObject responseJS=new JSONObject();

        String homeownerID=request.getParameter("homeownerid");
        String tenantID=request.getParameter("tenantid");
        String houseID=request.getParameter("houseid");
        double monthlyRent= Double.parseDouble(request.getParameter("monthlyrent"));

        Contract contract=new Contract(homeownerID,tenantID,monthlyRent,0,houseID);

        //search
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        boolean isSuccess = serviceToDaoInterface.addContract(contract);

        if (isSuccess) {
            System.out.println("Succeed.");
            responseJS.put("result", "true");
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
}
