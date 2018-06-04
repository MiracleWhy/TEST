<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="format-detection" content="telephone=no">
  <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
  <title>【收车管理】定价意见</title>
  <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet" />
  <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet" />
  <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet" />
  <link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<section class="follow_up_sec">
  <div class="follow_up_sec_inner">
    <div class="l-form-group l-form-group-first">
      <div class="l-l">验车时间</div>
      <div class="l-r" id="carInspectionTime"></div>
    </div>
    <div class="l-form-group ">
      <div class="l-l">VIN</div>
      <div class="l-r" id="vin"></div>
    </div>
    <div class="l-form-group l-multiline-text-wrapper">
      <div class="l-l">待收车型</div>
      <div class="l-r l-multiline-text" id="collectionType"></div>
    </div>
    <div class="l-form-group ">
      <div class="l-l">4s维保记录</div>
      <div class="l-r" id="maintenance"></div>
      <button class="btn-r" id="maintenanceBtn" hidden="hidden">查看详情</button>
    </div>
    <div class="l-form-group ">
      <div class="l-l">车辆估价</div>
      <div class="l-r" id="evaluate"></div>
      <button class="btn-r" id="evaluateBtn" hidden="hidden">查看详情</button>
    </div>
    <div class="l-form-group ">
      <div class="l-l">客户姓名</div>
      <div class="l-r" id="customerName"></div>
    </div>
    <div class="l-form-group ">
      <div class="l-l">联系电话</div>
      <div class="l-r" id="contentNum"></div>
    </div>
    <div class="l-form-group ">
      <div class="l-l">意向价格</div>
      <div class="l-r" id="preferPrice"></div>
      <div class="div-r">万元</div>
    </div>
    <div class="l-form-group ">
      <div class="l-l">信息来源</div>
      <div class="l-r" id="infoRecourse"></div>
    </div>
    <div class="l-form-group ">
      <div class="l-l">收车区域</div>
      <div class="l-r" id="collectionArea"></div>
    </div>
    <div class="l-form-group ">
      <div class="l-l">上牌时间</div>
      <div class="l-r" id="firstUpTime"></div>
    </div>
    <div id="priceDiv">

    </div>
    <div class="l-form-group ">
      <div class="l-l">是否成交</div>
      <div class="l-r" id="isDeal"></div>
    </div>
    <div class="l-form-group " id="closeingPriceDiv" hidden="hidden">
      <div class="l-l">成交价格</div>
      <div class="l-r" id="closeingPrice"></div>
      <div class="div-r">万元</div>
    </div>
    <div class="l-form-group ">
      <div class="l-l">收车合同</div>
      <div class="l-r" id="closedcarcontract">未添加</div>
      <button class="btn-r" id="closedcarcontractBtn" hidden="hidden">查看详情</button>
    </div>
    <div class="l-form-group ">
      <div class="l-l l-multiline">备注</div>
      <div class="l-r" id="remark"></div>
    </div>
    <div class="l-form-group border_bot_f5">
      <div class="l-l">定价意见</div>
      <input type="hidden" id="carId"/>
      <textarea class="l-r" placeholder="输入定价意见" id="pricingInfo" maxlength="200"></textarea>
      <div class="l-numNotice">
        <span class="momentnum">0</span>/<span class="maxnum">200</span>
      </div>
    </div>
    <div class="l-btn-wrapper">
      <button class="l-btn " onclick="followUpShareSave()">提交</button>
    </div>
    <div class="rem_fff" ></div>
    <div class="rem_ebebeb " ></div>
    <div id="pricingDiv" hidden="hidden">
      <h3 class="l-sug-list">定价意见列表</h3>
    </div>
  </div>
