<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
          name="viewport">
    <title>客户登记表</title>
    <link href="/carsokApi/css/defaultTheme.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <style>
        .layui-m-layerbtn span[yes] {
            color: #e29000;
        }

        .layui-m-layer0 .layui-m-layerchild {
            max-height: 500px;
            overflow-y: auto;
        }

        .layui-m-layercont {
            padding: 15px;
            text-align: left;;
        }

        .layui-m-layer-footer .layui-m-layercont {
            border-radius: 4px;
        }

        tbody {
            background: #fff;
        }
    </style>
</head>
<body class="white-body header-nav-body" style="padding-top:12rem;">
<!-- header start -->
<header class="search border-box">
    <div class="search-wrap border-box">
        <a href="javascript:;" class="search-ico"></a>
        <input class="border-box" id="search_key" type="text" placeholder="电话、姓名、意向车型" style="padding-left:2.6rem;">
        <a href="javascript:;" class="search-delete" onclick="Uton.emptySearch();"></a>
    </div>
    <div class="search-btn" id="search-btn"><a href="javascript:void(0);">搜索</a></div>
</header>
<div class="tab-tit  border-box">
    <ul id="filter_type">
        <li id="liDays" data-val="1" class="active">全部
            <c id="spand">(0)</c>
        </li>
        <li id="liWeeks" data-val="2">3日
            <c id="spanw">(0)</c>
        </li>
        <li id="liMonths" data-val="3">7日
            <c id="spanm">(0)</c>
        </li>
        <li id="liOverTime" data-val="4">过期
            <c id="spanover">(0)</c>
        </li>
    </ul>
    <input type="hidden" value="" id="timeStatus"/>
    <input type="hidden" value="" id="timesCache"/>

    <!--<input type="hidden" value="" id="liHide1"/>
    <input type="hidden" value="" id="liHide2"/>
    <input type="hidden" value="" id="liHide3"/>
    <input type="hidden" value="" id="liHide4"/>
    <input type="text" value="1" id="canDoAjax"/>-->
</div>
<!-- nav start -->
<nav class="car-filter border-box" style="top:8rem;">
    <ul class="clearfix">
        <li>
            <a class="car-sel" href="javascript:;" id="custXXLY">时间筛选</a>
            <ul class="filter-son border-box" id="custom-category">
                <li data-val="0" id="screen_sysj" class="active">所有时间</li>
                <li data-val="1" id="screen_pyjs">本日</li>
                <li data-val="2" id="screen_pyq">本周</li>
                <li data-val="3" id="screen_58tc">本月</li>
            </ul>
        </li>
        <li>
            <a class="car-sel" href="javascript:;" id="custKHJB">客户级别</a>
            <ul class="filter-son border-box" id="customLever1">
                <li data-val="" class="active" id="screen_alljb">所有级别</li>
                <li data-val="0" id="screen_a">A : 一周以内(购买欲望强)</li>
                <li data-val="1" id="screen_b">B : 一月以内(准买车用户)</li>
                <li data-val="2" id="screen_c">C : 三个月以内(有购买意向)</li>
                <li data-val="3" id="screen_d">D : 闲逛(近期无意向)</li>
            </ul>
        </li>
        <li>
            <a class="car-sel" href="javascript:;" id="custDFZT">到访状态</a>
            <ul class="filter-son border-box" id="customLever2">
                <li data-val="" class="active" id="screen_alzt">全部状态</li>
                <li data-val="0" id="screen_first">初次</li>
                <li data-val="1" id="screen_three">3日电话邀约</li>
                <li data-val="2" id="screen_seven">7日电话邀约</li>
                <li data-val="3" id="screen_shoucimai">首次购买</li>
                <li data-val="4" id="screen_shoucizhihuan">首次置换购买</li>
                <li data-val="5" id="screen_zhihuan">置换</li>
                <li data-val="6" id="screen_fugou">复购</li>
            </ul>
        </li>
        <li>
            <a class="car-sel" href="javascript:;" id="custGMYS">购买预算</a>
            <ul class="filter-son border-box" id="customLever3">
                <li data-val="" class="active" id="screen_allys">所有预算</li>
                <li data-val="0" id="screen_10">10万以下</li>
                <li data-val="1" id="screen_10_20">10-20万</li>
                <li data-val="2" id="screen_20_40">20-40万</li>
                <li data-val="2" id="screen_40_60">40-60万</li>
                <li data-val="2" id="screen_60">60万以上</li>
            </ul>
        </li>
    </ul>
</nav>
<!-- nav end -->
<!-- header end -->
<section class="border-box l-list-wrap">
    <div class="tab-table table-responsive" style="height: auto;" id="frameDiv">
        <div id="tbl"></div>
    </div>


