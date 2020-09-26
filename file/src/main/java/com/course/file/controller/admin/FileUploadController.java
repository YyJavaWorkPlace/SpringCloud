package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileTypeEnum;
import com.course.server.service.FileService;
import com.course.server.util.Base64ToMultipartFile;
import com.course.server.util.UuidUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import java.io.*;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/21 22:32
 */
@RestController
@RequestMapping("admin")
public class FileUploadController {
    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

    public static final String BUSSINESS_NAME = "文件上传";

    @Value("${file.domain}")
    private String FILE_DOMAIN;
    @Value("${file.path}")
    private String FILE_PATH;
    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {
        LOG.info("文件上传开始");
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);
        //保存文件到本地
        FileTypeEnum fileTypeEnum = FileTypeEnum.getByCode(use);
        //如果文件夹不存在则创建
        String dirname = fileTypeEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dirname);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String path = new StringBuffer(dirname)
                .append("/")
                .append(key)
                .append(".")
                .append(suffix)
                .toString();
        String localPath = new StringBuffer(path)
                .append(".")
                .append(fileDto.getShardIndex()).toString();
        //新文件名称
        String fullPath = FILE_PATH + localPath;
        File desc = new File(fullPath);
        shard.transferTo(desc);
        LOG.info("上传后的绝对路径:{}", desc.getAbsolutePath());
        LOG.info("保存文件记录开始");
        fileDto.setPath(path);
        fileService.save(fileDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(fileDto);
        fileDto.setPath(FILE_DOMAIN + path);
        if (fileDto.getShardIndex().equals(fileDto.getShardTotal())) {
            this.merge(fileDto);
        }
        return responseDto;
    }


    private void merge(FileDto fileDto) throws FileNotFoundException, InterruptedException {
        LOG.info("合并分片开始");
        Integer shardTotal = fileDto.getShardTotal();
        String path = fileDto.getPath();
        path = path.replace(FILE_DOMAIN, "");
        File newFile = new File(FILE_PATH + path);
        FileOutputStream outputStream = new FileOutputStream(newFile, true);
        FileInputStream inputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;
        try {
            for (int i = 1; i <= shardTotal; i++) {
                inputStream = new FileInputStream(new File(FILE_PATH + path + "." + i));
                while ((len = inputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOG.info("分片合并异常", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (Exception e) {
                LOG.error("IO流关闭");
            }
        }
        LOG.info("分片合并结束");

        System.gc();
        /**
         * 调用GC java不会马上去执行垃圾回收 会先进行手上的工作
         */
        Thread.sleep(100);

        //删除分片开始
        LOG.info("删除分片开始");
        for (int i = 1; i <= shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + 1;
            File file = new File(filePath);
            boolean result = file.delete();
            LOG.info("删除{},{}", filePath, result ? "成功" : "失败");
        }
        LOG.info("删除分片结束");
    }

    @GetMapping("/check/{key}")
    public ResponseDto check(@PathVariable String key) {
        LOG.info("检查上传分片开始:{}", key);
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = fileService.findByKey(key);
        if (fileDto != null) {
            fileDto.setPath(FILE_DOMAIN + fileDto.getPath());
        }
        responseDto.setContent(fileDto);
        return responseDto;
    }
}
