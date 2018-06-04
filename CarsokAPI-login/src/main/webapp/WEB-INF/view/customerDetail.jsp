<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>潜在客户管理</title>
</head>
<meta charset="UTF-8">
<meta name="format-detection" content="telephone=no">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
      name="viewport">
<link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
<link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
<style>
    .layui-m-anim-scale{
        max-height: 40rem;
        overflow-y: auto;
    }
</style>

<body>

<div class="wrap">
    <div class="ma-top-0-56">
        <div class="header-info">
            <div class="title">客户信息</div>
            <div class="text-right">
                <a class="info-btn" id="bianji" href="">编辑</a>
            </div>
        </div>
        <div class="center-info">
            <div class="car-info-list">
                <div class="car-info-left">
                    <span id="custName">加载中......</span>
                </div>
                <div id="custPhone">加载中......</div>
            </div>
            <div class="car-info-list">
                <div class="car-info-left" id="custCarName">
                    加载中......
                </div>

            </div>
            <div class="car-info-list">
                <div class="car-info-left" id="custIntime">
                    加载中......
                </div>
            </div>
            <div class="car-info-list">
                <div class="car-info-left" id="custLevel">
                    加载中......
                </div>
            </div>
            <div class="car-info-list">
                <div class="car-info-left" id="custSug">
                    加载中......
                </div>
            </div>
            <div class="car-info-list">
                <div class="car-insurance">
                    <div id="custInfo">加载中......</div>
                    <div>信息来源</div>
                </div>
                <div class="car-insurance">
                    <div id="custNum">加载中......</div>
                    <div>到店人数</div>
                </div>
                <div class="car-insurance">
                    <div id="custAdress">加载中......</div>
                    <div>地域</div>
                </div>
                <div class="car-insurance">
                    <div id="custBuy">加载中......</div>
                    <div>购买预算</div>
                </div>
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
                <div class="color-888"><span>初次到店日：</span><span id="first">2017.01.06</span></div>
                <div class="car-info-list">
                    <div>成交信息</div>
                    <div class="text-right">
                        <a class="info-btn custFlowInsert" data-id="1">跟进信息</a>
                    </div>
                </div>
            </div>
            <div class="cneter-list">
                <div class="color-888"><span>三日邀约日：</span><span id="three">2017.01.06</span></div>
                <div class="car-info-list">
                    <div>3日电话邀约</div>
                    <div class="text-right min-width">
                        <a class="info-btn telePhoneNuM" href="">拨打电话</a>
                        <a class="info-btn custFlowInsert" data-id="2">跟进信息</a>
                    </div>
                </div>
            </div>
            <div class="cneter-list">
                <div class="color-888"><span>七日邀约日：</span><span id="seven">2017.01.06</span></div>
                <div class="car-info-list">
                    <div>7日电话邀约</div>
                    <div class="text-right min-width">
                        <a class="info-btn telePhoneNuM" href="">拨打电话</a>
                        <a class="info-btn custFlowInsert" data-id="6">跟进信息</a>
                    </div>
                </div>
            </div>
            <div class="cneter-list">
                <div class="car-info-list">
                    <div>购车记录
                        <select style="width: 142px;height: 24px;" id="custStausSelect">
                            <option value="3">首次购买</option>
                            <option value="4">首次置换</option>
                            <option value="5">复购</option>
                            <option value="7">置换</option>
                        </select>
                    </div>
                    <div class="text-right">
                        <a class="info-btn custFlowInsert" data-id="9">跟进信息</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="ma-top-0-56">
        <div class="header-info">
            <div class="title">跟进信息</div>
        </div>
        <div class="center-info" id="custFlowMsg">

        </div>

    </div>
