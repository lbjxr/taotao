package guo.ping.taotao.rest.service.impl;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.ExceptionUtil;
import guo.ping.taotao.rest.dao.JedisClient;
import guo.ping.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;

	@Override
	public TaotaoResult syncContent(long contentCid) {
		try {
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid + "");
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult syncItem(long itemId, String lastParam){
		try {
			jedisClient.del(REDIS_ITEM_KEY + ":" + itemId + ":" + lastParam);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
