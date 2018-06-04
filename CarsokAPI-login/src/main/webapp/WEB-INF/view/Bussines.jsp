<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
          name="viewport">
    <title>客户登记表</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/defaultTheme.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>

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
            width: 25%
        }

        .layui-m-layer-footer .layui-m-layercont {
            border-radius: 4px;
        }

        div.pinch-zoom {
            position: relative;
        }

        div.pinch-zoom,
        div.pinch-zoom img {
            max-width: 100%;

            -webkit-user-drag: none;
        }

        tbody {
            background: #fff;
        }
    </style>
</head>
<body class="white-body header-nav-body" id="myBody">
<!-- header start -->
<header class="search border-box">
    <div class="search-wrap border-box">
        <a href="javascript:;" class="search-ico"></a>
        <input class="border-box" id="search_key" type="text" placeholder="电话、姓名、车行名称、地区" style="padding-left:2.6rem;">
        <a href="javascript:;" class="search-delete" onclick="Uton.emptySearch();"></a>
    </div>
    <div class="search-btn" id="search-btn"><a href="javascript:void(0);" onclick="pageModle(0)">搜索</a></div>
</header>
<div class="tab-tit  border-box">
    <ul>
        <li id="liDays" onclick="pageModle(1)">本日
            <c id="spand">(0)</c>
        </li>
        <li id="liWeeks" onclick="pageModle(2)">本周
            <c id="spanw">(0)</c>
        </li>
        <li id="liMonths" onclick="pageModle(3)">本月
            <c id="spanm">(0)</c>
        </li>
        <li id="liChoseMonth" class="tab_select"><input type="month" id="month" placeholder="选择月份" onchange="pageModle(4)"/></li>
    </ul>
    <input type="hidden" value="" id="timeStatus"/>
</div>
<!-- header end -->
<input type="hidden" id="mobile" value=""/>
<section class="border-box">
    <div class="tab-table table-responsive" style="height: auto;">
        <div id="record">
        </div>
        <div class="l-71rem"></div>
    </div>
    <div id="records"></div>
