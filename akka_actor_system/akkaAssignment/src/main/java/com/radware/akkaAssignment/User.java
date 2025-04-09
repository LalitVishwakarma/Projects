package com.radware.akkaAssignment;

import akka.actor.AbstractActor;

import java.util.HashSet;
import java.util.Set;

public class User {

    String name;
    Set<String> items = new HashSet<String>();

    public User(String name) {
        this.name = name;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public Set<String> getItems() {
        return items;
    }
}
