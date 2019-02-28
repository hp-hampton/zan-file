package com.zan.hup.file;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hupeng on 2018/12/5
 * @version 1.0
 * @Description
 */
@FeignClient
@RequestMapping("/api/storage")
public interface FileClient {

    @PostMapping("/upload")
    String upload(MultipartFile multipartFile);

    @PostMapping("/uploads")
    List<String> upload(MultipartFile[] multipartFiles);

    @DeleteMapping
    void delete(@RequestParam("objectId") String objectId);

    @GetMapping("/download")
    ResponseEntity download(@RequestParam("objectId") String objectId);

    @GetMapping("/preview")
    ResponseEntity preview(@RequestParam("objectId") String objectId);

}
