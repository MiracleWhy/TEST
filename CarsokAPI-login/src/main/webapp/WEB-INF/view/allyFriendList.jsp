<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="format-detection" content="telephone=no">
	<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
	<title>车商联盟</title>
	<link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
	<link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
	<link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
	<link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
</head>
<style>
	.layui-m-layerbtn span[yes] {
		color: #e29000;
	}
</style>
<body class="white-body header-body" style="background: #eeeeee;">
<!-- header start -->
<header class="search border-box">
	<div class="search-wrap border-box"> <a href="javascript:;" class="search-ico"></a>
		<input class="border-box" id="search_key" type="text" placeholder="车行名称/电话" style="padding-left:2.6rem;">
		<a href="javascript:;" class="search-delete" onclick="Uton.emptySearch();"></a> </div>
	<button class="search-btn" id="searchBtn" onclick="search1('ally')">搜索</button>
</header>

<!-- nav end -->
<section class="friendList" style="margin-top: 10px;">
	<ul id="friendList">
	</ul>
</section>
<input id="accountCode" type="hidden" value=""/>
<input id="weiShopUrl" type="hidden" value=""/>
<input id="searchParam" type="hidden" value=""/>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>

<script type="text/javascript">
	var flag = 0;
  $(function () {
      pageLoad();
      $("#friendList").on('touchstart', '.delete', function(e){
          e.stopPropagation();
          var id = $(this).attr('data-id');
          time = setTimeout(function(){
              layer.open({
                  content: '<h2 class="layer-tit">确定删除该盟友？</h2> <input type="text" class="layer-txt" id="applyMessage" placeholder="请填写删除理由(请输入少于三十个字符)" /><p id="hiddenBox" style="color: red;display: none;margin-top: 5px;">输入信息多于三十个字符！</p>',
                  btn: ['确定', '取消'],
				  yes:function () {
                      if($("#applyMessage").val().length >30){
                          $("#hiddenBox").show();
                          return false;
                      } else {
                          $("#hiddenBox").hide();
                      }
                      var json = {
                          "id":id,
                          "applyMessage":$("#applyMessage").val(),
                          "relationStatus":3
                      };
                      $.ajax({
                          type: 'post',
                          url: "/carsokApi/ally/addFriend.do",
                          "headers": {
                              "content-type": "application/json"
                          },
                          data: JSON.stringify(json),
                          success: function (data) {
                              layer.open({
                                  content:'<h2 class="layer-tit">删除成功</h2>',
                                  btn:['确定'],
                                  yes:function () {
                                      function closeLayer() {
                                          $('.layui-m-layer').remove();
                                      }
                                      pageLoad(closeLayer());
                                  }
                              })
                          },
                      })
                  }
			  })
          }, 1000);//这里设置长按响应时间
      });

      $("#friendList").on('touchend', '.delete', function(e){
          e.stopPropagation();
          clearTimeout(time);
      });
  })

	function pageLoad(fn) {
        $("#accountCode").val(${accountCode});
        $("#weiShopUrl").val('${weiShopUrl}');
        var accountCode = $("#accountCode").val();
        var weiShopUrl = $("#weiShopUrl").val();
        var friendList = "";
        $("#friendList1").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/ally/allyList.do",
                    dataType: 'json',
                    data: {
                        "accountCode": accountCode
                    },
                    success: function (data) {
                        if (data.data.length > 0 && data.data != null) {
                            var newdata = data.data;
                            for (var i = 0; i < newdata.length; i++) {
                                if (newdata[i].merchantName != "") {
                                    if (2 == newdata[i].relationStatus) {
                                        friendList += "<a href=" + weiShopUrl + newdata[i].accountCode + ".html>"
                                    }
                                    if (1 == newdata[i].relationStatus) {
                                        if (newdata[i].friendAccount == accountCode) {
                                            friendList += "<li class='delete' data-id='" + newdata[i].id + "'>"
                                        } else {
                                            friendList += "<li>"
                                        }
                                    } else {
                                        friendList += "<li>"
                                    }
                                    friendList += "<div class='friendImg'>" +
                                        "<img src=" + newdata[i].merchantImagePath + "?imageView2/1/w/140/h/95" + ">" +
                                        "</div>" +
                                        "<div class='friendDesc'>" +
                                        "<ul>" +
                                        "<li>" + newdata[i].merchantName + "</li>" +
                                        "<li>负责人: <span>" + newdata[i].contactName + "</span></li>" +
                                        "<li>在售车辆: <span>" + newdata[i].val + " 辆</span></li>" +
                                        "<li class='liaddr'>" + newdata[i].merchantAddress + "</span></li>" +
                                        "</ul>";
                                    if (1 == newdata[i].relationStatus) {
                                        if (newdata[i].friendAccount == accountCode) {
                                            friendList += "<div class='linkBtn' onclick='acceptFriend(" + newdata[i].id + "," + newdata[i].friendAccount + "," + newdata[i].carAccountCode + "," + flag + ")'>接受</div>";
                                        } else {
                                            friendList += "<div class='linkBtn'>申请中</div>";
                                        }
                                    } else {
                                        friendList += "";
                                    }
                                }
                                friendList += "</div></li>";
                                if (2 == newdata[i].relationStatus) {
                                    friendList += "</a>"
                                }
                            }
                        } else {
                            friendList += "<div style='width: 100%;text-align: center;position: relative'>" +
                                "<img src='../images/no-msg.png' alt='' style='width: 50%;margin-top: 15%'>" +
                                "</div>";
                        }
                        $("#friendList").html(friendList);
                        if (fn) {
                            fn();
                        }
                    }
                });
            }
        });
	}

</script>
<script src="/carsokApi/js/allyCommon.js"></script>
</body>
</html>