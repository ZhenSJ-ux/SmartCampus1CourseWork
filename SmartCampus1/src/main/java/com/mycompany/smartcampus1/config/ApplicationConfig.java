/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.config;

/**
 *
 * @author w2086907
 */
//PART1 Configuration class for the REST API

import javax.ws.rs.ApplicationPath;   // Base API path 
import javax.ws.rs.core.Application; // This shows the based class for configuring JAX-RS application

@ApplicationPath("/api/v1") // Sets the base URL for all endpoints, which for example ”rooms” -> ”/api/v1/rooms”)
public class ApplicationConfig extends Application {

    // Empty class – used only to activate JAX-RS and define base path
    // Jersey automatically scans for resources and providers
}