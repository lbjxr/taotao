package guo.ping.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.deploy.net.HttpUtils;
import guo.ping.taotao.common.pojo.EasyUIDataGridResult;
import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.HttpClientUtil;
import guo.ping.taotao.common.utils.IDUtils;
import guo.ping.taotao.mapper.TbItemDescMapper;
import guo.ping.taotao.mapper.TbItemMapper;
import guo.ping.taotao.mapper.TbItemParamItemMapper;
import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.pojo.TbItemDesc;
import guo.ping.taotao.pojo.TbItemParam;
import guo.ping.taotao.pojo.TbItemParamItem;
import guo.ping.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITME_INFO_URL}")
    private String ITME_INFO_URL;
    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;
    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;


    @Override
    public TbItem getItemById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<TbItem> itemList = tbItemMapper.getItemList();

        PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);

        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(itemList);
        easyUIDataGridResult.setTotal(pageInfo.getTotal());

        return easyUIDataGridResult;
    }

    @Override
    public TaotaoResult createItem(TbItem item, String desc, String itemParam) {
        // 生成商品ID
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        // 商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        // 创建时间和更新时间
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        // 插入商品表
        tbItemMapper.insert(item);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        tbItemDescMapper.insert(tbItemDesc);

        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParam);
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        tbItemParamItemMapper.insert(tbItemParamItem);

        return TaotaoResult.ok();
    }

    public TbItemDesc getItemDesc(Long itemId){
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
            if (!StringUtils.isBlank(json)){
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
                if (taotaoResult.getStatus() == 200){
                    TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
                    return itemDesc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TaotaoResult updateItem(TbItem item, String desc) {

        System.out.println("\n==================\nitem = " + item.getId());
        System.out.println("\n==================\ndesc = " + desc);
        //更新商品信息
        item.setUpdated(new Date());
        tbItemMapper.update(item);

        //更新商品描述信息
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(item.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.update(tbItemDesc);

        return TaotaoResult.ok();
    }
}
