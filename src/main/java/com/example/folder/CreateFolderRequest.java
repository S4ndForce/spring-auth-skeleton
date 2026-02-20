package com.example.folder;

import jakarta.validation.constraints.Size;

public record CreateFolderRequest(
        @Size(max = 1000, message = "Name cannot exceed 100 characters")
         String name
) {
}
