/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.exception;

/**
 *
 * @author w2086907
 */
// PART 5: Custom exception for preventing deletion of a room that still has sensors
public class RoomNotEmptyException extends RuntimeException {

    // Constructor that accepts an error message
    public RoomNotEmptyException(String message) {

        // Calls parent RuntimeException to store the message
        super(message);
    }
}
