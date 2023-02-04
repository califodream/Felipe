package com.wong.Felipe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Document
public class ToDo {
    @NotNull
    @Id
    private String id;
    @NotNull
    @NotBlank
    private String description;
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "GMT+8")
    // @Column(updatable = false)
    private LocalDateTime created;
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "GMT+8")
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
}
