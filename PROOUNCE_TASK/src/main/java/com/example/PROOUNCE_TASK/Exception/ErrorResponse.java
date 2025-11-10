package com.example.PROOUNCE_TASK.Exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    List<String> details,
    String path
) {}