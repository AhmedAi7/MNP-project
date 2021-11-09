<!-- PROJECT LOGO -->
<br />
<p align="center">
  
  <h2 align="center">MNP-project</h2>


<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
     <a href="#general-overview">General Overview</a>
    </li>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#database-design">Database Design</a></li>
        <li><a href="#apis-authentication">APIs Authentication</a></li>
        <li><a href="#apis-main-functions">APIs Main functions</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#how-it-works">How It Works</a></li>
  </ol>
</details>

<!-- GENERAL OVERVIEW -->
## General Overview
Mobile Number Portability (MNP) is a service that enables the mobile telephone user to retain his/her own number while switching from one mobile network operator ( Donor ) to another (Recipient ). The MNP gives the subscriber the right to retain his/her number regardless of the mobile service rovider and gives him/her the freedom to port his/her number to the eligible operator without forcing him/her to lose his/her number

<!-- ABOUT THE PROJECT -->
## About The Project
This Project implement the needed APIs to simulate a simple MNP functions including:
* submitRequest()
* acceptRequest()
* rejectRequest()
* cancelRequest()
* getPhoneNumStatus()
* getAllRequests()

### Built With

This section list any major frameworks that is used in buliding the project:
* [Spring Boot](https://spring.io/)
* [MySQL](https://www.mysql.com/)

### Database Design
Database contains a three tables:
* Request : Contains any request sended by any operator to another and its status either ACCEPTED, REJECTED, PENDING or CANCELD
* Operator: Contains all available Operators and their headers
* Phone_number : Contains any ported number (any phone number has at least one ACCEPTED request and has changed his default operator)
### APIs Authentication
APIs is fully Authenticated via a Custom Header based authentication. Any request should contains a header named "organization" to specify the operator sends the request . By a custom authentication filter , the system checks if the operator header is correct and already exists in data base table (Operator), else it will not accept the request and throw an exception.

### APIs Main Functions



<!-- GETTING STARTED -->
## Getting Started


### Prerequisites

This is a list of things you need to use the software and how to install them.
* Spring Boot
* MySQL

### Installation

1. Install Spring Boot using this Link [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html)
2. Download and install ```MySQL Installer 8.0.26``` using this link [MYSQL](https://dev.mysql.com/downloads/installer/)  

<!-- HHOW IT WORKS -->
## How It Works
