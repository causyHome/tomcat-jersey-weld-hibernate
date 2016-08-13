## Tomcat PoC: JAX-RS, CDI & JPA

This is a proof of concept to try creating a backend environment relying only on a servlet container (Tomcat 8), without using a full-blown JEE server or Spring. 

The features we want to implement here are:

  - Web services: [JAX-RS](https://jax-rs-spec.java.net), using [Jersey](https://jersey.java.net/) implementation
  - Dependency injection: [CDI](http://docs.oracle.com/javaee/6/tutorial/doc/giwhl.html), using [Weld](weld.cdi-spec.org/) implementation
  - *ORM: [JPA](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html), using [Hibernate](hibernate.org/) implementation (Work in progress)*


## Testing
After building the war and deploying it in tomcat:

- JAX-RS: `http://localhost:8080/<appName>/service/message/`
- JAX-RS: `http://localhost:8080/<appName>/service/person/get`
- JAX-RS & CDI: `http://localhost:8080/<appName>/service/person/get/anonymous`