<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
    <title>添加盟友</title>
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
    <button class="search-btn" id="searchBtn" onclick="search1('add')">搜索</button>
</header>

<!-- nav end -->
<section  id="friendList" class="friendList">
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
            $.ajax({
                type: 'post',
                url: "/carsokApi/ally/getNewFriendList.do",
                dataType: 'json',
                data: {
                    "accountCode": accountCode
                },
                success: function (data) {
                    if (data.data.newFriendList.length > 0 && data.data.newFriendList != null) {
                        friendList += "<div class='friendTitle'>新的盟友</div>" +
                            "<ul>";
                        var newFriendList = data.data.newFriendList;
                        for (var i = 0; i < newFriendList.length; i++) {
                            friendList += "<li class='delete' data-id='" + newFriendList[i].id + "'>" +
                                "<div class='friendImg'>" +
                                "<img  src=" + newFriendList[i].merchantImagePath + "?imageView2/1/w/140/h/95" + ">" +
                                "</div>" +
                                "<div class='friendDesc'>" +
                                "<ul >" +
                                "<li>" + newFriendList[i].merchantName + "</li>" +
                                "<li>" + newFriendList[i].applyMessage + "</li>" +
                                "</ul>" +
                                "<div class='linkBtn' onclick='acceptFriend(" + newFriendList[i].id + "," + newFriendList[i].friendAccount + "," + newFriendList[i].carAccountCode + "," + flag + ")'>接受</div>" +
                                "</div></li>";
                        }
                        friendList += "</ul>";
                    }
                    if (data.data.localFriendList.length > 0 && data.data.localFriendList != null) {
                        friendList += "<div class='friendTitle'>推荐盟友 | 本地友商</div>" +
                            "<ul class='' style='width: 100%;'>";
                        var localList = data.data.localFriendList;
                        for (var i = 0; i < localList.length; i++) {
                            if (1 == localList[i].relationStatus) {
                                if (localList[i].carAccountCode == $("#accountCode").val()) {
                                    friendList += "<li>";
                                } else if (localList[i].friendAccount == $("#accountCode").val()) {
                                    friendList += "<li class='delete' data-id='" + localList[i].id + "'>";
                                } else {
                                    friendList += "<li>";
                                }
                            } else {
                                friendList += "<li>";
                            }
                            friendList += "<div class='friendImg'>" +
                                "<img src=" + localList[i].merchantImagePath + "?imageView2/1/w/140/h/95" + ">" +
                                "</div>" +
                                "<div class='friendDesc'>" +
                                "<ul>" +
                                "<li>" + localList[i].merchantName + "</li>" +
                                "<li>负责人: <span>" + localList[i].contactName + "</span></li>" +
                                "<li>在售车辆: <span>" + localList[i].val + " 辆</span></li>" +
                                "<li class='liaddr'>" + localList[i].merchantAddress + "</li>" +
                                "</ul>";
                            if (1 == localList[i].relationStatus) {
                                if (localList[i].carAccountCode == $("#accountCode").val()) {
                                    friendList += "<div class='linkBtn' >申请中</div>";
                                } else if (localList[i].friendAccount == $("#accountCode").val()) {
                                    friendList += "<div class='linkBtn'  onclick='acceptFriend(" + localList[i].id + "," + localList[i].friendAccount + "," + localList[i].carAccountCode + "," + flag + ")'>接受</div>";
                                } else {
                                    friendList += "<div class='linkBtn' onclick='applyFriend(" + localList[i].accountCode + "," + flag + ")'>添加盟友</div>";
                                }
                            } else if (0 == localList[i].relationStatus || null == localList[i].relationStatus) {
                                friendList += "<div class='linkBtn' onclick='applyFriend(" + localList[i].accountCode + "," + flag + ")'>添加盟友</div>";
                            } else {
                                friendList += "";
                            }
                            friendList += "</div></li>";
                        }
                        friendList += "</ul>";
                    }
                    if (data.data.localFriendList.length <= 0 && data.data.newFriendList.length <= 0) {
                        friendList += "<div style='width: 100%;text-align: center;position: relative'>" +
                            "<img src='../images/no-msg.png' alt='' style='width: 50%;margin-top: 15%'>" +
                            "</div>";
                    }
                    $("#friendList").html(friendList);
                    if (fn) {
                        fn();
                    }
                }
            })
}

</script>
<script src="/carsokApi/js/allyCommon.js"></script>
</body>
</html>