package com.zan.hup.file.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @version 1.0
 * @author: hupeng
 * @create: 2018-10-06 21:16
 * @description:
 **/
public interface FileService {

    String singleFileUpload(MultipartFile multipartFile);

    List<String> multiFileUpload(MultipartFile[] multipartFiles);

    ResponseEntity download(String objectId);

    ResponseEntity preview(String objectId);
}
