<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
    <title>市场人员离职转移</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <style>
        .layui-m-layerbtn span[yes] {
            color: #e29000;
        }
        .layui-m-layer0 .layui-m-layerchild{ max-height:500px; overflow-y:auto;}
        .layui-m-layercont{ padding: 15px; text-align: left; ;}
        nav.car-filter > ul > li{ width:33.33%}
        .layui-m-layer-footer .layui-m-layercont{ border-radius:4px; }
        body{overflow:hidden;position: fixed;}
        tbody{ background:#fff;}

    </style>
</head>
<body class="white-body header-body" >
<!-- header start -->
<header class="search border-box">
    <div class="search-wrap border-box">
        <a href="javascript:;" class="search-ico"></a>
        <input class="border-box" id="search_key" type="text" placeholder="请输入待转移市场人员姓名" style="padding-left:2.6rem;">
        <a href="javascript:" class="search-delete" onclick="emptySearch()" ></a>
    </div>
    <div class="search-btn" id="search-btn" onclick="select()">搜索</div>
</header>
<section class="user-list">

    <div style=" overflow-y:auto; overflow-x:auto; height: 530px;">
        <%--<c:forEach items="${list}" var="node">--%>
        <ul>
            <%--<li ><c id="spands">(0)</c></li>--%>
            <li><c id="spand">(0)</c></li>
        </ul>
        <%--</c:forEach>--%>
    </div>
</section>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>


</body>
<script>
    $(function () {

        window.onscroll = function(){
            if(getScrollTop() + getWindowHeight() == getScrollHeight()){
                //alert("已经到最底部了！!");
                window.scrollTo(0,0);// 无动画版本回到顶部
            }
        };
        // 页数
        // 每页展示5个
        // 拼接HTML
        var mobile = ${mobile};
        $.ajax({
            type: 'GET',
            url: "/carsokApi/bussinesPage/searchserList.do",
            dataType: 'json',
            data: {
                "mobile": mobile,
            },
            success: function (data) {
                var dam=data;
                console.log(data);
                var html = '';
                for(var i=0;i<data.length;i++){
                    var name=data[i].name;
                    var id=data[i].id;
                    //alert(data[i].name);
                    html += '<li>'+'<a onclick="selec(\'' + data[i].id + '\')" >' + data[i].name + '</a>'+'</li>';
                }
                $("#spand").html(html);


                var ma=data;

                // 锁定

                // 无数据


            }
        });
    });

    function emptySearch(){
        document.getElementById("search_key").valueOf().value=null;
    }
    function  select() {
        var mobile = ${mobile};
        var name=document.getElementById("search_key").valueOf().value;
        $.ajax({
            type: 'GET',
            url: "/carsokApi/bussinesPage/searchserList.do",
            dataType: 'json',
            data: {
                "userLeaveName": name,
                "mobile": mobile,
            },
            success: function (data) {
                var dam=data;
                var html = '';
                for(var i=0;i<data.length;i++){
                    html += '<li>'+'<a onclick="selec(\'' + data[i].id + '\')" >' + data[i].name + '</a>'+'</li>';
                }
                $("#spand").html(html);
            }
        });

    }
    function selec(id){

        var mobile = ${mobile};

        $.ajax({
            type: 'GET',
            url: "/carsokApi/bussinesPage/jumpchange.do",
            dataType: 'json',
            data: {
                "mobile": mobile,
                "id": id
            },
            success: function (data) {
                var dam=data;
                window.location.href = "jump.do?id=" + data+"&&mobile="+mobile
            }
        });
    }
</script>
</html>