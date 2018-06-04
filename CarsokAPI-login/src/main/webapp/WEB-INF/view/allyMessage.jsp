<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
    <title>联盟消息</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
</head>
<body class="white-body" style="background: #eeeeee;">
<section class="">
    <ul class="" style="width: 100%;" id="messageList">
    </ul>

</section>
<input id="accountCode" type="hidden" value=""/>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script type="text/javascript">
    $(function () {
        pageLoad();
    })

    function delMessage(id) {
        layer.open({
            content: '<h2 class="layer-tit">确定删除该信息？</h2>',
            btn: ['确定', '取消'],
            yes: function() {
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/ally/updateMessageById.do",
                    data: {
                        "id":id
                    },
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
        });
    }

    function pageLoad(fn) {
        $("#accountCode").val(${accountCode});
        var accountCode = $("#accountCode").val();
        var messageHtml = "";
        $.ajax({
            type: 'post',
            url: "/carsokApi/ally/getMessageList.do",
            dataType: 'json',
            data: {
                "accountCode":accountCode
            },
            success: function (data) {
                if(data.data.length >0 && data.data != null){
                    var messageList = data.data;
                    for(var i = 0 ;i < messageList.length; i++){
                        if(messageList[i].pushStatus == 0){//状态0为已读
                            messageHtml += "<li style='margin-top: 10px;padding: 15px 15px;overflow: hidden;background: white'>" +
                                "<div style='position: relative'>" +
                                "<ul style='font-size: 12px;margin-left: 10px;color: #333333;line-height: 25px;margin-top: 3px'>" +
                                "<li style='font-size: 16px;color: #B3B2B0;'>"+messageList[i].title +"</li>" +
                                "<li style='color: #B3B2B0;'>"+messageList[i].content+"</li>" +
                                "<li style='color: #B3B2B0;'><span>"+messageList[i].createTime+"</span></li>" +
                                "</ul>" +
                                "<div style='width: 50px;height: 25px;text-align: center;line-height: 25px;position: absolute;right: 0px;bottom: 0px;cursor: pointer' onclick='delMessage("+messageList[i].id+")'>删除</div>" +
                                "</div></li>" ;
                        }else{//未读
                            messageHtml += "<li style='margin-top: 10px;padding: 15px 15px;overflow: hidden;background: white'>" +
                                "<div style='position: relative'>" +
                                "<ul style='font-size: 12px;margin-left: 10px;color: #333333;line-height: 25px;margin-top: 3px'>" +
                                "<li style='font-size: 16px;color: #1a2b41;'>"+messageList[i].title +"</li>" +
                                "<li>"+messageList[i].content+"</li>" +
                                "<li><span>"+messageList[i].createTime+"</span></li>" +
                                "</ul>" +
                                "<div style='width: 50px;height: 25px;text-align: center;line-height: 25px;position: absolute;right: 0px;bottom: 0px;cursor: pointer' onclick='delMessage("+messageList[i].id+")'>删除</div>" +
                                "</div></li>" ;
                        }

                    }

                }else{
                    messageHtml +="<div style='width: 100%;text-align: center;position: relative'>" +
                        "<img src='../images/no-msg.png' alt='' style='width: 50%;margin-top: 15%'>" +
                        "</div>";
                }
                $("#messageList").html(messageHtml);
                if(fn){
                    fn();
                }
            }
        })
    }
</script>
</body>
</html>