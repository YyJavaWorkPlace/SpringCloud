package com.course.file.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/21 22:32
 */
@RestController
@RequestMapping("admin")
public class FileUploadController {
    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

    public static final String File_NAME = "文件上传";

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("文件上传开始:{}", file);
        LOG.info("文件名:{}", file.getOriginalFilename());
        LOG.info("文件大小:{}", String.valueOf(file.getSize()));
        //保存文件到本地
        String fileName = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        //新文件名称
        String fullPath = "F:\\uploadVideo\\" + key + "-" + fileName;
        File desc = new File(fullPath);
        file.transferTo(desc);
        LOG.info("上传后的绝对路径:{}", desc.getAbsolutePath());
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
