package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**文件上传service
 * @author QinFen
 * @date 2019/9/6 0006 19:25
 */
public interface IFileService {

    /**文件上传
     * @param file
     * @param path
     * @return
     */
    String upload(MultipartFile file, String path);
}
