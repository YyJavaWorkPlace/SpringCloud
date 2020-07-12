package com.course.server.service;

import com.course.server.domain.Category;
import com.course.server.domain.CategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Category 大章表业务逻辑
 */
@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询课程大章列表
     *
     * @return
     */
    public void list(PageDto pageDto) {
        //遇到的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");

        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto> categoryDtos = CopyUtil.copyList(categoryList, CategoryDto.class);
        pageDto.setList(categoryDtos);
    }

    /**
     * 列表查询
     */
    public List<CategoryDto> all() {
        //遇到的第一个select语句会进行分页
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtos = CopyUtil.copyList(categoryList, CategoryDto.class);
        return categoryDtos;
    }

    public void save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto, Category.class);
        if (StringUtils.isEmpty(categoryDto.getId())) {
            this.insert(category);
        } else {
            this.update(category);
        }
    }

    private void insert(Category category) {
        Date now = new Date();
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    private void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    @Transactional
    public void delete(String id) {
        deleteChildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除子分类
     * 否则产生垃圾数据
     *
     * @param id
     */
    public void deleteChildren(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if ("00000000".equals(category.getParent())) {
            //如果是一级分类,需要删除其下的二级分类
            CategoryExample example = new CategoryExample();
            CategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(example);
        }
    }
}