</section>
<div></div>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>
<script type="text/javascript" src="/carsokApi/js/underscore.js"></script>
<script type="text/javascript" src="/carsokApi/js/pinchzoom.js"></script>
<script>
    var page = 0;
    //初加载
    $(function () {
        $("#mobile").val(${mobile});
        // dropload
        $("#records").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                // 拼接HTML
                var mobile = $("#mobile").val();
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/bussinesPage/businessDevelopments.do",
                    dataType: 'json',
                    data: {
                        "mobile": mobile
                    },
                    success: function (data) {
                        $("#spand").html("(" + data.maptr.daytr + ")");
                        $("#spanw").html("(" + data.maptr.weektr + ")");
                        $("#spanm").html("(" + data.maptr.monthtr + ")");


                    }
                });
            }
        });
        pageModle(1);
    });

    //本日1,本周2,本月3,选择月份4,搜索0
    function pageModle(times) {
        $("#timeStatus").val(times);
        if (times == 1) {
            $("#liWeeks").removeClass("active");
            $("#liMonths").removeClass("active");
            $("#liDays").addClass("active");
            $("#search_key").val("");
            $("#month").val("");
            GetDefaultMonth($("#month"));
        } else if (times == 2) {
            $("#liMonths").removeClass("active");
            $("#liDays").removeClass("active");
            $("#liWeeks").addClass("active");
            $("#search_key").val("");
            $("#month").val("");
            GetDefaultMonth($("#month"));
        } else if (times == 3) {
            $("#liWeeks").removeClass("active");
            $("#liDays").removeClass("active");
            $("#liMonths").addClass("active");
            $("#search_key").val("");
            $("#month").val("");
            GetDefaultMonth($("#month"));
        } else if (times == 4) {
            $("#liWeeks").removeClass("active");
            $("#liDays").removeClass("active");
            $("#liMonths").removeClass("active");
            // $("#search_key").val("");
        } else if (times == 0) {
            $("#liWeeks").removeClass("active");
            $("#liDays").removeClass("active");
            $("#liMonths").removeClass("active");
        }
        $("#record").html("");
        var mobile = $("#mobile").val();
        // 页数
        page = 0;
        // 每页展示5个
        // dropload
        $("#tb1").dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                page++;
                me.lockdrop();
                // 拼接HTML
                var record = '';
                $.ajax({
                    type: 'post',
                    url: "/carsokApi/bussinesPage/queryBusinessList.do",
                    dataType: 'json',
                    async: false,
                    data: {
                        "pc": page,
                        "mobile": mobile,
                        "times": $("#timeStatus").val(),
                        "month": $("#month").val(),
                        "selects": $("#search_key").val()
                    },
                    success: function (data) {

                        if (data.record.beanlist.length != 0) {
                            for (var i = 0; i < data.record.beanlist.length; i++) {
                                if (null == data.record.beanlist[i].carIndustry) {
                                    data.record.beanlist[i].carIndustry = ""
                                }
                                if (null == data.record.beanlist[i].personInCharge) {
                                    data.record.beanlist[i].personInCharge = ""
                                }
                                if (null == data.record.beanlist[i].region) {
                                    data.record.beanlist[i].region = ""
                                }
                                if (null == data.record.beanlist[i].address) {
                                    data.record.beanlist[i].address = ""
                                }
                                if (null == data.record.beanlist[i].telephone) {
                                    data.record.beanlist[i].telephone = ""
                                }
                                if (null == data.record.beanlist[i].firstVisittime) {
                                    data.record.beanlist[i].firstVisittime = ""
                                }
                                if (null == data.record.beanlist[i].vehicleScale) {
                                    data.record.beanlist[i].vehicleScale = ""
                                }
                                if (null == data.record.beanlist[i].interestLevel) {
                                    data.record.beanlist[i].interestLevel = ""
                                }
                                if (null == data.record.beanlist[i].empnumber) {
                                    data.record.beanlist[i].empnumber = ""
                                }
                                if (null == data.record.beanlist[i].purchaseBudget) {
                                    data.record.beanlist[i].purchaseBudget = ""
                                }
                                if (null == data.record.beanlist[i].saas) {
                                    data.record.beanlist[i].saas = ""
                                }
                                if (null == data.record.beanlist[i].isdealok) {
                                    data.record.beanlist[i].isdealok = ""
                                }
                                if (null == data.record.beanlist[i].nowCreater) {
                                    data.record.beanlist[i].nowCreater = ""
                                }
                                if (null == data.record.beanlist[i].remarks) {
                                    data.record.beanlist[i].remarks = ""
                                }

                                var num = i + 1 + (page - 1) * 10;
                                var imgsrc;
                                data.record.beanlist[i].shopboorPicture?imgsrc='http://pic.utonw.com/'+data.record.beanlist[i].shopboorPicture:imgsrc="/carsokApi/images/uton.png";
                                var shopwidth;
//                                if(data.record.beanlist[i].vehicleScale&&data.record.beanlist[i].empnumber ){
//                                    shopwidth =   data.record.beanlist[i].vehicleScale + "|" + data.record.beanlist[i].empnumber
//                                }else if(data.record.beanlist[i].vehicleScale&&!data.record.beanlist[i].empnumber){
//                                    shopwidth=data.record.beanlist[i].vehicleScale
//                                }else if(!data.record.beanlist[i].vehicleScale&&data.record.beanlist[i].empnumber){
//                                    shopwidth=data.record.beanlist[i].empnumber
//                                }else if(!data.record.beanlist[i].vehicleScale&&!data.record.beanlist[i].empnumber){
//                                    shopwidth=''
//                                }
                                shopwidth = data.record.beanlist[i].nowCreater;
                                record = '<div class="l-71rem"></div><div class="l-shop-list" data-id='+ data.record.beanlist[i].id+'><div class="l-l shop-list-l"> <div class="shop-list-l-inner"> <img src='+imgsrc+'  class="l-shop-pic"/></div> </div> <div class="l-r shop-list-r"> <div class="l-shop-title">' + data.record.beanlist[i].carIndustry + "(" + data.record.beanlist[i].region + ")" + '</div> <div class="l-shop-width"> <span class="l-shop-width-title">创建人：</span> <span class="l-shop-width-text">' + shopwidth+ '</span> </div> <div class="l-shop-phone"> <span class="l-shop-phone-title">联系电话：</span> <span class="l-shop-phone-text">' + data.record.beanlist[i].telephone + '</span> </div> <div class="l-shop-person"> <span class="l-shop-person-title">负责人：</span> <span class="l-shop-person-text">' + data.record.beanlist[i].personInCharge + ' </span> </div><div class="l-shop-time">' + data.record.beanlist[i].createTime + ' </div> </div> </div>'

                                $("#record").append(record);
                                $('.l-shop-list').click(function () {
                                    var dataid=$(this).attr('data-id');

                                    window.location.href="bussinesPageEdit:" + dataid;

                                });
                                me.unlock()
                            }
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
                    },
                    error: function (xhr, type) {
//                        alert('Ajax error!');
                        // 即使加载出错，也得重置
//                        me.lockdrop();
                        //me.resetload();
                    }
                });
            }

        });
    }

    //传递参数
    function cc(id) {
        $.ajax({
            type: 'post',
            url: "/carsokApi/bussinesPage/Pictures.do",
            dataType: 'json',
            data: {
                "id": id,
            },
            success: function (data) {
                if (data.flage == "1") {
                    //提示
                    layer.open({
                        content: data.msg
                        , skin: 'msg'
                        , time: 2 //2秒后自动关闭
                    });

                } else if (data.flage == "2") {
                    window.location.href = "pic.do?pic=" + data.pictures
                }
            },
            error: function () {
            }
        });
    }

    $("input[type='month']").on("input", function () {
        if ($(this).val().length > 0) {
            $(this).addClass("full");
        }
        else {
            $(this).removeClass("full");
        }
    });

    function GetDefaultMonth(object) {
        if (object.val().length > 0) {
            object.addClass("full");
        }
        else {
            object.removeClass("full");
        }
    }

</script>
</body>
</html>