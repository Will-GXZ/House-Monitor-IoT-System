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
    <link rel="stylesheet" href="/css/monitor_data.css"/>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js"></script>
</head>

<body onload="showCurrentData()">
<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">TWL Network</a>
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a id="current_data_tab" class="nav-link mr-0" href="#" onclick="return showCurrentData()"> Show Current Data</a>
        </li>
    </ul>
    <ul class="navbar-nav px-3 mr-auto">
        <li class="nav-item text-nowrap">
            <a id="history_data_tab" class="nav-link mr-0" href="#" onclick="return showHistoryData()"> Show History Data</a>
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
                <h5 class="sidebar-heading d-flex justify-content-start align-items-center px-3 mt-4 mb-2 text-muted">
                    <span>Select Border Router</span>
                    <span data-feather="chevrons-down"></span>
                </h5>
                <ul id="side_bar" class="nav flex-column">
                    <%--populated by script--%>
                </ul>
            </div>
        </nav>

        <main role="main" class="d-flex flex-column col-md-9 ml-sm-auto col-lg-10 px-4">

            <%-- buttons --%>
            <div class="d-flex justify-content-start flex-wrap flex-md-nowrap mt-3 mb-2 mb-3">
                <div class="mt-3">
                    <input class="float-left mr-0" id="toggle1" data-onstyle="success" data-offstyle="danger" type="checkbox" data-toggle="toggle" data-on="Auto Data Fetching Enabled" data-off="Auto Data Fetching Disabled">
                </div>
                <div id="toggle_alert" class="ml-2 float-left">
                    <div  class=" ml-3 float-left alert alert-info alert-dismissible fade show" role="alert">
                        <strong> Suggestion: </strong> <br> <strong><span data-feather="arrow-left"></span></strong>Click here to toggle auto data fetching.
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
                <div class="float-left mt-3 ml-4">
                    <button type="button" class="btn btn-warning" onclick="clearHistoryData()"> Clear History Data </button>
                </div>
            </div>

            <div id="data_alert" class="float-left m-0 p-2">
                <%-- populated by script --%>
            </div>

            <%--<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">--%>
                <%--<h1 class="h2">Dashboard</h1>--%>
                <%--<div class="btn-toolbar mb-2 mb-md-0">--%>
                    <%--<div class="btn-group mr-2">--%>
                        <%--<button class="btn btn-sm btn-outline-secondary">Share</button>--%>
                        <%--<button class="btn btn-sm btn-outline-secondary">Export</button>--%>
                    <%--</div>--%>
                    <%--<button class="btn btn-sm btn-outline-secondary dropdown-toggle">--%>
                        <%--<span data-feather="calendar"></span>--%>
                        <%--This week--%>
                    <%--</button>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<canvas class="my-4 w-100" width="900" height="380">--%>
                <%--&lt;%&ndash; populated by script &ndash;%&gt;--%>
            <%--</canvas>--%>
            <div id="chart_div" class="d-flex flex-column">
                <%-- populated by script --%>
            </div>

            <%-- pop up modal --%>
            <div id="pop_window_period" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title"> Please Set Period </h5>
                                <button type="button" class="close" onclick="closeModal()" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <div>
                                        <input id="period_input" type="number" class="form-control input-lg" placeholder="period in millisecond" name="password">
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" onclick="confirmModal()">Enable Auto Data Fetching</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<%-- core javascript logic here --%>
<script>
    // display the error message if there is something wrong with ajax requests,
    // need to encode "\n" with "%0A" for url encoding. And in back-end, need to
    // replace all "%0A" with "<br>"
    function displayErrorPage(xhttp) {
        console.log("error occured. Ready state: " + xhttp.readyState + ", Status: "
            + xhttp.status + "%0Abody: " + xhttp.responseText);
        window.location = "/page/errorPage?stackTrace=HTTP Status:%0A" + xhttp.status
            + "%0A%0AHTTP Response Body:%0A" + xhttp.responseText
            + "%0A%0A" + new Error().stack.replace(/\n/g, "%0A");
    }

    function clearHistoryData() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === 4 && xhttp.status === 200) {
                var count = parseInt(this.responseText);
                showAlert(count + " entries deleted. All sensor data in database has been deleted.", 'success')
            } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                history.pushState(null, null, "/error");
                document.write(this.responseText);
            } else if (xhttp.readyState === 4) {
                displayErrorPage(this);
            }
        }
        xhttp.open("DELETE", "/data/delete/all", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.setRequestHeader("Accept", "application/json");
        xhttp.setRequestHeader("ModelAttribute", "deleteAllData");
        xhttp.send();
    }
