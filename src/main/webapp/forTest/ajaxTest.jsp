<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
<div id="demo">
  <h1>XMLHttpRequest 对象 in JSP</h1>
  <button type="button" onclick="showTable()">显示内容</button>
  <button type="button" onclick="jump()">跳转</button>
  <br><br>
  <table id="idtable"></table>
  <br><br>
  <table id="housetable"></table>
</div>

<script>
  var fun="searchHouse";
  var searchtype="all"
  var num="10";
  function showTable() {
    var xhttp;
    if (window.XMLHttpRequest) {
      xhttp = new XMLHttpRequest();
    } else {
      // code for IE6, IE5
      xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xhttp.open("POST", "http://localhost:8080/HLS/HouseManager");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("function="+fun+"&searchtype="+searchtype+"&num="+num);

    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        alert(this.responseText);
        var obj = JSON.parse(this.responseText);
        var table = "<tr><th>houseID</th><th>ownerID</th><th>isLeased</th><th>isAbleSearched</th></tr>";
        for (var i in obj) {  //遍历数组
          alert(obj[i].isLeased);
          table += "<tr><td>" +
                  obj[i].houseID;
                  "</td><td>" +
                  obj[i].ownerID;
                  "</td><td>" +
                  obj[i].isLeased;
                  "</td><td>" +
                  obj[i].isAbleSearched;
                  "</td></tr>";
        }


      }
      document.getElementById("idtable").innerHTML = table;
    };
  }
  function jump() {
    window.location.href = 'aimJSP.jsp?id='+id;
  }
</script>
</body>
</html>


<%--<html>--%>
<%--<body>--%>

<%--<h1>XMLHttpRequest 对象</h1>--%>

<%--<button type="button" onclick="loadDoc()">请求数据</button>--%>

<%--<p id="demo"></p>--%>

<%--<script>--%>
<%--  function loadDoc() {--%>
<%--    var xhttp = new XMLHttpRequest();--%>
<%--    xhttp.onreadystatechange = function() {--%>
<%--      if (this.readyState == 4 && this.status == 200) {--%>
<%--        document.getElementById("demo").innerHTML = this.responseText;--%>
<%--      }--%>
<%--    };--%>
<%--    xhttp.open("POST", "PeopleMessage");--%>
<%--    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");--%>
<%--    xhttp.send("id=78999&identity=homeowner");--%>
<%--  }--%>
<%--</script>--%>

<%--</body>--%>
<%--</html>--%>



