package guo.ping.taotao.portal.service;

import guo.ping.taotao.pojo.TbUser;

public interface UserService {
	TbUser getUserByToken(String token);
}
