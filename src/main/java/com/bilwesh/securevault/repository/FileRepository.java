package com.bilwesh.securevault.repository;

import com.bilwesh.securevault.entity.FileEntity;
import com.bilwesh.securevault.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUser(User user);
}
