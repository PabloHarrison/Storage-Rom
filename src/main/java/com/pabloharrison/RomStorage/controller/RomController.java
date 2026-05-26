package com.pabloharrison.RomStorage.controller;

import com.pabloharrison.RomStorage.dto.RomPatchDTO;
import com.pabloharrison.RomStorage.dto.RomPostDTO;
import com.pabloharrison.RomStorage.dto.RomResponseDTO;
import com.pabloharrison.RomStorage.service.RomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roms")
public class RomController {

    private final RomService romService;

    @PostMapping
    public ResponseEntity<RomResponseDTO> romSave(@Valid @RequestBody RomPostDTO dto){
        RomResponseDTO responseDTO = romService.saveRom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("{id}")
    public ResponseEntity<RomResponseDTO> findRom(@PathVariable("id") String id){
        RomResponseDTO responseDTO = romService.findRomById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<RomResponseDTO> deleteRom(@PathVariable("id") String id){
        romService.deleteRom(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("{id}")
    public ResponseEntity<RomResponseDTO> updateRom(@PathVariable("id") String id, @RequestBody RomPatchDTO dto){
        RomResponseDTO responseDTO = romService.updateRom(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
