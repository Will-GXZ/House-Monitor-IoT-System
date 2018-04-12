<%--
  Created by IntelliJ IDEA.
  User: Guo
  Date: 4/10/18
  Time: 12:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="page for monitor sensor data">
    <meta name="author" content="xiaozheng guo">

    <title>Monitor Data Page</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../../css/monitor_data.css"/>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
</head>

<body>
<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">TWL Network</a>
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link mr-0 active" href="#"> Show Current Data</a>
        </li>
    </ul>
    <ul class="navbar-nav px-3 mr-auto">
        <li class="nav-item text-nowrap">
            <a class="nav-link mr-0" href="#"> Show History Data</a>
        </li>
    </ul>
    <ul class="navbar-nav px-3 ml-auto">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="../../">
                <span data-feather="log-out"></span>
                Exit
            </a>
        </li>
    </ul>
</nav>

<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
            <div class="sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="bar-chart-2"></span>
                            Reports
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="layers"></span>
                            Integrations
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

            <%--toggle--%>
            <div class="d-flex justify-content-start flex-wrap flex-md-nowrap mt-3 mb-2 mb-3">
                <div class="mt-3">
                    <input class="float-left" id="toggle1" data-onstyle="success" data-offstyle="danger" type="checkbox" data-toggle="toggle" data-on="Enabled" data-off="Disabled">
                </div>
                <div id="toggle_alert" class="ml-4 float-left">
                    <div  class=" ml-4 float-left alert alert-info alert-dismissible fade show" role="alert">
                        <strong> Suggestion: </strong> <br> <strong><span data-feather="arrow-left"></span></strong>Click here to toggle auto data fetching.
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Dashboard</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group mr-2">
                        <button class="btn btn-sm btn-outline-secondary">Share</button>
                        <button class="btn btn-sm btn-outline-secondary">Export</button>
                    </div>
                    <button class="btn btn-sm btn-outline-secondary dropdown-toggle">
                        <span data-feather="calendar"></span>
                        This week
                    </button>
                </div>
            </div>

            <canvas class="my-4 w-100" id="myChart" width="900" height="380"></canvas>

        </main>
    </div>
</div>

<!-- Icons -->
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>

<!-- Graphs -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js"></script>
<script>
    var ctx = document.getElementById("myChart");
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
            datasets: [{
                data: [15339, 21345, 18483, 24003, 23489, 24092, 12034],
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4,
                pointBackgroundColor: '#007bff'
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: false
                    }
                }]
            },
            legend: {
                display: false,
            }
        }
    });
</script>

<script>
    // toggle
    $(function() {
        $('#toggle1').change(function() {
            if ($('#toggle1').prop('checked'))
            {
                document.getElementById("toggle_alert").innerHTML =
                    "                    <div  class=\" ml-4 float-left alert alert-success alert-dismissible fade show\" role=\"alert\">\n" +
                    "                        <strong> Notification: </strong> <br> <strong><span data-feather=\"arrow-left\"></span></strong>Auto Data Fetching Enabled.\n" +
                    "                        <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "                            <span aria-hidden=\"true\">&times;</span>\n" +
                    "                        </button>\n" +
                    "                    </div>";
            } else {
                document.getElementById("toggle_alert").innerHTML =
                    "                    <div  class=\" ml-4 float-left alert alert-danger alert-dismissible fade show\" role=\"alert\">\n" +
                    "                        <strong> Notification: </strong> <br> <strong><span data-feather=\"arrow-left\"></span></strong>Auto Data Fetching Disabled.\n" +
                    "                        <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "                            <span aria-hidden=\"true\">&times;</span>\n" +
                    "                        </button>\n" +
                    "                    </div>";
            }
        })
    })
</script>
</body>
</html>

