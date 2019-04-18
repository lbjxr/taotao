package com.taotao.sso.service;

import guo.ping.taotao.common.pojo.TaotaoResult;

public interface UserService {

	TaotaoResult checkData(String content, Integer type);
}