</script>
<%-- javascript for show history data --%>
<script>
    // entrance for display history data feature
    function showHistoryData() {
        document.getElementById("current_data_tab").classList.remove("active");
        document.getElementById("history_data_tab").classList.add("active");
        clearChartDiv();
        getRouterIpAndName(generateSideBarForHistoryData);
    }

    // get border router ip and name, create side bar and implement data visualization in its
    // call back function
    function getRouterIpAndName(fun) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === 4 && xhttp.status === 200) {
                console.log(this.responseText); // DEBUG
                if (this.responseText.length === 0) {
                    showAlert("There is no border router in database.");
                    return;
                }
                fun(JSON.parse(this.responseText));
            } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                history.pushState(null, null, "/error");
                document.write(this.responseText);
            } else if (xhttp.readyState === 4) {
                displayErrorPage(this);
            }
        }
        xhttp.open("GET", "/data/get/borderRouterIpAndName", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.setRequestHeader("Accept", "application/json");
        xhttp.setRequestHeader("ModelAttribute", "getAllBorderRouterIpAndName");
        xhttp.send();
    }

    // genreate side bar for input router Ip and name list
    function generateSideBarForHistoryData(routerIpNameList) {
        if (routerIpNameList.size === 0) {
            showAlert("No border router in database. ");
            return;
        }
        // create side bar item for each border router
        clearSideBar();
        for (var i = 0; i < routerIpNameList.length; ++i) {
            createNavItemForHistoryData(routerIpNameList[i][0], routerIpNameList[i][1]);
        }
    }

    // create the nav item for the input border router IP and name
    function createNavItemForHistoryData(routerIP, routerName) {
        // create side bar nav item here
        var sideBar = document.getElementById("side_bar");
        var li = document.createElement("li");
        li.classList.add("nav-item");
        var link = document.createElement("a");
        link.classList.add("nav-link");
        link.innerHTML =
            "<span data-feather=\"cpu\"></span>\n" + routerName;
        // add onclick call back function, draw charts when click on side bar item
        link.addEventListener('click', function () {
            // clear charts created last time first
            clearChartDiv();
            // get data for this border router
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState === 4 && xhttp.status === 200) {
                    console.log("createNavItemForHistoryData: " + this.responseText); // DEBUG
                    if (this.responseText.length === 0) {
                        showAlert("There is no data for border router IP: " + routerIP + " in database.")
                        return;
                    };
                    // draw charts for this border router
                    var borderRouterWrapper = JSON.parse(this.responseText);
                    var dataTypeList = getDataTypeList(borderRouterWrapper);
                    var timeStampList = getTimeStampList(borderRouterWrapper);
                    var sensorNameList = getSensorNameList(borderRouterWrapper);
                    for (var i = 0; i < dataTypeList.length; ++i) {
                        var dataType = dataTypeList[i];
                        var dataLists = getDataListsForHistoryData(borderRouterWrapper, dataType);
                        createChartTitle(dataType, routerName);
                        var canvas = createCanvas();
                        drawLineChart(timeStampList, dataLists, canvas, sensorNameList);
                    }

                } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                    history.pushState(null, null, "/error");
                    document.write(this.responseText);
                } else if (xhttp.readyState === 4) {
                displayErrorPage(this);
                }
            }
            xhttp.open("GET", "/data/get/" + routerIP + "/database", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.setRequestHeader("Accept", "application/json");
            xhttp.setRequestHeader("ModelAttribute", "getDataForBorderRouterFromDatabase");
            xhttp.send();
        });
        li.appendChild(link);
        sideBar.appendChild(li);

        // replace feather icon
        feather.replace();
    }

    // this data list should be a 2D array, each row is data list for a sensor
    // each column is data of all sensors at one timestamp.
    function getDataListsForHistoryData(borderRouterWrapper, dataType) {
        var dataList = [];
        for (var i = 0; i < borderRouterWrapper.sensorWrapperList.length; ++i) {
            var sensorWrapper = borderRouterWrapper.sensorWrapperList[i];
            var dataListOfSensor = [];
            for (var j = 0; j < sensorWrapper.dataList.length; ++j) {
                dataListOfSensor.push(parseFloat(sensorWrapper.dataList[j].dataJson[dataType]));
            }
            dataList.push(dataListOfSensor);
        }
        console.log(dataList); // DEBUG
        return dataList;
    }

    function drawLineChart(timeLabelList, dataLists, canvas, sensorNameList) {
        // create data set list
        var dataSets = [];
        var colorList = [
            "#FE6584", "#38A2E7", "#4EC0BF", "#f9acac",
            "#f2ff00", "#fa217f", "#fc913a", "#99583b", "#5b3000", "#76877d",
            "#82a6b1", "#95017b", "#ffd900", "#4376b3", "#9da9b7", "#f46036"
        ];
        var transparentColorList = [
            "#FE658420", "#38A2E720", "#4EC0BF20", "#f9acac20",
            "#f2ff0020", "#fa217f20", "#fc913a20", "#99583b20", "#5b300020", "#76877d20",
            "#82a6b120", "#95017b20", "#ffd90020", "#4376b320", "#9da9b720", "#f4603620"
        ];
        for (var i = 0; i < dataLists.length; ++i) {
            var dataListForSensor = dataLists[i];
            dataSets.push({
                data: dataListForSensor,
                label: sensorNameList[i],
                lineTension: 0.3,
                backgroundColor: transparentColorList[i % colorList.length],
                borderColor: colorList[i % colorList.length],
                borderWidth: 2,
                cubicInterpolationMode: 'monotone',
                spanGaps: true,
                pointRadius: 3,
                pointHoverRadius: 9,
                pointHoverBackgroundColor: colorList[i % colorList.length]
            });
        }
        // convert timeLabelList from int list to Date list
        var labelList = [];
        for (var i = 0; i < timeLabelList.length; ++i) {
            labelList.push(new Date(timeLabelList[i]));
        }
        console.log("drawLineChart:  " + labelList); // DEBUG

        // calculate the scale of Y axis
        var maxValue = -0x80000000;
        for (var i = 0; i < dataLists.length; ++i) {
            maxValue = Math.max(maxValue, Math.max(...dataLists[i]));
        }

        var myChart = new Chart(canvas, {
            type: 'line',
            data: {
                labels: labelList,
                datasets: dataSets
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            max: maxValue * 1.2
                        }
                    }],
                    xAxes: [{
                        type: 'time',
                        time: {
                            displayFormats: {
                                quarter: 'h:mm:ss a'
                            }
                        },
                        gridLines: {
                            offsetGridLines: true
                        }
                    }]
                },
                legend: {
                    position: "bottom"
                },
                hover: {
                    mode: 'nearest',
                    intersect: true
                }
            }
        });
    }

    // get labelList, in this case, the lable list contains all timestamps of data
    // @return: a list of timestamp
    function getTimeStampList(borderRouterWrapper) {
        var dataList = borderRouterWrapper.sensorWrapperList[0].dataList;
        var timeStampList = [];
        for (var i = 0; i < dataList.length; ++i) {
            var data = dataList[i];
            timeStampList.push(parseInt(data.timeStamp));
        }
        console.log("getTimeStampList: --> " + timeStampList.toString());
        return timeStampList;
    }
