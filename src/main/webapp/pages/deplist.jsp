<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Test Work</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/custom.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dialog.css">
</head>
<body>

<div class="main" style="width: 600px;">
  <table border="1">
    <caption>Departments</caption>
  <tr>
    <th width="10%">№</th>
    <th width="10%">ID</th>
    <th>Name</th>
    <th width="125px"></th>
  </tr>
    <c:set var="num" value="0"/>
    <c:forEach var="depobj" items="${requestScope.deplist}">
      <tr>
        <td>${num = num + 1}</td>
        <td>${depobj.id}</td>
        <td><a class="dep" href="${pageContext.request.contextPath}/emplist?dep=${depobj.id}">${depobj.depName}</a></td>
        <td>
          <a class="button" href="${pageContext.request.contextPath}/deplist?del=${depobj.id}">Delete</a>
          <%--<a class="button" href="#confirm">Delete</a>--%>
          <a class="button" href="${pageContext.request.contextPath}/deplist?edt=${depobj.id}">Edit</a>
        </td>
      </tr>
  </c:forEach>
  <tr class="last">
    <td class="last" colspan="3"></td>
    <td class="last">
      <a class="button" href="${pageContext.request.contextPath}/deplist?edt=0">Add</a>
    </td>
  </tr>
</table>
</div>


  <%--<div id="confirm" class="modalDialog">--%>
    <%--<div class="main" style="width: 200px; position: absolute; left: 40%; top: 30%;">--%>
      <%--<p>Are you sure?</p>--%>
      <%--<a href="#close" title="Close" class="button" style="float: left;">No</a>--%>
      <%--<a class="button" style="float: right;" href="${pageContext.request.contextPath}/deplist?del=">Yes</a>--%>
    <%--</div>--%>
  <%--</div>--%>



</body>
</html>
