package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author QinFen
 * @date 2019/9/6 0006 19:25
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
