<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="format-detection" content="telephone=no">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
<title>资料管理</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>

</head>
<body class="white-body header-body" >
<!-- header start -->
<header class="search border-box">
  <div class="search-wrap border-box"> <a href="javascript:;" class="search-ico"></a>
    <input class="border-box" id="search_key" type="text" placeholder="车型" style="padding-left:2.6rem;">
    <a href="javascript:;" class="search-delete" onclick="Uton.emptySearch();"></a> </div>
    <button class="search-btn" id="searchBtn" onclick="searchBy()">搜索</button>
</header>

<!-- nav end -->
<section class="border-box">
  <div class="r_custombox" id="shop-box">
        <ul class="tabClick">
            <li class="active" onclick="selectByDate(1)" data-id="">本日<span id="dayC">(0)</span></li>
            <li onclick="selectByDate(2)" id="weekCC">本周<span id="weekC">(0)</span></li>
            <li onclick="selectByDate(3)" id="weekMM">本月<span id="monthC">(0)</span></li>
            <li onclick="remCla()"><input type="month" id="month" placeholder="选择月份" onchange="selectByDate()" align="center"/></li>
        </ul>
    <div class="lineBorder">
      <div class="lineDiv"><!--移动的div--></div>
    </div>
    <div class="radio-box top-radio-box">
    <label class="label" >
        <input class="radio" type="radio" name="customSta" value="0" onclick="perfectIf(0)" id="radio1">
        <span class="radioInput"></span> 在售车辆 </label>
      <label class="label">
        <input class="radio" type="radio" name="customSta" value="1" onclick="perfectIf(1)" id="radio2">
        <span class="radioInput"></span> 已售车辆 </label>

    </div>
    <div class="data_list" id="data_list">

    </div>
  </div>
</section>
<div> </div>
<input type="hidden" id="token" value=""/>
<input type="hidden" id="subToken" value=""/>
<input type="hidden" id="timeIf" value=""/>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>

<script charset="utf-8">

    var page = 0;

    $(function () {
        $("#token").val('${token}');
        $("#subToken").val('${subToken}');
        var createTime = "1";
        $("#timeIf").val(createTime);
        var token = $("#token").val();
        var subToken = $("#subToken").val();
        var saleStatus = "";
        var productName = $("#search_key").val();
        var mouth = $("#month").val();
        searchPage(page,subToken,token,saleStatus,productName,createTime,mouth);

    })

var windowWidth = document.body.clientWidth; //window 宽度;
var wrap = document.getElementById('shop-box');
var tabClick = wrap.querySelectorAll('.tabClick')[0];
var tabLi = tabClick.getElementsByTagName('li');
var lineBorder = wrap.querySelectorAll('.lineBorder')[0];
var lineDiv = lineBorder.querySelectorAll('.lineDiv')[0];
var init = {
    lineAnme: function (obj, stance) {
        obj.style.webkitTransform = 'translate3d(' + stance + 'px,0,0)';
        obj.style.transform = 'translate3d(' + stance + 'px,0,0)';
        obj.style.webkitTransition = 'all 0.1s ease-in-out';
        obj.style.transition = 'all 0.1s ease-in-out';
    }
}


function selectByDate(status) {
    $("#data_list").html("");
    $("#radio1").removeAttr("checked");
    $("#radio2").removeAttr("checked");
    var productName = $("#search_key").val();
    var token = $("#token").val();
    var subToken = $("#subToken").val();
    var saleStatus =  $("input[type='radio']:checked").val();
    if(status != null && status != "" && status != undefined) {
        $(".lineDiv").show();
        $("#timeIf").val(status);
        $("#month").removeClass("full");
        $("#month").val("");
    }else {
        var mouth =  $("#month").val();
    }
    var createTime = status;
        if(status == undefined){
            var index = 4-1;
            $(".tabClick li").removeClass("active");
            $(".tabClick li").eq(index).addClass("active");
            init.lineAnme(lineDiv, windowWidth / tabLi.length * index)
        }else{
            var index = status-1;
            $(".tabClick li").removeClass("active");
            $(".tabClick li").eq(index).addClass("active");
            init.lineAnme(lineDiv, windowWidth / tabLi.length * index)
        }
    var bool = false;
    if(page == 0){
        bool = true
    }else {
        bool = false
    }
    searchPage(page,subToken,token,saleStatus,productName,createTime,mouth,bool);

}

