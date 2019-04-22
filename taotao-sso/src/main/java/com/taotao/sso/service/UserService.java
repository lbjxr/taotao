package com.taotao.sso.service;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

	TaotaoResult checkData(String content, Integer type);
	TaotaoResult createUser(TbUser user);
	TaotaoResult userLogin(String username, String password,
						   HttpServletRequest request, HttpServletResponse response);
	TaotaoResult getUserByToken(String token);
}
