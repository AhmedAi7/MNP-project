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
        <li><a href="#project-architecture">Project Architecture</a></li>
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

### Project Architecture
The project contains of main components:
* Controller
* Interfaces for Services
* Service
* Repository
* Payload: Objects of messages that should be sent from or to user and Enum of all request Status to avoid mistyping errors.
* Secuirty: A middleware component to a the authenticate all requests before access the APIs.
* Exceptions: Contains a Global Exception handler and a Set of all the error messages passed by ath APIs

### Database Design
Database contains a three tables:
* Request : Contains any request sended by any operator to another and its status either ACCEPTED, REJECTED, PENDING or CANCELD
* Operator: Contains all available Operators and their headers
* Phone_number : Contains any ported number (any phone number has at least one ACCEPTED request and has changed his default operator) and its current operator
### APIs Authentication
APIs is fully Authenticated via a Custom Header based authentication. Any request should contains a header named "organization" to specify the operator sends the request . By a custom authentication filter , the system checks if the operator header is correct and already exists in data base table (Operator), else it will not accept the request and throw an exception.

### APIs Main Functions
* submitRequest(): This function is responsible for submitting a new porting request, it takes a requestPayload object which contains request data needed to submit as a parameter and validate this request in case it is not valid it returns BAD_REQUEST with a message explain what is the wrong, Else it save this request in the Database and returns HttpStatus.OK.
* acceptRequest() , rejectRequest(): Those functions are responsible for accepting or rejecting a porting request, it takes a request ID as a parameter and validate this request in case it is not exist or not a pending request it returns BAD_REQUEST with a message explain what is the wrong, Else it save the new status of the request in the Database and returns HttpStatus.OK.
* cancelRequest(): This function is responsible for canceling all requests that exceed their life time, it's being run automatically and independently with a @Scheduled method as a background task.
* getPhoneNumStatus(): This function is responsible for getting the current phone number status and the current mobile network holder if the number is ported.
* getAllRequests(): This function is responsible for view all requests (If the operator is a recipient or donor or its an ACCEPTED request)


<!-- GETTING STARTED -->
## Getting Started


### Prerequisites

This is a list of things you need to use the software and how to install them.
* Spring Boot
* MySQL

### Installation

1. Install Spring Boot using this Link [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html)
2. Download and install ```MySQL Installer 8.0.26``` using this link [MYSQL](https://dev.mysql.com/downloads/installer/)  
