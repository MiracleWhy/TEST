<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
	<title>卖车评估</title>
	<link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet" />
	<link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet" />
	<link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet" />
	<link href="/carsokApi/css/jquery.mmenu.all.css" type="text/css" rel="stylesheet"/>
	<style>
	.text-sm li span{
		padding-left:0;
	}
	.carModel{
		width: 100%;
		min-height: 2.4rem;
		float: right;
		width: calc(100% - 10rem);
		display: flex;
		justify-content: center;
		flex-direction: column;
	}
	</style>
</head>

<body>
<nav id="carList">
	<ul>
	</ul>
</nav>
<div class="car-assess-header">
	请填写您的卖车信息
</div>
<form id="forms">
<div class="custom-mes car-mes ">
	<ul>
		<li class="clearfix"> <span>客户姓名</span>
		<input type="text" step="1.00" placeholder="必填" value="" class="count required" id="name" name="name">
		</li>
		<li class="clearfix"> <span>客户手机号</span>
			<input type="tel" step="1.00" placeholder="必填" value="" class="count required" id="mobile" name="mobile">
		</li>
		<li class="clearfix"> <span>客户车型</span>
			<a href="#carList">
				<input readonly="readonly" type="text" step="1.00" style="display:none;" placeholder="请选择" value="" id="car_model_input" class="count no_required" name="vehicleModel">
				<div class="count carModel no_required" id="car_model">必填</div>
			</a>
			<input type="hidden" id="modelId" value=""/>
			<input type="hidden" id="brandName" value=""/>
		</li>
	</ul>
</div>
<input type="hidden" id="accountId" name="accountId" value="${accountId}"/>
	<input type="hidden" id="isCar" name="isCar" value="${isCar}"/>
<div style="margin-top: 9.18rem;text-align: center;">
	<a href="#" class="car_button" style="background-color: rgb(170,170,170);" disabled="disabled" id="toEvaluate">进入评估</a>
