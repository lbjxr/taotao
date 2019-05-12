package guo.ping.taotao.portal.service.impl;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.HttpClientUtil;
import guo.ping.taotao.common.utils.JsonUtils;
import guo.ping.taotao.portal.pojo.Order;
import guo.ping.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String  ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String  ORDER_CREATE_URL;

	@Override
	public String createOrder(Order order) {
		//调用taotao-order服务
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		TaotaoResult result = TaotaoResult.format(json);
		if (result.getStatus() == 200) {
			Object orderId = result.getData();
			return orderId.toString();
		}
		return "";
	}
}
