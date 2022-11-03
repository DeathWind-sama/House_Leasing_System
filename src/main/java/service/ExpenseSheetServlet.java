package service;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ExpenseSheetServlet", value = "/ExpenseSheetServlet")
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

    }
}
