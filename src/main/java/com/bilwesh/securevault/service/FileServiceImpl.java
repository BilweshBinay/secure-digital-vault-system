package com.bilwesh.securevault.service;

import com.bilwesh.securevault.dto.FileResponse;
import com.bilwesh.securevault.entity.FileEntity;
import com.bilwesh.securevault.entity.User;
import com.bilwesh.securevault.exception.UserNotFoundException;
import com.bilwesh.securevault.repository.FileRepository;
import com.bilwesh.securevault.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<FileResponse> getMyFile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<FileEntity> files = fileRepository.findByUser(user);

        List<FileResponse> response = new ArrayList<>();

        for (FileEntity file : files) {
            FileResponse fileResponse = new FileResponse(
                    file.getId(),
                    file.getFileName(),
                    file.getFileType(),
                    file.getFileSize(),
                    file.getUploadedAt()
            );

//            This is using Stream
//            return files.stream()
//                    .map(file -> new FileResponse(file.getId(), file.getFileName(), file.getFileType(),
//                            file.getFileSize(), file.getUploadedAt()))
//                    .toList();

            response.add(fileResponse);
        }
        return response;
    }

    @Override
    public Resource downloadFile(Long id) throws MalformedURLException {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!file.getUser().getId().equals(user.getId())){
            throw  new RuntimeException("Access Denied");
        }

        Path path = Paths.get(file.getFilePath());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }

        return resource;
    }
}