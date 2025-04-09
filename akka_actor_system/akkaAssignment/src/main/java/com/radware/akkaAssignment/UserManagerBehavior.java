package com.radware.akkaAssignment;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.util.HashMap;
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

    public static class ListCommand implements Command {
        public static final long serialVersionUID = 1L;
        private String username;

        public ListCommand(String username) {
            this.username = username;
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

    public static class ResultCommand implements Command {
        public static final long serialVersionUID = 1L;
        private ActorRef<UserWorkerBehavior.Command> userRef;
        private String username;

        public ResultCommand(ActorRef<UserWorkerBehavior.Command> userRef, String username) {
            this.userRef = userRef;
            this.username = username;
        }

        public ActorRef<UserWorkerBehavior.Command> getUserRef() {
            return userRef;
        }

        public String getUsername() {
            return username;
        }
    }

    public static class ListResultCommand implements Command {
        public static final long serialVersionUID = 1L;
        private Set<String> items;
        private String username;

        public ListResultCommand(String username, Set<String> items) {
            this.username = username;
            this.items = items;
        }

        public String getUsername() {
            return username;
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

    private Map<String, ActorRef<UserWorkerBehavior.Command>> users = new HashMap<String, ActorRef<UserWorkerBehavior.Command>>();

    DataService dataService = DataService.getInstance();
    private Map<String, Set<String>> itemsMap = new HashMap<>();


    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(AddUserCommand.class, command -> {
                    System.out.println("==in here==");
                    if (command.getMessage().equals("create user") && !users.containsKey(command.getUsername())) {
                        System.out.println("==in here==2");
                        ActorRef<UserWorkerBehavior.Command> userActor = getContext().spawn(UserWorkerBehavior.create(), command.getUsername());
                        System.out.println("==in here==3");
                        userActor.tell(new UserWorkerBehavior.UserWorkerCommand("create user", command.getUsername(), getContext().getSelf()));
                    }
                    return this;
                })
                .onMessage(ItemCommand.class, command -> {
                    if (command.getMessage().equals("add item") && users.containsKey(command.getUsername())) {
                        System.out.println("adding item 2");
                        users.get(command.getUsername()).tell(new UserWorkerBehavior.ItemWorkerCommand("add item", command.username, command.item, getContext().getSelf()));
                    } else if (command.getMessage().equals("remove item") && users.containsKey(command.getUsername())) {
                        System.out.println("removing item 2");
                        users.get(command.getUsername()).tell(new UserWorkerBehavior.ItemWorkerCommand("remove item", command.username, command.item, getContext().getSelf()));
                    }
                    return this;
                })
                .onMessage(ListCommand.class, command -> {
                    if (users.containsKey(command.getUsername())) {
                        System.out.println("getting list of items for "+ command.getUsername());
                        users.get(command.getUsername()).tell(new UserWorkerBehavior.ListItemWorkerCommand(command.username, getContext().getSelf()));
                    }
                    return this;
                })
                .onMessage(ResultCommand.class, command -> {
                    users.put(command.getUsername(), command.getUserRef());
                    System.out.println("I have received user" + command.username +" prime numbers");
                    return this;
                })
                .onMessage(ListResultCommand.class, command -> {
                    dataService.itemMap.put(command.getUsername(), command.getItems());
                    System.out.println("I have received list of items for user" + command.username +" items : ");
                    for (String item: command.getItems()) {
                        System.out.print(item + " ");
                    }
                    System.out.println();
                    return this;
                })
                .build();
    }
}
