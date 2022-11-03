package service;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SheetManager", value = "/SheetManager")
public class ExpenseSheetServlet extends HttpServlet {
    //set price
    public static final double registerHousePrice=20.5;
    public static final double communicationPrice=20.5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: POST to SheetManager");
        response.setContentType("text/html;charset=utf-8");

    }
}
