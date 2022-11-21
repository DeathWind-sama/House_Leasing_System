<%--
  Created by IntelliJ IDEA.
  User: DeathWind
  Date: 2022/11/21
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>aimJSP</title>
</head>
<body>
  <p>
    <%
      String textContent = request.getParameter("id");
    %>
    <br/>
    <%=textContent%>

</body>
</html>
