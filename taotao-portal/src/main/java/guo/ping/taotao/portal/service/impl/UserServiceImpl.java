package guo.ping.taotao.portal.service.impl;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.HttpClientUtil;
import guo.ping.taotao.pojo.TbUser;
import guo.ping.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//用户管理
@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOEKN}")
	private String SSO_USER_TOEKN;
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;

	@Override
	public TbUser getUserByToken(String token) {
		//调用sso服务，更加token获取用户信息
		String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOEKN + token);
		//把json转为TaotaoResult
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbUser.class);
		if (taotaoResult.getStatus() == 200){
			TbUser user = (TbUser) taotaoResult.getData();
			return user;
		}
		return null;
	}
}
