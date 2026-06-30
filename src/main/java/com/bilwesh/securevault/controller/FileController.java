package com.bilwesh.securevault.controller;

import com.bilwesh.securevault.dto.FileResponse;
import com.bilwesh.securevault.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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

    @GetMapping("/my-files")
    public List<FileResponse> getMyFiles() {
        System.out.println("Inside getMyFile()");
        return fileService.getMyFile();
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("TEST ENDPOINT");
        return "Working";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws MalformedURLException {
        Resource resource = fileService.downloadFile(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""+ resource.getFilename()+"\"")
                .body(resource);
    }
}
