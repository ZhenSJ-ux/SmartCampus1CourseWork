Module: Client Server Architectures
student id:w2086907
Title: ”Smart Campus” Sensor & Room Management API


## Part1 Service Architecture & Setup (10 Marks)

Project & Application Configuration (5 Marks)
Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a
new instance instantiated for every incoming request, or does the runtime treat it as a
singleton? Elaborate on how this architectural decision impacts the way you manage and
synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.

Answer: 
JAX-RS classes uses request scoped which takes new resources instances to create the incoming request than treating it as a singleton.
Due to this lifecycle, the in memory data would not be stored in the normal instances fields that is inside of the classes. As the resources
would just belong to one request than having multiple which then becomes not as useful. For this model, all of this is stored in the DataStore
class as it uses static collections. It makes sure that the request access the same in memory data. This is done so that when multiple client
uses the API, it will stay consistance to reduce the risk of lost updates. This is why we use the ConcurrentHashMap as it is used
for thread safe data storage in multi threaded environments (examples uses for rooms, sensors and readings).


The ”Discovery” Endpoint (5 Marks)
- Question: Why is the provision of ”Hypermedia” (links and navigation within responses)
considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach
benefit client developers compared to static documentation?

Answer:
Hypermedia is considered a hall mark of advanced RESTful design as it allows clients to discover the resources that is available and that the actions goes through the 
API response. How this benefits the cleint developers compared to static documentation in several ways such as the reducing the need for hardcoded URLs, when the server
provides the correct links for the runtime. It also helps the maintainability as the endpoint can change without breaking any existing client. This helps to enchances 
the usability as the developers can explore and have ways of interaction with the API without any uses of documentation.

## Part2 Room Management (20 Marks)

Room Resource Implementation (10 Marks)
- Question: When returning a list of rooms, what are the implications of returning only
IDs versus returning the full room objects? Consider network bandwidth and client side
processing.

Answer:
When returing only room IDs it will use less network bandwidth which means it uses smaller response. This mean it can be processed faster when it comes to many
rooms. But this then creates limited information which clients would need to make more request for full room details. This then increases the numbers of API
requests. If the room objects gives the client all relvant information with in one response. It will be convenient as it would allow to reduce the need of
extra request which then becomes a problem as the repsones is bigger and bandwidth would increase the usage, in which will result into both uses for client and server.
In the end returning just the IDs is more reliable as it does not need to use more bandwidth while returing full room will be used for client. 

Room Deletion & Safety Logic (10 Marks)
- Question: Is the DELETE operation idempotent in your implementation? Provide a detailed
justification by describing what happens if a client mistakenly sends the exact same DELETE
request for a room multiple times.

Answer:
The DELETE is idempotent as this is in implementation. If the room exists and has no sensors assigned to it, then the DELETE can request to remove it successfully. However, if the client send the exact same DELETE request, then the room does not exisit and will return with a API saying 404 Not Found. The server state will not change after the first deletion which would then result the operation is still idempotent.

## Part 3: Sensor Operations & Linking (20 Marks)
Sensor Resource & Integrity (10 Marks)
- Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on
the POST method. Explain the technical consequences if a client attempts to send data in
a different format, such as text/plain or application/xml. How does JAX-RS handle this
mismatch?

Answer:
When using @Consumes (MediaType.APPLICATION_JSON) it will let JAX-RS POST to only accepts request bodies that is in JSON format.
So when the client sends the data that uses different content type (example application/xml) it will request the media type that
is not matched with what the method is connected to. Therefore, JAX-RS will not make attempts to the map the body and would return
HTTP 415.


Filtered Retrieval & Search (10 Marks)
- Question: You implemented this filtering using @QueryParam. Contrast this with an alterna-
tive design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why
is the query parameter approach generally considered superior for filtering and searching
collections?

Answer:
Why @QueryParam is considered more appropriate for filtering and searching is because if client is still on request for the same collection for resource,
/sensor, which then looks for narrowing the results. Example such as /sensor?type=CO2 which means to show the collection but only that are CO2. Using
/api/vl/sensors/type/CO2 would consider to be less useful as it filter look for other resources. Which is why Query parameters are more flexible and
easier to extend when more filters are needed.