</div>
</form>
<div class="car-tip" id="car-tip"></div>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/jquery.mmenu.all.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script>
	$(function() {
        var res = "^[0-9]+\.?[0-9]*$";
        var isMob = new RegExp(res);
        var phoneNumber = "";
        var evaluateUrl = "http://bolang.91echedai.com/home";


		var flag;

		checkInput();

            $("#toEvaluate").click(function () {

                if ($("#name").val().length == 0) {
                    Uton.tip("请输入姓名");
                    return;
                }
                if ($("#mobile").val().length == 0) {
                    Uton.tip("请输入手机号");
                    return;
                }
                if (!isMob.test($("#mobile").val()) || $("#mobile").val().length != 11) {
                    Uton.tip("手机号码格式不正确");
                    return;
                }
                if ($("#car_model").text().length == 0 || $("#car_model").text() == '必填') {
                    Uton.tip("请选择车型");
                    return;
                }
                //Submit

                $.ajax({
                    type: "post",
                    url: "/carsokApi/utonwGK/evaluationSubmit.do",
                    data: $("#forms").serializeArray(),
                    success: function (data) {
                        if (data == "\"0000\"") {
                            //redirect
                            var url = evaluateUrl
                                + "?phoneNumber=" + $("#mobile").val()
                                + "&modelId=" + $("#modelId").val()
                                + "&modelName=" + $("#car_model").text()
                                + "&brandName=" + $("#brandName").val();
                            location.href = url;
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        //                    alert(XMLHttpRequest.status);
                        //                    alert(XMLHttpRequest.readyState);
                        //                    alert(textStatus);
                    }
                });


            });

		//动态获取车辆信息
		$.ajax({
			url: "/carsokApi/carBrand.do",
			type: 'POST', //GET
			async: false,    //或false,是否异步
			success: function (brandList) {
				brandList = JSON.parse(brandList);
				if (brandList.retCode == "0001") {
					layer.open({content: "获取车型信息失败！", btn: '知道了'});
					return;
				}
				$.each(brandList.data, function(n, value){
					for(var i=0;i<value.length;i++){
						//console.log(value[i]);
						if ((i == 0 || value[i].initial != value[i - 1].initial)) {
							$("#carList ul:eq(0)").append('<li class="Divider">' + value[i].initial + '</li>');
						}
						$("#carList ul:eq(0)").append('<li id=' + value[i].id + ' class="brand" ><span>' + value[i].name + '</span><ul><li></li></ul></li>');
					}
				});
				$(".brand").click(function () {
					var seriesObj = $($(this).find("a").data("target"));
					$("#brandName").val($(this).find("span").html())
					var param = {id: $(this).attr("id")};
					$.ajax({
						url: "/carsokApi/carType.do",
						type: 'POST', //GET
						dataType : "json",
						contentType : "application/json;charset=utf-8",
						data: JSON.stringify(param),
						async: false,    //或false,是否异步
						success: function (seriesList) {
							$.each(seriesList.data, function(n, value){
								for(var i=0;i<value.length;i++){
									seriesObj.find(".mm-listview").append('<li id=' + value[i].id + ' class="series"><a class="mm-next mm-fullsubopen" href="#mm-200" data-target="#mm-200"></a><span>' + value[i].name + '</span><ul></ul></li>');
								}
							});
							$(".series").click(function () {
								var prevId = $(this).parents("div:eq(0)").attr("id");
								var prevStr = $(this).find("span").html()
								var params = {id: $(this).attr("id")};
								$.ajax({
									url: "/carsokApi/carModel.do",
									type: 'POST', //GET
									async: false,    //或false,是否异步
									dataType : "json",
									contentType : "application/json;charset=utf-8",
									data: JSON.stringify(params),
									success: function (modelsList) {
										$("#mm-200").remove();
										var mmpanel = '<div class="mm-panel mm-hasnavbar mm-hidden" id="mm-200"><div class="mm-navbar">'
												+ '<a class="mm-btn mm-prev" href="#' + prevId + '" data-target="#' + prevId + '"></a><a class="mm-title" href="#' + prevId + '">' + prevStr + '</a></div>'
												+ '<ul class="mm-listview text-sm">';
										$.each(modelsList.data, function(n, value){
											for(var i=0;i<value.length;i++){
												mmpanel += '<li id=' + value[i].id + ' class="model"><a href="#"><b>' + value[i].name + '</b><span>' + value[i].price + '万</span></a></li>';
											}
										});
										mmpanel += '</ul></div>';
										$("#carList .mm-panels:eq(0)").append(mmpanel);

										//实时监测表单是否为空

										$("#name").on("input",function(){
											checkInput();
											if(flag == true){
												changeBtnable();
											}else{
												changeBtndisable();
											}
										});

										$("#mobile").on("input",function(){
											checkInput();
											if(flag == true){
												changeBtnable();
											}else{
												changeBtndisable();
											}
										});

										$(".model").click(function () {
											$("#car_model_input").val($(this).find("a").find("b").html());
											$("#car_model").html($(this).find("a").find("b").html());
											$("#modelId").val($(this).attr("id"));
											//$("#toEvaluate").attr("href",evaluateUrl+"&modelId="+$(this).attr("id")+"&modelName="+$("#car_model").val()+"&brandName="+$("#brandName").val());
											var api = $("nav#carList").data("mmenu");
											api.close();

											checkInput();
											if(flag == true){
												changeBtnable();
											}else{
												changeBtndisable();
											}
										})

									}
								});
							})
						}
					});
				});
			},
			error: function (xhr, textStatus) {
				console.log('错误')
				console.log(xhr)
				console.log(textStatus)
			}
		});

		$('nav#carList').mmenu({
			extensions: ["pagedim-black"],
			sectionIndexer: true,
			dividers: {
				"add": false,
				"fixed": true
			},
			offCanvas: {
				position: "right"
			},
			navbar: {
				title: "选择品牌"
			}
		}).on('click', 'a[href^="#/"]',
				function () {
					return false;
				}
		);

		//判断flag


		function changeBtnable(){
			$("#toEvaluate").removeAttr("disabled");
			$("#toEvaluate").css("background-color","rgb(189,164,119)");
		}
		function changeBtndisable(){
			$("#toEvaluate").attr("disabled","disabled");
			$("#toEvaluate").css("background-color","#aaa");
		}

		function checkInput(){
			if($("#name").val()  && $("#mobile").val() && ($("#car_model").text() || $("#car_model").text() != '必填')){
				flag = true;
				changeBtnable();
			}else{
				flag = false;
				changeBtndisable();
			}

			return flag;
		}


	})
</script>


</body>




</html>