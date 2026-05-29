package com.pabloharrison.RomStorage.service;

import com.pabloharrison.RomStorage.dto.RomPatchDTO;
import com.pabloharrison.RomStorage.dto.RomPostDTO;
import com.pabloharrison.RomStorage.dto.RomResponseDTO;
import com.pabloharrison.RomStorage.entity.Rom;
import com.pabloharrison.RomStorage.exception.RomNotFoundException;
import com.pabloharrison.RomStorage.mapper.RomMapper;
import com.pabloharrison.RomStorage.repository.RomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RomService {

    public final RomMapper romMapper;
    public final RomRepository romRepository;
    public final GoogleDriveService googleDriveService;

    @Transactional
    public RomResponseDTO saveRom(RomPostDTO dto, MultipartFile file) throws IOException {
        String storageKey = null;
        try {
            String fileName = file.getOriginalFilename();
            Long sizeBytes = file.getSize();
            storageKey = googleDriveService.uploadFile(file);

            Rom rom = romMapper.toEntity(dto);
            rom.setFileName(fileName);
            rom.setSizeBytes(sizeBytes);
            rom.setStorageKey(storageKey);
            Rom romSaved = romRepository.save(rom);
            return romMapper.toDTO(romSaved);
        }
        catch (Exception e){
            if (storageKey != null) {
                googleDriveService.deleteFile(storageKey);
            }
            throw e;
        }
    }
    public Rom findByID(String id){
        UUID idRom = UUID.fromString(id);
        return romRepository.findById(idRom).orElseThrow(() -> new RomNotFoundException("Rom not found!"));
    }
    public RomResponseDTO findRomById(String id){
        Rom rom = findByID(id);
        return romMapper.toDTO(rom);
    }
    public void deleteRom(String id) throws IOException{
        Rom rom = findByID(id);
        googleDriveService.deleteFile(rom.getStorageKey());
        romRepository.delete(rom);
    }
    public RomResponseDTO updateRom(String id, RomPatchDTO dto){
        Rom rom = findByID(id);
        romMapper.updateEntity(dto, rom);
        romRepository.save(rom);
        return romMapper.toDTO(rom);
    }
}