</script>

<%-- javascript for show current data --%>
<script>
    // default tab is show current data, entrance for display current data feature
    function showCurrentData() {
        document.getElementById("current_data_tab").classList.add("active");
        document.getElementById("history_data_tab").classList.remove("active");
        clearChartDiv();
        getRouterIpAndName(generateSideBarForCurrentData);
    }

    // list all border router names in side bar, add onclick event listener. If
    // user click one of the name, draw data charts for that border router.
    function generateSideBarForCurrentData(routerIpNameList) {
        if (routerIpNameList.size === 0) {
            showAlert("No border router in database.");
            return;
        }
        // create side bar item for each border router
        clearSideBar();
        for (var i = 0; i < routerIpNameList.length; ++i) {
            createNavItemForCurrentData(routerIpNameList[i][0], routerIpNameList[i][1]);
        }
    }

    // create the nav item for the input border router,
    function createNavItemForCurrentData (routerIP, routerName) {
        // create side bar nav item here
        var sideBar = document.getElementById("side_bar")
        var li = document.createElement("li");
        li.classList.add("nav-item");
        var link = document.createElement("a");
        link.classList.add("nav-link");
        link.innerHTML = "<span data-feather=\"cpu\"></span>\n" + routerName;
        // add onclick call back function, draw charts when click on this item
        link.addEventListener('click', function () {
            // clear charts created last time first
            clearChartDiv();
            // get data for this border router
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState === 4 && xhttp.status === 200) {
                    console.log("createNavItemForCurrentData: " + this.responseText); // DEBUG
                    if (this.responseText.length === 0) {
                        showAlert("There is no data got for border router IP: " + routerIP + ", check sensor network connection.")
                        return;
                    }
                    // draw charts for this border router
                    var borderRouterWrapper = JSON.parse(this.responseText);
                    var dataTypeList = getDataTypeList(borderRouterWrapper);
                    var sensorNameList = getSensorNameList(borderRouterWrapper);
                    for (var i = 0; i < dataTypeList.length; ++i) {
                        var dataType = dataTypeList[i];
                        var dataList = getDataListForCurrentData(dataType, borderRouterWrapper);
                        createChartTitle(dataType, routerName);
                        var canvas = createCanvas(900, 300);
                        drawBarChart(sensorNameList, dataList, canvas, dataType);
                    }

                } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                    history.pushState(null, null, "/error");
                    document.write(this.responseText);
                } else if (xhttp.readyState === 4) {
                displayErrorPage(this);
                }
            };
            xhttp.open("GET", "/data/get/" + routerIP + "/sensor", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.setRequestHeader("Accept", "application/json");
            xhttp.setRequestHeader("ModelAttribute", "getDataForBorderRouterFromSensor");
            xhttp.send();
        });
        li.appendChild(link);
        sideBar.appendChild(li);

        // replace feather icon
        feather.replace();
    }

    // create chart title for given datatype
    // @return: a HTML element block contains title
    function createChartTitle(datatype, routerName) {
        var chartDiv = document.getElementById("chart_div");
        var title = document.createElement("div");
        title.setAttribute("class", "d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom");
        title.innerHTML = "<h1 class=\"h2\">" + routerName + ": <span class='text-muted'>" + datatype + "</span></h1>\n";
        chartDiv.appendChild(title);
    }

    function createCanvas(width, height) {
        var div = document.getElementById("chart_div");
        var canvas = document.createElement("canvas");
        canvas.setAttribute("class", "my-4 w-100");
        canvas.setAttribute("width", width);
        canvas.setAttribute("height", height);
        div.appendChild(canvas);
        return canvas;
    }

    function clearChartDiv() {
        var div = document.getElementById("chart_div");
        div.innerHTML = "";
    }

    // get data list for given data type from given border router wrapper
    // @return: a list of data, list<float>
    function getDataListForCurrentData(dataType, borderRouterWrapper) {
        dataList = [];
        for (var i = 0; i < borderRouterWrapper.sensorWrapperList.length; ++i) {
            var sensorWrapper = borderRouterWrapper.sensorWrapperList[i];
            var data = sensorWrapper.dataList[0].dataJson[dataType];
            dataList.push(parseFloat(data));
        }
        console.log(dataList); // DEBUG
        return dataList;
    }

    // delete all nodes of border router nav item in side bar
    function clearSideBar() {
        var sideBar = document.getElementById("side_bar");
        sideBar.innerHTML = "";
    }

    // draw bar chart for the given label list and dataList
    function drawBarChart(labelList, dataList, canvas, dataType) {
        var myChart = new Chart(canvas, {
            type: 'horizontalBar',
            data: {
                labels: labelList,
                datasets: [{
                    data: dataList,
                    label: dataType,
                    borderWidth: 1,
                    borderColor: [
                        "#FE6584", "#38A2E7", "#4EC0BF", "#f9acac",
                        "#f2ff00", "#fa217f", "#fc913a", "#99583b", "#5b3000", "#76877d",
                        "#82a6b1", "#95017b", "#ffd900", "#4376b3", "#9da9b7", "#f46036"
                    ],
                    backgroundColor: [
                        "#FE658440", "#38A2E740", "#4EC0BF40", "#f9acac40",
                        "#f2ff0040", "#fa217f40", "#fc913a40", "#99583b40", "#5b300040", "#76877d40",
                        "#82a6b140", "#95017b40", "#ffd90040", "#4376b340", "#9da9b740", "#f4603640"
                    ],
                    hoverBackgroundColor: [
                        "#FE658480", "#38A2E780", "#4EC0BF80", "#f9acac80",
                        "#f2ff0080", "#fa217f80", "#fc913a80", "#99583b80", "#5b300080", "#76877d80",
                        "#82a6b180", "#95017b80", "#ffd90080", "#4376b380", "#9da9b780", "#f4603680"
                    ]
                }]
            },
            options: {
                scales: {
                    xAxes: [{
                        ticks: {
                            beginAtZero: true
                        },
                        gridLines: {
                            offsetGridLines: true
                        }
                    }],
                    yAxes: [{
                        barPercentage: 0.6,
                        maxBarThickness: 100,
                        gridLines: {
                            offsetGridLines: true
                        }
                    }]
                },
                legend: {
                    display: false
                },
                hover: {
                    mode: 'nearest'
                }
            }
        });
    }

    // get labelList, in this case, the labelList contains all sensor names of a border router
    // @param: border router wrapper object
    // @return: a list of sensor name
    function getSensorNameList(borderRouterWrapper) {
        var labelList = [];
        if (borderRouterWrapper.size === 0) {
            return labelList;
        }
        for (var i = 0; i < borderRouterWrapper.sensorWrapperList.length; ++i) {
            var sensorWrapper = borderRouterWrapper.sensorWrapperList[i];
            labelList.push(sensorWrapper.sensor.sensorName);
        }
        console.log("get label: " + labelList); // DEBUG
        return labelList;
    }

    // get dataTypeList from borderRouterWrapper
    // @param: border router wrapper
    // @Return: a list of data type
    function getDataTypeList(borderRouterWrapper) {
        if (borderRouterWrapper.size === 0) {
            return [];
        }
        var dataObj = borderRouterWrapper
                     .sensorWrapperList[0]
                     .dataList[0]
                     .dataJson;
        var dataTypeList = [];
        for (var dataType in dataObj) {
            if (dataObj.hasOwnProperty(dataType)) {
                dataTypeList.push(dataType);
            }
        }
        console.log(dataTypeList); // DEBUG
        return dataTypeList;
    }

    // show alert in div: #data_alert for the given message
    function showAlert(message, type) {
        if (typeof type === 'undefined') {
            type = 'warning';
        }
        document.getElementById("data_alert").innerHTML =
            "<div  class=\"m-0 alert alert-" + type + " alert-dismissible fade show\" role=\"alert\">\n" +
            "  <h4>" +
            "  <strong> Alert: </strong>" + message +
            "  </h4>" +
            "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
            "    <span aria-hidden=\"true\">&times;</span>\n" +
            "  </button>\n" +
            "</div>";
    }


    //****************************** Debug ***********************************
    // getCurrentData();
    // getHistoryData();
