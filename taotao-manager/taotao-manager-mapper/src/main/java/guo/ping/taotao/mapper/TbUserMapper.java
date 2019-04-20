package guo.ping.taotao.mapper;

import guo.ping.taotao.pojo.TbUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbUserMapper {
	List<TbUser> selectByUsername(@Param(value="username") String username);
	List<TbUser> selectByPhone(@Param(value="phone") String phone);
	List<TbUser> selectByEmail(@Param(value="email") String email);
	void insert(TbUser user);
}
