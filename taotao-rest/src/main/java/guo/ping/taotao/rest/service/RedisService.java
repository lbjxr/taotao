package guo.ping.taotao.rest.service;

import guo.ping.taotao.common.pojo.TaotaoResult;

public interface RedisService {

	TaotaoResult syncContent(long contentCid);
}
