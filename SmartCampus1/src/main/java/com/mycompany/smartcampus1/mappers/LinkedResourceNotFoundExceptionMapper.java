/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.mappers;

/**
 *
 * @author w2086907
 */
// PART 5: Exception Mapper for handling invalid linked resources (422 Unprocessable Entity)
import com.mycompany.smartcampus1.exception.ApiError;                     // produces error response structure
import com.mycompany.smartcampus1.exception.LinkedResourceNotFoundException; // Custom exception for invalid relationships
import javax.ws.rs.core.Response;        // Used to build HTTP responses
import javax.ws.rs.ext.ExceptionMapper; // Interfaces uses map exception for reponses
import javax.ws.rs.ext.Provider;         // The class will provide JAX-RS as provider
@Provider // Jersey will detect the mapper
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException>{

    // PART 5: Convert LinkedResourceNotFoundException into HTTP 422 response
    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {

        // Create structured JSON error response
        ApiError error = new ApiError(
                422,                         // HTTP status code
                "Unprocessable Entity",      // Error type
                ex.getMessage()              // Message from exception
        );
        // Return 422 response with JSON body
        return Response.status(422)
                .entity(error)
                .build();
    }
}