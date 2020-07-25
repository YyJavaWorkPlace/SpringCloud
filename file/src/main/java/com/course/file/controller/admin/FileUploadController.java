package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.FileService;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    public static final String BUSSINESS_NAME = "文件上传";

    @Value("${file.domain}")
    private String FILE_DOMAIN;
    @Value("${file.path}")
    private String FILE_PATH;
    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("文件上传开始:{}", file);
        LOG.info("文件名:{}", file.getOriginalFilename());
        LOG.info("文件大小:{}", String.valueOf(file.getSize()));
        //保存文件到本地
        String fileName = file.getOriginalFilename();

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        String key = UuidUtil.getShortUuid();
        String path = "teacher/" + key + "." + suffix;
        //新文件名称
        String fullPath = FILE_PATH + path;
        File desc = new File(fullPath);
        file.transferTo(desc);
        LOG.info("上传后的绝对路径:{}", desc.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setName(fileName);
        fileDto.setPath(path);
        /*
         * 转为Int类型
         */
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse("");
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(FILE_DOMAIN + path);
        return responseDto;
    }
}
