package guo.ping.taotao.portal.service.impl;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.CookieUtils;
import guo.ping.taotao.common.utils.HttpClientUtil;
import guo.ping.taotao.common.utils.JsonUtils;
import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.portal.pojo.CartItem;
import guo.ping.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;

	/**
	 * 向购物车添加商品
	 * @param itemId
	 * @param num
	 * @return
	 */
	@Override
	public TaotaoResult addCartItem(Long itemId, int num,
									HttpServletRequest request, HttpServletResponse response) {
		CartItem cartItem = null;
		//取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		//判断购物车是否存在此商品
		for (CartItem cItem : itemList){
			//如果存在此商品
			if (cItem.getId() == itemId){
				//增加商品数量
				cItem.setNum(cItem.getNum() + num);
				cartItem = cItem;
				break;
			}
		}
		if (cartItem == null) {
			//取商品信息
			cartItem = new CartItem();
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
			if (result.getStatus() == 200) {
				TbItem item = (TbItem) result.getData();
				//将item转换为cartItem
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setPrice(item.getPrice());
				cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
				cartItem.setNum(num);
			}
			//添加商品到购物车列表
			itemList.add(cartItem);
		}
		//把商品列表加入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);

		return TaotaoResult.ok();
	}

	/**
	 * 获取购物车列表
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}

	/**
	 * 删除购物车商品
	 * @param cartId
	 * @return
	 */
	@Override
	public TaotaoResult deleteCartItem(Long cartId, HttpServletRequest request, HttpServletResponse response) {
		//从cookie中获取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		//从列表中找到此商品
		for (CartItem item : itemList){
			if (item.getId() == cartId){
				itemList.remove(item);
				break;
			}
		}
		//把处理后的购物车列表写会cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		return TaotaoResult.ok();
	}

	/**
	 * 从cookie取商品列表
	 * @param request
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request){
		//从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (cartJson == null){
			return new ArrayList<>();
		}
		//转换cartJson为商品列表
		List<CartItem> cartItemList = null;
		try {
			cartItemList = JsonUtils.jsonToList(cartJson, CartItem.class);
			return cartItemList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
