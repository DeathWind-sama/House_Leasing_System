package service;

import com.alibaba.fastjson2.JSON;
import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.House;
import object.VisitRecord;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 通过id获取一个人的所有看房记录。无记录则404
 */
@WebServlet(name = "VisitRecordManager", value = "/VisitRecordManager")
public class VisitRecordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---Servlet: POST to CommunicationManager---");
        response.setContentType("text/html;charset=utf-8");

        ArrayList<VisitRecord> visitRecords = new ArrayList<>();//result

        String id = request.getParameter("id");
        //search
        ServiceToDaoInterface serviceToDaoInterface = new ServiceToDaoRealization();
        visitRecords = serviceToDaoInterface.getOwnVisitRecords(id);

        //没有满足要求的看房记录
        if (visitRecords.size() == 0) {
            response.setStatus(404);
        }

        //response
        String responseJSStr = JSON.toJSONString(visitRecords);
        System.out.println("Response JS: " + responseJSStr);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(responseJSStr);
    }
}
