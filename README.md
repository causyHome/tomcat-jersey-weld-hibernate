## Tomcat PoC: JAX-RS, CDI & JPA

This is a proof of concept to try creating a backend environment relying only on a servlet container (Tomcat 8), without using a full-blown JEE server or Spring. 

The features we want to implement here are:

  - REST Web services: [JAX-RS](https://jax-rs-spec.java.net), using [Jersey](https://jersey.java.net/) implementation
  - Dependency injection: [CDI](http://docs.oracle.com/javaee/6/tutorial/doc/giwhl.html), using [Weld](http://weld.cdi-spec.org/) implementation
  - Object-Relationnal Mapping: [JPA](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html), using [Hibernate](http://hibernate.org/) implementation


## Testing
After building the war and deploying it in tomcat:

- JAX-RS: `http://localhost:8080/<appName>/service/message/`
- JAX-RS: `http://localhost:8080/<appName>/service/person/get`
- JAX-RS & CDI: `http://localhost:8080/<appName>/service/person/get/anonymous`
- JPA: unit tested in [HibernateSetupTest.java](https://bitbucket.org/causyhome/tomcat-jersey-weld-hibernate/src/22cdf66ca0a1e379d6fb59042c1aed78ae95fdf7/src/test/java/com/causy/persistence/HibernateSetupTest.java?fileviewer=file-view-default_)