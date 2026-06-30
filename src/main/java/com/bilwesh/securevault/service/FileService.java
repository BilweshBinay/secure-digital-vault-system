package com.bilwesh.securevault.service;

import com.bilwesh.securevault.dto.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileService  {
    void uploadFile(MultipartFile file) throws IOException;
    List<FileResponse> getMyFile();
    Resource downloadFile(Long id) throws MalformedURLException;
}
