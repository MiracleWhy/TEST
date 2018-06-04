<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="format-detection" content="telephone=no">
	<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
	<title>会员注册</title>
    <link href="css/base.css" type="text/css" rel="stylesheet"/>
	<link href="css/common.css" type="text/css" rel="stylesheet"/>
	<link href="css/my.css" type="text/css" rel="stylesheet"/>
</head>
<body class="white-body">
<section class="reg-banner">
<img src="images/banner.png" />
</section>
<section class="reg-invite-box">
    <input type="hidden" id="hdnInviter" value="${mobile}">
	<h2>${mobile} <small>邀请您开始享受<b>“免费汽车4S店维保记录查询”</b>资格</small></h2>
</section>
<section>
    <!-- 添加车牌 start -->
    <div class="car-add car-add-vip">
        <ul class="add-mes" id="add-mes">
            <li class="clearfix">
                <span class="add-username"></span>
                <input class="border-box" type="text" placeholder="请输入姓名" id="add-username">
            </li>
            <li class="clearfix">
                <span class="add-phone"></span>
                <input class="border-box" type="text" placeholder="请输入手机号码" id="add-phone">
            </li>
            <li class="clearfix">
                <span class="add-password"></span>
                <input class="border-box" type="text" placeholder="首次注册设定密码" id="add-password">
            </li>
            <li class="clearfix ewm">
                <span class="add-code"></span>
                <input class="border-box" type="text" placeholder="请输入验证码" id="add-code">
                <input class="border-box" id="getcode" type="button" value="获取验证码">
            </li>
            
        </ul>
       
        <div class="add-btn">
            <input type="button" value="确认并下载APP" id="add-submit">
        </div>
    </div>
    <!-- 添加车牌 end -->
</section>
<!-- 提示 start -->
<div class="car-tip" id="car-tip"></div>
<!-- 提示 end -->
<script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="js/common.js" type="text/javascript"></script>
<script src="js/md5.js" type="text/javascript"></script>
<script src="js/addLicensePlate-vip.js" type="text/javascript"></script>
</body>
</html>