</section>
<input type="hidden" id="mobile" value=""/>
<input type="hidden" id="customerPhone" value=""/>
<input type="hidden" id="xxly" value=""/>
<input type="hidden" id="khjb" value=""/>
<input type="hidden" id="dfzt" value=""/>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script src="/carsokApi/js/carList.js" type="text/javascript"></script>
<script src="/carsokApi/js/jquery.fixedheadertable.min.js" type="text/javascript" charset="utf-8"></script>
<script>
    $(function () {
        window.onscroll = function () {
            if (getScrollTop() + getWindowHeight() == getScrollHeight()) {

            }
        };
        $("#mobile").val(${mobile});
        $("#customerPhone").val(${customerPhone});
        $("#records").html("");
        // 页数
        var page = 0;
        var mobile = $("#mobile").val();
        // 每页展示5个

        // dropload
        function BuildFilter(btn)
        {
            var filter = {}
            filter.pageNum = 0;
            var liTimes = $("#custom-category").find(".active");
            filter.times= liTimes.length>0?liTimes.data("val"):0;
            filter.mobile = mobile;
            filter.khjb =$("#customLever1").find(".active").html();
            filter.dfzt =$("#customLever2").find(".active").html();
            filter.gmys =$("#customLever3").find(".active").html();
            filter.type=$("#filter_type").find(".active").data("val");
            filter.select=$("#search_key").val();
            filter.btn=btn;
            return filter;
        }

        $("#filter_type li").click(function(){
            $("#filter_type li").removeClass("active");
            $(this).addClass("active");
            dropHelper.updateParams(0)
        })

        $(".filter-son li").click(function(){
            $(this).parent().addClass("in");
            $(this).parent().attr("style", "max-height: 0px;");
            $(this).parent().parent().removeClass("active");
            var mobile = $("#mobile").val();
            $(this).parent().find("li").removeClass("active");
            $(this).addClass("active");
            dropHelper.updateParams(0)
        })

        $("#search-btn").click(function(){
            ClearFilter();
            dropHelper.updateParams(1)
        })


        function ClearFilter()
        {
            $("#filter_type li").removeClass("active");
            $(".filter-son li").removeClass("active");
            $("#customLever1").find("li").eq(0).addClass("active");
            $("#customLever2").find("li").eq(0).addClass("active");
            $("#customLever3").find("li").eq(0).addClass("active");
            $("#custom-category").find("li").eq(0).addClass("active");
        }

        var dropHelper = {
            mydrop:null,
            params: {
                pageNum: page,
                times: $("#custom-category").find(".active").length > 0 ? $("#custom-category").find(".active").data("val") : 0,
                mobile: mobile,
                khjb: $("#customLever1").find(".active").html(),
                dfzt: $("#customLever2").find(".active").html(),
                gmys: $("#customLever3").find(".active").html(),
                type: $("#filter_type").find(".active").data("val"),
                select: $("#search_key").val(),
                btn: 0,
            },
            updateParams: function (btn) {
                //$("#frameDiv").html('<div id="tbl"></div>');
                $("#tbl").empty();
                dropHelper.params = BuildFilter(btn);
                // 解锁
                mydrop.unlock();
                mydrop.noData(false);
                mydrop.resetload();
            },
            init: function () {
                mydrop=$("#frameDiv").dropload({
                    scrollArea: window,
                    loadDownFn: function (me) {
                        if (dropHelper.params.btn == 0) {
                            dropHelper.UpdateTbl(me)
                        }
                        else {
                            dropHelper.UpdateTblSearch(me);
                        }
                    }
                });
            },
            UpdateTbl: function (me) {
                dropHelper.params.pageNum++;
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/storeOrAcquisitionCar/storeMethod.do",
                    async: true,
                    dataType: 'json',
                    data: dropHelper.params,
                    success: function (data) {
                        $("#spand").html('(' + data.count.all + ')');
                        $("#spanw").html('(' + data.count.three + ')');
                        $("#spanm").html('(' + data.count.seven + ')');
                        $("#spanover").html('(' + data.count.out + ')');
                        dropHelper.loadData(data, me);
                    },
                    error: function () {
                        //me.resetload();
                    }
                });
            },
            UpdateTblSearch: function (me) {
                dropHelper.params.pageNum++;
                // 拼接HTML
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/storeOrAcquisitionCar/storeSearchOrMonth.do",
                    async: true,
                    dataType: 'json',
                    data: dropHelper.params,
                    success: function (data) {
                        dropHelper.loadData(data, me);
                        //me.resetload();
                    },
                    error: function (xhr, type) {
                        //me.resetload();
                    }
                });

            },
            loadData: function (data, me) {
                var recordHtml = ''
                if (data.record.list.length != 0) {
                    for (var i = 0; i < data.record.list.length; i++) {
                        custN = data.record.list[i].customerName;
                        custP = data.record.list[i].customerPhone;
                        custC = data.record.list[i].customerTrack;
                        custI = data.record.list[i].id;
                        custTime = data.record.list[i].inTime;
                        custCar = data.record.list[i].intentionalVehicle;
                        recordHtml += '<a href="/carsokApi/storeOrAcquisitionCar/customerCenterPage.do?mobile='+dropHelper.params.mobile+'&custId='+custI+'">'
                                + '<div class="l-71rem"></div><div class="l-cus-list" data-id="">'
                                + '<div class="l-l cus-list-l">'
                                + '<div class="cus-list-l-inner"><img src="/carsokApi/images/head.png" class="l-cus-headpic"/>'
                                + '<p class="l-cus-tips">' + custN + '</p></div>'
                                + '</div>'
                                + '<div class="l-r cus-list-r">'
                                + '<div class="cus-list-r-carname">'
                                + '<div class="l-l l-car-inTimeTitle">意向车型</div>'
                                + '<div class="l-l l-car-inTime l-car-text">' + custCar + '</div>'
                                + '</div>'
                                + '<div class="l-car-inTimeBox">'
                                + '<div class="l-l l-car-inTimeTitle">进店时间</div>'
                                + '<div class="l-l l-car-inTime">' + custTime + '</div>'
                                + '</div>'
                                + '<div class="l-car-mobileBox">'
                                + '<div class="l-l l-car-inTimeTitle">手机号码</div>'
                                + '<div class="l-l l-car-inTime">' + custP + '</div>'
                                + '</div>'
                                + '<img src="/carsokApi/images/center-icon.png" class="cus-list-arrright"></div>'
                                + '</div></a>';
                    }
                    // 如果没有数据
                } else if (data.record.list.length == 0) {
                    // 锁定
                    me.lockdrop();
                    // 无数据
                    me.noData();
                } else {
                    return;
                }
                $("#tbl").append(recordHtml);
                me.resetload();
            }
        }

        dropHelper.init();

        var otherId = ${otherId};
        if (otherId != null && otherId != "") {
            //从代办事项进入
            var xxly = $("li .active").eq(0).html();
            var khjb = $("li .active").eq(1).html();
            var dfzt = $("li .active").eq(2).html();
            var gmys = $("li .active").eq(3).html();
            $("#tbl").dropload({
                scrollArea: window,
                loadDownFn: function (me) {
                    page++;
                    // 拼接HTML
                    var mobile = $("#mobile").val();
                    var record = '';
                    $.ajax({
                        type: 'post',
                        url: "/carsokApi/customer/selectByRemind.do",
                        async: true,
                        dataType: 'json',
                        data: {
                            "pc": page,
                            "mobile": mobile,
                            "otherId": otherId,
                            "xxly": xxly,
                            "khjb": khjb,
                            "gmys": gmys,
                            "dfzt": dfzt
                        },
                        success: function (data) {
                            loadData(data,me);
                            me.resetload();
                            GuDingBiaoTou();
                        }
                    });

                }

            });
        } else {
            //UpdateTbl();
        }
    });

    var i = 0;

    function deletePushMsg(id) {
        $.ajax({
            type: 'post',
            url: "/carsokApi/handlerCount/deleteHandlerMsg.do",
            dataType: 'json',
            data: {
                "mendianId": id
            },
            success: function (data) {
                //console.log(10)
            }
        });
    }


    //滚动条在Y轴上的滚动距离

    function getScrollTop() {
        var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
        if (document.body) {
            bodyScrollTop = document.body.scrollTop;
        }
        if (document.documentElement) {
            documentScrollTop = document.documentElement.scrollTop;
        }
        scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
        return scrollTop;
    }

    //文档的总高度

    function getScrollHeight() {
        var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
        if (document.body) {
            bodyScrollHeight = document.body.scrollHeight;
        }
        if (document.documentElement) {
            documentScrollHeight = document.documentElement.scrollHeight;
        }
        scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
        return scrollHeight;
    }

    //浏览器视口的高度

    function getWindowHeight() {
        var windowHeight = 0;
        if (document.compatMode == "CSS1Compat") {
            windowHeight = document.documentElement.clientHeight;
        } else {
            windowHeight = document.body.clientHeight;
        }
        return windowHeight;
    }


</script>
</body>
</html>