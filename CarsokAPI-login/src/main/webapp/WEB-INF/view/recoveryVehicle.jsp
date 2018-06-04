<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
          name="viewport">
    <title>客户登记表</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet"/>
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

        nav.car-filter > ul > li {
            width: 33.33%
        }

        .layui-m-layer-footer .layui-m-layercont {
            border-radius: 4px;
        }

        .tab-tit li {
            width: 33.333%;
        }

        tbody {
            background: #fff;
        }
    </style>
</head>
<body class="white-body header-nav-body" style="padding-top:0;">
<div id="margin">
<!-- header start -->
<header class="search border-box">
    <div class="search-wrap border-box"><a href="javascript:;" class="search-ico"></a>
        <input class="border-box" id="search_key" type="text" placeholder="请输入搜索的关键字" style="padding-left:2.6rem;">
        <a href="javascript:;" class="search-delete" onclick="Uton.emptySearch();"></a></div>
    <div class="search-btn" id="search-btn" onclick="selects()">搜索</div>
</header>
<div class="tab-tit  border-box" style="border-bottom:none;">
    <ul>
        <li id="NNN" onclick="selectByDeal(1)">本日新增
            <c id="dealsN">(0)</c>
        </li>
        <li id="YYY" onclick="selectByDeal(2)">本周新增
            <c id="dealsY">(0)</c>
        </li>
        <li id="FFF" onclick="selectByDeal(3)">本月新增
            <c id="dealsF">(0)</c>
        </li>
        <li id="liChoseMonth" onclick="clearActive()" class="tab_select">
            <input type="month" id="month"
                   placeholder="选择月份"
                   onchange="selectMonth()"/>

        </li>
    </ul>
    <input type="hidden" id="deals"/>
</div>
<!-- nav start -->
<nav class="car-filter border-box" style="top:8rem;">
    <ul class="clearfix">
        <li class="l-line4"><a class="car-sel" href="javascript:;" id="cust_xxly">信息来源</a>
            <ul class="filter-son border-box">
                <li data-val="9" class="active" id="screen_allly" onclick="screenMsg(this)">全部车源</li>
                <li data-val="0" id="screen_csappcyzx" onclick="screenMsg(this)">车商APP车源中心</li>
                <li data-val="1" id="screen_qtwlpt" onclick="screenMsg(this)">其他网络平台</li>
                <li data-val="2" id="screen_pyq" onclick="screenMsg(this)">朋友圈</li>
                <li data-val="3" id="screen_xxyjs" onclick="screenMsg(this)">信息员介绍</li>
                <li data-val="4" id="screen_yshc" onclick="screenMsg(this)">友商合车</li>
                <li data-val="5" id="screen_pm" onclick="screenMsg(this)">拍卖</li>
                <li data-val="6" id="screen_zdd" onclick="screenMsg(this)">自到店</li>
                <li data-val="7" id="screen_khzjs" onclick="screenMsg(this)">客户转介绍</li>
                <li data-val="8" id="screen_zh" onclick="screenMsg(this)">置换</li>
            </ul>
        </li>


        <li class="l-line4"><a class="car-sel" href="javascript:;" id="cust_zt">排序</a>
            <ul class="filter-son border-box">
                <li data-val="0" class="active" onclick="screenMsg(this)" id="screen_allpx">默认排序</li>
                <li data-val="1" onclick="screenMsg(this)" id="screen_sell">意向价格最高</li>
                <li data-val="2" onclick="screenMsg(this)" id="screen_notsell">意向价格最低</li>
            </ul>
        </li>

        <li class="l-line4"><a class="car-sel" href="javascript:;" id="cust_px">成交状态</a>
            <ul class="filter-son border-box">
                <li data-val="0" class="active" onclick="screenMsg(this)" id="screen_allzt">全部</li>
                <li data-val="2" onclick="screenMsg(this)" id="screen_max">未成交</li>
                <li data-val="1" onclick="screenMsg(this)" id="screen_min">成交</li>
                <li data-val="3" onclick="screenMsg(this)" id="screen_forgive">放弃</li>
            </ul>
        </li>

    </ul>
