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
    <link rel="stylesheet" href="/css/error.css"/>
</head>
<body>
    <div class="overlay"></div>
    <div class="terminal">
        <h1>Error <span class="errorcode">500</span></h1>
        <p class="output">
            Application has encountered an error, please contact
            <a href="mailto:xguo.tufts@gmail.com">xguo.tufts@gmail.com</a> for more information.
            <br>Thank you.
        </p>

        <p class="output">
            Following is the Exception messages:<br>
        </p>

        <pre>
            Failed URL: ${url}
            Exception:  ${exception.message}
            <c:forEach items = "${exception.stackTrace}" var = "ste">
                ${ste}
            </c:forEach>
        </pre>
    </div>
</body>
</html>
