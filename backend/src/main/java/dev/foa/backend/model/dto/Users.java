package dev.foa.backend.model.dto;

import dev.foa.backend.model.entity.User;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.List;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "email"})})
public record Users(
        List<User> users
) {}
