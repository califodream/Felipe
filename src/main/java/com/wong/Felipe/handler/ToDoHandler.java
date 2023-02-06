package com.wong.Felipe.handler;

import com.wong.Felipe.domain.ToDo;
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

    public Mono<ServerResponse> getToDo(ServerRequest request) {
        String toDoId = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<ToDo> toDo = this.toDoRepository.findById(toDoId);
        return toDo.flatMap(t -> ServerResponse
                .ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(t))).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getToDos(ServerRequest serverRequest) {
        Flux<ToDo> toDos = toDoRepository.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(toDos, ToDo.class);
    }
}
