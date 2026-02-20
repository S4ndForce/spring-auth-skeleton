package com.example.note;

import jakarta.validation.constraints.Size;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateNoteRequest (
        @Size(max = 1000, message = "Content cannot exceed 1000 characters")
         String content
) {
}
