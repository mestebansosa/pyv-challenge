# Fullstack SW Dev challenge

## The Project:
I used a spring guide for rest service to create a web server in order to connecto to the Payvision server.
[Spring guide] (https://spring.io/guides/gs/rest-service/)

Import to Eclipse Oxygen.2 Release (4.7.2) as a Maven Project.

Adapt Project to include the endpoints required in the challenge. 
The Project maintains the original gradle configuration, but I only used the Maven configuration.
I let some classes that at the end are not used, like RestTemplateClient.java and Transactions.java.

Besides the Project includes `swagger-ui` where you can see all the API configuration.

## The service on port 9100:
Encapsulate the authorization and connect to the Payvison server.
It can be configured in the application.properties, and also has default values activated inside the code.

## The Swagger console:
[Access throguh swagger] (http://localhost:9100/swagger-ui.html#)
The service has one Controller: `transaction-controller` with two endPoints
- GET **/filter?name=x** (where x can be action, currencyCode and orderBy). This methods returns the possible values for each filter, and it used by the COnsole in order to set the values in the dropdown list.
- GET **/transactions** with no parameters return all the transactions. With parameters return the filtered transactions. 

You can access directly to the controller endpoints by swagger or by a browser on the port 9100. Examples:
- [Access through browser] (http://localhost:9100/transactions)
- [Access through browser] (http://localhost:9100/transactions?action=credit&currencyCode=EUR)

## The Console service on port 8080
Made with `angularjs and Bootstrap`, it is deployed on the `tomcat` server (embedded by spring boot) and runs on the default port 8080.
The console connects to the service on port 9100, and at the start up makes the following rest operation to fill out the table and to get the current filter values:
- (http://localhost:9100/transactions)
- (http://localhost:9100/filters?name=action)
- (http://localhost:9100/filters?name=currencyCode)
- (http://localhost:9100/filters?name=orderBy)

## The Tests:
- For the service, I created a jmeter to have a sunny day test plan for the endPoints. It can be found at `gs-rest-service\complete\src\test\java\payvision.jmx`. You can open with any jmeter versión 4.0
- For the console, I did not create any automated tests. Tested manually in Chrome, IE and Firefox (current versions)

## The source code
The java source code can be found under the folder src\main\java

The html and angularjs code can be found under the folder src\main\resources

I can be cloned from github and imported into Eclipse as a maven project.

It should be executed with the maven command: **spring-boot:run**

To generate the executable jar file, the maven command **install** will generate a jar file called: **gs-rest-service-0.1.0.jar**

## Execution with the jar file: 
In any machine with java 8 installed, execute: **java -jar gs-rest-service-0.1.0.jar**

When the service is up, the you can open the console in your browser with http://localhost:9100/transactions and you will see:
![image](https://user-images.githubusercontent.com/26966488/43990739-08e1c3ba-9d60-11e8-8172-529aba7a12ca.png)


## TODO:
- I would prefer to separate the application in two services. The console service and the service itself. It should be two different processes. It is better that each service do what they have to do.
- The console HTML includes the css. It is better to separate the look and feel in a ccs configuration apart.
- Not configured to have log4j log file. Traces are shown on the console, but limited.

