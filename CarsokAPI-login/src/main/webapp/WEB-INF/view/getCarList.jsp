
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="format-detection" content="telephone=no">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
<title>车辆列表</title>
<link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
<style>
.layui-m-layercont{ padding:30px;}
.layui-m-layerbtn span[yes]{ color:#e29000;}
</style>
</head>
<body class=" header-body" style="padding-top: 8.4rem;padding-bottom: 0" >
<!-- header start -->
<header class="buy border-box">
  <div class="search-wrap border-box">
    <div class="search-position" id="search-position">全国</div>
    <a href="javascript:;" class="search-ico"></a>
    <input class="border-box" id="search_key" type="text" placeholder="请输入品牌/车系">
    <a href="javascript:;" class="search-delete" onclick="Uton.emptySearch();"></a> </div>
  <div class="buy-btn" id="search-btn">搜索</div>
</header>
<!-- header end --> 
<!-- nav start -->
<nav class="car-filter border-box">
  <ul class="clearfix">
    <li> <a href="javascript:;" id="car-brand">品牌</a> </li>
    <li> <a class="car-sel" href="javascript:;">车龄</a>
      <ul class="filter-son border-box" id="car-age">
        <li data-val="" class="active">不限车龄</li>
        <li data-val="0,1">一年以内</li>
        <li data-val="1,3">1-3年</li>
        <li data-val="3,5">3-5年</li>
        <li data-val="5,8">5-8年</li>
        <li data-val="8,10">8-10年</li>
        <li data-val="10">10年以上</li>
      </ul>
    </li>
    <li> <a class="car-sel" href="javascript:;">排序</a>
      <ul class="filter-son border-box" id="car-order">
        <li data-val="0" class="active">默认排序</li>
        <li data-val="1">价格最低</li>
        <li data-val="2">价格最高</li>
        <li data-val="3">车龄最短</li>
        <li data-val="4">里程最少</li>
      </ul>
    </li>
        <li> <a class="car-sel" href="javascript:;" id="car-status-txt">未购买</a>
      <ul class="filter-son border-box" id="car-status">
        <li data-val="0" class="active">未购买</li>
        <li data-val="1" >已购买</li>
      </ul>
    </li>
  </ul>
</nav>
<!-- nav end --> 
<!-- 车列表 start -->
<%--<div id="get-car-list-con" class="car-list-con">--%>
  <%--<div class="pro-list car-pro-list" id="car-pro-list">--%>
    <%--<ul>--%>
    <%--</ul>--%>
  <%--</div>--%>
<%--</div>--%>



<div id="car-list-con" class="car-list-con" >
  <div class="pro-list car-pro-list" id="car-pro-list">
    <ul></ul>
  </div>
</div>
<!-- 车列表 end -->

<!-- 遮罩 start -->
<div class="car-mask"></div>
<!-- 遮罩 end --> 
<!-- 定位弹层 start -->
<div class="layer-position-header border-box" id="layer-header-address"> <a class="fl" href="javascript:;" id="cancel-position">取消</a> <a class="fr" href="javascript:;" id="sure-position">确定</a> </div>
<div class="layer-position layer-province border-box" id="layer-province">
  <div>
    <h4 data-val="" id="whole-country">全国</h4>
    <ul>
    </ul>
  </div>
</div>
<div class="layer-position layer-city border-box" id="layer-city">
  <div>
    <h4 data-val="" id="whole-city">不限</h4>
    <ul>
    </ul>
  </div>
</div>
<!-- 定位弹层 end --> 
<!-- 品牌车系弹层 start -->
<div class="layer-position-header border-box" id="layer-header-car"> <a class="fl" href="javascript:;" id="cancel-brand">取消</a> <a class="fr" href="javascript:;" id="sure-brand">确定</a> </div>
<div class="layer-position layer-province border-box" id="layer-brand">
  <ul>
  </ul>
</div>
<div class="layer-position layer-city border-box" id="layer-type">
  <ul>
  </ul>
</div>
<div class="layer-position layer-model border-box" id="layer-model">
  <ul>
  </ul>
</div>
<!-- 品牌车系弹层 end --> 
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script src="/carsokApi/js/iscroll.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script> 
<script src="/carsokApi/js/carListForData.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        Uton.init();
        initscroll();
    })
</script>
</body>
</html>
