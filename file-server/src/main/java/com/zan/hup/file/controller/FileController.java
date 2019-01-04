package com.zan.hup.file.controller;

import com.zan.hup.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hupeng on 2018/10/16
 * @version 1.0
 * @Description
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String upload(@NotNull MultipartFile multipartFile) {
        return fileService.singleFileUpload(multipartFile);
    }

    @PostMapping("/uploads")
    public List<String> upload(@NotNull MultipartFile[] multipartFiles) {
        return fileService.multiFileUpload(multipartFiles);
    }

    @DeleteMapping
    public void delete(@NotNull @RequestParam("objectId") String objectId) {
        fileService.deleteFile(objectId);
    }

    @GetMapping("/download")
    public ResponseEntity download(@NotNull @RequestParam("objectId") String objectId) {
        return fileService.download(objectId);
    }

    @GetMapping("/preview")
    public ResponseEntity preview(@NotNull @RequestParam("objectId") String objectId) {
        return fileService.preview(objectId);
    }

}
