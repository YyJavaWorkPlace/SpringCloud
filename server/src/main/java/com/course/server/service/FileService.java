package com.course.server.service;

import com.course.server.domain.File;
import com.course.server.domain.FileExample;
import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.FileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.ognl.CollectionElementsAccessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * File 大章表业务逻辑
 */
@Service
public class FileService {
    @Resource
    private FileMapper fileMapper;

    /**
     * 查询课程大章列表
     *
     * @return
     */
    public void list(PageDto pageDto) {
        //遇到的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        FileExample fileExample = new FileExample();

        List<File> fileList = fileMapper.selectByExample(fileExample);
        PageInfo<File> pageInfo = new PageInfo<>(fileList);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDto> fileDtos = CopyUtil.copyList(fileList, FileDto.class);
        pageDto.setList(fileDtos);
    }

    public void save(FileDto fileDto) {
        File file = CopyUtil.copy(fileDto, File.class);
        File fileDb = selectByKey(fileDto.getKey());
        if (fileDb==null) {
            this.insert(file);
        } else {
            fileDb.setShardIndex(fileDto.getShardIndex()+1);
            this.update(fileDb);
        }
    }

    private void insert(File file) {
        Date now = new Date();
        file.setUpdatedAt(now);
        file.setId(UuidUtil.getShortUuid());
        fileMapper.insert(file);
    }

    private void update(File file) {
        file.setUpdatedAt(new Date());
        fileMapper.updateByPrimaryKey(file);
    }

    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }


    public File selectByKey(String key) {
        FileExample example = new FileExample();
        example.createCriteria().andKeyEqualTo(key);
        List<File> files = fileMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(files)) {
            return null;
        } else {
            return files.get(0);
        }
    }
}
