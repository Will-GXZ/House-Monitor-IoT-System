<%--
  Created by IntelliJ IDEA.
  User: Guo
  Date: 4/9/18
  Time: 3:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Set Border Router</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Setting page for data type">
    <meta name="author" content="xiaozheng guo">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

    <style>
        html,
        body {
            height: 100%;
        }

        body {
            display: -ms-flexbox;
            display: -webkit-box;
            display: flex;
            -ms-flex-align: center;
            -ms-flex-pack: center;
            -webkit-box-align: center;
            align-items: center;
            -webkit-box-pack: center;
            justify-content: center;
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        input.form-control::placeholder { /* Most modern browsers support this now. */
            color: #bcbcbc;
            opacity: 1;
        }
    </style>

    <script>
        // form validation
        (function() {
            'use strict';

            window.addEventListener('load', function() {
                // Fetch all the forms we want to apply custom Bootstrap validation styles to
                var forms = document.getElementsByClassName('needs-validation');

                // Loop over them and prevent submission
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();

        function addFormRow() {
            // create new row first
            var newRow = document.createElement("div");
            newRow.classList.add("row");
            newRow.innerHTML = "     <div class=\"col-md-6 mb-3\">\n" +
                "                        <input type=\"text\" class=\"form-control\" placeholder=\"borderRouterIP\" value=\"\" required>\n" +
                "                        <div class=\"invalid-feedback\">\n" +
                "                            Valid borderRouterIP is required.\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"col-md-6 mb-3\">\n" +
                "                        <input type=\"text\" class=\"form-control\" placeholder=\"borderRouterName\" value=\"\" required>\n" +
                "                        <div class=\"invalid-feedback\">\n" +
                "                            Valid borderRouterName is required.\n" +
                "                        </div>\n" +
                "                    </div>\n";
            var btnRow = document.getElementById("btn_plus_row");
            document.getElementById("form1").insertBefore(newRow, btnRow);
        }

        function readForm(form) {
            var routerIpNameList = [];
            var inputs = form.getElementsByTagName("input");
            for (var i = 0; i < inputs.length; i += 2) {
                var pair = [inputs[i].value, inputs[i + 1].value];
                if (pair[0] === "" || pair[1] === "") {
                    console.log("input invalid");
                    return false;
                }
                routerIpNameList.push(pair);
            }

            // convert routerIpNameList to JSON
            var json = JSON.stringify(routerIpNameList);
            console.log(json);

            // send to server
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState === 4 && xhttp.status === 200) {
                    if (this.responseText === "HTTP_OK") {
                        console.log("redirect to set_sensor_name page");
                        window.location.href = "${contextPath}/page/setSensorNamePage";
                    } else {
                        window.location.href = "${contextPath}/page/errorPage";
                    }
                } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                    history.pushState(null, null, "${contextPath}/error");
                    document.write(this.responseText);
                }
            }
            xhttp.open("POST", "${contextPath}/setting/setBorderRouter", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.setRequestHeader("Accept", "application/json");
            xhttp.setRequestHeader("ModelAttribute", "borderRouterIpAndName");
            xhttp.send(json);

            return false; // disable submit action
        }
    </script>
</head>
<body class="text-center">
    <div class="container">
        <div class="row justify-content-center mb-3">
            <div class="col-sm-9">
                <h2 class="mb-3">Set Border Router</h2>
                <p class="lead">
                    Fill in this form to set up each <code>[BorderRouterIP, BorderRouterName]</code> pair. Duplicated
                    border router IP will be ignored. Border router name can be duplicated, but it is not recommended.
                </p>
            </div>
        </div>

        <div class="row justify-content-center">
            <form id="form1" class="needs-validation" onsubmit="return readForm(this)" novalidate>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <input type="text" class="form-control" placeholder="borderRouterIP" value="" required>
                        <div class="invalid-feedback">
                            Valid borderRouterIP is required.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <input type="text" class="form-control" placeholder="borderRouterName" value="" required>
                        <div class="invalid-feedback">
                            Valid borderRouterName is required.
                        </div>
                    </div>
                </div>
                <div id="btn_plus_row" class="row">
                    <button class="btn btn-sm ml-3" type="button" style="padding: 0" onclick="addFormRow()">
                        <img src="${contextPath}/imgs/plus.png"/>
                    </button>
                </div>
                <small class="mb-0">Press <strong>+</strong> to add another border router</small>
                <hr class="mb-4 mt-0">
                <button class="btn btn-primary btn-success btn-block" type="submit">Submit</button>
            </form>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>
