# EISGroup Practical Test

## Currency Change API - Java Spring Boot

As part of my practical test I was supposed to develop an application that would query "Lietuvos Bankas" 
for currency data, preprocess the data and return it to the client. 

The application is developed with Java 11 (Amazon Correto JDK 11) and Spring Boot framework as a RESTful API.

### The project consists of 3 packages: 
    * controllers - Pakacge that hold HTTP controllers
    * models - Package with POJOs
    * services - Package containing application specific services

### In order to run the application:
    1). Clone the repository or download and uzip it.
    2). Open project with your preffered IDE that support Spring Boot (STS, Eclipse or IntelliJ)
    3). Build the project with maven - mvn clean install. It will get all dependencies required for the project
    4). Run/Debug the project - by default API is configured to run on port 12126.

### Making request to an API: 

    API has a single endpoint - /api/currency (POST)

    To make a request, open Postman and create a new request with the URL: http://localhost:12126/api/currency
    and reuqest body: 
    
```
    {
	    "codes": [
		    "BGN", "BRL"
		],
	    "fromDate": "27/11/2019",
	    "toDate": "02/12/2019"
    }
```

API should return the following response back to the client:

```
[
    {
        "code": "BGN",
        "name": "Bulgarijos levas",
        "currencyChange": 0.0,
        "perCurrencyChange": 0.0
    },
    {
        "code": "BRL",
        "name": "Brazilijos realas",
        "currencyChange": -0.008899999999999686,
        "perCurrencyChange": 0.0
    }
]

```