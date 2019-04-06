package guo.ping.taotao.rest.service.impl;

import guo.ping.taotao.common.utils.JsonUtils;
import guo.ping.taotao.mapper.TbContentMapper;
import guo.ping.taotao.pojo.TbContent;
import guo.ping.taotao.rest.dao.JedisClient;
import guo.ping.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;
    /**
     * 获取内容列表服务
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentList(Long cid) {
        //到redis缓存中取数据
        try {
            String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, cid + "");
            if (!StringUtils.isBlank(result)){
                //把字符串转换成list
                List< TbContent> tbContentList = JsonUtils.jsonToList(result, TbContent.class);
                return tbContentList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据内容分类id查询内容列表
        List<TbContent> contentList = tbContentMapper.getContentListByCategoryId(cid);

        //向缓存中添加内容
        try {
            //吧list转换成字符串
            String cacheString = JsonUtils.objectToJson(contentList);
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY, cid + "", cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  contentList;
    }
}
