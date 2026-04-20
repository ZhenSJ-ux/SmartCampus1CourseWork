/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.model;

/**
 *
 * @author w2086907
 */
// PART 1: Model class representing a sensor reading (data structure)

public class SensorReading {

    // Unique identifier for each reading
    private String id;

    // Timestamp of when the reading was recorded
    private long timestamp;

    // Value of the sensor reading (e.g. temperature, CO2 level)
    private double value;

    // Default constructor (required for JSON deserialization)
    public SensorReading() {}

    // Constructor to create a reading with all fields
    public SensorReading(String id, long timestamp, double value) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }

    // Getter for id
    public String getId() { 
        return id;
    }

    // Setter for id
    public void setId(String id) { 
        this.id = id; 
    }

    // Getter for timestamp
    public long getTimestamp() { 
        return timestamp; 
    }

    // Setter for timestamp
    public void setTimestamp(long timestamp) { 
        this.timestamp = timestamp;
    }

    // Getter for value
    public double getValue() { 
        return value; 
    }

    // Setter for value
    public void setValue(double value) { 
        this.value = value;
    }
}
