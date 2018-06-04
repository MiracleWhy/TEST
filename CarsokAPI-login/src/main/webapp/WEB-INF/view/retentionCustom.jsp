<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
          name="viewport">
    <title>保有客户管理</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
</head>
<body class="white-body header-body" style="padding-top: 12rem">
<!-- header start -->
<header class="search border-box">
    <div class="search-wrap border-box"><a href="javascript:;" class="search-ico"></a>
        <input class="border-box" id="search_key" type="text" placeholder="电话 / 姓名 / 车系" style="padding-left:2.6rem;">
        <a href="javascript:;" class="search-delete" onclick="Uton.emptySearch();"></a></div>
    <div class="search-btn" data-id="0" id="searchBtn" onclick="selectByDate(0)">搜索</div>

</header>
<div class=" l-tab-title" id="shop-box">
    <ul class="tabClick">
        <li class="active timeSelect" data-id="1">全部<span id="dayC">(0)</span></li>
        <li class="timeSelect" id="weekCC" data-id="2">3日<span id="weekC">(0)</span></li>
        <li class="timeSelect" id="weekMM" data-id="3">7日<span id="monthC">(0)</span></li>
        <li class="timeSelect" id="weekOver" data-id="4">过期<span id="overC">(0)</span></li>
    </ul>
</div>
<input type="hidden" id="mobile" value=""/>
<!-- nav end -->
<nav class="car-filter border-box" style="top:8rem;">
    <ul class="clearfix">
        <li class="l-line3"><a class="car-sel" href="javascript:;" id="cust_xxly">待完善</a>
            <ul class="filter-son border-box" id="status1">
                <li data-val=""  id="screen_all">全部</li>
                <li data-val="1" class="active" id="screen_allly">待完善</li>
                <li data-val="2" id="screen_csappcyzx">已完善</li>
            </ul>
        </li>


        <li class="l-line3"><a class="car-sel" href="javascript:;" id="cust_zt">时间筛选</a>
            <ul class="filter-son border-box" id="status2">
                <li data-val=""  class="active">全部</li>

                <li data-val="1" id="screen_allpx">本日</li>
                <li data-val="2" id="screen_sell">本周</li>
                <li data-val="3" id="screen_notsell">本月</li>
            </ul>
        </li>

        <li class="l-line3"><a class="car-sel" href="javascript:;" id="cust_px">购买状态</a>
            <ul class="filter-son border-box" id="status3">
                <li data-val="" class="active">全部</li>
                <li data-val="3" id="screen_allzt">首次购买</li>
                <li data-val="4" id="screen_max">首次置换</li>
                <li data-val="5" id="screen_min">复购</li>
                <li data-val="7" id="screen_forgive">置购</li>
            </ul>
        </li>

    </ul>
</nav>
<input id="compeleteStatus" type="hidden" value="1"/>
<input id="dateStatus" type="hidden"/>
<input id="buyStatus" type="hidden"/>
<input id="type" type="hidden" value="1"/>
<section class="border-box">


    <div class="tab-table table-responsive" style="height: auto;">

        <div class="r_customList" id="record" style="padding: 0;">

        </div>


    </div>
    <div id="records">

    </div>

</section>

<div></div>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/customList.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script src="/carsokApi/js/carList.js" type="text/javascript"></script>
<script src="/carsokApi/js/jquery.fixedheadertable.min.js" type="text/javascript" charset="utf-8"></script>


