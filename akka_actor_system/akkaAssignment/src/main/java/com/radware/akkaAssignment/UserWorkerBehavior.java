package com.radware.akkaAssignment;

import akka.actor.typed.ActorRef;
        import akka.actor.typed.Behavior;
        import akka.actor.typed.javadsl.AbstractBehavior;
        import akka.actor.typed.javadsl.ActorContext;
        import akka.actor.typed.javadsl.Behaviors;
        import akka.actor.typed.javadsl.Receive;

        import java.io.Serializable;
        import java.math.BigInteger;
        import java.util.Random;

public class UserWorkerBehavior extends AbstractBehavior<UserWorkerBehavior.Command> {

    public interface Command extends Serializable {
    }

    public static class UserWorkerCommand implements Command {
        private static final long serialVersionUID = 1L;
        private String message;
        private String username;
        private ActorRef<UserManagerBehavior.Command> sender;

        public UserWorkerCommand(String message, String username, ActorRef<UserManagerBehavior.Command> sender) {
            this.message = message;
            this.username = username;
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public String getUsername() {
            return username;
        }

        public ActorRef<UserManagerBehavior.Command> getSender() {
            return sender;
        }
    }

    public static class ItemWorkerCommand implements Command {
        private static final long serialVersionUID = 1L;
        private String message;
        private String username;
        private String item;
        private ActorRef<UserManagerBehavior.Command> sender;

        public ItemWorkerCommand(String message, String username, String item, ActorRef<UserManagerBehavior.Command> sender) {
            this.message = message;
            this.username = username;
            this.item = item;
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public String getItem() {
            return item;
        }

        public String getUsername() {
            return username;
        }

        public ActorRef<UserManagerBehavior.Command> getSender() {
            return sender;
        }
    }

    public static class ListItemWorkerCommand implements Command {
        private String username;
        private ActorRef<UserManagerBehavior.Command> sender;

        public ListItemWorkerCommand(String username, ActorRef<UserManagerBehavior.Command> sender) {
            this.username = username;
            this.sender = sender;
        }

        public String getUsername() {
            return username;
        }

        public ActorRef<UserManagerBehavior.Command> getSender() {
            return sender;
        }
    }

    private UserWorkerBehavior(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(UserWorkerBehavior::new);
    }

    private User user;

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(UserWorkerCommand.class, command -> {
                    if (command.getMessage().equals("create user")) {
                        this.user= new User(command.username);
                        System.out.println("Adding user "+ command.getUsername());
                        command.getSender().tell(new UserManagerBehavior.ResultCommand(getContext().getSelf(), command.username));
                    }
                    return this;
                })
                .onMessage(ItemWorkerCommand.class, command -> {
                    if (command.getMessage().equals("add item")) {
                        this.user.addItem(command.item);
                        System.out.println("adding item " + command.item + " for user " + command.username);
                        command.getSender().tell(new UserManagerBehavior.ResultCommand(getContext().getSelf(), command.username));
                    } else if(command.getMessage().equals("remove item")) {
                        this.user.removeItem(command.item);
                        System.out.println("removing item " + command.item + " for user " + command.username);
                        command.getSender().tell(new UserManagerBehavior.ResultCommand(getContext().getSelf(), command.username));
                    }
                    return this;
                })
                .onMessage(ListItemWorkerCommand.class, command -> {
                        System.out.println("sending list of items for "+ command.getUsername());
                        command.getSender().tell(new UserManagerBehavior.ListResultCommand(command.username, this.user.getItems()));
                    return this;
                })
                .build();
    }
}

