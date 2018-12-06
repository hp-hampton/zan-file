package com.zan.hup.file.service.impl;


import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zan.hup.file.model.File;
import com.zan.hup.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: hupeng
 * @create: 2018-10-06 21:23
 * @description:
 **/
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MongoDbFactory mongoDbFactory;


    public String singleFileUpload(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectId store = gridFsTemplate.store(inputStream, originalFilename, contentType);
        return store.toString();
    }

    @Override
    public List<String> multiFileUpload(MultipartFile[] multipartFiles) {
        List<String> objectIds = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String objectId = singleFileUpload(multipartFile);
            objectIds.add(objectId);
        }
        return objectIds;
    }

    public ResponseEntity download(String objectId) {
        File file = getFile(objectId);
        InputStreamResource inputStreamResource = new InputStreamResource(file.getContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()))
                .header("name", file.getName())
                .header("contentType", file.getContentType())
                .body(inputStreamResource);
    }

    public ResponseEntity preview(String objectId) {
        File file = getFile(objectId);
        InputStreamResource inputStreamResource = new InputStreamResource(file.getContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                .body(inputStreamResource);
    }

    private File getFile(String objectId) {
        Query query = Query.query(Criteria.where("_id").is(objectId));
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        String fileName = gridFSFile.getFilename().replace(",", "");
        GridFSBucket bucket = GridFSBuckets.create(mongoDbFactory.getDb());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bucket.downloadToStream(gridFSFile.getId(), byteArrayOutputStream);
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        File file = new File();
        file.setName(fileName);
        file.setContentType(gridFSFile.getMetadata().get("_contentType").toString());
        file.setLength(gridFSFile.getLength());
        file.setChunkSize(gridFSFile.getChunkSize());
        file.setUploadDate(gridFSFile.getUploadDate());
        file.setMd5(gridFSFile.getMD5());
        file.setContent(arrayInputStream);
        return file;
    }
}
