<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Guo
  Date: 4/4/18
  Time: 9:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
    <h1>Error Page</h1>
    <p>
        Application has encountered an error, please contact
        <i>xguo.tufts@gmail.com</i> for more information.
        <br>Thank you.
    </p>

    <p>
        Following is the Exception messages:<br>
    </p>

    <pre>
        Failed URL: ${url}
        Exception:  ${exception.message}
        <c:forEach items = "${exception.stackTrace}" var = "ste">
            ${ste}
        </c:forEach>
    </>
</body>
</html>
