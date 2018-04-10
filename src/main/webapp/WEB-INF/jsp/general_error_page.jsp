<%--
  Created by IntelliJ IDEA.
  User: Guo
  Date: 4/9/18
  Time: 9:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>General Error Page</title>
    <link rel="stylesheet" href="/css/error.css"/>
</head>
<body>
    <div class="overlay"></div>
    <div class="terminal">
        <h1>Error <span class="errorcode">500</span></h1>
        <p class="output">The server encountered an internal error or misconfiguration and was unable
            to complete your request.<p>

        Please contact the administrator of House Monitor: <a href="mailto:xguo.tufts@gmail.com">xguo.tufts@gmail.com</a>,
        and inform them of the time the error occurred, and anything you might have
        done that may have caused the error.
        <p class="output">Please try <a href="/">this link</a>
        <p class="output">Good luck</p>
    </div>
</body>
</html>