function remCla() {
    $(".tabClick li").eq(3).addClass("active");
    init.lineAnme(lineDiv, windowWidth / tabLi.length * 3);
}


function perfectIf(status) {
    $("#data_list").html("");
    var productName = $("#search_key").val();
    var token = $("#token").val();
    var subToken = $("#subToken").val();
    var  mouth = $("#month").val();
    if(mouth != null && mouth != undefined && mouth != ""){
        createTime = '';
    }else{
        var createTime = $("#timeIf").val();
    }
    var bool = false;
    if(page == 0){
        bool = true
    }else {
        bool = false
    }
    searchPage(page,subToken,token,status,productName,createTime,mouth,bool);
}

 function searchBy() {
     $("#data_list").html("");
     $(".tabClick li").removeClass("active");
     init.lineAnme(lineDiv, windowWidth / tabLi.length * 5);
     $("#radio1").removeAttr("checked");
     $("#radio2").removeAttr("checked");
     var token = $("#token").val();
     var subToken = $("#subToken").val();
     $("#timeIf").val("");
     var createTime = $("#timeIf").val();
     var month = $("#month").val();
     // 每页展示5个
     // dropload
     var saleStatus =  $("input[type='radio']:checked").val();
     var productName = $("#search_key").val();
     var bool = false;
     if(page == 0){
         bool = true
     }else {
         bool = false
     }
     searchPage(page,subToken,token,saleStatus,productName,createTime,month,bool);
 }
 
 function searchPage(page,subToken,token,saleStatus,productName,createTime,mouth,clear) {
     $("#searchBtn").attr("disabled", true);
     $("#data_list").dropload({
         scrollArea: window,
         loadDownFn: function (me) {
             var data_list = '';
             page++;
             // 拼接HTML
             $.ajax({
                 type: 'post',
                 url: "/carsokApi/datumManage/datumList.do",
                 dataType: 'json',
                 //设置为同步刷新
                 async:false,
                 "headers": {
                     "token": token,
                     "subToken":subToken
                 },
                 data: {
                     "pc": page,
                     "saleStatus":saleStatus,
                     "productName":productName,
                     "createTime":createTime,
                     "mouth":mouth
                 },
                 success: function (data) {
                     $("#dayC").html("("+data.count.dayCount+")");
                     $("#weekC").html("("+data.count.weekCount+")");
                     $("#monthC").html("("+data.count.monthCount+")");
                     if (data.dataList.list.length != 0) {
                         if(clear && page == 1){
                             $(".dataListItem").remove();
                         }
                         if(data.dataList.pageNum != page){
                             return;
                         }
                         for (var i = 0; i < data.dataList.list.length; i++) {
                             data_list = "<a class='dataListItem' href='/carsokApi/datumManage/datumDetailPage.do?zlglProductId="+data.dataList.list[i].id+"'>" +
                                 "<dl>" +
                                 "<dt>" +
                                 "<img src=\'"+ data.dataList.list[i].picPath + "?imageView2/1/w/140/h/95" + "\'/>"+
                                 "</dt>" +
                                 "<dd>" +
                                 "<p class='ell'>" + data.dataList.list[i].productName + "</p>" +
                                 "<p><b>VIN</b><span>" + data.dataList.list[i].vin + "</span></p>" +
                                 "<p><b>状态</b><span style=' padding-right: 10px;'>" + data.dataList.list[i].saleStatus + "</span><b style=' padding-left: 10px; width: 4.5rem; border-left:solid 1px #927f5b;'>上架时间</b><span>" + data.dataList.list[i].onShelfTime + "</span></p>"+
                                 "</dd>" +
                                 "</dl>" +
                                 "</a>";
                             $(".dropload-down").before(data_list);
                         }
                         // 如果没有数据
                     } else if (data.dataList.list.length == 0) {
                         // 锁定
                         me.lockdrop();
                         // 无数据
                         me.noData();
                     } else {
                         return;
                     }
                     // 插入数据到页面，放到最后面
                     // 每次数据插入，必须重置
                     me.resetload();
                     $("#searchBtn").attr("disabled", false);
                 }
             });
         }
     });
 }
    $("input[type='month']").on("input",function(){

        if($(this).val().length>0){

            $(this).addClass("full");
        }
        else{
            $(this).removeClass("full");
        }
    });
    function GetDefaultMonth(object){
        if(object.val().length>0){

            object.addClass("full");
        }
        else{
            object.removeClass("full");
        }
    }
</script>
</body>
</html>