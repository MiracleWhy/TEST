package com.uton.carsokApi.event.handler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.dao.CarsokOrderDetailMapper;
import com.uton.carsokApi.dao.NoticeMapper;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.CarsokOrderDetail;
import com.uton.carsokApi.model.Notice;
import com.uton.carsokApi.pay.lakala.model.ProductStatus;
import com.uton.carsokApi.service.OrderService;
import com.uton.carsokApi.service.ProductService;

@EventHandler(name = EventConstants.CANCEL_ORDER_EVNET)
@Service
public class OrderCancelEventHandler extends EventHandle {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CarsokOrderDetailMapper orderDetailMapper;
	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	public OperateResult handle(BaseEvent event) {
		String orderNo = event.getData();
		List<CarsokOrderDetail> orderDetails = orderDetailMapper.selectOrderDetails(orderNo);
		CarsokOrder order = orderService.queryByOrderNo(orderNo);
		OperateResult result = new OperateResult();
		if (orderDetails == null || orderDetails.size() == 0) {
			return new OperateResult(true, "");
		} else {
			for (CarsokOrderDetail orderDetail : orderDetails) {
				orderDetail.setStatus(ProductStatus.UNFREEZE.name());
				orderDetail.setGmtModify(new Date());
				orderDetailMapper.updateByPrimaryKey(orderDetail);
				result = productService.unFreezeProduct(orderDetail.getProductId(),
						Integer.valueOf(order.getAccountId()));
				if(result.isSuccess()){
					Notice notice=new Notice();
					noticeMapper.insert(notice);
				}
				
			}
		}
		return result;
	}

}
