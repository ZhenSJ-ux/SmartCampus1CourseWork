/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.resource;

/**
 *
 * @author w2086907
 */ 
// PART 3: Sensor management (create sensors, get sensors, filtering)

import com.mycompany.smartcampus1.model.Room;          // Room model (used to validate roomId)
import com.mycompany.smartcampus1.model.Sensor;        // Sensor model (main data object)
import com.mycompany.smartcampus1.model.SensorReading; // SensorReading model (for linking readings later)
import com.mycompany.smartcampus1.store.DataStore;     // In-memory storage

import java.net.URI;                                   // Used to build response URI after POST
import java.util.ArrayList;                            // Used to create lists
import java.util.List;                                 // List interface
import java.util.stream.Collectors;                    // Used for filtering sensors

import javax.ws.rs.Consumes;                           // Defines accepted input format (JSON)
import javax.ws.rs.GET;                                // HTTP GET method
import javax.ws.rs.POST;                               // HTTP POST method
import javax.ws.rs.Path;                               // Defines endpoint path
import javax.ws.rs.PathParam;                          // Extracts path variables
import javax.ws.rs.QueryParam;                         // Extracts query parameters (e.g. ?type=CO2)
import javax.ws.rs.Produces;                           // Defines response format (JSON)
import javax.ws.rs.core.Context;                       
import javax.ws.rs.core.MediaType;                     
import javax.ws.rs.core.Response;                      // Used to build HTTP responses
import javax.ws.rs.core.UriInfo;                       // Used to build resource URI

import com.mycompany.smartcampus1.exception.LinkedResourceNotFoundException; // Custom exception for invalid roomId

@Path("/sensors")                         // Base endpoint: /api/v1/sensors
@Produces(MediaType.APPLICATION_JSON)     // All responses are JSON
@Consumes(MediaType.APPLICATION_JSON)     // Accept JSON input

public class SensorResource {

    // PART 3: GET all sensors (with optional filtering)
    @GET
    public Response getSensors(@QueryParam("type") String type) {

        // Get all sensors from DataStore
        List<Sensor> sensors = new ArrayList<>(DataStore.sensors.values());

        // If query parameter exists, filter sensors by type
        if (type != null && !type.trim().isEmpty()) {
            sensors = sensors.stream()
                    .filter(sensor -> sensor.getType() != null
                    && sensor.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }

        // Return list of sensors
        return Response.ok(sensors).build();
    }

    // PART 3: Create a new sensor
    @POST
    public Response createSensor(Sensor sensor, @Context UriInfo uriInfo) {

        // Validate sensor ID
        if (sensor == null || sensor.getId() == null || sensor.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Sensor id is required\"}")
                    .build();
        }

        // Validate roomId exists in request
        if (sensor.getRoomId() == null || sensor.getRoomId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"roomId is required\"}")
                    .build();
        }

        // Prevent duplicate sensor IDs
        if (DataStore.sensors.containsKey(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Sensor with this id already exists\"}")
                    .build();
        }

        // Validate that the referenced room exists
        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room == null) {
            throw new LinkedResourceNotFoundException("Referenced room does not exist");
        }

        // Store sensor in DataStore
        DataStore.sensors.put(sensor.getId(), sensor);

        // Link sensor to room
        room.getSensorIds().add(sensor.getId());

        // Initialize empty readings list for this sensor
        DataStore.readings.put(sensor.getId(), new ArrayList<SensorReading>());

        // Build URI for the created resource
        URI uri = uriInfo.getAbsolutePathBuilder().path(sensor.getId()).build();

        // Return 201 Created response
        return Response.created(uri).entity(sensor).build();
    }

    // PART 4: Sub-resource locator (nested endpoint)
    // Handles: /api/v1/sensors/{sensorId}/readings
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {

        // Pass sensorId to SensorReadingResource
        return new SensorReadingResource(sensorId);
    }
}
