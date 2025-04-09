package com.radware.akkaAssignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataService {
    private static DataService single_instance = null;

    // Declaring a variable of type String
    public ConcurrentHashMap<String, Set<String>> itemMap;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private DataService() {
        itemMap = new ConcurrentHashMap<>();
    }

    // Static method
    // Static method to create instance of Singleton class
    public static DataService getInstance()
    {
        if (single_instance == null)
            single_instance = new DataService();

        return single_instance;
    }
}
