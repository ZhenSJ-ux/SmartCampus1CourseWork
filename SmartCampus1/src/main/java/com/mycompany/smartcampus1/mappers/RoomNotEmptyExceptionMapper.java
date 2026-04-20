/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.mappers;

/**
 *
 * @author w2086907
 */
// PART 5: Exception Mapper for handling room deletion conflicts (409 Conflict)

import com.mycompany.smartcampus1.exception.ApiError;              // Custom error response structure
import com.mycompany.smartcampus1.exception.RoomNotEmptyException; // Custom exception when room has sensors
import javax.ws.rs.core.Response;        // Used to build the HTTP responses
import javax.ws.rs.ext.ExceptionMapper; // Interface to map exceptions to the HTTP responses
import javax.ws.rs.ext.Provider;         // Uses this class as a JAX-RS provider

@Provider // Allows Jersey to detect and use this mapper automatically
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    // PART 5: Convert RoomNotEmptyException into HTTP 409 Conflict response
    @Override
    public Response toResponse(RoomNotEmptyException ex) {

        // Create structured JSON error response
        ApiError error = new ApiError(
                409,              // HTTP status code
                "Conflict",       // Error type
                ex.getMessage()   // Message from exception
        );
        // Returns the 409 Conflict with JSON body
        return Response.status(Response.Status.CONFLICT)
                .entity(error)
                .build();
    }
}
