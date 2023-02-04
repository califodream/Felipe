package com.wong.Felipe.controller;

import com.wong.Felipe.builder.ToDoBuilder;
import com.wong.Felipe.domain.ToDo;
import com.wong.Felipe.repository.ToDoRepository;
import com.wong.Felipe.validation.ToDoValidationError;
import com.wong.Felipe.validation.ToDoValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ToDoController {
    /*@Autowired
    private CommonRepository<ToDo> repository;*/
    /*@Autowired
    private JdbcCommonRepositoryImpl repository;*/
    @Autowired
    private ToDoRepository repository;

    @GetMapping("/todos")
    public ResponseEntity<Iterable<ToDo>> getToDos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable String id) {
        Optional<ToDo> result = repository.findById(id);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id) {
        Optional<ToDo> result = repository.findById(id);
        if (!result.isPresent())
            return ResponseEntity.notFound().build();
        ToDo toDo = result.get();
        toDo.setCompleted(true);
        repository.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(toDo.getId()).toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @RequestMapping(value = "/todo", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        ToDo result = repository.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id) {
        repository.delete(ToDoBuilder.created().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo) {
        repository.delete(toDo);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }
}
