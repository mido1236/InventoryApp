# InventoryApp

This is a Spring Boot demo application, created using Intellij Ultimate Edition. 

Instructions:

1- Clone the repo and open using Intellij Ultimate Edition
2- The project's depedencies are setup using Maven, so this should be handled automatically
3- Create a local mysql database at port 3306 with the database db_example. You won't need to do create anything else in the database,
as the project will create the tables as needed
4- To run the project, setup a Spring Boot run configuration. This was load a test Tomcat server on port 8080
5- The endpoints are http://localhost:8080/orders/ and http://localhost:8080/inventories
6- The tests can be found under src\test\java\com\example\inventory\demo, with two test files InventoryControllerTests and OrderControllerTests. 
Simply run them as Junit tests (Incidentally, the tests can be run without a mysql database)
