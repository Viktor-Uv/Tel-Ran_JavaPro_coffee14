package com.example.coffee14;

// http://localhost:8080/coffees

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
    Spring - библиотека для создания серверных приложений
    @Controller @RestController - компонент который взаимодействует с клиентами
    @Service @Component - компоненты приложения реализующие бизнес-логику
    @Repository - компонент для взаимодействия с базой данных
        Hibernate/JPA - Object-Relational Mapping - напрямую спасать и поднимать из
        базы данных экземпляры классов
    @Bean
    @Configuration

    Spring boot - дополнительная надстройка на Spring которая позволяет создавать
        серверные приложения еще быстрее и проще

    RestController -
    принимаются http-запросы из вне
    ответами на них являются экземпляры каких-либо объектов
    которые будут по умолчанию сериализованы в json

    REST - Remote State Transfer

    HTTP - hypertext transfer protocol
        GET - like sql SELECT
        PUT - like sql UPDATE
        POST - like sql INSERT
        DELETE - like sql DELETE
        HEAD
 */

// @RestController - контроллер, который принимает и отдает клиентам экземпляры
// каких-то внутренних классов проекта
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

    // HTTP GET http://localhost:8080/coffees
    // GET запрос к этому url /coffees вызывает метод контроллера getCoffees
    @GetMapping({"/coffees", "/coffees/"})
    // getting multiple requests
    Iterable<Coffee> getCoffees() {
        return coffees;
    }

    // HTTP GET http://localhost:8080/coffees/123
    @GetMapping("/coffees/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable("id") String id) {
        return coffees.stream()
                .filter(
                        coffee -> coffee.getId().equals(id)
                )
                .findFirst();
    }

    /*
    // DELETE http://localhost:8080/coffees/{id}
    // напишите мэппинг
    @DeleteMapping("/coffees/{id}")
    void delete(@PathVariable String id) {
        // напишите тело метода
        coffees.removeIf(coffee -> coffee.getId().equals(id));
    } // this option doesn't return any response, neither positive nor negative outcome
     */

    // DELETE http://localhost:8080/coffees/{id}
    @DeleteMapping("/coffees/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        if (coffees.removeIf(coffee -> coffee.getId().equals(id))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    } // this option returns response, signalling operation result

    // PUT http://localhost:8080/coffees
    // @RequestBody - json с параметрами кофе, который нужно создать, будет передаваться в теле запроса
    @PutMapping("/coffees")
    public Coffee create(@RequestBody Coffee coffee) {
        coffees.add(coffee);
        return coffee;
    }

    // POST http://localhost:8080/coffees/{id}
    // @RequestBody - json с параметрами кофе, который нужно обновить, будет передаваться в теле запроса
    // напишите мэппинг
    @PostMapping("/coffees/{id}")
    public Coffee update(
            @PathVariable String id,
            @RequestBody Coffee coffee
    ) {
        // обновите кофе с нужным идентификатором в списке
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getId().equals(id)) { // only delete item if id matches
                coffees.set(i, coffee);
                // верните его
                return coffee;
            }
        }
        return null;
    }

    // GET http://localhost:8080/find?text=ap
    // @RequestParam указывает на переменную из http запроса
    @GetMapping("/find")
    public Iterable<Coffee> find(@RequestParam("text") String text) {
        return coffees.stream()
                .filter(
                        coffee -> coffee.getName().contains(text)
                )
                .toList();
    }
}
