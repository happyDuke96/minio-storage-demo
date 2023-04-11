package com.example.fileupload.repository;

import com.example.fileupload.domain.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileUploadRepository extends JpaRepository<FileUpload,String>, JpaSpecificationExecutor<FileUpload> {

    @Query("select f from FileUpload f where f.fileName in (:fileNames)")
    List<FileUpload> findAllByFileNameIn(@Param("fileNames") List<String> fileNames);

}