<script charset="utf-8">
    $(function () {
        var compeleteStatus = 1;
        var dateStatus = "";
        var buyStatus = "";
        var type = 1;
        $('.tabClick').eq(0).find('li').click(function () {
            type = $(this).attr('data-id');
            $("#type").val(type)

        });

        $('#status1').find('li').click(function () {
            $('#compeleteStatus').val($(this).attr('data-val'));
            $(this).parent().prev().text($(this).text());
            $(this).parent().addClass("in");
            $(this).parent().attr("style", "max-height: 0px;");
            $(this).parent().parent().removeClass("active");
            var mobile = $("#mobile").val();
            $(this).addClass("active").siblings().removeClass("active");


            compeleteStatus = $(this).attr('data-val');
            selectByDate()
        });
        $('#status2').find('li').click(function () {
            $('#dateStatus').val($(this).attr('data-val'));
            $(this).parent().addClass("in");
            $(this).parent().attr("style", "max-height: 0px;");
            $(this).parent().parent().removeClass("active");
            var mobile = $("#mobile").val();
            $(this).addClass("active").siblings().removeClass("active");


            dateStatus = $(this).attr('data-val');
            selectByDate()
        });
        $('#status3').find('li').click(function () {
            $('#buyStatus').val($(this).attr('data-val'));

            $(this).parent().addClass("in");
            $(this).parent().attr("style", "max-height: 0px;");
            $(this).parent().parent().removeClass("active");
            var mobile = $("#mobile").val();
            $(this).addClass("active").siblings().removeClass("active");

            buyStatus = $(this).attr('data-val');
            selectByDate()
        });

        $('.timeSelect').click(function () {
            selectByDate()

        });

        $("#mobile").val(${mobile});
        var mobile = $("#mobile").val();

        // 页数
        var page = 0;
        // 每页展示5个
        // dropload
        var otherId = ${otherId};
//        if(otherId != null && otherId != ""){

        $("#records").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                var record = '';
//                $.ajax({
//                    type: 'post',
//                    url: "/carsokApi/TenureCustomer/selectCustCounts.do",
//                    dataType: 'json',
//                    data: {
//                        "mobile": mobile,
//                    },
//                    success: function (data) {
//                        $("#dayC").html("(" + data.counts.allNum + ")");
//                        $("#weekC").html("(" + data.counts.threeNum + ")");
//                        $("#monthC").html("(" + data.counts.seveNum + ")");
//                        $("#overC").html("(" + data.counts.outDateNum + ")");
//
//
//                        // 锁定
//                        me.lockdrop();
//                        // 无数据
//                        me.noData();
//
//                        // 插入数据到页面，放到最后面
//                        // 每次数据插入，必须重置
//                        me.resetload();
//                    }
//                });
                $('#compeleteStatus').val(1);
                selectByDate()
            }

        });

    });

    var windowWidth = document.body.clientWidth; //window 宽度;
    var wrap = document.getElementById('shop-box');
    var tabClick = wrap.querySelectorAll('.tabClick')[0];
    var tabLi = tabClick.getElementsByTagName('li');
    var lineBorder = wrap.querySelectorAll('.lineBorder')[0];
    var init = {
        lineAnme: function (obj, stance) {
            obj.style.webkitTransform = 'translate3d(' + stance + 'px,0,0)';
            obj.style.transform = 'translate3d(' + stance + 'px,0,0)';
            obj.style.webkitTransition = 'all 0.1s ease-in-out';
            obj.style.transition = 'all 0.1s ease-in-out';
        }
    };

    function selectByDate(isSearch) {
        var searchKey;
        var status = $('#type').val();
        var compeleteStatus = $('#compeleteStatus').val();
        var dateStatus = $('#dateStatus').val();
        var buyStatus = $('#buyStatus').val();
        if (isSearch === 0) {
            searchKey = $("#search_key").val();
            status=0;
            $(".tabClick li").removeClass("active");

        } else {
            $("#search_key").val("");
            searchKey = '';
            var index = status - 1;
            $(".tabClick li").removeClass("active");
            $(".tabClick li").eq(index).addClass("active");
        }

        $("#record").html("");
        $("#records").html("");
        //alert(status);
        var page = 0;
        // 每页展示5个
        // dropload


        $("#records").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                // 拼接HTML
                var mobile = $("#mobile").val();
                var times = status;
                var record = '';
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/TenureCustomer/selectListNew.do",
                    dataType: 'json',
                    data: {
                        "page": page,
                        "mobile": mobile,
                        "type":  status,
                        "compeleteStatus":  $('#compeleteStatus').val(),
                        "dateStatus":$('#dateStatus').val(),
                        "buyStatus":  $('#buyStatus').val(),
                        "selects":  $('#search_key').val()



                    },
                    success: function (data) {
                        $("#dayC").html("(" + data.counts.allNum + ")");
                        $("#weekC").html("(" + data.counts.threeNum + ")");
                        $("#monthC").html("(" + data.counts.seveNum + ")");
                        $("#overC").html("(" + data.counts.outDateNum + ")");
                        if (data.record.length != 0) {
                            for (var i = 0; i < data.record.length; i++) {
                                var custMobiles = data.record[i].custPhone;
                                var custNames = data.record[i].custName;
                                if (custMobiles == null || custMobiles == "") {
                                    custMobiles = "(待完善)";
                                }
                                if (custNames == null || custNames == "") {
                                    custNames = "(待完善)";
                                }

                                record = "<div class='l-71rem'></div><a href='/carsokApi/TenureCustomer/tenureDetail.do?mobile=" + $("#mobile").val() + "&id=" + data.record[i].tid + ":" + data.record[i].tcid + "'>" +
                                    "<dl>" +
                                    "<dt>" +
                                    custNames +
                                    "</dt>" +
                                    "<dd>" +
                                    "<p>" + data.record[i].tenureCarname + "</p>" +
                                    "<p><b>售出时间</b><span>" + data.record[i].saleTime.substring(0, 10) + "</span></p>" +
                                    "<p><b>手机号码</b><span>" + custMobiles + "</span></p>" +
                                    "</dd>" +
                                    "</dl>" +
                                    "</a>";

                                $("#record").append(record);
                            }
                            // 如果没有数据
                        } else if (data.record.length == 0) {

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


</script>
</body>
</html>
