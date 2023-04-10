# NS Joke Application

This application is build with :
- Java 17 (compiler) 
- SpringBoot 3 
- maven 3.8.1

## Swagger page

Once started the swagger page can found on 
 - [my machine](http://localhost:8080/swagger-ui.html)

## ControllerAdvice

As a bonus I've added a controller advice for handling NSServiceException which will be thrown when a non functional error is thrown.
So if the jokeApi throws a RestClientException it'll be converted to an NSServiceException.

I didn't add any unittest for this functionality. The classes used should typically reside in a jar file which should be added as a dependency.

