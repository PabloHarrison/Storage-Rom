package com.pabloharrison.RomStorage.repository;

import com.pabloharrison.RomStorage.entity.Rom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RomRepository extends JpaRepository<Rom, UUID> {
}
