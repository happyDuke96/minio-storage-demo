package com.example.fileupload.repository;

import com.example.fileupload.domain.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload,String> {
}
