package com.pabloharrison.RomStorage.service;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    private final Drive drive;
    @Value("${google.drive.folder-id}")
    private String folderId;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(multipartFile.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(folderId));

        InputStreamContent mediaContent = new InputStreamContent(multipartFile.getContentType(), multipartFile.getInputStream());

        File file = drive.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        return file.getId();
    }

    public void deleteFile(String fileId) throws IOException{
        drive.files().delete(fileId).execute();
    }
}
