/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.model;

/**
 *
 * @author w2086907
 */
// PART 1: Model class representing a sensor

public class Sensor {

    // Unique identifier for the sensor
    private String id;

    // Type of sensor (e.g. Temperature, CO2, Humidity)
    private String type;

    // Current status of the sensor (e.g. ACTIVE, INACTIVE, MAINTENANCE)
    private String status;

    // Latest recorded value from the sensor
    private double currentValue;

    // ID of the room this sensor belongs to (relationship link)
    private String roomId;

    // Default constructor (required for JSON handling)
    public Sensor() {
    }

    // Constructor to create a sensor with all fields
    public Sensor(String id, String type, String status, double currentValue, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }

    // Getter for sensor ID
    public String getId() { 
        return id; 
    }

    // Setter for sensor ID
    public void setId(String id) { 
        this.id = id; 
    }

    // Getter for sensor type
    public String getType() { 
        return type; 
    }

    // Setter for sensor type
    public void setType(String type) { 
        this.type = type; 
    }

    // Getter for sensor status
    public String getStatus() { 
        return status; 
    }

    // Setter for sensor status
    public void setStatus(String status) { 
        this.status = status; 
    }

    // Getter for current sensor value
    public double getCurrentValue() { 
        return currentValue; 
    }

    // Setter for current sensor value
    public void setCurrentValue(double currentValue) { 
        this.currentValue = currentValue; 
    }

    // Getter for associated room ID
    public String getRoomId() { 
        return roomId; 
    }

    // Setter for associated room ID
    public void setRoomId(String roomId) { 
        this.roomId = roomId; 
    }
}
