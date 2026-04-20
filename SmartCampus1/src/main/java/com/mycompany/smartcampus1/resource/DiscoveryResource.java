/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.resource;

/**
 *
 * @author w2086907
 */
// PART 1: Discovery endpoint (basic API info)

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
        response.put("contact", "yourname@westminster.ac.uk");            // Contact info

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