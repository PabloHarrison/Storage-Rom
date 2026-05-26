package com.pabloharrison.RomStorage.mapper;

import com.pabloharrison.RomStorage.dto.RomPatchDTO;
import com.pabloharrison.RomStorage.dto.RomPostDTO;
import com.pabloharrison.RomStorage.dto.RomResponseDTO;
import com.pabloharrison.RomStorage.entity.Rom;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RomMapper {

    public Rom toEntity(RomPostDTO dto);

    public RomResponseDTO toDTO(Rom rom);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateEntity(RomPatchDTO dto, @MappingTarget Rom rom);
}
