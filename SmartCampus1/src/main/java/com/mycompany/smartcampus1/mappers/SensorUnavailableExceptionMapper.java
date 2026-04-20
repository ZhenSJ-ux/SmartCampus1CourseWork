/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.mappers;

/**
 *
 * @author w2086907
 */
// PART 5: Exception Mapper for handling sensor availability errors (403 Forbidden)
import com.mycompany.smartcampus1.exception.ApiError;                 // Custom error response class
import com.mycompany.smartcampus1.exception.SensorUnavailableException; // Custom exception for maintenance sensors
import javax.ws.rs.core.Response;        // Used to build the HTTP responses
import javax.ws.rs.ext.ExceptionMapper; // Interface for mapping exceptions to responses
import javax.ws.rs.ext.Provider;         // Uses this class as a JAX-RS provider

@Provider // Tells Jersey to automatically detect and use this mapper
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    // PART5:  Converts SensorUnavailableExceptionMapper into HTTP 403 response
    @Override
    public Response toResponse(SensorUnavailableException ex) {

        // Create structured JSON error response
        ApiError error = new ApiError(
                403,                 // HTTP status code
                "Forbidden",         // Error type
                ex.getMessage()      // Exception message
        );

        // Return 403 Forbidden with JSON body
        return Response.status(Response.Status.FORBIDDEN)
                .entity(error)
                .build();
    }
}
