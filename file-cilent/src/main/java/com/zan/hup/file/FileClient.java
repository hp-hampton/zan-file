package com.zan.hup.file;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/download")
    ResponseEntity download(@RequestParam("objectId") String objectId);

    @GetMapping("/preview")
    ResponseEntity preview(@RequestParam("objectId") String objectId);

}
