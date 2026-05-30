package com.pabloharrison.RomStorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pabloharrison.RomStorage.dto.RomPatchDTO;
import com.pabloharrison.RomStorage.dto.RomPostDTO;
import com.pabloharrison.RomStorage.dto.RomResponseDTO;
import com.pabloharrison.RomStorage.entity.Rom;
import com.pabloharrison.RomStorage.service.GoogleDriveService;
import com.pabloharrison.RomStorage.service.RomService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roms")
public class RomController {

    private final RomService romService;
    private final ObjectMapper objectMapper;
    private final GoogleDriveService googleDriveService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RomResponseDTO> romSave(
            @RequestPart("data") String data,
            @RequestPart("file") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String contentType = file.getContentType();
        if (!"application/zip".equals(contentType) && !"application/x-zip-compressed".equals(contentType)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
        RomPostDTO dto = objectMapper.readValue(data, RomPostDTO.class);
        RomResponseDTO responseDTO = romService.saveRom(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<RomResponseDTO> findRom(@PathVariable("id") String id) {
        RomResponseDTO responseDTO = romService.findRomById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<RomResponseDTO>> findAllRoms() {
        List<RomResponseDTO> responseDTO = romService.findAllRoms();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RomResponseDTO> deleteRom(@PathVariable("id") String id) throws IOException {
        romService.deleteRom(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<RomResponseDTO> updateRom(@PathVariable("id") String id, @RequestBody RomPatchDTO dto) {
        RomResponseDTO responseDTO = romService.updateRom(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") String id) throws IOException {
        Rom rom = romService.findByID(id);
        Resource resource = googleDriveService.downloadFile(rom.getStorageKey());
        String fileName = rom.getFileName();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(rom.getSizeBytes())
                .body(resource);

    }

    @GetMapping("/verify/{id}")
    public ResponseEntity<Boolean> verifyChecksum(@PathVariable("id") String id) throws NoSuchAlgorithmException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(romService.verifyChecksum(id));
    }
}
