package com.zan.hup.file.repository;

import com.zan.hup.file.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hupeng on 2018-12-18
 * @version 1.0
 * @Description
 */
public interface FileRepository extends MongoRepository<File,String> {
}
