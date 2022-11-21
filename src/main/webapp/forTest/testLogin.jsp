<%@ page language="java" contentType="text/html; ISO-8859-1html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>房屋租赁系统</title>
<%--  <link rel="stylesheet" type="text/css" href="css/styles.css">--%>
  <br><br>
  <table id="idtable"></table>
</head>
<body>
<div class="wrapper">

  <div class="container">
    <h1>房屋租赁系统</h1>
      <div><input id="id" type="text" name="id" placeholder="用户名" ></div>
      <div><input id="password" type="password" name="password" placeholder="密码" ></div>
      <div>
        <label class="radio inline">
          <input id="homeowner" type="radio" name="identity" value="homeowner"  checked/> 房主
        </label>
        <label class="radio inline">
          <input id="tenant" type="radio" name="identity" value="tenant" /> 租客
        </label>
      </div>
      <button type="button"  onclick="loginfun('login')">登录</button>
      <button type="button" name="function" value="register" onclick="loginfun('register')">注册</button>

  </div>

  <ul class="bg-bubbles">
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
  </ul>

</div>
<script language=javascript>
  function loginfun(fun) {
    var id=document.getElementById("id").value;
    var password=document.getElementById("password").value;
    var identity="";
    var radio = document.getElementsByName("identity");
    for (var i=0; i<radio.length; i++) {
      if (radio[i].checked) {
        alert(radio[i].value)
        identity=radio[i].value;
      }
    }

    var xhttp;
    if (window.XMLHttpRequest) {
      xhttp = new XMLHttpRequest();
    } else {
      // code for IE6, IE5
      xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xhttp.open("POST", "http://localhost:8080/HLS/LoginManager");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id="+id+"&password="+password+"&identity="+identity+"&function="+fun);

    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        alert(this.responseText);
        var obj = JSON.parse(this.responseText);
        document.getElementById("idtable").innerHTML = obj.result;
        if(obj.result==="true") {//obj.result
          window.location.href = 'aimJSP.jsp?id=' + id;
        }
      }
    };
  }
</script>
</body>
</html>

