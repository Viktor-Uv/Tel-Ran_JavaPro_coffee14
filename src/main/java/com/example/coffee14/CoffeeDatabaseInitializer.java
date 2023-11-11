package com.example.coffee14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service // - пометка спрингу, что он должен создать единственный экземпляр этого класса при старте приложения
// потом он может быть предоставлен как зависимость
public class CoffeeDatabaseInitializer implements CommandLineRunner {

    // для взаимодействия с базой данных нужен репозиторий
    @Autowired // автоматически предоставляет зависимость
    private CoffeeRepository coffeeRepository;

    /*
        перед созданием экземпляра "CoffeeDatabaseInitializer", спринг сначала создаст экземпляр
            "coffeeRepository" (т.к. присутствует аннотация @Autowired), который как зависимость
            предоставится потом экземпляру "CoffeeDatabaseInitializer"
     */

    private static final Logger logger = LoggerFactory.getLogger(CoffeeDatabaseInitializer.class);

    // CommandLineRunner однократно запускается метод run() спрингом при старте приложения
    @Override
    public void run(String... args) throws Exception {
        // initialise database:
        coffeeRepository.saveAll(
                Arrays.asList(
                        new Coffee("Cappuccino", BigDecimal.valueOf(5.99)),
                        new Coffee("Latte", BigDecimal.valueOf(6.99)),
                        new Coffee("Espresso", BigDecimal.valueOf(3.99)),
                        new Coffee("Americano", BigDecimal.valueOf(4.99)),
                        new Coffee("Ristretto", BigDecimal.valueOf(7.99))
                )
        );
        // trace
        // debug
        // info
        // warn
        // error
        logger.info("Coffees added to repository");
    }
}
