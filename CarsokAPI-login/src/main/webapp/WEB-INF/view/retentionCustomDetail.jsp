<%--
  Created by IntelliJ IDEA.
  User: Raytine
  Date: 2017/10/19
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>保有客户信息</title>
</head>
<meta charset="UTF-8">
<meta name="format-detection" content="telephone=no">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
      name="viewport">
<link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
<style>
    .layui-m-layercont{ padding:30px;}
    .layui-m-layerbtn span[yes]{ color:#e29000;}
    .layui-m-anim-scale{
        max-height: 40rem;
        overflow-y: auto;

    }
    .speechcraft-list  dl dd.right-dialog{
        background: none;
    }
</style>



<body>

<div class="wrap">
    <div class="ma-top-0-56">
        <div class="header-info">
            <div class="title">基本信息</div>
            <div class="text-right">
                <a class="info-btn" id="goEdit">编辑</a>
            </div>
        </div>
        <div class="center-info">
            <div class="car-info-list">
                <div class="car-info-left">
                    <span id="cusname"></span><span id="sex"></span><span id="birthday"></span>
                </div>
                <div id="telephone"></div>
            </div>
            <div class="car-info-list" style="display: flex">
                <div class="car-info-left" id="carName" style="margin: auto">

                </div>
                <div class="color-888" style="margin: auto"><b id="carPrice">58</b>万</div>
            </div>
            <div class="car-info-list" style="padding: 0;">
                <div class="car-info-left">
                    <span id="carVin"></span>
                    <span id="carMale"></span>
                    <span id="carNum"></span>
                    <span id="job">自由职业者</span>
                    <span id="buyStatus">首次购买</span>
                    <span id="payFun">全款</span>
                </div>
            </div>

            <div class="car-info-list">
                <div class="car-insurance">
                    <div id="baoyang">20175555</div>
                    <div>保养到期</div>
                </div>
                <div class="car-insurance">
                    <div id="zhibao">20175555</div>
                    <div>质保到期</div>
                </div>
                <div class="car-insurance">
                    <div id="jiaoqiang">20175555</div>
                    <div>交强险</div>
                </div>
                <div class="car-insurance">
                    <div id="shangye">20175555</div>
                    <div>商业险</div>
                </div>
            </div>

            <div class="car-info-list">
                <div class="car-info-left" style="flex-grow: 0">
                    之前车型：
                </div>
                <div id="beforecar" style="width: 100%"></div>
            </div>

            <div class="car-info-list">
                <div class="car-info-left" style="flex-grow: 0">
                    备注信息：
                </div>
                <div id="tips" style="width: 100%"></div>
            </div>

        </div>
    </div>

    <div class="ma-top-0-56">
        <div class="header-info">
            <div class="title">跟进操作</div>
            <div class="text-right color-888" id="tel_pianzi">电话话术提醒</div>
        </div>
        <div class="center-info">
            <div class="cneter-list">
                <div class="color-888"><span>成交日期：</span><span id="followDate"></span></div>
                <div class="car-info-list">
                    <div>成交信息</div>
                    <div class="text-right">
                        <a data-val="1" class="info-btn layBtn">跟进信息</a>
                    </div>
                </div>
            </div>
            <div class="cneter-list">
                <div class="color-888"><span>三日回访日：</span><span id="followThree"></span></div>
                <div class="car-info-list">
                    <div>3日电话邀约</div>
                    <div class="text-right min-width">
                        <a class="info-btn goTel">拨打电话</a>
                        <a data-val="2" class="info-btn layBtn" id="threeBtn">跟进信息</a>
                    </div>
                </div>
            </div>

            <div class="cneter-list">
                <div class="color-888"><span>七日回访日：</span><span id="followSeven"></span></div>
                <div class="car-info-list">
                    <div>7日电话邀约</div>
                    <div class="text-right min-width">
                        <a class="info-btn goTel">拨打电话</a>
                        <a data-val="3" class="info-btn layBtn">跟进信息</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="ma-top-0-56">
        <div class="header-info">
            <div class="title">跟进信息</div>
        </div>
        <div class="center-info" id="followList">

        </div>

    </div>
<input type="hidden" id="phone"/>
</div>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script src="/carsokApi/js/jquery.fixedheadertable.min.js" type="text/javascript" charset="utf-8"></script>
<script>
    $(function () {

        var mobile =${mobile};
        var carid = location.href.split('id=')[1];
        var tid;

        carid.split(":")[0] === 'null' ? tid = '' : tid = carid.split(":")[0];
        var tcid = carid.split(":")[1];


        $.ajax({
            type: 'post',
            url: "/carsokApi/TenureCustomer/tenureCarPeopleMsg.do",
            async: true,
            dataType: 'json',
            data: {
                "tid": tid,
                "mobile": mobile,
                "tcid": tcid

            },
            success: function (data) {
                var custName = data.tenureMap.custName;
                var custBirthday = data.tenureMap.custBirthday;
                var custSex = data.tenureMap.custSex;
                var custPhone = data.tenureMap.custPhone;
                if(custPhone == '' || custPhone == null){
                    $('.goTel').hide();
                }
                $('#phone').val(custPhone);
                $('.goTel').prop('href', 'tel:' + $('#phone').val());
                var tenureCarname = data.carMap.tenureCarname;
                var tenureCarprice = data.carMap.tenureCarprice;
                var tenureVin = data.carMap.tenureVin;
                var carMales = data.carMap.carMales + '公里';
                var tenureCarnum = data.carMap.tenureCarnum;
                var custVocation = data.tenureMap.custVocation;
                var custBeforecar = data.tenureMap.custBeforecar;
                var custRemark = data.tenureMap.custRemark
                var purchaseStatus;
                $('#goEdit').click(function () {
                    var ttid;
                    if(tid){
                        ttid=tid
                    }else {
                        ttid='null'
                    }
                    window.location.href="/carsokApi/TenureCustomer/tenureMsgPage.do?mobile=" + mobile + "&id=" + ttid + ":" + tcid + ""
                });

                switch (data.tenureMap.purchaseStatus) {
                    case 3:
                        purchaseStatus = '首次购买';
                        break;
                    case 4:
                        purchaseStatus = '首次置换';
                        break;
                    case 7:
                        purchaseStatus = '置换';
                        break;
                    case 5:
                        purchaseStatus = '复购';
                        break;

                }
                var payFun;
                if (data.carMap.tenureCartype == 1) {
                    payFun = '全款'
                } else if (data.carMap.tenureCartype == 2) {
                    payFun = '按揭'
                }
                var jiaoqiang = data.carMap.tenureCompulsory == null ? "" : data.carMap.tenureCompulsory.substring(0, 10);
                var shangye = data.carMap.tenureBusiness == null ? "" : data.carMap.tenureBusiness.substring(0, 10);
                var baoyang = data.carMap.tenureMaintain == null ? "" : data.carMap.tenureMaintain.substring(0, 10);
                var zhibao = data.carMap.tenureWarranty == null ? "" : data.carMap.tenureWarranty.substring(0, 10);
                var saleTime = data.carMap.saleTime;
                var timeBox = new Date(saleTime).getTime();
                var today = new Date().getTime();
                var three = timeBox + 259200000;
                var seven = timeBox + 604800000;
                var threeDay =data.threeTime;
                var sevenDay = data.sevenTime;

                if (today > three) {
                    $('#threeBtn').hide()
                }


                // 绑定数据

                if (custName) {
                    $('#cusname').text(custName);
                } else {
                    $('#cusname').text('姓名未完善');

                }
                if (custSex) {
                    $('#sex').text(custSex);
                } else {
                    $('#sex').hide();

                }
                if (custBirthday) {
                    $('#birthday').text(custBirthday.substring(0, 10));

                } else {
                    $('#birthday').hide();
                }

                if (custPhone) {
                    $('#telephone').text(custPhone);
                } else {
                    $('#telephone').hide();
                }
                if (tenureCarname) {
                    $('#carName').text(tenureCarname);

                } else {
                    $('#carName').hide();

                }

                if(tenureVin){
                    $('#carVin').text(tenureVin);

                }else {
                    $('#carVin').hide();

                }
                if(carMales){
                    $('#carMale').text(carMales);

                }else {
                    $('#carMale').hide();

                }

                if(tenureCarnum){
                    $('#carNum').text(tenureCarnum);

                }else {
                    $('#carNum').hide();

                }
                if(tenureCarprice){
                    $('#carPrice').text(tenureCarprice);

                }else {
                    $('#carPrice').hide();

                }
                if(jiaoqiang){
                    $('#jiaoqiang').text(jiaoqiang);

                }else {
                    $('#jiaoqiang').hide();
                    $('#jiaoqiang').next().hide()
                }
                if(shangye){
                    $('#shangye').text(shangye);

                }else {
                    $('#shangye').hide();
                    $('#shangye').next().hide()

                }
                if(baoyang){
                    $('#baoyang').text(baoyang);

                }else {
                    $('#baoyang').hide();
                    $('#baoyang').next().hide()

                }
                if(zhibao){
                    $('#zhibao').text(zhibao);

                }else {
                    $('#zhibao').hide();
                    $('#zhibao').next().hide()

                }
                if(custVocation){
                    $('#job').text(custVocation);

                }else {
                    $('#job').hide();

                }
                if(purchaseStatus){
                    $('#buyStatus').text(purchaseStatus);

                }else {
                    $('#buyStatus').hide();

                }
                if(payFun){
                    $('#payFun').text(payFun);

                }else {
                    $('#payFun').hide();

                }
                if(custBeforecar){
                    $('#beforecar').text(custBeforecar);

                }else {
                    $('#beforecar').text('- -');

                }
                if(custRemark){
                    $('#tips').text(custRemark);

                }else {
                    $('#tips').text('- -');

                }

                $('#followDate').text(saleTime.substring(0, 10).replace(/-/g, '.'));
                $('#followThree').text(threeDay);
                $('#followSeven').text(sevenDay);

                if (data.followMap.length > 0) {
                    for (var i in data.followMap) {
                        var followList = '';
                        var buyType = '';
                        var followTime = '';
                        if (data.followMap[i].followType === 1) {
                            buyType = '成交信息'
                        } else if (data.followMap[i].followType === 2) {
                            buyType = '3日电话回访'
                        } else if (data.followMap[i].followType === 3) {
                            buyType = '7日电话回访'
                        }

                        data.followMap[i].createTime ? followTime = data.followMap[i].createTime.substring(0, 10) : followTime = '';


                        followList += '<div class="cneter-list"> <div class="color-888"><span class="info-time">' + followTime + '</span>' + buyType + '<span></span></div> <div class="car-info-list">' + data.followMap[i].followMessage + ' </div></div>'
                        $('#followList').append(followList)
                    }

                }else {
                    $('#followList').text('暂无跟进信息');
                }

//               new Date().toDateString() === new Date(str).toDateString()
            },
            error: function () {
                //me.resetload();
            }
        });

        $('.layBtn').click(function () {
            var type = $(this).attr('data-val');
            btnClick(tcid, mobile, type);
        });




        $('#tel_pianzi').click(function () {
            layer.open({
                content: '' +
                '<div id="mes"><div class="speechcraft-list "><h4>再次电话回访</h4>' +
                '<dl><dt><img  src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">您好，请问您是× 先生/女士么？</div></dd></dl>' +
                '<dl class="dl-right"><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="right-dialog"><div class="dialog_box">是 / 否</div></dd></dl>' +
                '<dl><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">我是梧桐网辉程名车。您在x月来本店看了xx款车，请问考虑的怎么样了？</div></dd></dl>' +
                '<dl class="dl-right"><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="right-dialog"><div class="dialog_box">我在比较、最近忙、观望等</div></dd></dl>' +
                '<dl><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">说出本门店最近活动或优惠政策等等，您看（封闭提问）什么时间方便来了解一下？给您具体介绍扫一下。</div></dd></dl>' +
                '<dl class="dl-right"><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="right-dialog"><div class="dialog_box">好的，我再想想</div></dd></dl>' +
                '<dl><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">自由发挥，记载回访情况。</div></dd></dl>' +
                '</div>' +
                '<div class="speechcraft-list "><h4>已购买</h4>' +
                '<p>1. 如果购买的是本车行的商品车，请记载商品情况</p >' +
                '<p>2. 如果购买了其他车行商品车，要询问客户</p >' +
                '<ol><li>购买的车辆类型</li><li>在哪一家车行购买</li><li>为什么购买此车</li></ol>' +
                '</div></div>'
                , btn: ['确定']
                , yes: function (index) {
                    layer.close(index);
                },
                area:['auto','300px']
            });
        });


        function btnClick(tcid, mobile, type) {

            var custName = $('#cusname').text();
            var custPhone = $('#telephone').text();
            layer.open({

                content: '<p class="align-left">客户姓名：' + custName + '</p><p class="align-left">客户电话：<a href="tel:' + custPhone + '" onclick="deletePushMsg(' + custPhone + ')">' + custPhone + '</a></p><textarea id="tracks"  rows="3" maxlength="200" class="txtarea" onkeyup="value=value.replace(/(:)/g,\'\')"></textarea>'

                , btn: ['提交', '取消']
                , yes: function (index) {
                    if (!$('#tracks').val()) {
                        return
                    }
                    var tracks = $('#tracks').val()
                    $.ajax({
                        type: 'post',
                        url: "/carsokApi/TenureCustomer/saveFollowMessage.do",
                        dataType: 'json',
                        data: {
                            "tenurecarId": tcid,
                            "followMessage": tracks,
                            "followType": type,
                            "mobile": mobile
                        },
                        success: function (data) {
                            // 模拟一条跟进信息
                            var followType = '';
                            if (type == 1) {
                                followType = '初次购买'
                            } else if (type == 2) {
                                followType = '三日回访'

                            } else if (type == 3) {
                                followType = '七日回访'

                            }
                            var time = new Date().toLocaleString().substring(0, 10).replace(/\//g, '-');
                            var followListDiv = '<div class="cneter-list"> <div class="color-888"><span class="info-time">' + time + '</span>' + followType + '<span></span></div> <div class="car-info-list">' + tracks + ' </div></div>'
                            if($('#followList').html()=='暂无跟进信息'){
                                $('#followList').html('');
                            }
                            $('#followList').prepend(followListDiv);

                        }
                    });
                    layer.close(index);
                }
            });
        }
    })
</script>
</body>

</html>
