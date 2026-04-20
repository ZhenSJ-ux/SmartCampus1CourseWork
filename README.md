Module: Client Server Architectures
student id:w2086907
Title: ”Smart Campus” Sensor & Room Management API
1. Introduction & Scenario
Scenario: You have been appointed as the Lead Backend Architect for the university’s
”Smart Campus” initiative. What began as a pilot project for tracking individual tempera-
ture sensors has evolved into a comprehensive campus-wide infrastructure project. The
university now requires a robust, scalable, and highly available RESTful API to manage
thousands of Rooms and the diverse array of Sensors (e.g., CO2 monitors, occupancy
trackers, smart lighting controllers) contained within them.
This system will be built as a high-performance web service using JAX-RS (Jakarta RESTful
Web Services). Your goal is to provide a seamless interface for campus facilities managers
and automated building systems to interact with the campus data.
Objective: This coursework is designed to simulate a real-world development task. It
assesses your proficiency in RESTful architectural patterns, the implementation of re-
source nesting, and the design of a resilient error-handling strategy using JAX-RS. You
are expected to demonstrate industry-standard practices, including the use of appropri-
ate HTTP status codes, meaningful JSON responses, and a logical resource hierarchy that
reflects the physical structure of the campus

## Overview:
This model for the RESTful API was built using JAVA netbeans and uses JAX-RS for creating managing rooms, 
assign sensors to the rooms and record sensor reading rush as temperture or CO2 levels. The system uses JAX-RS
and uses RESTful principles, which includes resource based enpoint, HTTP status codes, and stateless communication.
Data will be stored in a concurrent data structure that will be the backend of the datastore. The API will have error
handling uses for custom exceptions and exception mappers, which will also have logging filters to track incoming request and 
responses. 

## Features
- Room management (create, get, delete)
- Sensor management with room linking
- Sensor readings (nested resources)
- Logging filter for requests and responses
- Exception handling e.g(409, 402, 403, 500)

## Base URL
http://localhost:8080/SmartCampus1/api/v1

## Endpoints
- GET /rooms
- POST /rooms
- DELETE /rooms/{id}
- GET /sensors
- POST /sensors
- GET /sensors/{id}/readings
- POST /sensors/{id}/readings

## Technologies
- Java
- JAX-RS (Jersey)
- Tomcat


## Part1 
1. Project & Application Configuration (5 Marks):

Bootstrap a Maven project integrating a JAX-RS implementation (e.g., Jersey) and a lightweight
servlet container or embedded server.

Maven project integrating a JAX-RS implementation (e.g., Jersey):
 <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.mycompany</groupId>
    <artifactId>SmartCampus1</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!-- added this war instead of jar as it was for tomcat--> 
    <!-- All of this will be based for part 1 for the start for using tomcat-->
    <packaging>war</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <exec.mainClass>com.mycompany.smartcampus1.SmartCampus1</exec.mainClass>
        
        <!-- This part was for the Jersey version for REST api-->
        <jersey.version>2.39</jersey.version>
    </properties>
    <dependencies>
        <!-- This is the Jersey servlet container which runs the REST API on Tomcat-->
        <dependency>
            <groupId>org.glassfish.jersey.containers</groupId>
            <artifactId>jersey-container-servlet</artifactId>
            <version>${jersey.version}</version>
        </dependency>

        <dependency>
            <groupId>org.glassfish.jersey.inject</groupId>
            <artifactId>jersey-hk2</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <!-- JASON uses to convert the objects in JAVA--> 
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-json-jackson</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <!-- This was used for the JAX-RS API for like @GET, @POST-->
        <dependency>
            <groupId>javax.ws.rs</groupId>
            <artifactId>javax.ws.rs-api</artifactId>
            <version>2.1.1</version>
            <scope>provided</scope>
        </dependency>
        <!-- Uses servelt API for the required in tomcat-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    <!-- Shows the name of the URL that will appear-->
    <build>
        <finalName>SmartCampus</finalName>
    </build>

</project>


servlet container:

