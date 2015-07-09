# ZKBASE #

This project serves as example code base using following J2EE open-source frameworks and tools:

  * [ZK Framework](http://www.zkoss.org/)
  * [Hibernate](http://www.hibernate.org/)
  * [Spring](http://www.springsource.org/)
  * [Spring Security (Acegi)](http://static.springsource.org/spring-security/site/index.html)
  * [Maven](http://maven.apache.org)

### Overview ###
The main focus lies on the integration of the ZK front-end framework. However, as the backend is also realized further frameworks are considerd. For instance the persistance framework Hibernate is used for mapping the domain objects to a relational database (tested on MySQL, Oracle and HSQL). JPA is used to generalize the interface to the Hibernate layer.
Spring comes into play for wiring the various layers together as well as for providing the desired level of security and user authentication.
Maven serves as building tool allowing a smooth build process as well as the execution of the application.

### The Application ###
The purpose of the application is to show the functioning of common features web applications nowadays have implemented. Within this scope user authentication, securing of content, sorted lists, pagination, and so forth can be found.

Following list demonstrates the basic workflow:
  1. On the start page the "Init"-button will set up the user-database with one admin account and 100 user accounts.
  1. It is possible to log in using either the admin account or one of the 100 user accounts.
  1. Dependent of the roles (ROLE\_ADMIN, ROLE\_USER,..) the access rights are restricted for certain areas. For instance, a user can only alter his own profile whereas the admin can alter all profiles in the system.

### The Features ###
...regarding ZK
  * MVC based on [ZkToDo2](http://www.zkoss.org/smalltalks/mvc4/)
  * listbox including pagination, sorting
  * menu

...regarding Spring
  * dependency injections
  * auto-wiring
  * security

...regarding JPA/Hibernate
  * annotation driven transactions

...regarding Maven
  * embedded Jetty web server (target: jetty:run)
