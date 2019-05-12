package guo.ping.taotao.portal.controller;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.ExceptionUtil;
import guo.ping.taotao.portal.pojo.CartItem;
import guo.ping.taotao.portal.pojo.Order;
import guo.ping.taotao.portal.service.CartService;
import guo.ping.taotao.portal.service.OrderService;
import org.apache.xpath.operations.Mod;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request,
								HttpServletResponse response,
								Model model){
		//取购物车商品列表
		List<CartItem> cartList = cartService.getCartItemList(request, response);
		//传递页面
		model.addAttribute("cartList",cartList);
		return "order-cart";
	}

	@RequestMapping("/create")
	public String createOrder(Order order, Model model){
		try {
			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", ExceptionUtil.getStackTrace(e));
			return "error/exception";
		}
	}
}
