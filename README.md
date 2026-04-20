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

- Implement a subclass of javax.ws.rs.core.Application and use the @ApplicationPath("/api/v1")
annotation to establish your API’s versioned entry point.

Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a
new instance instantiated for every incoming request, or does the runtime treat it as a
singleton? Elaborate on how this architectural decision impacts the way you manage and
synchronize your in-memory data structures (maps/lists) to prevent data loss or race con-
ditions.

















