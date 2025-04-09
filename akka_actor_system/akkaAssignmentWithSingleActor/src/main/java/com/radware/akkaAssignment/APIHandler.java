package com.radware.akkaAssignment;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;
import akka.pattern.Patterns;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import scala.concurrent.Await;

import java.net.URI;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionStage;


@RestController
public class APIHandler {

    private ActorSystem<UserManagerBehavior.Command> actorSystem = ActorSystem.create(UserManagerBehavior.create(), "firstActorSystem");

    @PostMapping("/users")
    ResponseEntity<Object> createUser(@RequestBody String username) {
        actorSystem.tell(new UserManagerBehavior.AddUserCommand("create user", username));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(username)
                .toUri();
        //TODO wait till it created
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/users/{username}/items")
    ResponseEntity<Object> addItem(@PathVariable String username, @RequestBody String item) {
        System.out.println("username " + username);
        System.out.println("item " + item);
        actorSystem.tell(new UserManagerBehavior.ItemCommand(username, "add item", item));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/users/{username}/items")
    ResponseEntity<Object> deleteItem(@PathVariable String username, @RequestBody String item) {
        actorSystem.tell(new UserManagerBehavior.ItemCommand(username, "remove item", item));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{username}/items")
    Set<String> getAllItemsForUser(@PathVariable String username) {
        CompletionStage<UserManagerBehavior.ResultListCommand> result = AskPattern.ask(
                actorSystem,
                replyTo -> new UserManagerBehavior.ListCommand(username, replyTo),
                Duration.ofSeconds(3),
                actorSystem.scheduler());
        return result.toCompletableFuture().join().getItems();
    }

}