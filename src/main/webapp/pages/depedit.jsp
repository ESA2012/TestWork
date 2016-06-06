<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="depobj" class="esa2012.service.datatransport.DepartmentDTO" scope="request"/>
<jsp:setProperty name="depobj" property="*"/>

<%--
  Created by IntelliJ IDEA.
  User: snake
  Date: 02.06.16
  Time: 3:11
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/custom.css">
</head>
<body>
<c:choose>
  <c:when test="${depobj.id == null}">
    <c:set var="action" value="add"/>
    <c:set var="formlabel" value="Add new department"/>
    <c:set var="buttontext" value="Add"/>
  </c:when>
  <c:otherwise>
    <c:set var="action" value="upd"/>
    <c:set var="formlabel" value="Edit exists department"/>
    <c:set var="buttontext" value="Update"/>
  </c:otherwise>
</c:choose>
<div id="edit" class="main" style="width: 400px;">
  <div>
    <h2>
      ${formlabel}
    </h2>
    <hr>
    <form method="post" action="${pageContext.request.contextPath}/depedit" name="depobj">
      <input type="hidden" name="dep_id" value="${depobj.id}">
      <label for="dep_name">Name:</label>
      <input id="dep_name" type="text" name="dep_name" value="${depobj.depName}">

      <div style="height: 35px;">
        <hr>
        <a class="button" href="${pageContext.request.contextPath}/deplist">Back</a>
        <input style="float: right" type="submit" value="${buttontext}" name="${action}">
      </div>
    </form>
  </div>
</div>

<c:import url="errorlist.jsp"/>

</body>
</html>