</nav>
<!-- nav end -->
<input type="hidden" id="mobile"/>
<section class=" border-box">
    <div class="tab-table table-responsive" id="custom-con" style="height: auto;">
        <div id="record"></div>
        <div id="records"></div>
    </div>
</section>
<div></div>
</div>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/customList.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script src="/carsokApi/js/carList.js" type="text/javascript"></script>
<script>
    $(function () {
        var topHeight = $('.search').outerHeight()+$('.tab-tit').outerHeight()+$('.car-filter').outerHeight() - 9;
        $('#margin').css("marginTop",topHeight);
        $("#mobile").val(${mobile});
        $("#records").html("");
        $("#record").html("");
        var page = 0;
        // 每页展示5个
        // dropload
        var xxly = $("li .active").eq(0).html();
        var ycsj = $("li .active").eq(1).html();
        var px = $("li .active").eq(2).html();
        $("#records").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var mobile = $("#mobile").val();
                var record = '';
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/AcquisitionCar/selectDWMCounts.do",
                    dataType: 'json',
                    data: {
                        "mobile": mobile,

                    },
                    success: function (data) {
                        console.log(data)
                        $("#dealsN").html("(" + data.counts.DayNum + ")");
                        $("#dealsY").html("(" + data.counts.WeekNum + ")");
                        $("#dealsF").html("(" + data.counts.MonthNum + ")");

                        // 锁定
                        me.lockdrop();
                        // 无数据
                        me.noData();

                        // 插入数据到页面，放到最后面
                        // 每次数据插入，必须重置
                        me.resetload();

                    }
                });

            }

        });

        selectByDeal(1);


    });


    function selectMonth() {
        var holder=$('#month').val().substring(0,4)+'年'+$('#month').val().substring(5)+'月'
        $('#month').attr('placeholder',holder)
        $("#deals").val(4);
        $("#FFF").removeClass("active");
        $("#YYY").removeClass("active");
        $("#NNN").removeClass("active");

        $('#search_key').val('');
        $("#mobile").val(${mobile});
        $("#records").html("");
        $("#record").html("");
        var page = 0;
        // 每页展示5个
        // dropload
        var xxly;
        if ($("li .active").eq(0).attr('data-val') == 9) {
            xxly = ''
        } else {
            xxly = $("li .active").eq(0).html()
        }


        var px;

        if ($("li .active").eq(1).attr('data-val') == 0) {
            px = ''
        } else {
            px = $("li .active").eq(1).attr('data-val')
        }


        var cjzt;
        if ($("li .active").eq(2).attr('data-val') == 0) {
            cjzt = ''
        } else {
            cjzt = $("li .active").eq(2).attr('data-val')
        }

        $("#records").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var mobile = $("#mobile").val();
                var record = '';
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/AcquisitionCar/selectListNew.do",
                    dataType: 'json',
                    data: {
                        "type": 4,
                        "pc": page,
                        "mobile": mobile,
                        "xxly": xxly,
                        "px": px,
                        'months': $('#month').val(),
                        'cjzt': cjzt,
                        "selects": $("#search_key").val()
                    },
                    success: function (data) {
                        if (data.record.beanlist.length != 0) {
                            var isDeals = '';
                            var closedcarcontract = '';
                            var evaluatePrice = '';
                            var vin = '';
                            var maintenance = '';
                            for (var i = 0; i < data.record.beanlist.length; i++) {
                                var isDeal;
                                if (data.record.beanlist[i].isDeal === 1) {
                                    isDeal = '成交'
                                }
                                if (data.record.beanlist[i].isDeal === 2) {
                                    isDeal = '未成交'
                                }
                                if (data.record.beanlist[i].isDeal === 3) {
                                    isDeal = '放弃'

                                }
                                // 意向价格
                                var preferPrice;
                                if (data.record.beanlist[i].preferPrice) {
                                    preferPrice = data.record.beanlist[i].preferPrice + '万'
                                    var preferPrice
                                } else {
                                    preferPrice = '- -'
                                }
                                // 成交价格
                                var closeingPrice;
                                if (data.record.beanlist[i].closeingPrice) {
                                    closeingPrice = data.record.beanlist[i].closeingPrice + '万'
                                } else {
                                    closeingPrice = '- -'
                                }

                                var firstTime = data.record.beanlist[i].firstUpTime.substring(0, 10);
                                var consultPrice;
                                data.record.beanlist[i].consultList.length>0 ? consultPrice = data.record.beanlist[i].consultList[0].consultPrice + '万' : consultPrice = '- -';

                                var evaluatePrice;

                                data.record.beanlist[i].evaluatePrice ? evaluatePrice = data.record.beanlist[i].evaluatePrice : evaluatePrice = '未估价';
                                record = "<div class='l-71rem'></div><div class='l-card-wrap' data-id=" + data.record.beanlist[i].id + ">" +
                                    "<div class='l-title-name'><div class='l-intentionalVehicle'>" + data.record.beanlist[i].collectionType + "</div>" +
                                    " <div class='l-customerName'>" + data.record.beanlist[i].customerName + "</div></div>" +
                                    "<div class='l-time-mobile'><div class='l-inTime l-l' ><span>上牌时间：</span><span class='l-inTime-value'>" + firstTime + " </span></div>" +
                                    " <div class='l-customerPhone l-r'>" + data.record.beanlist[i].contentNum + "</div></div>"
                                    + "<div class='l-price-wrap'><div class='l-price-inner'><div class='l-price-cont'><p class='l-align-price'>" + evaluatePrice+ "</p><p class='l-align-text'>车辆估价</p></div><div class='l-price-cont'><p class='l-align-price'>" + preferPrice + "</p><p class='l-align-text'>意向价格</p></div><div class='l-price-cont'><p class='l-align-price'>" + consultPrice + "</p><p class='l-align-text'>第一次给价</p></div><div class='l-price-cont l-price-cont-last'><p class='l-align-price'>" + closeingPrice + "</p><p class='l-align-text'>成交价格</p></div></div>"
                                    + "<div class='l-margin-borderbox'></div>"
                                    + "<div class='l-time-btn'><div class='l-time-btn-inner'><span class='l-rectime-text'>验车时间：</span><span class='l-rectime-value'>" + data.record.beanlist[i].carInspectionTime + "</span></div><button class='l-listbtn'>" + isDeal + "</button><div></div></div>"

                                $("#record").append(record);

                                $('.l-card-wrap').click(function () {
                                    var dataid = $(this).attr('data-id');
                                    location.href = "/carsokApi/AcquisitionCar/selectMsg.do?id=" + dataid
                                })

                            }
                            // 如果没有数据
                        } else if (data.record.beanlist.length == 0) {
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
                    }
                });

            }

        });
    }


    function selectByDeal(status) {
        $("#search_key").val('');
        var month = $('#month').val();
        $("#deals").val(status);
        if (status == 1) {
            $("#NNN").addClass("active");
            $("#YYY").removeClass("active");
            $("#FFF").removeClass("active");
            $("#month").removeClass("active");

        } else if (status == 2) {
            $("#YYY").addClass("active");
            $("#NNN").removeClass("active");
            $("#FFF").removeClass("active");
            $("#month").removeClass("active");
        } else if (status == 3) {
            $("#FFF").addClass("active");
            $("#YYY").removeClass("active");
            $("#NNN").removeClass("active");
            $("#month").removeClass("active");
        } else if (status == 4) {
            $("#month").addClass("active");
            $("#FFF").removeClass("active");
            $("#YYY").removeClass("active");
            $("#NNN").removeClass("active");
        }

        $("#mobile").val(${mobile});
        $("#records").html("");
        $("#record").html("");
        var page = 0;
        // 每页展示5个
        // dropload


        var xxly;
        if ($("li .active").eq(0).attr('data-val') == 9) {
            xxly = ''
        } else {
            xxly = $("li .active").eq(0).html()
        }

        var px;

        if ($("li .active").eq(1).attr('data-val') == 0) {
            px = ''
        } else {
            px = $("li .active").eq(1).attr('data-val')
        }


        var cjzt;
        if ($("li .active").eq(2).attr('data-val') == 0) {
            cjzt = ''
        } else {
            cjzt = $("li .active").eq(2).attr('data-val')
        }

        $("#records").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var mobile = $("#mobile").val();
                var record = '';
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/AcquisitionCar/selectListNew.do",
                    dataType: 'json',
                    data: {
                        "type": status,
                        "pc": page,
                        "mobile": mobile,
                        "xxly": xxly,
                        "px": px,
                        'months': $('#month').val(),
                        'cjzt': cjzt,
                        "selects": $("#search_key").val()
                    },
                    success: function (data) {

                        if (data.record.beanlist.length != 0) {
                            var isDeals = '';
                            var closedcarcontract = '';
                            var evaluatePrice = '';
                            var vin = '';
                            var maintenance = '';
                            for (var i = 0; i < data.record.beanlist.length; i++) {
                                var isDeal;
                                if (data.record.beanlist[i].isDeal === 1) {
                                    isDeal = '成交'
                                } else if (data.record.beanlist[i].isDeal === 2) {
                                    isDeal = '未成交'
                                } else if (data.record.beanlist[i].isDeal === 3) {
                                    isDeal = '放弃'
                                }
                                // 意向价格
                                var preferPrice;
                                if (data.record.beanlist[i].preferPrice) {
                                    preferPrice = data.record.beanlist[i].preferPrice + '万'
                                } else {
                                    preferPrice = '- -'
                                }
                                // 成交价格
                                var closeingPrice;
                                if (data.record.beanlist[i].closeingPrice) {
                                    closeingPrice = data.record.beanlist[i].closeingPrice + '万'
                                } else {
                                    closeingPrice = '- -'
                                }

                                var consultPrice
//                                data.record.beanlist[i].consultPrice ? consultPrice = data.record.beanlist[i].consultPrice + '万' : consultPrice = '- -';
                                data.record.beanlist[i].consultList.length>0 ? consultPrice = data.record.beanlist[i].consultList[0].consultPrice + '万' : consultPrice = '- -';

                                var firstTime = data.record.beanlist[i].firstUpTime.substring(0, 10);
                                var evaluatePrice;

                                data.record.beanlist[i].evaluatePrice ? evaluatePrice = data.record.beanlist[i].evaluatePrice : evaluatePrice = '未估价';
                                record = "<div class='l-71rem'></div><div class='l-card-wrap' data-id=" + data.record.beanlist[i].id + ">" +
                                    "<div class='l-title-name'><div class='l-intentionalVehicle'>" + data.record.beanlist[i].collectionType + "</div>" +
                                    " <div class='l-customerName'>" + data.record.beanlist[i].customerName + "</div></div>" +
                                    "<div class='l-time-mobile'><div class='l-inTime l-l' ><span>上牌时间：</span><span class='l-inTime-value'>" + firstTime + " </span></div>" +
                                    " <div class='l-customerPhone l-r'>" + data.record.beanlist[i].contentNum + "</div></div>"
                                    + "<div class='l-price-wrap'><div class='l-price-inner'><div class='l-price-cont'><p class='l-align-price'>" + evaluatePrice + "</p><p class='l-align-text'>车辆估价</p></div><div class='l-price-cont'><p class='l-align-price'>" + preferPrice + "</p><p class='l-align-text'>意向价格</p></div><div class='l-price-cont'><p class='l-align-price'>" + consultPrice + "</p><p class='l-align-text'>第一次给价</p></div><div class='l-price-cont l-price-cont-last'><p class='l-align-price'>" + closeingPrice + "</p><p class='l-align-text'>成交价格</p></div></div>"
                                    + "<div class='l-margin-borderbox'></div>"
                                    + "<div class='l-time-btn'><div class='l-time-btn-inner'><span class='l-rectime-text'>验车时间：</span><span class='l-rectime-value'>" + data.record.beanlist[i].carInspectionTime + "</span></div><button class='l-listbtn'>" + isDeal + "</button><div></div></div>";

                                $("#record").append(record);
                                $('.l-card-wrap').click(function () {
                                    var dataid = $(this).attr('data-id')
                                    location.href = "/carsokApi/AcquisitionCar/selectMsg.do?id=" + dataid
                                })
                            }
                            // 如果没有数据
                        } else if (data.record.beanlist.length == 0) {
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
                    }
                });

            }

        });
    }

    function screenMsg(msg) {
        $('#search_key').val('')
        var month = $('#month').val();
        $("#record").html("");
        $("#records").html("");
        $(msg).parent().addClass("in");
        $(msg).parent().attr("style", "max-height: 0px;");
        $(msg).parent().parent().removeClass("active");
        var mobile = $("#mobile").val();
        $(msg).parent().find("li").removeClass("active");
        var ids = $(msg).attr("id");
        $("#" + ids).addClass("active");
        var xxly;
        if ($("li .active").eq(0).attr('data-val') == 9) {
            xxly = ''
        } else {
            xxly = $("li .active").eq(0).html()
        }


        var px;

        if ($("li .active").eq(1).attr('data-val') == 0) {
            px = ''
        } else {
            px = $("li .active").eq(1).attr('data-val')
        }


        var cjzt;
        if ($("li .active").eq(2).attr('data-val') == 0) {
            cjzt = ''
        } else {
            cjzt = $("li .active").eq(2).attr('data-val')
        }
        var page = 0;
        var deals = $("#deals").val();
        $("#records").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var record = '';
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/AcquisitionCar/selectListNew.do",
                    dataType: 'json',
                    data: {
                        "type": deals,
                        "pc": page,
                        "mobile": mobile,
                        "xxly": xxly,
                        "px": px,
                        'months': $('#month').val(),
                        'cjzt': cjzt,
                        "selects": $("#search_key").val()
                    },
                    success: function (data) {

                        if (data.record.beanlist.length != 0) {
                            var isDeals = '';
                            var closedcarcontract = '';
                            var evaluatePrice = '';
                            var vin = '';
                            var maintenance = '';
                            for (var i = 0; i < data.record.beanlist.length; i++) {
                                var isDeal;
                                if (data.record.beanlist[i].isDeal === 1) {
                                    isDeal = '成交'
                                } else if (data.record.beanlist[i].isDeal === 2) {
                                    isDeal = '未成交'
                                } else if (data.record.beanlist[i].isDeal === 3) {
                                    isDeal = '放弃'

                                }
                                // 意向价格
                                var preferPrice;
                                if (data.record.beanlist[i].preferPrice) {
                                    preferPrice = data.record.beanlist[i].preferPrice + '万'
                                } else {
                                    preferPrice = '- -'
                                }
                                // 成交价格
                                var closeingPrice;
                                if (data.record.beanlist[i].closeingPrice) {
                                    closeingPrice = data.record.beanlist[i].closeingPrice + '万'
                                } else {
                                    closeingPrice = '- -'
                                }

                                var consultPrice;
//                                data.record.beanlist[i].consultPrice ? consultPrice = data.record.beanlist[i].consultPrice + '万' : consultPrice = '- -';
                                data.record.beanlist[i].consultList.length>0 ? consultPrice = data.record.beanlist[i].consultList[0].consultPrice + '万' : consultPrice = '- -';

                                var firstTime = data.record.beanlist[i].firstUpTime.substring(0, 10);

                                var evaluatePrice;

                                data.record.beanlist[i].evaluatePrice ? evaluatePrice = data.record.beanlist[i].evaluatePrice : evaluatePrice = '未估价';
//
                                record = "<div class='l-71rem'></div><div class='l-card-wrap' data-id=" + data.record.beanlist[i].id + ">" +
                                    "<div class='l-title-name'><div class='l-intentionalVehicle'>" + data.record.beanlist[i].collectionType + "</div>" +
                                    " <div class='l-customerName'>" + data.record.beanlist[i].customerName + "</div></div>" +
                                    "<div class='l-time-mobile'><div class='l-inTime l-l' ><span>上牌时间：</span><span class='l-inTime-value'>" + firstTime + " </span></div>" +
                                    " <div class='l-customerPhone l-r'>" + data.record.beanlist[i].contentNum + "</div></div>"
                                    + "<div class='l-price-wrap'><div class='l-price-inner'><div class='l-price-cont'><p class='l-align-price'>" + evaluatePrice+ "</p><p class='l-align-text'>车辆估价</p></div><div class='l-price-cont'><p class='l-align-price'>" + preferPrice + "</p><p class='l-align-text'>意向价格</p></div><div class='l-price-cont'><p class='l-align-price'>" + consultPrice + "</p><p class='l-align-text'>第一次给价</p></div><div class='l-price-cont l-price-cont-last'><p class='l-align-price'>" + closeingPrice + "</p><p class='l-align-text'>成交价格</p></div></div>"
                                    + "<div class='l-margin-borderbox'></div>"
                                    + "<div class='l-time-btn'><div class='l-time-btn-inner'><span class='l-rectime-text'>验车时间：</span><span class='l-rectime-value'>" + data.record.beanlist[i].carInspectionTime + "</span></div><button class='l-listbtn'>" + isDeal + "</button><div></div></div>";

                                $("#record").append(record);
                                $('.l-card-wrap').click(function () {
                                    var dataid = $(this).attr('data-id')
                                    location.href = "/carsokApi/AcquisitionCar/selectMsg.do?id=" + dataid
                                })
                            }
                            // 如果没有数据
                        } else if (data.record.beanlist.length == 0) {
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

                    }
                });

            }

        });
    }

    function selects() {

        clearActive();
        $("#mobile").val(${mobile});
        $("#records").html("");
        $("#record").html("");
        var page = 0;
        // 每页展示5个
        // dropload
        var xxly = $("li .active").eq(0).html();
        var ycsj = $("li .active").eq(1).html();
        var px = $("li .active").eq(2).html();
        $(window).unbind();
        $("#records").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var mobile = $("#mobile").val();
                var status = 10;
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/AcquisitionCar/selectListNew.do",
                    dataType: 'json',
                    data: {
                        'type': 0,
                        "pc": page,
                        "mobile": mobile,
                        "selects": $("#search_key").val(),
                        'months':$('#month').val()
                    },
                    success: function (data) {
                        if (data.record.beanlist.length != 0) {
                            var isDeals = '';
                            var closedcarcontract = '';
                            var evaluatePrice = '';
                            var vin = '';
                            var maintenance = '';
                            var record = '';
                            for (var i = 0; i < data.record.beanlist.length; i++) {
                                if (data.record.beanlist[i].isDeal == 1) {
                                    isDeals = '是';
                                } else if (data.record.beanlist[i].isDeal == 2) {
                                    isDeals = '否';
                                } else if (data.record.beanlist[i].isDeal == 3) {
                                    isDeals = "放弃";
                                }
                                if (data.record.beanlist[i].closedcarcontract == null || data.record.beanlist[i].closedcarcontract == '') {
                                    closedcarcontract = "未添加";
                                } else if (data.record.beanlist[i].closedcarcontract != null) {
                                    closedcarcontract = "已添加";
                                }
                                if (data.record.beanlist[i].evaluatePrice == null || data.record.beanlist[i].evaluatePrice == '') {
                                    evaluatePrice = '未估价';
                                } else if (data.record.beanlist[i].evaluatePrice != null) {
                                    evaluatePrice = data.record.beanlist[i].evaluatePrice;
                                }
                                if (data.record.beanlist[i].vin == null || data.record.beanlist[i].vin == '') {
                                    vin = '无';
                                }
                                else if (data.record.beanlist[i].vin != null) {
                                    vin = data.record.beanlist[i].vin;
                                }
                                if (data.record.beanlist[i].maintenance == null || data.record.beanlist[i].maintenance == '') {
                                    maintenance = '未查询'
                                } else if (data.record.beanlist[i].maintenance != null) {
                                    maintenance = '已查询'
                                }

                                var isDeal;
                                if (data.record.beanlist[i].isDeal === 1) {
                                    isDeal = '成交'
                                } else if (data.record.beanlist[i].isDeal === 2) {
                                    isDeal = '未成交'
                                } else if (data.record.beanlist[i].isDeal === 3) {
                                    isDeal = '放弃'

                                }
                                // 意向价格
                                var preferPrice;
                                if (data.record.beanlist[i].preferPrice) {
                                    preferPrice = data.record.beanlist[i].preferPrice + '万'
                                    console.log(data.record.beanlist[i].preferPrice)
                                } else {
                                    preferPrice = '- -'
                                }
                                // 成交价格
                                var closeingPrice;
                                if (data.record.beanlist[i].closeingPrice) {
                                    closeingPrice = data.record.beanlist[i].closeingPrice + '万'
                                } else {
                                    closeingPrice = '- -'
                                }
                                var firstTime = data.record.beanlist[i].firstUpTime.substring(0, 10);
                                var consultPrice;
//                                data.record.beanlist[i].consultPrice ? consultPrice = data.record.beanlist[i].consultPrice + '万' : consultPrice = '- -';
                                data.record.beanlist[i].consultList.length>0 ? consultPrice = data.record.beanlist[i].consultList[0].consultPrice + '万' : consultPrice = '- -';

                                var firstTime = data.record.beanlist[i].firstUpTime.substring(0, 10);
                                var evaluatePrice;

                                data.record.beanlist[i].evaluatePrice ? evaluatePrice = data.record.beanlist[i].evaluatePrice : evaluatePrice = '未估价';
                                record += "<div class='l-71rem'></div>" +
                                    "<div class='l-card-wrap' data-id=" + data.record.beanlist[i].id + ">" +
                                    "<div class='l-title-name'>" +
                                    "<div class='l-intentionalVehicle'>" + data.record.beanlist[i].collectionType + "</div>" +
                                    " <div class='l-customerName'>" + data.record.beanlist[i].customerName + "</div>" +
                                    "</div>" +
                                    "<div class='l-time-mobile'>" +
                                    "<div class='l-inTime l-l' ><span>上牌时间：</span><span class='l-inTime-value'>" + firstTime + " </span></div>" +
                                    " <div class='l-customerPhone l-r'>" + data.record.beanlist[i].contentNum + "</div>" +
                                    "</div>"
                                    + "<div class='l-price-wrap'>" +
                                    "<div class='l-price-inner'>" +
                                    "<div class='l-price-cont'><p class='l-align-price'>" + evaluatePrice + "</p><p class='l-align-text'>车辆估价</p></div>" +
                                    "<div class='l-price-cont'><p class='l-align-price'>" + preferPrice + "</p><p class='l-align-text'>意向价格</p></div>" +
                                    "<div class='l-price-cont'><p class='l-align-price'>" + consultPrice + "</p><p class='l-align-text'>第一次给价</p></div>" +
                                    "<div class='l-price-cont l-price-cont-last'><p class='l-align-price'>" + closeingPrice + "</p><p class='l-align-text'>成交价格</p></div>" +
                                    "</div>"
                                    + "<div class='l-margin-borderbox'></div>"
                                    + "<div class='l-time-btn'>" +
                                    "<div class='l-time-btn-inner'><span class='l-rectime-text'>验车时间：</span><span class='l-rectime-value'>" + data.record.beanlist[i].carInspectionTime + "</span></div>" +
                                    "<button class='l-listbtn'>" + isDeal + "</button>" +
                                    "</div></div>" +
                                    "</div>"
                            }
                            if(page == 1){
                                $("#record").html(record);
                            }else {
                                $("#record").append(record);
                            }
                            $('.l-card-wrap').click(function () {
                                var dataid = $(this).attr('data-id')
                                location.href = "/carsokApi/AcquisitionCar/selectMsg.do?id=" + dataid
                            })
                            // 如果没有数据
                        }
                        if (data.record.beanlist.length == 0) {
                            // 锁定
                            me.lockdrop();
                            // 无数据
                            me.noData();
                        }
                        // 插入数据到页面，放到最后面
                        // 每次数据插入，必须重置
                        me.resetload();

                    }
                });

            }

        });
    }

    function deletePushMsg(id) {
        $.ajax({
            type: 'post',
            url: "/carsokApi/handlerCount/deleteHandlerMsg.do",
            dataType: 'json',
            data: {
                "shoucheId": id
            },
            success: function (data) {

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

    function clearActive() {
        $("#NNN").removeClass("active");
        $("#YYY").removeClass("active");
        $("#FFF").removeClass("active");
    }


</script>
</body>
</html>