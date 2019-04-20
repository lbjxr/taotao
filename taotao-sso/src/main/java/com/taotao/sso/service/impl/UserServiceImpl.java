package com.taotao.sso.service.impl;

import com.taotao.sso.service.UserService;
import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.mapper.TbUserMapper;
import guo.ping.taotao.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;

	/**
	 * 数据校验是否可用
	 * @param content
	 * @param type
	 * @return
	 */
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

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@Override
	public TaotaoResult createUser(TbUser user) {
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//密码md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		tbUserMapper.insert(user);
		return TaotaoResult.ok();
	}
}
