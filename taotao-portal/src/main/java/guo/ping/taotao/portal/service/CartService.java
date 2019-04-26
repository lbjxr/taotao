package guo.ping.taotao.portal.service;

import guo.ping.taotao.common.pojo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CartService {
	TaotaoResult addCartItem(Long itemId, int num, HttpServletRequest request, HttpServletResponse response);
}
