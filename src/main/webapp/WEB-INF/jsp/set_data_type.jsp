<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Guo
  Date: 4/8/18
  Time: 8:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Setting page for data type">
    <meta name="author" content="xiaozheng guo">

    <title>Data Type Setting Page</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

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

        .col-center-block {
            float: none;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
        
        .mb-4 {
            margin-bottom: 30px;
        }

        .mb-2 {
            margin-bottom: 15px;
        }

        hr {
            display: block;
            height: 1px;
            border: 0;
            border-top: 1px solid #ccc;
            margin: 0em;
            padding: 0;
        }
    </style>

    <script>
        $(function()
        {
            $(document).on('click', '.btn-add', function(e)
            {
                e.preventDefault();

                var controlForm = $('.controls form:first'),
                    currentEntry = $(this).parents('.entry:first'),
                    newEntry = $(currentEntry.clone()).appendTo(controlForm);

                newEntry.find('input').val('');
                controlForm.find('.entry:not(:last) .btn-add')
                    .removeClass('btn-add').addClass('btn-remove')
                    .removeClass('btn-success').addClass('btn-danger')
                    .html('<span class="glyphicon glyphicon-minus"></span>');
            }).on('click', '.btn-remove', function(e)
            {
                $(this).parents('.entry:first').remove();

                e.preventDefault();
                return false;
            });
        });

        function submitForm() {
            var dataTypeList = [];
            var inputs = document.getElementById("form1").getElementsByTagName("input");
            console.log(inputs);
            for (var i = 0; i < inputs.length; ++i) {
                console.log(inputs[i].value);
                if (inputs[i].type !== "text") continue;
                if (inputs[i].value === "") continue;
                dataTypeList.push(inputs[i].value);
            }
            if (dataTypeList.length === 0) {
                alert("You must input at least one data type!");
                return;
            }

            // convert to json, send to server
            var json = JSON.stringify(dataTypeList);
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState === 4 && xhttp.status === 200) {
                    console.log("response: " + this.responseText);
                    window.location.href = "../page/setBorderRouterPage";
                } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                    history.pushState(null, null, "/error");
                    document.write(this.responseText);
                }
            }
            xhttp.open("POST", "../setting/dataTypes", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.setRequestHeader("Accept", "application/json");
            xhttp.setRequestHeader("ModelAttribute", "DataTypes");
            xhttp.send(json);
        }
    </script>

</head>

<body class="text-center">
    <div class="container">
        <div class="row mb-4">
            <div class="col-md-8 col-center-block">
                <h2 class="mb-4">Set Data Type</h2>
                <p class="lead mb-3">
                    Enter data types you want to monitor. The data type you entered must be supported
                    by sensors you are using. For example, if you want to monitor <code>temperature</code>,
                    you might need to enter <code>temp</code> for it.
                </p>
                <p class="lead mb-5">
                    If you want to use the default <code>dataTypeList</code> that can be configured in "app_custom.properties" file,
                    click <code>Skip</code> button. Currently, the default dataTypeList is:
                    <code>
                    <%
                        List dataTypeList = (List) request.getAttribute("dataTypeList");
                        out.print(dataTypeList.toString());
                    %>
                    </code>
                    .
                </p>
            </div>
        </div>

        <div class="row col-xs-6 col-center-block">
            <div class="control-group" id="fields">
                <div class="controls">
                    <form id="form1" role="form" autocomplete="off">
                        <div class="entry input-group mb-2">
                            <input class="form-control" name="fields[]" type="text" placeholder="Data type" />
                            <span class="input-group-btn">
                                <button class="btn btn-success btn-add" type="button">
                                    <span class="glyphicon glyphicon-plus"></span>
                                </button>
                            </span>
                        </div>
                    </form>
                    <br>
                    <small class="mb-0">Press <span class="glyphicon glyphicon-plus gs"></span> to add another data type</small>

                    <div>
                        <hr class="mb-2 mt-4">
                        <button id="submit_btn1" class="mr-3 col-xs-5 btn btn-primary btn-success" onclick="submitForm()">Submit</button>
                        <span class="col-xs-2"></span>
                        <a class="ml-3 col-xs-5 btn btn-primary btn-success" href="../page/setBorderRouterPage">Skip</a>
                    </span>
                </div>
            </div>
        </div>
    </div>

</body>

</html>
