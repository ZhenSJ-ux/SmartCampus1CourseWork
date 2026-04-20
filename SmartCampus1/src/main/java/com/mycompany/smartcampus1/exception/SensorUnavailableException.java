/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.exception;

/**
 *
 * @author w2086907
 */
// PART 5: Custom exception for handling sensors that are unavailable example maintenace model
public class SensorUnavailableException extends RuntimeException {

    // Constructor that accepts an error message
    public SensorUnavailableException(String message) {

        // Calls the parent RuntimeException constructor to store the message
        super(message);
    }
}