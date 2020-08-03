package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileTypeEnum;
import com.course.server.service.FileService;
import com.course.server.util.UuidUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    public ResponseDto upload(@RequestParam MultipartFile file, String use, String name, String suffix, Integer size, Integer shardIndex, Integer shardSize, Integer shardTotal) throws IOException {
        LOG.info("文件上传开始");
        //保存文件到本地
        FileTypeEnum fileTypeEnum = FileTypeEnum.getByCode(use);
        String fileName = file.getOriginalFilename();

//        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        //如果文件夹不存在则创建
        String dirname = fileTypeEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dirname);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String key = UuidUtil.getShortUuid();
        String path = dirname + File.separator + key + "." + suffix;
        //新文件名称
        String fullPath = FILE_PATH + path;
        File desc = new File(fullPath);
        file.transferTo(desc);
        LOG.info("上传后的绝对路径:{}", desc.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setName(name);
        fileDto.setPath(path);
        /*
         * 转为Int类型
         */
        fileDto.setSize(size);
        fileDto.setSuffix(suffix);
        fileDto.setUse(use);
        fileDto.setShardIndex(shardIndex);
        fileDto.setShardTotal(shardTotal);
        fileDto.setShardSize(shardSize);
        fileDto.setKey(key);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    @GetMapping("/merge")
    public ResponseDto merge() throws FileNotFoundException {
        File newFile = new File(FILE_PATH + "/course/test123.mp4");
        FileOutputStream outputStream = new FileOutputStream(newFile, true);
        FileInputStream inputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;
        try {
            inputStream = new FileInputStream(new File(FILE_PATH + "/course/snMxobRL.blob"));
            while ((len = inputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
            }
            //读取第二个分片
            inputStream = new FileInputStream(new File(FILE_PATH + "/course/yEP281ww.blob"));
            while ((len = inputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
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
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
