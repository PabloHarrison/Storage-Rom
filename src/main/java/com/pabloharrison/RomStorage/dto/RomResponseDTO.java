package com.pabloharrison.RomStorage.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RomResponseDTO(
        UUID id,
        String title,
        String fileName,
        String storageKey,
        Long sizeBytes,
        String platform,
        String coverUrl,
        String checksum,
        LocalDateTime createdAt) {
}
