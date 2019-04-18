package com.taotao.sso.service.impl;

import com.taotao.sso.service.UserService;
import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.mapper.TbUserMapper;
import guo.ping.taotao.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;

	@Override
	public TaotaoResult checkData(String content, Integer type) {
		//对数据进行校验：type类型1,2,3对应username、phone、email
		if (1 == type){
			List<TbUser> userList = tbUserMapper.selectByUsername(content);
			if (userList == null || userList.size() == 0){
				return  TaotaoResult.ok(true);
			}
		}
		if (2 == type){
			List<TbUser> userList = tbUserMapper.selectByPhone(content);
			if (userList == null || userList.size() == 0){
				return  TaotaoResult.ok(true);
			}
		}
		if (3 == type){
			List<TbUser> userList = tbUserMapper.selectByEmail(content);
			if (userList == null || userList.size() == 0){
				return  TaotaoResult.ok(true);
			}
		}

		return TaotaoResult.ok(false);
	}
}