## Part 4: Deep Nesting with Sub - Resources (20 Marks)

The Sub-Resource Locator Pattern (10 Marks)
- Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How
does delegating logic to separate classes help manage complexity in large APIs compared
to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?

Answer:
The Sub resource locator pattern improves API design by spliting the tasks into smaller bits, which dedicated classes instead of
placing all logic into one controller. Doing this the design becomes more simpler to manage as it improves the readability of the 
separation and has a clear defined responsibility, as all of this would not affect the API. When using this in a larger scale system, 
it becomes to show its usefulness due to it reduces duplication, easier to extend the API with nested resources and supports better
organisation for the future. But defining all the nested paths like sensors/{id}/readings/{rid} would become difficult to understand as
testing and maintaining would take much more of a longer approach. 


## Part 5: Advanced Error Handling, Exception Mapping & Logging (30 Marks)

Dependency Validation (422 Unprocessable Entity) (10 Marks)
- Question: Why is HTTP 422 often considered more semantically accurate than a standard
404 when the issue is a missing reference inside a valid JSON payload?
  
Answer:
HTTP 422 Unprocessable Entity is considered more semantically accurate thatn the standard 404 Not Found when dealing with missing references
inside a valid JSON payload is because of the correctness of the data being submitted. For example if the client has sent a syntactically valid request
that the server is able to understand but the request is not granted due to validation errors, like referencing the room that does not exist when creating
a sensor. The use for 422 can communicate with the structure of the request that is correct with the content invalid, which means the client can understand
the error. On the other end with 404 response, it would incorrectly suggerst the request resource not be found. This creates problem as it cant accurately
reflect the problem. Which is why using 422 improves API clarity, aligns with RESTful principles, and provides more meaningful feedback to the client.

The Global Safety Net (500) (5 Marks)
- Question: From a cybersecurity standpoint, explain the risks associated with exposing
internal Java stack traces to external API consumers. What specific information could an
attacker gather from such a trace?

Answer:
From a cybersecurity perspective, exposing internal Java stack traces to external API consumer poses serious risk as they can reveal detailed information
about the internal working of the application. The stack traces may have information of the class names, package, file path, method names and more, which
all of this can be valuable insights into how the system is designed and implemented. This information for any attackers can use this to indentify
potential vulnerabitiles, which they can use this to understand the application so that it can be exploited and weaken the secure endpoint. Another problem
is that exposing such details would violate the principles of information hiding which will increase risk of attacks of the system. To have less of this
problem, API will have to use the error messages to clients while logging detailed stack traces internally for debugging and monitoring purposes.

API Request & Response Logging Filters (5 Marks)
- Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like
logging, rather than manually inserting Logger.info() statements inside every single re-
source method?

Answer:
JAX-RS filters for handling cross cutting concerns such as logging is advanageous because it allows developers to centralise the funcationality 
in a single location rather than dulicating logging logic across multiple resource methods. Doing the implementation logging at the filter with request
and responses that can be consistently captured without modifying individual endpoint implementations. This helps to maintain the approach as if changes
happens to the logging behaviours it would need to be made in single place, so that it reduces duplication and making the code more robust amd cleaner
to manage. Therefore, it helps having it consistent logging strategy across the API entirly as it helps for debugging and moitoring. If it has to 
manually inserting the logger statements into each resources method, it would become repetitive and would have inconsistencies with the code making
it harder to read and maintain the scale. 


## part 1
http://localhost:8080/SmartCampus1/api/v1

## part 2
http://localhost:8080/SmartCampus1/api/v1/rooms
{
  "id": "R1",
  "name": "Lecture Hall",
  "capacity": 100
}

http://localhost:8080/SmartCampus1/api/v1/rooms/R1

## part 3
http://localhost:8080/SmartCampus1/api/v1/rooms/sensors
{
  "id": "S1",
  "type": "CO2",
  "status": "ACTIVE",
  "currentValue": 0,
  "roomId": "R1"
}
http://localhost:8080/SmartCampus1/api/v1/rooms/S1/sensors

http://localhost:8080/SmartCampus1/api/v1/rooms/S1/sensors?type=CO2

## part 4
http://localhost:8080/SmartCampus1/api/v1/sensors/S1/readings
{
  "value": 25.5
}






