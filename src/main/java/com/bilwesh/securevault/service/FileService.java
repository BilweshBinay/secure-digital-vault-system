package com.bilwesh.securevault.service;

import com.bilwesh.securevault.dto.FileResponse;
import com.bilwesh.securevault.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService  {
    void uploadFile(MultipartFile file) throws IOException;
    List<FileResponse> getMyFile();
}
