/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.exception;

/**
 *
 * @author w2086907
 */
// PART 5: Custom exception for invalid resource relationships like bad roomid for example
public class LinkedResourceNotFoundException extends RuntimeException {

    // Constructor that accepts an error message
    public LinkedResourceNotFoundException(String message) {

        // Calls the parent RuntimeException constructor
        super(message);
    }
}