</div>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script src="/carsokApi/js/jquery.fixedheadertable.min.js" type="text/javascript" charset="utf-8"></script>
<script>
    $(function () {
        var mobile =${mobile};
        var custId =${custId};
        var intimes;
        var intimesShow;
        var peopleNums;
        var customerNames;
        var customerPhones;
        var customerStatuss;
        var informationSourcess;
        var intentionalVehicles;
        var customerLevels;
        var customerTracks;
        var customerBudgets;
        var customerRegions;
        var threeDays;
        var sevenDays;
//        var carid = location.href.split('id=')[1];
//        var tid;
//        carid.split(":")[0] === 'null' ? tid = '' : tid = carid.split(":")[0]
//        var tcid = carid.split(":")[1];

        $.ajax({
            type: 'post',
            url: "/carsokApi/storeOrAcquisitionCar/searchStoreCustMsg.do",
            async: true,
            dataType: 'json',
            data: {
                "mobile": mobile,
                "id":custId
            },
            success: function (data) {
                intimes = data.intimes;
                intimesShow = data.intimesShow;
                peopleNums = data.custMsg.peopleNum;
                customerNames = data.custMsg.customerName;
                customerPhones = data.custMsg.customerPhone;
                customerStatuss = data.custMsg.customerStatus;
                informationSourcess = data.custMsg.informationSources;
                intentionalVehicles = data.custMsg.intentionalVehicle;
                if(data.custMsg.customerLevel.indexOf('A')>-1){
                    customerLevels = "A:一周以内(购买欲望强)";
                }else if(data.custMsg.customerLevel.indexOf('B')>-1){
                    customerLevels = "B:一月以内(准买车用户)";
                }else if(data.custMsg.customerLevel.indexOf('C')>-1){
                    customerLevels = "C:三个月以内(有购买意向)";
                }else if(data.custMsg.customerLevel.indexOf('D')>-1){
                    customerLevels = "D:闲逛(近期无意向)";
                }
                customerTracks = data.custMsg.customerTrack;
                customerBudgets = data.custMsg.customerBudget;
                customerRegions = data.custMsg.customerRegion;
                threeDays = data.threeDays;
                sevenDays = data.sevenDays;
                $("#custName").html(customerNames);
                $("#custPhone").html(customerPhones);
                $("#custCarName").html("意向车型："+intentionalVehicles);
                $("#custIntime").html("进店时间："+intimesShow);
                $("#custLevel").html("客户级别："+customerLevels);
                $('#custSug').html('备注信息：'+customerTracks);
                $("#custInfo").html(informationSourcess);
                $("#custBuy").html(customerBudgets);
                $("#custAdress").html(customerRegions);
                $("#custNum").html(peopleNums);
                $("#bianji").attr("href","mdjdglEdit:"+custId);
                $(".telePhoneNuM").attr("href","tel:"+customerPhones);

                var timeBox = new Date(intimes).getTime();
                var today = new Date().getTime();
                var three = timeBox + 259200000;
                var seven = timeBox + 604800000;
                var threeDay = data.threeTime;
                var sevenDay = data.sevenTime;

                $("#first").html(data.saleTime);
                $("#three").html(threeDay);
                $("#seven").html(sevenDay);

                var timeBox = new Date(intimes).getTime();
                var today = new Date().getTime();
                var three = timeBox + 259200000;
                var seven = timeBox + 604800000;
                var threeDay = formatDate(new Date(three));
                var sevenDay = formatDate(new Date(seven));
                function formatDate (date) {
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    m = m < 10 ? '0' + m : m;
                    var d = date.getDate();
                    d = d < 10 ? ('0' + d) : d;
                    return y + '.' + m + '.' + d;
                };
                $("#first").html(formatDate(new Date(intimes)));
                $("#three").html(threeDay);
                $("#seven").html(sevenDay);

//                var timeBox = new Date(intimes).getTime();
//                var today = new Date().getTime();
//                var three = timeBox + 259200000;
//                var seven = timeBox + 604800000;
//                var threeDay = threeDays;
//                var sevenDay = formatDate(new Date(seven));
//                function formatDate (date) {
//                    var y = date.getFullYear();
//                    var m = date.getMonth() + 1;
//                    m = m < 10 ? '0' + m : m;
//                    var d = date.getDate();
//                    d = d < 10 ? ('0' + d) : d;
//                    return y + '.' + m + '.' + d;
//                };
                $("#first").html(intimes);
                $("#three").html(threeDays);
                $("#seven").html(sevenDays);

                var recordHtml = '';
                if(data.flowList.length>0){
                    for(var i = 0;i<data.flowList.length;i++){
                        var custStatus = '';
                        if(data.flowList[i].status == 1){
                            custStatus = '初次到店'
                        }if(data.flowList[i].status == 2){
                            custStatus = '3日电话邀约'
                        }if(data.flowList[i].status == 3){
                            custStatus = '首次购买'
                        }if(data.flowList[i].status == 4){
                            custStatus = '首次置换'
                        }if(data.flowList[i].status == 5){
                            custStatus = '复购'
                        }if(data.flowList[i].status == 6){
                            custStatus = '7日电话邀约'
                        }if(data.flowList[i].status == 7){
                            custStatus = '置换'
                        }
                        recordHtml = '<div class="cneter-list">'
                                    + '<div class="color-888"><span class="info-time">'+data.flowList[i].createTime+'</span>'+custStatus+'<span></span></div>'
                                    + '<div class="car-info-list">'+ data.flowList[i].customerFlowMessage +'</div>'
                                    + '</div>';
                        $("#custFlowMsg").append(recordHtml);
                    }
                }else{
                    $("#custFlowMsg").html('暂无跟进信息');
                }
            },
            error: function () {
                //me.resetload();
            }
        });
        $('#tel_pianzi').click(function () {
            layer.open({
                content: '' +
                '<div id="mes"><div class="speechcraft-list "><h4>再次电话回访</h4>' +
                '<dl><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">您好，请问您是× 先生/女士么？</div></dd></dl>' +
                '<dl class="dl-right"><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="right-dialog"><div class="dialog_box">是 / 否</div></dd></dl>' +
                '<dl><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">我是梧桐网辉程名车。您在x月来本店看了xx款车，请问考虑的怎么样了？</div></dd></dl>' +
                '<dl class="dl-right"><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="right-dialog"><div class="dialog_box">我在比较、最近忙、观望等</div></dd></dl>' +
                '<dl><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">说出本门店最近活动或优惠政策等等，您看（封闭提问）什么时间方便来了解一下？给您具体介绍扫一下。</div></dd></dl>' +
                '<dl class="dl-right"><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="right-dialog"><div class="dialog_box">好的，我再想想</div></dd></dl>' +
                '<dl><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">自由发挥，记载回访情况。</div></dd></dl>' +
                '</div>' +
                '<div class="speechcraft-list "><h4>已购买</h4>' +
                '<p>1. 如果购买的是本车行的商品车，请记载商品情况</p>' +
                '<p>2. 如果购买了其他车行商品车，要询问客户</p>' +
                '<ol><li>购买的车辆类型</li><li>在哪一家车行购买</li><li>为什么购买此车</li></ol>' +
                '</div></div>'
                , btn: ['确定']
                , yes: function (index) {
                    layer.close(index);
                },
                area:['auto','300px']
            });
        });

        $(".custFlowInsert").click(function () {
            var custStatuses = '';
            var creatT = new Date().toLocaleString().substring(0, 10).replace(/\//g, '-');
            if($(this).attr("data-id")==9){
                custStatuses = $("#custStausSelect").val();
            }else{
                custStatuses = $(this).attr("data-id");
            }
            layer.open({
                content: '<div class="clearfix layer_form"><p>客户姓名：'+customerNames+'</p><p>客户电话：'+customerPhones+'<a href="tel:"><font color=blue></font></a></p><p style="overflow: hidden;">' +
                '<div class="clearfix"><div class="radio-box">' +
                '</div></div><textarea id="tracks" placeholder="请输入跟踪信息" rows="3" class="txtarea"></textarea></div><div class="clearfix"><p class="pull-right more-btn" ></p></div>' +
                '<div id="mes" style="display:none;"><div class="speechcraft-list "><h4>再次电话回访</h4>' +
                '</div>' +
                '<div class="speechcraft-list ">' +
                '</div></div>'
                , btn: ['提交', '取消']
                , yes: function (index) {
                    var flow = $("#tracks").val();
                    var custLe = '';
                    var pushTimes = $("#pushTime").val();
                    var comes = $('input[name="customSta"]').filter(':checked').val();
                    $.ajax({
                        type: 'post',
                        url: "/carsokApi/customer/customerFlow.do",
                        dataType: 'json',
                        data: {
                            "mobile":mobile,
                            "custI": custId,
                            "custFlow": flow,
                            "customerStatus": custStatuses
                        },
                        success: function (data) {
                            if(custStatuses == 1){
                                custLe = '初次到店'
                            }if(custStatuses == 2){
                                custLe = '3日电话邀约'
                            }if(custStatuses == 3){
                                custLe = '首次购买'
                            }if(custStatuses == 4){
                                custLe = '首次置换'
                            }if(custStatuses == 5){
                                custLe = '复购'
                            }if(custStatuses == 6){
                                custLe = '7日电话邀约'
                            }if(custStatuses == 7){
                                custLe = '置换'
                            }
                            var recordHtmls = '<div class="cneter-list">'
                                    + '<div class="color-888"><span class="info-time">'+creatT+'</span>'+custLe+'<span></span></div>'
                                    + '<div class="car-info-list">'+ flow +'</div>'
                                    + '</div>';
                            if($("#custFlowMsg").html() == "暂无跟进信息"){
                                $("#custFlowMsg").html('');
                            }
                            $("#custFlowMsg").append(recordHtmls);
                        }
                    });
                    layer.close(index);
                }
            });
        });


    })

</script>
</body>

</html>
