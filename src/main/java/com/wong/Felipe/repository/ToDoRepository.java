package com.wong.Felipe.repository;

import com.wong.Felipe.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}
