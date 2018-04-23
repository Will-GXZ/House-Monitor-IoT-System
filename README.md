# House Monitor Sensor Network System
_update April 16, 2018_

This repository contains the source code of a smart home sensor network system created by Xiaozheng(Will) Guo and Xiaofei(Elfie) Guo, which is also our Master's project.

If you have any questions or advice, please feel free to contact us:  

**Email:**   
Will: _xguo.tufts@gmail.com_   
Elfie: _elfiegxf@gmail.com_  

<br>

---
## Introduction
This project is a web application that can communicate with sensor networks using CoAP protocol. It can collect environmental data sets from sensors and store them in database. The back-end part of the application provides a set of RESTful API that are easy to program with. Other developers can use this set of API in their Android, IOS or Web applications.

Also, the application has a front-end part that communicates with the back-end part via RESTful API. The front-end pages allow users to set up the application and monitor data from a visual interface in their browser.

* ### Main Techniques
  * Using **Spring MVC** for the management of web application page flow;
  * Using **Hibernate** for object-relational mapping between object-oriented domain and **MySQL database**;
  * Using **Apache Tomcat Catalina** as servlet container;
  * Using **Bootstrap 4** framework to develop front-end pages;
  * Using **Apache Maven** to build the project and manage its dependencies;
  
<br>

## Technology Stack
* Spring Core
* Spring MVC
* Hibernate
* MySQL
* BootStrap
* HTML, CSS
* JavaScript
* JQuery
* Chart.js
* Maven
* IntelliJ Idea
* Git
* Californium (Cf) CoAP framework
* Linux
* Contiki

<br>

## Architecture
This part covers the basic structure of the whole project. The project can be divided 

![architecture picture](https://dl.dropboxusercontent.com/s/pnrg9p13vq8ooc3/architecture.png?dl=0)






