package com.wong.Felipe.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDo {
    private String id;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    public ToDo() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.created = localDateTime;
        this.modified = localDateTime;
    }

    public ToDo(String description) {
        this();
        this.description = description;
    }

    public ToDo(String description, boolean completed) {
        this.description = description;
        this.completed = completed;
    }
}
