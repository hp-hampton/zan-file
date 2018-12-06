package com.zan.hup.file.model;

import lombok.Data;

import java.io.ByteArrayInputStream;
import java.util.Date;

/**
 * @version 1.0
 * @author: hupeng
 * @create: 2018-10-06 21:27
 * @description:
 **/
@Data
public class File {
    private String name; // 文件名称
    private String contentType; // 文件类型
    private Long length;
    private Integer chunkSize;
    private Date uploadDate;
    private String md5;
    private ByteArrayInputStream content; // 文件内容
}
