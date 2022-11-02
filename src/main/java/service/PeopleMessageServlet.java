package service;

import com.alibaba.fastjson2.JSONArray;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "PeopleMessage", value = "/PeopleMessage")
public class PeopleMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: POST to PeopleMessage");
        Map<String, String> responseMap = new HashMap<>();
        JSONArray responseJSArray = new JSONArray();

        String identity = request.getParameter("identity");
        String id = request.getParameter("id");

    }
}
