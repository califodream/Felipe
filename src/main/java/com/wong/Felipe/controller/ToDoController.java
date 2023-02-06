package com.wong.Felipe.controller;

import com.wong.Felipe.domain.ToDo;
import com.wong.Felipe.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/todo/{id}")
    public Mono<ToDo> getToDo(@PathVariable String id) {
        return toDoRepository.findById(id);
    }

    @GetMapping("/todos")
    public Flux<ToDo> getToDos() {
        return toDoRepository.findAll();
    }
}
