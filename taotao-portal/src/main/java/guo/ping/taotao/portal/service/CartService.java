package guo.ping.taotao.portal.service;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
	TaotaoResult addCartItem(Long itemId, int num, HttpServletRequest request, HttpServletResponse response);
	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
}
