package com.example.fileupload.service;

import com.example.fileupload.exception.BadRequestException;
import com.example.fileupload.exception.NotFoundException;
import com.example.fileupload.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.ParameterizedType;
import java.util.function.Supplier;

@Slf4j
public class BaseService<T,ID> {

    private final BaseRepository<T, ID> repository;

    public BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }


    public T findById(ID id) {
        String entityName =
                ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
        return repository.findById(id).orElseThrow(notFoundException(entityName, entityName + " is not found with given id:" + id));
    }

    protected void validateFile(MultipartFile file) {
        if (file.isEmpty() || file == null) {
            throw new BadRequestException("File must not be null or empty");
        }
        if (!StringUtils.hasLength(file.getOriginalFilename())) {
            throw new BadRequestException("File must not be null or empty");
        }
    }

    protected Supplier<NotFoundException> notFoundException(String entityName){
       return this.notFoundException(entityName,null);
    }

    protected Supplier<NotFoundException> notFoundException(String entityName,String developerMessage){
        return () -> new NotFoundException(entityName,developerMessage);
    }
}
