package guo.ping.taotao.rest.service.impl;

import guo.ping.taotao.mapper.TbContentMapper;
import guo.ping.taotao.pojo.TbContent;
import guo.ping.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    /**
     * 获取内容列表服务
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentList(Long cid) {
        //到redis缓存中查询是否有
        return tbContentMapper.getContentListByCategoryId(cid);
    }
}
