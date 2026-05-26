package com.pabloharrison.RomStorage.dto;

public record RomPatchDTO(
        String title,
        String fileName,
        String storageKey,
        Long sizeBytes,
        String platform,
        String coverUrl){
}
