/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.filter;

/**
 *
 * @author w2086907
 */
// PART 5: Logging Filter (API request & response logging)

import java.io.IOException;                          // Handles IO exceptions
import java.util.logging.Logger;                    // Java built-in logger

import javax.ws.rs.container.ContainerRequestContext;   // Access incoming request data
import javax.ws.rs.container.ContainerRequestFilter;    // Interface for request filtering
import javax.ws.rs.container.ContainerResponseContext;  // Access outgoing response data
import javax.ws.rs.container.ContainerResponseFilter;   // Interface for response filtering
import javax.ws.rs.ext.Provider;                         // Marks this class as a JAX-RS provider

@Provider // Allows Jersey to automatically detect and apply this filter
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    // Logger instance used to print logs to console
    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());

    // PART 5: Runs BEFORE the request reaches your resource (incoming request)
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Log HTTP method and full request URL
        LOGGER.info("Incoming Request: " +
                requestContext.getMethod() + " " +
                requestContext.getUriInfo().getRequestUri().toString());
    }

    // PART 5: Runs AFTER your resource method executes (outgoing response)
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        // Log HTTP response status code (e.g. 200, 404, 500)
        LOGGER.info("Outgoing Response: HTTP " + responseContext.getStatus());
    }
}
