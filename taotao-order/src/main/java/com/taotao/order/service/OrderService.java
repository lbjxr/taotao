package com.taotao.order.service;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.pojo.TbOrder;
import guo.ping.taotao.pojo.TbOrderItem;
import guo.ping.taotao.pojo.TbOrderShipping;

import java.util.List;

public interface OrderService {

	TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> orderItems, TbOrderShipping tbOrderShipping);
}
