<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
		<title>卖车消息</title>
		<link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet" />
		<link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet" />
		<link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet" />
		<link href="/carsokApi/css/c-detail.css" type="text/css" rel="stylesheet" />
	</head>

	<body>

		<div class="car-assess-header" style="height: .5rem;"></div>

		<div class="custom-mes car-mes ">
			<ul>
				<li class="clearfix"> <span>意向价格</span>
					<div class="count news_list">${intentionalPrice}</div>
				</li>
				<li class="clearfix"> <span>姓名</span>
					<div class="count news_list">${name}</div>
				</li>
				<li class="clearfix"> <span>联系电话</span>
					<div class="count news_list"><a href="tel:${mobile}">${mobile}</a></div>
				</li>
			</ul>
		</div>
		<div class="data_list bargain_list">
				<dl>
					<dt>
            	<img src="${picture}" id="tupian" />
            </dt>
					<dd>
						<p class="ell" id="chexing">${productName}</p>
						<p><span id="shangpaishijian">${firstUpTime}</span>&nbsp;&nbsp;&nbsp;<b style=" padding-left: 10px; width: 4.5rem; border-left:solid 1px #927f5b;" id="gonglishu">${miles}</b>&nbsp;公里</p>
						<p><b id="jiage">价格</b><span style=" padding-right: 10px;">${price}万元</span><b style=" padding-left: 10px; width: 4.5rem; border-left:solid 1px #927f5b;" id="baodijia">保底价</b><span>${miniPrice}万元</span></p>
					</dd>
				</dl>
		</div>
	</body>

</html>