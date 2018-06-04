<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
		<title>卖车消息</title>
		<link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet" />
		<link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet" />
		<link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet" />
	</head>

	<body>
		<div class="car-assess-header">
			卖车信息
		</div>
		<div class="custom-mes car-mes ">
			<ul>
				<li class="clearfix"> <span>客户姓名</span>
					<div class="count news_list">${name}</div>
				</li>
				<li class="clearfix"> <span>客户手机号</span>
					<div class="count news_list">${mobile}</div>
				</li>
				<li class="clearfix"> <span style="vertical-align: middle;">客户车型</span>
					<div class="count carModel no_required" style="vertical-align: middle;line-height: 2.4rem;">${vehicleModel}</div>
				</li>
			</ul>
		</div>
		<div style="margin-top: 9.18rem;text-align: center;">
			<a id="" href="http://bolang.91echedai.com/historyList?phoneNumber=${mobile}" class="car_button" style="background-color: rgb(189,164,119);">查看评估报告</a>
		</div>
	</body>
<script>
//	$(function () {
//
//	})
</script>

</html>