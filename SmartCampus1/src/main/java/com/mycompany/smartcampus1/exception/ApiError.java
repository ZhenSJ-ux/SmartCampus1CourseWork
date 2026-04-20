/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.exception;

/**
 *
 * @author w2086907
 */
// PART 5: Standard API error response model (used by all exception mappers)

public class ApiError {

    // HTTP status code (e.g. 400, 403, 409, 422, 500)
    private int status;

    // Short error type (e.g. "Conflict", "Forbidden")
    private String error;

    // Detailed message describing the error
    private String message;

    // Default constructor (required for JSON serialization/deserialization)
    public ApiError() {
    }

    // Constructor to create a full error response
    public ApiError(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    // Getter for status code
    public int getStatus() {
        return status;
    }

    // Setter for status code
    public void setStatus(int status) {
        this.status = status;
    }

    // Getter for error type
    public String getError() {
        return error;
    }

    // Setter for error type
    public void setError(String error) {
        this.error = error;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }
}
