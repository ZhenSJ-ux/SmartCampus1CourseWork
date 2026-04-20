/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.resource;

/**
 *
 * @author w2086907
 */
// PART 4: Sensor readings (nested resource) + PART 5 validation (maintenance check)

import com.mycompany.smartcampus1.model.Sensor;          // Sensor model (used to validate sensor exists)
import com.mycompany.smartcampus1.model.SensorReading;   // SensorReading model (data for readings)
import com.mycompany.smartcampus1.store.DataStore;       // In-memory storage

import java.util.ArrayList;                              // Used to create list if null
import java.util.List;                                   // List interface
import java.util.UUID;                                   // Used to generate unique IDs

import javax.ws.rs.Consumes;                             // Accept JSON input
import javax.ws.rs.GET;                                  // HTTP GET method
import javax.ws.rs.POST;                                 // HTTP POST method
import javax.ws.rs.Produces;                             // Return JSON output
import javax.ws.rs.core.MediaType;                       
import javax.ws.rs.core.Response;                        // Used to build HTTP responses

import com.mycompany.smartcampus1.exception.SensorUnavailableException; // Custom exception (Part 5)

@Produces(MediaType.APPLICATION_JSON)    // All responses are JSON
@Consumes(MediaType.APPLICATION_JSON)    // Accept JSON requests

public class SensorReadingResource { 

    // PART 4: Store sensorId passed from SensorResource (sub-resource locator)
    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    // PART 4: GET readings for a specific sensor
    @GET
    public Response getReadings() {

        // Check if sensor exists
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Sensor not found\"}")
                    .build();
        }

        // Get readings list for sensor
        List<SensorReading> readings = DataStore.readings.get(sensorId);

        // If no readings exist, return empty list instead of null
        if (readings == null) {
            readings = new ArrayList<>();
        }

        // Return readings list
        return Response.ok(readings).build();
    }

    // PART 4: Add a new reading to a sensor
    // PART 5: Includes validation for sensor status (maintenance)
    @POST
    public Response addReading(SensorReading reading) {

        // Check if sensor exists
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Sensor not found\"}")
                    .build();
        }

        // PART 5: Prevent adding readings if sensor is in maintenance
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException(
                "Sensor is in maintenance mode and cannot accept new readings."
            );
        }

        // Validate request body
        if (reading == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Reading body is required\"}")
                    .build();
        }

        // Generate ID if not provided
        if (reading.getId() == null || reading.getId().trim().isEmpty()) {
            reading.setId(UUID.randomUUID().toString());
        }

        // Set timestamp if not provided
        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        // Get readings list for sensor
        List<SensorReading> readings = DataStore.readings.get(sensorId);

        // If list doesn't exist, create and store it
        if (readings == null) {
            readings = new ArrayList<>();
            DataStore.readings.put(sensorId, readings);
        }

        // Add new reading to list
        readings.add(reading);

        // PART 4: Update sensor's current value with latest reading
        sensor.setCurrentValue(reading.getValue());

        // Return 201 Created with new reading
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
