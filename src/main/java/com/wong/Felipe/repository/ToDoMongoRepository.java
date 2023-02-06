package com.wong.Felipe.repository;

import com.wong.Felipe.domain.ToDo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoMongoRepository extends ReactiveMongoRepository<ToDo, String> {
}
