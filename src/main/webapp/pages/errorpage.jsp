<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>
<%--
  Created by IntelliJ IDEA.
  User: snake
  Date: 05.06.16
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<html>
<head>
    <title>Test Work</title>
    <link href="<c:url value="/css/custom.css"/>" rel="stylesheet" />
</head>
<body>
  <div class="main" style="width: 90%">

      <p><h2 style="color: red">Oooops! We have an application error ;(</h2>

      <p><h2>Message:</h2>

      <p align="left"><%=exception.getMessage()%></p>

      <p><h2>Stack trace:</h2>

      <p align="left">
         <%
             StringWriter stringWriter = new StringWriter();
             PrintWriter printWriter = new PrintWriter(stringWriter);
             exception.printStackTrace(printWriter);
             out.println(stringWriter);
             printWriter.close();
             stringWriter.close();
         %>
      </p>

  </div>
</body>
</html>
