package com.taotao.order.service.impl;

import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.mapper.TbOrderItemMapper;
import guo.ping.taotao.mapper.TbOrderMapper;
import guo.ping.taotao.mapper.TbOrderShippingMapper;
import guo.ping.taotao.pojo.TbOrder;
import guo.ping.taotao.pojo.TbOrderItem;
import guo.ping.taotao.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;
import java.util.List;

/**
 * 订单管理service
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;

	@Override
	public TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> orderItems, TbOrderShipping tbOrderShipping) {
		//向订单表插入记录
		//获取订单号
		String string = jedisClient.get(ORDER_GEN_KEY);
		if (StringUtils.isBlank(string)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}
		long orderId = jedisClient.incr(ORDER_GEN_KEY);
		//补全pojo
		tbOrder.setOrderId(orderId + "");
		//状态 1、未付款；2、已付款；3、未发货；4、已发货；5、交易成功；6、交易关闭。
		tbOrder.setStatus(1);
		Date date = new Date();
		tbOrder.setCreateTime(date);
		tbOrder.setUpdateTime(date);
		//0：未评价；1：已评价
		tbOrder.setBuyerRate(0);
		//向订单表插入数据
		tbOrderMapper.insert(tbOrder);

		//插入订单明细
		for (TbOrderItem orderItem : orderItems) {
			//取订单明细ID
			long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			orderItem.setId(orderDetailId + "");
			orderItem.setOrderId(orderId + "");
			tbOrderItemMapper.insert(orderItem);
		}

		//插入物流表
		tbOrderShipping.setOrderId(orderId + "");
		tbOrderShipping.setCreated(date);
		tbOrderShipping.setUpdated(date);
		tbOrderShippingMapper.insert(tbOrderShipping);

		return TaotaoResult.ok(orderId);
	}
}
