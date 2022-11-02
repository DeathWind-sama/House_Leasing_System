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
import java.util.ArrayList;
import java.util.Objects;

/**
 * searchtype为"own"时返回id房主的所有房子；为"all"时返回num个所有可被检索的房子信息
 */
@WebServlet(name = "SearchHouse", value = "/SearchHouse")
public class SearchHouseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: POST to SearchHouse");

        String searchType=request.getParameter("searchtype");
        ArrayList<House> houses=new ArrayList<>();//result

        if(Objects.equals(searchType, "own")){
            String id=request.getParameter("id");
            //search
            ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
            boolean isSuccess = serviceToDaoInterface.getOwnHouses(id, houses);
        }else{
            //get num
            int num= 0;
            try {
                num=Integer.parseInt(request.getParameter("num"));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            //search
            ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
            boolean isSuccess = serviceToDaoInterface.getSomePayedHouses(num, houses);
        }

        JSONArray responseJSArray = JSONArray.parseArray(JSONObject.toJSONString(houses));
        String responseJSStr=JSONObject.toJSONString(responseJSArray);
        System.out.println("Response JS: " + responseJSStr);

        //response
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }
}
