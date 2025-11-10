package com.example.PROOUNCE_TASK.DTO;

// Using a record for a simple, immutable data carrier for Part A
public record Course(
    Long id,
    String name,
    String professor
) {}