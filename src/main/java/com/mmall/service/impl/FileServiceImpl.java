package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**文件上传实现
 * @author QinFen
 * @date 2019/9/6 0006 19:26
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //使用随机数对文件名重新命名，防止重复
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传的文件名：{},上传的路径：{},新文件名：{}",fileName,path,uploadFileName);

        File file1Dir = new File(path);
        if (!file1Dir.exists()){
            //给予写的权限
            file1Dir.setWritable(true);
            file1Dir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功，将文件上传到FTP服务器，删除upload下的文件
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
        } catch (IOException e) {
            logger.error("上传文件异常",e);
        }
        return targetFile.getName();
    }
}
