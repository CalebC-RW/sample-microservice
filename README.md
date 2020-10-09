# Sample Service with OpenFeign

This service demonstrates how to perform common operations with best practices.
This includes writing Constructors, Services, Repositories, and unit tests for them.

There are two services in this project: Sample Service and Sample Service Consumer.

## Sample Service
This service demonstrates the best practices mentioned above.

To build the service, run:

```bash
mvn clean package
```

Then, to start the service (port 8080), run:

```java
java -jar sample-app/target/sample-app-<version>.jar
```

where `<version>` is the version of the service (e.g. `1.0.0-SNAPSHOT`)

The service can be tested by visiting its endpoints, for example:

```
http://localhost:8080/api/people
```

This service contains several modules that allow for OpenFeign to work properly.

### Modules
* `service-app`: contains the service implementation and assembly
    * depends on `service-rest`
* `service-model`: contains the service's model classes
    * very few dependencies
* `service-rest`: contains the interfaces to the service's controllers
    * depends on `service-model`
* `service-rest-client`: contains the OpenFeign interfaces
    * depends on `service-rest`

## Sample Service Consumer
This service demonstrates how the OpenFeign client is used.  The only configuration
needed is the location of the service it is consuming (see `/src/main/resources/application.yml`).

To start the consumer service (port 8088), run:

```java
java -jar sample-app/target/sample-app-<version>.jar
```

To demonstrate the OpenFeign operation, run both services and then visit:

```
http://localhost:8088/mypeeps
```

This will call the sample service's REST endpoint `/api/people` and return the list of people.