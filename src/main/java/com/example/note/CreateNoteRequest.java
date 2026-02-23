package com.example.note;

import jakarta.validation.constraints.Size;


public record CreateNoteRequest (
        @Size(max = 1000, message = "Content cannot exceed 1000 characters")
         String content
) {
}
