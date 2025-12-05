package com.app.exceptions;

import java.time.LocalDateTime;

public record ExceptionResponse(
        int statusCode,
        Object details,
        LocalDateTime timestamp,
        String path
) {}
