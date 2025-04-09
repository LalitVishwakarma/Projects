package com.radware.akkaAssignment;

import akka.actor.AbstractActor;

import java.util.HashSet;
import java.util.Set;

public class User {

    String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
