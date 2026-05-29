package com.pabloharrison.RomStorage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RomPostDTO(
        @NotBlank(message = "It must not be blank.") String title,
        @NotBlank(message = "It must not be blank.") String fileName,
        @NotBlank(message = "It must not be blank.") String storageKey,
        @NotNull(message = "It must not be null.") Long sizeBytes,
        String platform,
        String coverUrl) {
}
