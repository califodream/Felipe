package com.wong.Felipe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class ToDo {
    @NotNull
    @Id
    private String id;
    @NotNull
    @NotBlank
    private String description;
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "GMT+8")
    @Column(updatable = false)
    private LocalDateTime created;
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modified;
    private boolean completed;

    public ToDo() {
        this.id = UUID.randomUUID().toString();
    }

    public ToDo(String description) {
        this();
        this.description = description;
    }

    @PrePersist
    void OnCreate() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    void OnUpdate() {
        this.setModified(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ToDo toDo = (ToDo) o;
        return id != null && Objects.equals(id, toDo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
