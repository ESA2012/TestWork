<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ESA2012
  Date: 02.06.16
  Time: 3:11
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
  <title>Test Work</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/custom.css">
</head>
<body>
<c:choose>
  <c:when test="${requestScope.empobj.id == null}">
    <c:set var="action" value="add"/>
    <c:set var="formlabel" value="Add new employee to "/>
    <c:set var="buttontext" value="Add"/>
  </c:when>
  <c:otherwise>
    <c:set var="action" value="upd"/>
    <c:set var="formlabel" value="Edit exists employee in "/>
    <c:set var="buttontext" value="Update"/>
  </c:otherwise>
</c:choose>
<div id="edit" class="main" style="width: 500px;">
  <h2>${formlabel} ${requestScope.depobj.depName}</h2>
  <hr>
  <form method="post" action="${pageContext.request.contextPath}/empedit?dep=${requestScope.depobj.id}" name="edit">
    <input type="hidden" name="dep_id" value="${requestScope.depobj.id}"/>

    <input type="hidden" name="emp_id" value="${requestScope.empobj.id}"/>

    <label for="emp_fname">First name:</label>
    <input id="emp_fname" type="text" name="emp_firstname" value="${requestScope.empobj.firstName}">

    <label for="emp_lname">Last name:</label>
    <input id="emp_lname" type="text" name="emp_lastname" value="${requestScope.empobj.lastName}">

    <label for="emp_dob">Date of birth:</label>
    <input id="emp_dob" type="text" name="emp_dateofbirth" value="${requestScope.empobj.dateOfBirth}">

    <label for="emp_mail">e-mail:</label>
    <input id="emp_mail" type="text" name="emp_email" value="${requestScope.empobj.email}">

    <label for="emp_post">Position:</label>
    <input id="emp_post" type="text" name="emp_position" value="${requestScope.empobj.position}">

    <label for="emp_salary">Salary:</label>
    <input id="emp_salary" type="text" name="emp_salary" value="${requestScope.empobj.salary}">

    <div style="height: 35px;">
      <hr>
      <a class="button" href="${pageContext.request.contextPath}/emplist?dep=${requestScope.depobj.id}">Back</a>
      <input style="float: right" type="submit" value="${buttontext}" name="${action}">
      </div>
  </form>
</div>

<jsp:include page="errorlist.jsp"/>

</body>
</html>
