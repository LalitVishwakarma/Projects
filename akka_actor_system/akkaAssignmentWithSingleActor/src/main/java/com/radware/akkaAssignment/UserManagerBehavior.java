package com.radware.akkaAssignment;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserManagerBehavior extends AbstractBehavior<UserManagerBehavior.Command> {

    public interface Command extends Serializable {
    }

    public static class AddUserCommand implements Command {
        public static final long serialVersionUID = 1L;
        private String username;
        private String message;

        public AddUserCommand(String message, String username) {
            this.username = username;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public String getUsername() {
            return username;
        }
    }

    public static class ItemCommand implements Command {
        public static final long serialVersionUID = 1L;
        private String username;
        private String item;
        private String message;

        public ItemCommand(String username, String message, String item) {
            this.username = username;
            this.item = item;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public String getUsername() {
            return username;
        }

        public String getItem() {
            return item;
        }
    }

    public static class ListCommand implements Command {
        public static final long serialVersionUID = 1L;
        private String username;
        public final ActorRef replyTo;

        public ListCommand(String username, ActorRef replyTo) {
            this.username = username;
            this.replyTo = replyTo;
        }

        public String getUsername() {
            return username;
        }
    }

    public static class ResultListCommand implements Command {
        public static final long serialVersionUID = 1L;
        private Set<String> items;

        public ResultListCommand(Set<String> items) {
            this.items = items;
        }

        public Set<String> getItems() {
            return items;
        }
    }

    private UserManagerBehavior(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(UserManagerBehavior::new);
    }

    private Map<String, Set<String>> itemsMap = new HashMap<>();

    public Set<String> getListOfItems(String username) {
        return itemsMap.get(username);
    }


    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(AddUserCommand.class, command -> {
                    System.out.println("==in here==");
                    if (command.getMessage().equals("create user") && !itemsMap.containsKey(command.getUsername())) {
                        itemsMap.put(command.getUsername(), new HashSet<>());
                        System.out.println("User " + command.getUsername() + " is added");
                    }
                    return this;
                })
                .onMessage(ItemCommand.class, command -> {
                    if (itemsMap.containsKey(command.getUsername())) {
                        Set<String> items = itemsMap.get(command.getUsername());
                        if (command.getMessage().equals("add item")) {
                            System.out.println("adding item " + command.getItem() + " in cart of user " + command.getUsername());
                            items.add(command.getItem());
//                            itemsMap.put(command.getUsername(), items);
                            System.out.println("Added item to the cart");
                        } else if (command.getMessage().equals("remove item")) {
                            System.out.println("removing item " + command.getItem() + " in cart of user " + command.getUsername());
                            items.remove(command.getItem());
//                            itemsMap.put(command.getUsername(), items);
                            System.out.println("Removed item from the cart");
                        }
                    }
                    return this;
                })
                .onMessage(ListCommand.class, command -> {
                    if (itemsMap.containsKey(command.getUsername())) {
                        command.replyTo.tell(new ResultListCommand(itemsMap.get(command.getUsername())));
                    }
                    return this;
                })
                .build();
    }
}
