package com.example.coffee14;

// http://localhost:8080/coffees

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
    Spring - библиотека для создания серверных приложений

    Spring приложения работают под управлением Java Application Server (servlet container)
        приложения создаются в форматах war или jar
            ibm websphere application server (was)
            bea weblogic
            tomcat - https://tomcat.apache.org/ (free application server)

    @Controller @RestController - компонент который взаимодействует с клиентами
    @Service @Component - компоненты приложения реализующие бизнес-логику
    @Repository - компонент для взаимодействия с базой данных
        Hibernate/JPA - Object-Relational Mapping - напрямую спасать и поднимать из
        базы данных экземпляры классов
    @Bean - зависимость которую Spring сможет создать и предоставить потом всем желающим
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

    https://swagger.io/ - allows to describe the structure of APIs so that machines can
        read them and generate interactive documentation, client SDKs, and server stubs

 */

// @RestController - контроллер, который принимает и отдает клиентам экземпляры
// каких-то внутренних классов проекта
@RestController
public class CoffeeController {
    List<Coffee> coffees = new ArrayList<>();

    public CoffeeController() {
        coffees.addAll(
                Arrays.asList(
                        new Coffee("Cappuccino", new BigDecimal("5.99")),
                        new Coffee("Latte", new BigDecimal("6.99")),
                        new Coffee("Espresso", new BigDecimal("3.99")),
                        new Coffee("Americano", new BigDecimal("4.99")),
                        new Coffee("Ristretto", new BigDecimal("7.99"))
                )
        );
    }

    // add link to a repository
    @Autowired
    private CoffeeRepository coffeeRepository; // links to a same repository, because "@Repository" are
                                               //   created only as single instance when Spring starts

    // HTTP GET http://localhost:8080/coffees
    // GET запрос к этому url /coffees вызывает метод контроллера getCoffees
    @GetMapping({"/coffees", "/coffees/"})
    // getting multiple requests
    Iterable<Coffee> getCoffees() {
//        return coffees;

        // верните все кофе из coffeeRepository
        return coffeeRepository.findAll();
    }

    // HTTP GET http://localhost:8080/coffees/123
    @GetMapping("/coffees/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable("id") String id) {
//        return coffees.stream()
//                .filter(
//                        coffee -> coffee.getId().equals(id)
//                )
//                .findFirst();

        // верните из coffeeRepository кофе по его идентификатору
        return coffeeRepository.findById(id);
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
//        if (coffees.removeIf(coffee -> coffee.getId().equals(id))) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }

        if (coffeeRepository.existsById(id)) {
            coffeeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    } // this option returns response, signalling operation result

    // PUT http://localhost:8080/coffees
    // @RequestBody - json с параметрами кофе, который нужно создать, будет передаваться в теле запроса
    @PutMapping("/coffees")
    public Coffee create(@RequestBody Coffee coffee) {
//        coffees.add(coffee);
//        return coffee;

        return coffeeRepository.save(coffee);
    }

    // POST http://localhost:8080/coffees/{id}
    // @RequestBody - json с параметрами кофе, который нужно обновить, будет передаваться в теле запроса
    // напишите мэппинг
    @PostMapping("/coffees/{id}")
    public ResponseEntity<Coffee> update(
            @PathVariable String id,
            @RequestBody Coffee coffee
    ) {
//        // обновите кофе с нужным идентификатором в списке
//        for (int i = 0; i < coffees.size(); i++) {
//            if (coffees.get(i).getId().equals(id)) { // only delete item if id matches
//                coffees.set(i, coffee);
//                // верните его
//                return coffee;
//            }
//        }
//        return null;

        if (coffeeRepository.existsById(id)) {
            return ResponseEntity.ok(coffeeRepository.save(coffee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET http://localhost:8080/find?text=ap
    // @RequestParam указывает на переменную из http запроса
    @GetMapping("/find")
    public Iterable<Coffee> find(@RequestParam("text") String text) {
//        return coffees.stream()
//                .filter(
//                        coffee -> coffee.getName().contains(text)
//                )
//                .toList();

        return coffeeRepository.findByNameContaining(text);
    }

    // Homework 22
    // GET http://localhost:8080/between?from=1.2&to=3.4
    @GetMapping("/between") // expects parameters 'from' and 'to' in the url as the price range
    public Iterable<Coffee> between(
            @RequestParam("from") BigDecimal from, // extracts 'from' parameter from the url query
            @RequestParam("to") BigDecimal to // extracts 'to' parameter from the url query
    ) {
//        return coffees.stream()
//                .filter(
//                        coffee ->
//                                // filter the price including lower and upper bounds
//                                coffee.getPrice().compareTo(from) >= 0 &&
//                                        coffee.getPrice().compareTo(to) <= 0
//                )
//                .toList(); // convert filtered stream to list and return it

        return coffeeRepository.findByPriceBetween(from, to);
    }
}
