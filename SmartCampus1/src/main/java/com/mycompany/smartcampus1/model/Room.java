/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author w2086907
 */
// PART 1: Model class representing a room

import java.util.ArrayList; // Used to initialize sensor list
import java.util.List;      // List to store sensor IDs

public class Room {

    // Unique identifier for the room
    private String id;

    // Name of the room (e.g. Lecture Hall, Lab)
    private String name;

    // Maximum capacity of the room
    private int capacity;

    // List of sensor IDs assigned to this room (relationship)
    private List<String> sensorIds = new ArrayList<>();

    // Default constructor (required for JSON deserialization)
    public Room() {
    }

    // Constructor to create a room with initial values
    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.sensorIds = new ArrayList<>(); // Ensure list is initialized
    }

    // Getter for room ID
    public String getId() {
        return id;
    }

    // Setter for room ID
    public void setId(String id) {
        this.id = id;
    }

    // Getter for room name
    public String getName() {
        return name;
    }

    // Setter for room name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for room capacity
    public int getCapacity() {
        return capacity;
    }

    // Setter for room capacity
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Getter for list of sensor IDs
    public List<String> getSensorIds() {
        return sensorIds;
    }

    // Setter for sensor IDs list
    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }
}
