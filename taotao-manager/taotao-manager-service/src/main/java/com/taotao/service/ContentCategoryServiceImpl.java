package com.taotao.service;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    TbContentCategoryMapper categoryMapper;

    /**
     * 内容分类列表查询
     * @param parentId
     * @return
     */
    @Override
    public List<EUTreeNode> getCategoryList(Long parentId) {

        //模板查询
        TbContentCategoryExample categoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categoryList = categoryMapper.selectByExample(categoryExample);
        List<EUTreeNode> treeNodeList = new ArrayList<>();

        for (TbContentCategory category : categoryList){
            //创建一个节点
            EUTreeNode treeNode = new EUTreeNode();
            treeNode.setId(category.getId());
            treeNode.setText(category.getName());
            treeNode.setState(category.getIsParent() ? "closed" : "open");

            treeNodeList.add(treeNode);
        }

        return treeNodeList;
    }

    /**
     * 内容分类节点添加
     * 返回新插入的分类的id
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public TaotaoResult insertCategory(Long parentId, String name) {

        //创建一个pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        //新创建没有子节点，isParent为0
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //添加记录
        categoryMapper.insert(contentCategory);
        //查看父节点的isparent列是否为true，不是则改为true
        TbContentCategory parentCat = categoryMapper.selectByPrimaryKey(parentId);
        if (!parentCat.getIsParent()){
            parentCat.setIsParent(true);
            categoryMapper.updateByPrimaryKey(parentCat);
        }

        //返回结果
        return TaotaoResult.ok(contentCategory);
    }

    /**
     * 分类节点删除
     * @param parentId
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteCategoryById(Long parentId, Long id) {
        //物理删除节点记录
        categoryMapper.deleteByPrimaryKey(id);

        //判断父节点下是否还有子节点，没有则将父节点状态设为false
        TbContentCategoryExample categoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categoryList = categoryMapper.selectByExample(categoryExample);

        if (categoryList.size() > 0 && categoryList != null ){
            //还存在至少一个子节点
            return TaotaoResult.ok();
        }else {
            //没有子节点。修改父节点的isparentID为false
            TbContentCategory category = new TbContentCategory();
            category.setId(parentId);
            category.setIsParent(false);
            categoryMapper.updateByPrimaryKey(category);
        }
        return TaotaoResult.ok();
    }

    /**
     * 内容节点名称修改
     * @param id
     * @param name
     * @return
     */
    @Override
    public TaotaoResult updateCategoryNameById(Long id, String name) {

        //创建pojo对象，补全字段
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        categoryMapper.updateByPrimaryKeySelective(contentCategory);

        return TaotaoResult.ok();
    }
}
