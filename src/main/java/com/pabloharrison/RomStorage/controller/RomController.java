package com.pabloharrison.RomStorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pabloharrison.RomStorage.dto.RomPatchDTO;
import com.pabloharrison.RomStorage.dto.RomPostDTO;
import com.pabloharrison.RomStorage.dto.RomResponseDTO;
import com.pabloharrison.RomStorage.service.RomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roms")
public class RomController {

    private final RomService romService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RomResponseDTO> romSave(
            @RequestPart("data") String data,
            @RequestPart("file") MultipartFile file) throws IOException {
        RomPostDTO dto = objectMapper.readValue(data, RomPostDTO.class);

        RomResponseDTO responseDTO = romService.saveRom(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("{id}")
    public ResponseEntity<RomResponseDTO> findRom(@PathVariable("id") String id){
        RomResponseDTO responseDTO = romService.findRomById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<RomResponseDTO> deleteRom(@PathVariable("id") String id) throws IOException {
        romService.deleteRom(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("{id}")
    public ResponseEntity<RomResponseDTO> updateRom(@PathVariable("id") String id, @RequestBody RomPatchDTO dto){
        RomResponseDTO responseDTO = romService.updateRom(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
