<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <title>Test Work</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/custom.css">
</head>
<body>

<div class="main" style="width: 95%;">

<table border="1">
    <caption>Employees of ${requestScope.depobj.depName}</caption>
    <tr>
        <th width="5%">№</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>e-mail</th>
        <th>Date of Birth</th>
        <th>Position</th>
        <th>Salary</th>
        <th width="125px"></th>
    </tr>
    <c:set var="num" value="0"/>
    <c:forEach var="empobj" items="${requestScope.emplist}">
        <tr>
            <td>${num = num + 1}</td>
            <td>${empobj.firstName}</td>
            <td>${empobj.lastName}</td>
            <td>${empobj.email}</td>
            <td>${empobj.dateOfBirth}</td>
            <td>${empobj.position}</td>
            <td>$${empobj.salary}</td>
            <td>
                <a class="button" href="${pageContext.request.contextPath}/emplist?dep=${requestScope.depobj.id}&del=${empobj.id}">Delete</a>
                <a class="button" href="${pageContext.request.contextPath}/emplist?dep=${requestScope.depobj.id}&edt=${empobj.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
    <tr class="last">
        <td class="last" colspan="7"><a class="button" href="${pageContext.request.contextPath}/deplist">Back</a></td>
        <td class="last">
            <a class="button" href="${pageContext.request.contextPath}/emplist?dep=${requestScope.depobj.id}&edt=0">Add</a>
        </td>
    </tr>
</table>
</div>

</body>
</html>
