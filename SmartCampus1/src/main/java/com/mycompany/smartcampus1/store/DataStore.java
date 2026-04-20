/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.store;

/**
 *
 * @author w2086907
 */
// PART1                                               // all of these parts are from other import models:
import com.mycompany.smartcampus1.model.Room;          // stores the Room data
import com.mycompany.smartcampus1.model.Sensor;        // stores the Sensor data
import com.mycompany.smartcampus1.model.SensorReading; // stores the reading data

import java.util.List;                                 // stores the list of readings
import java.util.Map;                                  // key value storage
import java.util.concurrent.ConcurrentHashMap;         // Thread-safe map (important for web apps)

// This class acts as an in-memory database 
public class DataStore {

    // Stores all rooms using room ID as key
    public static final Map<String, Room> rooms = new ConcurrentHashMap<>();

    // Stores all sensors using sensor ID as key
    public static final Map<String, Sensor> sensors = new ConcurrentHashMap<>();

    // Stores sensor readings:
    // Key = sensor ID
    // Value = list of readings for that sensor
    public static final Map<String, List<SensorReading>> readings = new ConcurrentHashMap<>();

    // Private constructor prevents creating instances of this class
    // (since we only use static data)
    private DataStore() {
    }
}
