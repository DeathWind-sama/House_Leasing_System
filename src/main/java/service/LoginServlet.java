package service;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import static service.LoginManager.login;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: GET to login");
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet: POST to login");
        try{
            PrintWriter pw = response.getWriter();

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.println("账号是：" + username);
            System.out.println("密码是：" + password);

//            boolean isLoginSuccess=login(username,password,true);
            boolean isLoginSuccess=true;

            if(isLoginSuccess){
                System.out.println("登陆成功");
                pw.write("success");
            }else{
                System.out.println("登陆失败");
                pw.write("fail");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
