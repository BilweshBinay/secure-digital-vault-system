package com.bilwesh.securevault.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name= "file_table")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String filePath;
    private LocalDateTime uploadedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
