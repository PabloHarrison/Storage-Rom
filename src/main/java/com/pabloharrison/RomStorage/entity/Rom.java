package com.pabloharrison.RomStorage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "roms")
@Getter
@Setter
public class Rom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "storage_key", nullable = false)
    private String storageKey;
    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;
    private String platform;
    @Column(name = "cover_url")
    private String coverUrl;
    private String checksum;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
