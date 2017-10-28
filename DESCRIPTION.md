# Implementation description

## How to build and run

 To build and run the application you need the JDK 8 to be installed.
 Project was built and tested on Oracle JDK 8u121.
 
 ##### Building and running on Linux/macOS
 
 `./gradlew clean build && java -jar build/libs/java-test-task-0.0.1-SNAPSHOT.jar`
 
##### Building and running on Windows
 
 `gradlew.bat clean build`
 `java -jar build/libs/java-test-task-0.0.1-SNAPSHOT.jar`
 
 ##### REST API docs
 
 After launching the app, use Swagger UI:
 
 http://localhost:8080/swagger-ui.html
 
## Implementation comments

* Task 1: Implemented non-blocking REST endpoints via Spring Boot, Java 8 CompletableFuture and embedded Undertow HTTP server.
* Task 2: Created high throughput SSL socket client with connection pooling via Netty (non-blocking IO). 
For additional system reliability wrapped external system call with circuit breaker via Spring Cloud and Netflix Hystrix libraries.
 