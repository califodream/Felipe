package com.wong.Felipe.handler;

import com.wong.Felipe.domain.ToDo;
import com.wong.Felipe.repository.ToDoMongoRepository;
import com.wong.Felipe.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ToDoHandler {

    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private ToDoMongoRepository toDoMongoRepository;

    public Mono<ServerResponse> getToDo(ServerRequest request) {
        String toDoId = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<ToDo> toDo = this.toDoMongoRepository.findById(toDoId);
        return toDo.flatMap(t -> ServerResponse
                .ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(t))).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getToDos(ServerRequest serverRequest) {
        Flux<ToDo> toDos = toDoMongoRepository.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(toDos, ToDo.class);
    }

    public Mono<ServerResponse> findById(String id) {
        Mono<ToDo> toDo = toDoMongoRepository.findById(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(toDo))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> newTodo(ServerRequest serverRequest) {
        Mono<ToDo> toDo = serverRequest.bodyToMono(ToDo.class);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(toDo.flatMap(this::save), ToDo.class));
    }

    public Mono<ToDo> save(ToDo toDo) {
        return Mono.fromSupplier(
                () -> {
                    toDoMongoRepository.save(toDo).subscribe();
                    return toDo;
                }
        );
    }
}
