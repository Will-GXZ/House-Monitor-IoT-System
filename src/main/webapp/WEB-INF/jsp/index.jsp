<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Xiaozheng Guo">

    <title>House Monitor Sensor Network System Index Page</title>

    <!-- Bootstrap core CSS -->
    <link href="${contextPath}/index/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="${contextPath}/index/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

    <!-- Plugin CSS -->
    <link href="${contextPath}/index/vendor/magnific-popup/magnific-popup.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${contextPath}/index/css/creative.min.css" rel="stylesheet">

</head>

<body id="page-top">

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="#page-top">TWL sensor network</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="#start">start</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="#about">about</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="#contact">Contact</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<header class="masthead text-center text-white d-flex">
    <div class="container my-auto">
        <div class="row">
            <div class="col-lg-10 mx-auto">
                <h1 class="text-uppercase">
                    TWL Sensor Network System
                </h1>
                <h1>
                    <strong>House Monitor</strong>
                </h1>
                <hr>
            </div>
            <div class="col-lg-8 mx-auto">
                <p class="text-faded mb-5">Monitor, store and analyze environmental data in your house. </p>
                <a class="btn btn-primary btn-xl js-scroll-trigger" href="#start">find out more</a>
            </div>
        </div>
    </div>
</header>

<section class="bg-primary" id="start">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 mx-auto text-center">
                <h2 class="section-heading text-white">It is easy to use!</h2>
                <hr class="light my-4">
                <p class="text-faded mb-4">Just type in your border router IPv6 address, our application will find all sensors connected to your border router automatically. All our interfaces are self-explained, just click your mouse and see what's going on in your house!</p>
                <a class="btn btn-light mx-3 btn-xl js-scroll-trigger" href="${contextPath}/page/dataTypeSettingPage">Get Started</a>
                <a class="btn btn-light mx-3 btn-xl js-scroll-trigger" href="${contextPath}/page/monitorDataPage">Continue</a>
            </div>
        </div>
    </div>
</section>

<section class="bg-dark text-white" id="about">
    <div class="container text-center">
        <h2 class="mb-4">House Monitor Sensor Network System</h2>
        <p class="text-faded mb-4">
            This project is a full-stack project that can communicate with sensor networks using CoAP protocol. It can collect
            environmental data sets from sensors and store them in database. The back-end part provides a set of RESTful API
            that are easy to program with. Other developers can use this set of API in their Android, IOS or Web applications.
            See <a href="https://will-gxz.github.io/House-Monitor-IoT-System/">JavaDoc</a> for more information. Also, the application has a front-end part that communicates with the back-end part
            via RESTful API. The front-end pages allow users to set up the application and monitor data from a visual interface in
            their browser.
        </p>
        <a class="btn btn-light btn-xl sr-button" href="https://github.com/Will-GXZ/smart-home-sensor-network-system">View source code</a>
    </div>
</section>

<section id="contact">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 mx-auto text-center">
                <h2 class="section-heading">Let's Get In Touch!</h2>
                <hr class="my-4">
                <p class="mb-5">This is the Master's project of Xiaozheng Guo and Xiaofei Guo in Tufts University. If you have any question or advice, please feel free to contact us.</p>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3"></div>
            <div class="col-lg-3 mr-auto text-center">
                <i class="fa fa-envelope-o fa-3x mb-3 sr-contact"></i>
                <p>
                    <a href="mailto:xguo.tufts@gmail.com">xguo.tufts@gmail.com</a>
                </p>
            </div>
            <div class="col-lg-3 mr-auto text-center">
                <i class="fa fa-envelope-o fa-3x mb-3 sr-contact"></i>
                <p>
                    <a href="mailto:elfiegxf@gmail.com">elfiegxf@gmail.com</a>
                </p>
            </div>
            <div class="col-lg-3"></div>
        </div>
    </div>
</section>

<!-- Bootstrap core JavaScript -->
<script src="${contextPath}/index/vendor/jquery/jquery.min.js"></script>
<script src="${contextPath}/index/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${contextPath}/index/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${contextPath}/index/vendor/scrollreveal/scrollreveal.min.js"></script>
<script src="${contextPath}/index/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

<!-- Custom scripts for this template -->
<script src="${contextPath}/index/js/creative.min.js"></script>

</body>

</html>
