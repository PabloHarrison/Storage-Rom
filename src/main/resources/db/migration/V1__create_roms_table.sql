CREATE TABLE roms (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    storage_key VARCHAR(255) NOT NULL,
    size_bytes BIGINT NOT NULL,
    platform VARCHAR(100),
    cover_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL
);