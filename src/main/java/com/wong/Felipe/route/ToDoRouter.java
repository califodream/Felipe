package com.wong.Felipe.route;

import com.wong.Felipe.handler.ToDoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ToDoRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ToDoHandler toDoHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/todo/{id}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        toDoHandler::getToDo)
                .andRoute(RequestPredicates.GET("/todos")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        toDoHandler::getToDos)
                .andRoute(RequestPredicates.POST("/todo")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),toDoHandler::newTodo);
    }
}
