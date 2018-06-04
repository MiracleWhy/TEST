<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>车辆基本信息</title>
	<link rel="stylesheet" href="/carsokApi/css/pay.css">
</head>
<body>
<h2 style="padding-top:15%; text-align:center; line-height:48px;"><img src="/carsokApi/images/48x48.png"/><br/>支付宝支付</h2>
<div class="sk-spinner sk-spinner-wave">
	<div class="sk-rect1"></div>
	<div class="sk-rect2"></div>
	<div class="sk-rect3"></div>
	<div class="sk-rect4"></div>
	<div class="sk-rect5"></div>
</div>
<p style="margin-top:20%; font-size:16px; text-align:center; ">支付宝支付正在处理中...</p>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script>
	var queryCount = 10;

	$(function () {
		function getQueryStringByName(name) {
			var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
			if (result == null || result.length < 1) {
				return "";
			}
			return result[1];
		}

		if (location.href.indexOf("orderNum") != -1) {
			var orderNum = getQueryStringByName("orderNum");

			var int = setInterval(function () {
				queryCount--;
				if (queryCount <= 0) {
					location.href = "aliPayFail.do"
				}

				$.get("/carsokApi/order/getPayBill.do", {"billNo": orderNum}, function (data) {
					//支付成功
					if (data.success ==true) {
						var billStatus=data.data.billStatus;
						if(billStatus=='PAY_SUCCESS'){
							location.href = "aliPaySuccess.do?orderNum="+data.data.orderNo;
						}
					}
				},"json")
			}, 1000);
		}
		else
		{
			location.href = "aliPayFail.do";
		}
	})
</script>
</body>
</html>