<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
         http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
         <!-- All Part1 and some Part5-->
    <!-- PART1: Register Jersey as a RESTAPI servlet-->
    <servlet>
        <servlet-name>jersey-servlet</servlet-name>
        <!-- Handles all the REST request from Jersey servlet container-->
        <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
    <!-- To tell Jersey  where to find the resources, mappers and filters-->
        <init-param>
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>
                com.mycompany.smartcampus1.resource,<!--Part1 REST endpoint-->
                com.mycompany.smartcampus1.mappers, <!--Part5 ExceptionMappers-->
                com.mycompany.smartcampus1.filter <!--Part5 LoggingFilter-->
            </param-value>
        </init-param>
        <!-- load servlet when server starts-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>jersey-servlet</servlet-name> <!-- Maps the API to Jersey-->
        <url-pattern>/api/v1/*</url-pattern> <!-- this is based path for all API endpoint-->
    </servlet-mapping>

</web-app>





Implement a subclass of javax.ws.rs.core.Application and use the @ApplicationPath("/api/v1")
annotation to establish your API’s versioned entry point: 

//PART1 Configuration class for the REST API
import javax.ws.rs.ApplicationPath;   // Base API path 
import javax.ws.rs.core.Application; // This shows the based class for configuring JAX-RS application

@ApplicationPath("/api/v1") // Sets the base URL for all endpoints, which for example ”rooms” -> ”/api/v1/rooms”)
public class ApplicationConfig extends Application {

    // Empty class – used only to activate JAX-RS and define base path
    // Jersey automatically scans for resources and providers
}



Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a
new instance instantiated for every incoming request, or does the runtime treat it as a
singleton? Elaborate on how this architectural decision impacts the way you manage and
synchronize your in-memory data structures (maps/lists) to prevent data loss or race con-
ditions.

Life cycle: 
**// This class acts as an in-memory database 
public class DataStore {

    // Stores all rooms using room ID as key
    public static final Map<String, Room> rooms = new ConcurrentHashMap<>();

    // Stores all sensors using sensor ID as key
    public static final Map<String, Sensor> sensors = new ConcurrentHashMap<>();

    // Stores sensor readings:
    // Key = sensor ID
    // Value = list of readings for that sensor
    public static final Map<String, List<SensorReading>> readings = new ConcurrentHashMap<>();

    // Private constructor prevents creating instances of this class
    // (since we only use static data)
    private DataStore() {
    }
}

Answer: 
JAX-RS classes uses request scoped which takes new resources instances to create the incoming request than treating it as a singleton.
Due to this lifecycle, the in memory data would not be stored in the normal instances fields that is inside of the classes. As the resources
would just belong to one request than having multiple which then becomes not as useful. For this model, all of this is stored in the DataStore
class as it uses static collections. It makes sure that the request access the same in memory data. This is done so that when multiple client
uses the API, it will stay consistance to reduce the risk of lost updates. This is why we use the ConcurrentHashMap as it is used
for thread safe data storage in multi threaded environments (examples uses for rooms, sensors and readings).


The ”Discovery” Endpoint (5 Marks):

- Implement a root ”Discovery” endpoint at GET /api/v1. This should return a JSON object
providing essential API metadata: versioning info, administrative contact details, and a
map of primary resource collections (e.g., ”rooms” -> ”/api/v1/rooms”).


// PART 1: Discovery endpoint 

import java.util.HashMap;          // Used to create response objects
import java.util.Map;              // Key-value structure for JSON response
import javax.ws.rs.GET;            // HTTP GET method
import javax.ws.rs.Path;           // Defines endpoint path
import javax.ws.rs.Produces;       // Defines response format
import javax.ws.rs.core.MediaType; // Media type (JSON)

// Base endpoint: /api/v1
@Path("/")
@Produces(MediaType.APPLICATION_JSON) // Return JSON responses

public class DiscoveryResource {
    
    // PART 1: Simple test endpoint to check API is running
    @GET
    public Map<String, Object> discover() {

        // Create main response object
        Map<String, Object> response = new HashMap<>();

        // Basic API information
        response.put("api", "Smart Campus Sensor & Room Management API"); // API name
        response.put("version", "v1");                                    // API version
        response.put("contact", "yourname@westminster.ac.uk");            // Contact info just for example

        // Create links to available resources
        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");     // Endpoint for rooms
        links.put("sensors", "/api/v1/sensors"); // Endpoint for sensors

        // Add links to response
        response.put("resources", links);

        // Return JSON response
        return response;
    }
}


- Question: Why is the provision of ”Hypermedia” (links and navigation within responses)
considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach
benefit client developers compared to static documentation?


## Part2












