<%--
  Created by IntelliJ IDEA.
  User: Guo
  Date: 4/9/18
  Time: 11:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Setting page for sensor name">
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
</head>
<body class="text-center">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-sm-9">
                <h2 class="mb-4">Set Sensor Name</h2>
                <p class="lead mb-5">
                    Fill in this form to set name of each sensor. Sensor name can be duplicated but this is not recommended.
                    The name of each sensor should be meaningful, for example, use <code>Kitchen</code> or <code>Bedroom</code> for sensors
                    in your kitchen and bedroom respectively.
                </p>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-sm-6">
                <form id="form1" class="needs-validation" onsubmit="return readForm(this)" novalidate></form>
            </div>
        </div>
    </div>

    <script>
        // global variable, sensor ip list
        var sensorIpList;
        // get all sensor IP, create form after the request got response
        (function() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState === 4 && xhttp.status === 200) {
                    console.log(this.responseText);
                    sensorIpList = JSON.parse(this.responseText);
                    // create form after success
                    createForm(sensorIpList);
                } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                    history.pushState(null, null, "/error");
                    document.write(this.responseText);
                }
            }
            xhttp.open("GET", "/setting/getAllSensorIp", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.setRequestHeader("Accept", "application/json");
            xhttp.setRequestHeader("ModelAttribute", "getAllSensorIp");
            xhttp.send();
        })();

        // create form
        function createForm(sensorIpList) {
            var form = document.getElementById("form1");
            // if there is no sensor, display error message
            if (sensorIpList.length === 0) {
                form.innerHTML =
                    "<hr class=\"mb-4\">" +
                    "  <div class=\"text-center text-info h4\">" +
                    "    class=\"text-warning\">No sensor found, please check your connection. <br>" +
                    "    Try <a href=\"/page/setBorderRouterPage\">this</a> link to reset border router IP List." +
                    "  </div>" +
                    "<hr class=\"mt-4\">";
            }
            // create form
            for (var i = 0; i < sensorIpList.length; ++i) {
                var sensorIP = sensorIpList[i];
                var row = document.createElement("div");
                row.classList.add("row");
                row.classList.add("justify-content-center");
                row.innerHTML =
                    "<div class=\"input-group mb-2 col-sm-10\">\n" +
                    "  <div class=\"input-group-prepend\">\n" +
                    "    <span class=\"input-group-text\">" + sensorIP + "</span>\n" +
                    "  </div>\n" +
                    "  <input type=\"text\" class=\"form-control\" aria-describedby=\"basic-addon3\" placeholder='Sensor Name' required>\n" +
                    "  <div class=\"invalid-feedback\">\n" +
                    "    Sensor name is required.\n" +
                    "  </div>" +
                    "</div>";
                form.appendChild(row);
            }
            // add submit button
            var submit_btn = document.createElement("div");
            submit_btn.classList.add("row");
            submit_btn.classList.add("justify-content-sm-center");
            submit_btn.classList.add("my-4");
            submit_btn.innerHTML =
                "    <div class='col-sm-6 mb-3 ml-0'>" +
                "      <button class=\"btn btn-primary btn-success btn-block\" type=\"submit\">submit</button>\n" +
                "    </div>" +
                "    <div class=\"col-sm-6 mb-3 mr-0\">" +
                "      <button class=\"btn btn-primary btn-success btn-block\" type=\"button\" onclick=\"window.location='/page/monitorDataPage'\">skip</button>\n" +
                "    </div>";

            var hr = document.createElement("hr");
            hr.classList.add("px-0");
            hr.classList.add("mx-0");
            hr.classList.add("bx-0");
            hr.classList.add("mt-4");

            form.appendChild(hr);
            form.appendChild(submit_btn);
        }

        function readForm(form) {
            var sensorIpNameList = [];
            var elements = form.getElementsByTagName("input");
            for (var i = 0; i < elements.length; ++i) {
                console.log(elements[i].value);
                if (elements[i].value === "") elements[i].value = sensorIpList[i];
                sensorIpNameList.push([sensorIpList[i], elements[i].value]);
            }
            var sensorIpNameJson = JSON.stringify(sensorIpNameList);
            console.log(sensorIpNameJson);

            // send json to server
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState === 4 && xhttp.status === 200) {
                    if (this.responseText === "HTTP_OK") {
                        console.log("redirect to monitor data page");
                        window.location.href = "/page/monitorDataPage";
                    } else {
                        window.location.href = "/page/errorPage";
                    }
                } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                    history.pushState(null, null, "/error");
                    document.write(this.responseText);
                }
            }
            xhttp.open("PUT", "/setting/setSensorName", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.setRequestHeader("Accept", "application/json");
            xhttp.setRequestHeader("ModelAttribute", "sensorIpAndName");
            xhttp.send(sensorIpNameJson);

            return false;
        }

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
    </script>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>
