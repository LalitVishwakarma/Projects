package com.radware.akkaAssignment;

import akka.actor.typed.ActorSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
public class APIHandler {

    ActorSystem<UserManagerBehavior.Command> actorSystem = ActorSystem.create(UserManagerBehavior.create(), "firstActoSystem");
    DataService dataService = DataService.getInstance();

    @GetMapping("/hello-world")
    public String helloWorld(){
        actorSystem.tell(new UserManagerBehavior.AddUserCommand("create user", "lalit"));
        actorSystem.tell(new UserManagerBehavior.ItemCommand("lalit", "add item", "shirt"));
//        actorSystem.tell(new UserManagerBehavior.ItemCommand("lalit", "add item", "jeans"));
        actorSystem.tell(new UserManagerBehavior.ItemCommand("lalit", "remove item", "shirt"));
        return "Hello World";
    }

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

    @PostMapping("/users/{username}")
    ResponseEntity<Object> addItem(@PathVariable String username, @RequestBody String item) {
        System.out.println("username "  + username);
        System.out.println("item "  + item);
        actorSystem.tell(new UserManagerBehavior.ItemCommand(username, "add item", item));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(username)
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{username}")
    ResponseEntity<Object> deleteItem(@PathVariable String username, @RequestBody String item) {
        actorSystem.tell(new UserManagerBehavior.ItemCommand(username, "remove item", item));
        return ResponseEntity.ok().build();
    }

     @GetMapping("/users/{username}")
    Set<String> getAllItemsForUser(@PathVariable String username) {
        actorSystem.tell(new UserManagerBehavior.ListCommand(username));
        Set<String> listOfItems = dataService.itemMap.get(username);
        return listOfItems;
    }

}
