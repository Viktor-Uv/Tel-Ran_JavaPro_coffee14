package com.example.coffee14;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
    RestController -
    принимаются http-запросы из вне
    ответами на них являются экземпляры каких-либо объектов
    которые будут по умолчанию сериализованы в json
 */

@RestController
public class CoffeeController {
    List<Coffee> coffees = new ArrayList<>();

    public CoffeeController() {
        coffees.addAll(
                Arrays.asList(
                        new Coffee("Cappuccino"),
                        new Coffee("Latte"),
                        new Coffee("Espresso"),
                        new Coffee("Americano"),
                        new Coffee("Ristretto")
                )
        );
    }

    // http://localhost:8080/coffees
    @GetMapping("/coffees")
    // HTTP GET http://localhost:8080/coffees
    Iterable<Coffee> getCoffees() {
        return coffees;
    }

    // http://localhost:8080/coffees/123
    @GetMapping("/coffees/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable("id") String id) {
        return coffees.stream()
                .filter(
                        coffee -> coffee.getId().equals(id)
                ).findFirst();
    }
}
