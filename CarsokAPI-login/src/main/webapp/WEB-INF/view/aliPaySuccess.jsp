<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>支付结果</title>
<link rel="stylesheet" href="/carsokApi/css/pay.css">
</head>
<link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/carsokApi/js/layer.js"></script>
<body style="background:#f2f2f2;" >
<div class="result_box" id="result_div"> <i><img src="/carsokApi/images/icon.png" /></i>
  <h2>支付成功</h2>
</div>
</body>
<script type="text/javascript">
$(function () {
	function getQueryStringByName(name) {
		var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
			return "";
		}
		return result[1];
	}
		if (location.href.indexOf("orderNum") != -1) {
		debugger;
		var orderNum = getQueryStringByName("orderNum");
		$.get("/carsokApi/order/getDataOrderPhone.do", {"orderNo": orderNum}, function (data) {
			//支付成功
			if (data.success ==true) {
				var phone=data.data;
				layer.open({
					content : '可在车源中心已购买中查看。',
					btn : [ '立即拨打', '暂不联系' ],
					yes : function(index) {
						//创建订单
						location.href="tel:"+phone;
						layer.close(index);
					}
				});
			}
		},"json")
		
	}
})
</script>
</html>