package com.zan.hup.file;

import com.sun.istack.internal.NotNull;
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
    String upload(@NotNull MultipartFile multipartFile);

    @PostMapping("/uploads")
    List<String> upload(@NotNull MultipartFile[] multipartFiles);

    @DeleteMapping
    void delete(@NotNull @RequestParam("objectId") String objectId);

    @GetMapping("/download")
    ResponseEntity download(@NotNull @RequestParam("objectId") String objectId);

    @GetMapping("/preview")
    ResponseEntity preview(@NotNull @RequestParam("objectId") String objectId);

}
