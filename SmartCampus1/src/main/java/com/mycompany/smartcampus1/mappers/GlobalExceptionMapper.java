/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.mappers;

/**
 *
 * @author w2086907
 */
// PART 5: Global Exception Mapper (catch-all for unexpected errors for HTTP 500)

import com.mycompany.smartcampus1.exception.ApiError; // Custom error response structure

import javax.ws.rs.core.Response;        // Used to build HTTP responses
import javax.ws.rs.ext.ExceptionMapper; // Interface to map exceptions to responses
import javax.ws.rs.ext.Provider;         // Marks this class as a JAX-RS provider

@Provider // Allows Jersey to automatically detect and use this mapper
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    
    // PART 5: Catch any unhandled exception in the application
    @Override
    public Response toResponse(Throwable ex) {

        // Print stack trace to server logs (for debugging only, not exposed to users)
        ex.printStackTrace();

        // Create safe, generic error response (do NOT expose internal details)
        ApiError error = new ApiError(
                500,                             // HTTP status code
                "Internal Server Error",         // Error type
                "An unexpected error occurred. Please contact support." // message
        );

        // Return HTTP 500 response with JSON body
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }
}
