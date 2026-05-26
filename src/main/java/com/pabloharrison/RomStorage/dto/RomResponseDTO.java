package com.pabloharrison.RomStorage.dto;

import java.util.UUID;

public record RomResponseDTO(
        UUID id,
        String title,
        String fileName,
        String storageKey,
        Long sizeBytes,
        String platform,
        String coverUrl,
        String createdAt) {
}