</section>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script>
  $(function () {
      var id = GetArgsFromHref("id");
      $('#pricingInfo').keyup(function(){
          $('.momentnum').eq(0).text($(this).val().length)
      })
      $.ajax({
          type: 'GET',
          url: "/carsokApi/AcquisitionCar/selectMsg.do"+"?id="+id,
          async: false,
          success: function (obj) {
              obj = JSON.parse(obj).data.acquisitionCar;

              $('#carId').val(obj.id);

              var consultList = obj.consultList;
              console.log(consultList);
              var timestr;
              var times;
              for (var i=0;i<consultList.length;i++){
                  if (i==0){
                      timestr = "第一次给价";
                      $('#priceDiv').append("<div class='l-form-group '>"
                          +"<div class='l-l'>"+timestr+"</div>"
                          +"<div class='l-r'>"+consultList[i].consultPrice+"</div>"
                          +"<div class='div-r'>万元</div>"
                          +"</div>");
                  }else{
                      times = chinanum(i);
                      timestr = "第"+times+"次追加";
                      $('#priceDiv').append("<div class='l-form-group '>"
                          +"<div class='l-l'>"+timestr+"</div>"
                          +"<div class='l-r'>"+consultList[i].consultPrice+"</div>"
                          +"<div class='div-r'>万元</div>"
                          +"</div>");
                  }
              }

              var pricingList = obj.pricingList;
              if(pricingList.length > 0){
                  $('#pricingDiv').show();
              }
              for (var i=0;i<pricingList.length;i++){
                  $('#pricingDiv').append("<div class='l-form-group l-sug-history'>"
                      +"<div class='l-sug-content'>"+pricingList[i].pricingInfo+"</div>"
                      +"<span class='r-timebox'>"+pricingList[i].createTime+"</span>"
                      +"</div>");
              }

              if (obj.carInspectionTime != "" && obj.carInspectionTime != null){
                  $('#carInspectionTime').html(obj.carInspectionTime.split(" ")[0]);
              }else {
                  $('#carInspectionTime').html("--");
              }
              if (obj.firstUpTime != "" && obj.firstUpTime != null){
                  $('#firstUpTime').html(obj.firstUpTime.split(" ")[0]);
              }else {
                  $('#firstUpTime').html("--");
              }
              if (obj.vin != "" && obj.vin != null){
                  $('#vin').html(obj.vin);
              }else {
                  $('#vin').html("--");
              }
              if (obj.collectionType != "" && obj.collectionType != null){
                  $('#collectionType').html(obj.collectionType);
              }else {
                  $('#collectionType').html("--");
              }
              if (obj.customerName != "" && obj.customerName != null){
                  $('#customerName').html(obj.customerName);
              }else {
                  $('#customerName').html("--");
              }
              if (obj.contentNum != "" && obj.contentNum != null){
                  $('#contentNum').html(obj.contentNum);
              }else {
                  $('#contentNum').html("--");
              }
              if (obj.preferPrice != "" && obj.preferPrice != null){
                  $('#preferPrice').html(obj.preferPrice);
              }else {
                  $('#preferPrice').html("--");
              }
              if (obj.infoRecourse != "" && obj.infoRecourse != null){
                  $('#infoRecourse').html(obj.infoRecourse);
              }else {
                  $('#infoRecourse').html("--");
              }
              if (obj.collectionArea != "" && obj.collectionArea != null){
                  $('#collectionArea').html(obj.collectionArea);
              }else {
                  $('#collectionArea').html("--");
              }
              if (obj.closeingPrice != "" && obj.closeingPrice != null){
                  $('#closeingPrice').html(obj.closeingPrice);
              }else {
                  $('#closeingPrice').html("--");
              }
              if (obj.remark != "" && obj.remark != null){
                  $('#remark').html(obj.remark);
              }else {
                  $('#remark').html("--");
              }

              var isDeal = obj.isDeal;
              if (isDeal == 1){
                  $('#isDeal').html("是");
                  $('#closeingPriceDiv').show();
              }else if(isDeal == 2){
                  $('#isDeal').html("否");
                  $('#closeingPriceDiv').hide();
              }else if(isDeal == 3){
                  $('#isDeal').html("放弃");
                  $('#closeingPriceDiv').hide();
              }
              var maintenance = obj.maintenance;
              if (maintenance != "" && maintenance != null){
                  $('#maintenance').html("已查看");
                  $('#maintenanceBtn').show();
                  $('#maintenanceBtn').on('click',function () {
                      //window.open(maintenance);
                      window.location = maintenance;
                  });
              }else{
                  $('#maintenance').html("未查看");
              }
              var evaluate = obj.evaluate;
              if (evaluate != "" && evaluate != null){
                  $('#evaluate').html(obj.evaluatePrice+"元");
                  $('#evaluateBtn').show();
                  $('#evaluateBtn').on('click',function () {
                      //window.open(evaluate);
                      window.location = evaluate;
                  });
              }else{
                  $('#evaluate').html("未估价");
              }
              var closedcarcontract = obj.closedcarcontract;
              if (closedcarcontract != "" && closedcarcontract != null){
                  $('#closedcarcontract').html("已添加");
                  $('#closedcarcontractBtn').show();
                  $('#closedcarcontractBtn').on('click',function () {
                      window.open(closedcarcontract);
                  });
              }else{
                  $('#closedcarcontract').html("未添加");
              }
          }
      });
  });

  //sArgName表示要获取哪个参数的值
  function GetArgsFromHref(sArgName) {
      var sHref = window.location.href;//测试数据，实际情况是用window.location.href得到URL
      var args = sHref.split("?");
      var retval = "";
      if(args[0] == sHref) /*参数为空*/
      {
          return retval; /*无需做任何处理*/
      }
      var str = args[1];
      args = str.split("&");
      for(var i = 0; i < args.length; i++ )
      {
          str = args[i];
          var arg = str.split("=");
          if(arg.length <= 1) continue;
          if(arg[0] == sArgName) retval = arg[1];
      }
      return retval;
  }
  
  //通过阿拉伯数字获取汉字数字
  function chinanum(num) {
      var china = new Array('一','二','三','四','五','六','七','八','九','十','十一','十二','十三','十四','十五','十六','十七','十八','十九','二十');
      return china[num];
  }

  //保存定价意见
  function followUpShareSave() {
      var carId = $('#carId').val();
      var pricingInfo = $('#pricingInfo').val();
      if(pricingInfo != "" && pricingInfo != null){
          $.ajax({
              type: 'GET',
              url: "/carsokApi/AcquisitionCar/followUpShareSave.do"+"?carId="+carId+"&pricingInfo="+pricingInfo,
              async: false,
              success: function (obj) {
                  layer.open({
                      content: JSON.parse(obj).retMsg
                      ,skin: 'msg'
                      ,time: 2 //2秒后自动关闭
                  });
                  $('#pricingDiv').append("<div class='l-form-group l-sug-history'>"
                      +"<div class='l-sug-content'>"+pricingInfo+"</div>"
                      +"<span class='r-timebox'>"+JSON.parse(obj).data+"</span>"
                      +"</div>");
                  $('#pricingInfo').val("");
                  $('.momentnum').eq(0).text(0);
                  $('#pricingDiv').show();
                  //location.reload();
                  //window.location.href=window.location.href+"&rand="+10000*Math.random();
              }
          });
      }else{
          layer.open({
              content: '请填写定价意见'
              ,skin: 'msg'
              ,time: 2 //2秒后自动关闭
          });
      }
  }
</script>
</body>
</html>