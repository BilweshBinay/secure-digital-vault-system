package com.bilwesh.securevault.controller;

import com.bilwesh.securevault.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)
            throws IOException { // Why return type is String
        fileService.uploadFile(file);
        return "File uploaded successfully";
    }
}
