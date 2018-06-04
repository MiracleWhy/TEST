<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
          name="viewport">
    <title>消息详情</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
    <style>
        .car-name {
            height: 6.2rem;
        }

        .redBorderBox {
            height: 1.4rem;
            line-height: 1.4rem;
            text-align: center;
            width: 1.4rem;
            color: #ff8725;
            border: 1px #ff8725 solid;
            display: inline-block;
            vertical-align: top;
            border-radius: 3px;
            margin: auto;
            margin-right: 7px;
            margin-left: 0;

        }

        .carnameBox {
            height: 2.5rem;
            line-height: 2.5rem;
        }

        .color333 {
            color: #333;
        }

        .car-area {
            height: 1.4rem;
            line-height: 1.4rem;
            text-align: center;
            color: #ff8725;
            border: 1px #ff8725 solid;
            display: inline-block;
            padding: 0 0.53rem;
            border-radius: 3px;
        }

        .textOverFlow {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            display: inline-block;
        }

        .timeBox {
            height: 2.5rem;
            line-height: 2.5rem;
        }

        .timeBox span {
            vertical-align: middle;
        }

        .color999 {
            color: #999999;
        }

        .car-source-header {
            height: 3.2rem;
            line-height: 3.2rem;
            background: #fff;
            color: #333;
            font-size: 1rem;
            text-align: center;
        }

        .hasborder {
            border-bottom: 1px #e6e6e6 solid;
        }

        .car-mes {
            padding: 0;
            margin-bottom: .71rem;
        }

        .hasPad {
            padding: 0 1rem;

        }

        .hasPadLeft {
            padding-left: 1rem !important;
        }

        .logo-img {
            float: left;
            display: flex;
        }

        .logo-img img {
            height: 2.8rem;
            width: 2.8rem;
            margin: auto;
        }

        .l-carBox .top {
            height: 4.4rem;
        }

        .l-carBox .bottomL {
            height: 2.75rem;
            line-height: 2.75rem;
            color: #666666;
        }

        .carNameBoxL {
            height: 2.25rem;
            line-height: 2.25rem;
            display: flex;
        }

        .clearBoth {
            clear: both;
            /*overflow: hidden;*/
        }

        .color94 {
            color: #666 !important;
        }

        .colorchengse {
            color: #ff8725;
        }

        .car-mes li {
            border-bottom: 1px #e6e6e6 solid;
        }
    </style>
</head>

<body>
<div class="car-source-header hasborder" style="margin-top:.71rem ">
    报价信息
</div>
<div class="custom-mes car-mes ">
    <div class="timeBox hasborder hasPad">
        <img style="width: 5%;vertical-align: middle;" src="/carsokApi/images/timeicon2.png">
        <span class="car-send-date color999">${cqr.createTime}</span>

    </div>


    <div class="l-carBox">
        <div class="top  hasPad clearBoth">
            <div style="display: flex;height: 100%;float: left;margin-right: 1rem">
                <c:if test="${cqr.brandLogo!= null}">
                    <div class="logo-img"><img src="${cqr.brandLogo}"/></div>
                </c:if>
                <c:if test="${cqr.brandLogo == null}">
                    <div class="logo-img"><img src="/carsokApi/images/logo.png"/></div>
                </c:if>
            </div>
            <div class="hasborder" style="float: left;height: 100%;width: 84%;">
                <div class="carNameBoxL">
                    <span class="redBorderBox">寻</span>
                    <span class="color333 textOverFlow">${cqr.brand} ${cqr.model}</span>
                </div>
                <c:if test="${cqr.carColour!= ''}">
                    <span class="car-area">${cqr.carColour}</span>

                </c:if>
                <c:if test="${cqr.configuration!= ''}">
                    <span class="car-area"> ${cqr.configuration}</span>

                </c:if>

                <c:if test="${cqr.province!= null && cqr.city!=null}">
                    <span class="car-area">卖${cqr.province} ${cqr.city}</span>

                </c:if>

            </div>


        </div>
        <p class="bottomL hasPad">已有${cqr.quoteCount}家车商参与报价 </p>

        <%----%>
    </div>

</div>
<div class="car-mes">
    <ul>
        <li class="clearfix hasPadLeft"><span class="color94">报价价格</span>
            <div class="count news_list color333">${cqr.intentionalPrice} 万</div>
        </li>
        <li class="clearfix hasPadLeft"><span class="color94">姓名</span>
            <div class="count news_list color333">${cqr.name}</div>
        </li>
        <li class="clearfix hasPadLeft"><span class="color94">联系电话</span>
            <div class="count news_list color333"><a href="tel:${cqr.mobile}" class="colorchengse">${cqr.mobile}</a>
            </div>
        </li>
        <li class="clearfix hasPadLeft"><span class="color94">备注</span>
            <div class="count news_list color333 textOverFlow" style="max-width: 60%;">${cqr.remark}</div>
        </li>
    </ul>
</div>
<%--</div>--%>
</body>
</html>