</script>

<!-- Icons -->
<script>
    feather.replace()
</script>


<script>
    /**
     * Scripts for auto data fetching toggle
     */

    // start auto data fetching
    function enableAutoDataFetching(period) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === 4 && xhttp.status === 200) {
                console.log(this.responseText);
            } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                history.pushState(null, null, "/error");
                document.write(this.responseText);
            } else if (xhttp.readyState === 4) {
                displayErrorPage(this);
            }
        }
        xhttp.open("POST", "/setting/startTask/savingData", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.setRequestHeader("Accept", "application/json");
        xhttp.setRequestHeader("ModelAttribute", "startSavingData");
        xhttp.send(period); // 4 seconds period
    }

    // stop auto data fetching
    function disableAutoDataFetching() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === 4 && xhttp.status === 200) {
                console.log(this.responseText);
            } else if (xhttp.readyState === 4 && xhttp.status === 500) {
                history.pushState(null, null, "/error");
                document.write(this.responseText);
            } else if (xhttp.readyState === 4) {
                displayErrorPage(this);
            }
        }
        xhttp.open("POST", "/setting/stopTask/savingData", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.setRequestHeader("Accept", "application/json");
        xhttp.setRequestHeader("ModelAttribute", "stopSavingData");
        xhttp.send();
    }

    // toggle state change call back function
    $(function() {
        $('#toggle1').change(function() {
            if ($('#toggle1').prop('checked')) {
                // pop window, ask for time period
                $('#pop_window_period').modal('toggle');
            } else {
                // disable auto data fetching
                disableAutoDataFetching();
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

    // hide the pop up modal, switch the toggle to "off"
    function closeModal() {
        $('#toggle1').bootstrapToggle('off');
        $('#pop_window_period').modal('hide');
    }

    // check if the input value is valid
    // if true, enable auto data fetching
    // else, switch toggle to 'off',
    // close the pop up modal
    function confirmModal() {
        var inputVal = document.getElementById("period_input").value;
        var period = parseInt(inputVal);
        console.log("modal closed:  " + inputVal);
        if (isNaN((period))) {
            $('#toggle1').bootstrapToggle('off');
        } else {
            enableAutoDataFetching(period);
            // enable auto data fetching
            document.getElementById("toggle_alert").innerHTML =
                "                    <div  class=\" ml-4 float-left alert alert-success alert-dismissible fade show\" role=\"alert\">\n" +
                "                        <strong> Notification: </strong> <br> <strong><span data-feather=\"arrow-left\"></span></strong>Auto Data Fetching Enabled.\n" +
                "                        <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "                            <span aria-hidden=\"true\">&times;</span>\n" +
                "                        </button>\n" +
                "                    </div>";
        }
        $('#pop_window_period').modal('hide');
    }

</script>
</body>
</html>

