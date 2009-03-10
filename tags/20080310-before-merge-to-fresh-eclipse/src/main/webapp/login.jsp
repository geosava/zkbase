<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<html>
   <body>
      <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
         Fehler beim Login:  
         <%=((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
         </br></br>
      </c:if>

      <form action="<c:url value='j_spring_security_check'/>" method="post">
         Benutzername: <input type="text" name="j_username">
         <br/>
         Passwort: <input type="text" name="j_password">
         <br/>
         <input type="submit" value="Login">
      </form>
   </body>
</html>
