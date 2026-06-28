package com.bilwesh.securevault.repository;

import com.bilwesh.securevault.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
