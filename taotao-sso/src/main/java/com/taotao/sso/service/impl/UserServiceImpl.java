package com.taotao.sso.service.impl;

import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.JsonUtils;
import guo.ping.taotao.mapper.TbUserMapper;
import guo.ping.taotao.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;

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

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public TaotaoResult userLogin(String username, String password) {
		List<TbUser> userList = tbUserMapper.selectByUsername(username);
		//如果没有此用户名
		if (null == userList || userList.size() == 0){
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		TbUser user = userList.get(0);
		//比对密码
		//密码错误
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
			return TaotaoResult.build(400, "用户名或密码错误");
		}

		//用户名和密码正确，生成token
		String token = UUID.randomUUID().toString();
		//将用户信息写入redis
		//清空用户密码
		user.setPassword(null);
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		//设置session过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		return TaotaoResult.ok(token);
	}

	/**
	 * 获取用户信息
	 * @param token
	 * @return
	 */
	@Override
	public TaotaoResult getUserByToken(String token) {
		//更加token从redis获取用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		//判断是否为空
		if (StringUtils.isBlank(json)){
			return TaotaoResult.build(400, "session已过期，请重新登录");
		}
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

		return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}
}
