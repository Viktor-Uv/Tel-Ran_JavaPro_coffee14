package com.example.coffee14;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
@Repository
// так как класс Coffee проаннотирован, то спринг дата знает как
// его экземпляры сохранять и поднимать из таблицы базы данных
public interface CoffeeRepository extends CrudRepository<Coffee, String> {
    // Coffee - для какого класса этот репозиторий (DAO)
    // String - тип первичного ключа (id)

    // coffee id: 123, name: Latte, price: 1.33
    // insert into coffee (id, name, price) values('123', 'Latte', 1.33);

    Iterable<Coffee> findByNameContaining(String text);
    Iterable<Coffee> findByPriceBetween(BigDecimal from, BigDecimal to);
}
