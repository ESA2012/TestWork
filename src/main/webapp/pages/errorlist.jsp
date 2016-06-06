<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.errorlist!=null && requestScope.errorlist.hasErrors()}">
  <div class="main" style="width: 600px;">
    <c:forEach items="${requestScope.errorlist.errors}" var="error">
      <p class="error">${error}</p><br>
    </c:forEach>
  </div>
</c:if>