package com.wong.Felipe.repository;

import com.wong.Felipe.domain.ToDo;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Repository
public class ToDoRepository {

    private final Flux<ToDo> toDoFlux = Flux.fromIterable(Arrays.asList(
            new ToDo("Hello World", true),
            new ToDo("Royal never give up"),
            new ToDo("Winter is comming", true)
    ));

    public Mono<ToDo> findById(String id) {
        return Mono.from(toDoFlux.filter(p -> p.getId().equals(id)));
    }

    public Flux<ToDo> findAll() {
        return toDoFlux;
    }
}
