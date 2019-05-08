package guo.ping.taotao.service.impl;

import guo.ping.taotao.common.pojo.EasyUITreeNode;
import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.mapper.TbContentCategoryMapper;
import guo.ping.taotao.pojo.TbContentCategory;
import guo.ping.taotao.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理Service
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    /**
     * 获取商品分类列表
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        List<TbContentCategory> categories = tbContentCategoryMapper.selectTbContentCatsByParentId(parentId);
        List<EasyUITreeNode> nodes = new ArrayList<>();

        for (TbContentCategory contentCategory : categories) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent() ? "closed" : "open");
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * 新增一台商品分类
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public TaotaoResult insertCategory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        contentCategory.setIsParent(false);
        // 1正常 2删除
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        tbContentCategoryMapper.insertCategory(contentCategory);

        TbContentCategory parentContentCategory = tbContentCategoryMapper.selectTbContentCatById(parentId);
        if (!parentContentCategory.getIsParent()) {
            parentContentCategory.setIsParent(true);
            parentContentCategory.setUpdated(new Date());
            tbContentCategoryMapper.updateContentCategoryById(parentContentCategory);
        }
        return TaotaoResult.ok(contentCategory.getId());
    }

    /**
     * 删除一条分类
     * @param parentId
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteCategory(Long parentId, Long id) {

        //查询删除的结点是否为父节点
        List<TbContentCategory> categoryList = tbContentCategoryMapper.selectTbContentCatsByParentId(id);

        if (categoryList == null || categoryList.size() == 0){
            //要删除的分类不是父节点，直接删除
            tbContentCategoryMapper.deleteContenCategoryById(id);
        }else if (categoryList.size() == 1){
            //删除的结点不是父节点，且父节点下只有一个子节点。将父节点状态改为0
            tbContentCategoryMapper.deleteContenCategoryById(id);
            TbContentCategory contentCategory = new TbContentCategory();
            contentCategory.setIsParent(false);
            contentCategory.setUpdated(new Date());
            contentCategory.setId(parentId);
            tbContentCategoryMapper.updateContentCategoryById(contentCategory);
        }else {
            //删除的分类是父节点，删除下面的所有结点
            tbContentCategoryMapper.deleteContenCategoryByParentId(parentId);
        }

        return TaotaoResult.ok();
    }
}
