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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RomService {

    public final RomMapper romMapper;
    public final RomRepository romRepository;

    public RomResponseDTO saveRom(RomPostDTO dto){
        Rom rom = romMapper.toEntity(dto);
        romRepository.save(rom);
        return romMapper.toDTO(rom);
    }
    public Rom findByID(String id){
        UUID idRom = UUID.fromString(id);
        return romRepository.findById(idRom).orElseThrow(() -> new RomNotFoundException("Rom not found!"));
    }
    public RomResponseDTO findRomById(String id){
        Rom rom = findByID(id);
        return romMapper.toDTO(rom);
    }
    public void deleteRom(String id){
        Rom rom = findByID(id);
        romRepository.delete(rom);
    }
    public RomResponseDTO updateRom(String id, RomPatchDTO dto){
        Rom rom = findByID(id);
        romMapper.updateEntity(dto, rom);
        romRepository.save(rom);
        return romMapper.toDTO(rom);
    }
}
