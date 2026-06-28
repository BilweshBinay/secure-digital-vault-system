package com.bilwesh.securevault.service;

import com.bilwesh.securevault.entity.FileEntity;
import com.bilwesh.securevault.entity.User;
import com.bilwesh.securevault.exception.UserNotFoundException;
import com.bilwesh.securevault.repository.FileRepository;
import com.bilwesh.securevault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void uploadFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("Please select a file");
        }

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        String uploadDir = System.getProperty("user.dir") + "/StoreFile";

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + "/" + file.getOriginalFilename();

        file.transferTo(new File(filePath));

        FileEntity fileEntity = new FileEntity();

        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileSize(file.getSize());
        fileEntity.setFilePath(filePath);
        fileEntity.setUploadedAt(LocalDateTime.now());
        fileEntity.setUser(user);

        fileRepository.save(fileEntity);
    }
}