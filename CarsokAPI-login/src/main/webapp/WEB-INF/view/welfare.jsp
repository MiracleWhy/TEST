<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
          name="viewport">
    <title>保有客户编辑</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
    <style>
        .layui-m-layerbtn span[yes] {
            color: #e29000;
        }

        .layui-m-layer0 .layui-m-layerchild {
            max-height: 500px;
            overflow-y: auto;
        }

        .layui-m-layer-footer .layui-m-layercont {
            padding: 0;
        }

        .layui-m-layercont {
            padding: 10px;
        }

        .layui-m-layerchild h3 {
            height: 40px;
            line-height: 40px;
            border-bottom: solid 1px #ddd;
        }
    </style>
</head>
<body>

<!-- 车辆信息 start -->
<form id="forms">
    <input hidden name="tenurecarId" id="tenurecarId_hidden"/>

    <div class="custom-mes car-mes ">
        <ul>
            <li class="clearfix"><span>手机号码</span>
                <input id="custPhone" type="text" placeholder="必填" name="custPhone"
                       onkeyup="selectExsit(this)" style="width:30%;">
                <a id="tellphone"
                   style="display:inline-block; width:30px; float:right; margin-top:5px;margin-left: 10px;"><img
                        src="/carsokApi/images/tel-icon.png" style="width:80%;" onclick="makeHref()"/></a>
                <a id="importPhone" style="display:inline-block; width:30px; float:right; margin-top:5px;"><img
                        src="/carsokApi/images/importPhone.png" style="width:80%;" onclick="importPhone()"/></a>
            </li>
            <li class="clearfix"><span>购买状态</span>
                <select id="gmzt" onchange="switchState()" name="purchaseStatus" class="l-select">
                    <option value="3">首次购买</option>
                    <option value="4">首次置换</option>
                    <option value="7">置换</option>
                    <option value="5">复购</option>
                </select>
            </li>
        </ul>
    </div>
    <div class="custom-mes car-mes " style="margin-top:10px;" id="zhToo">
        <h2>置换车辆</h2>
        <div class="r_customList r_customCar" id="records"></div>

    </div>
    <input type="hidden" value="${mobile}"/>
    <div class="welfare-mes car-mes " style="margin-top:10px;">
        <input type="hidden" id="tid" value="" name="tid"/>
        <input type="hidden" id="tcid" value="" name="tcid"/>
        <%--<h2>车辆信息<p class="text-center"--%>
                   <%--style=" color:#999;  line-height:16px; float:right; text-align:right; font-size:1rem;"--%>
                   <%--onclick="showfun();"><img src="/carsokApi/images/question.png" height="16"/> 电话话术提醒 </p></h2>--%>
        <%--<div class="car-desc">--%>
            <%--<div class="clearfix">--%>
                <%--<h3 id="carName">车辆信息加载中...</h3>--%>
                <%--<span class="txt-price"><b id="carPrice">0.00</b> 万</span>--%>
            <%--</div>--%>
            <%--<p id="carVin">VIN：空 | 0公里 | 全款/按揭</p>--%>
        <%--</div>--%>

        <ul>
            <li class="clearfix" style="padding-bottom: 0px;"><span>品牌车系</span>
                <p id="carName">

                </p>
                <%--<input id="carName" type="text" placeholder="选填" name="tenureCarnum"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\-]/g,'')"/>
                <textarea rows="1" id="carName" placeholder="选填" name="tenureCarname" cols="20" style="font-size: 1rem;" ></textarea>
            --%>
            </li>
            <li class="clearfix"><span>车架号(VIN)</span>
                <input id="carVin" type="text" placeholder="选填" name="tenureVin"
                      maxlength="17"/>
            </li>

            <li class="clearfix"><span>出售价格</span>
                <input id="carPrice" type="text"  placeholder="选填" name="tenureCarprice"/>
                <span class="l-right-span">万元</span>
            </li>
            <li class="clearfix"><span>行驶里程</span>
                <input id="carFar" type="text" placeholder="选填" name="carMales"/>
                <span class="l-right-span">公里</span>
            </li>
            <li class="clearfix"><span>车牌号码</span>
                <input id="carNum" type="text" placeholder="选填" name="tenureCarnum"/>
            </li>
            <li class="clearfix"><span>付款方式</span>
                <%--<input id="payFun" type="text" placeholder="选填" name="tenureCarnum"--%>
                       <%--onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\-]/g,'')"/>--%>
                <select class="l-select" id="carPay" name="tenureCartype">
                    <option value="1">全款</option>
                    <option value="2">按揭</option>
                </select>
            </li>
            <li class="clearfix"><span>交强险到期</span>
                <input id="jiaoqiangxian" type="date"  placeholder="必填" class="l-move" name="tenureCompulsory"/>
            </li>
            <li class="clearfix"><span>商业险到期</span>
                <input id="shangyexian" type="date" placeholder="必填" class="l-move" name="tenureBusiness"/>
            </li>
            <li class="clearfix"><span>保养到期</span>
                <input id="baoyang" type="date" placeholder="必填" class="l-move" name="tenureMaintain"/>
            </li>
            <li class="clearfix"><span>质保到期</span>
                <input id="zhibao" type="date" placeholder="必填" class="l-move" name="tenureWarranty"/>
            </li>
        </ul>
    </div>

    <!-- 车辆信息 end -->
    <div class="custom-mes car-mes " style="margin-top:10px;">
        <h2>客户信息</h2>

        <ul>
            <li class="clearfix" style="display:none"><span>销售顾问</span>
                <input id="saledPeople" type="text" value="自动获取"/>
            </li>
            <li class="clearfix"><span>客户姓名</span>
                <input id="custName" type="text" placeholder="必填" name="custName"/>
            </li>

            <li class="clearfix"><span>性别</span>
                <label class="sexlabel">
                    <input id="sexMan" class="sexradio" type="radio" name="custSex" checked value="男"/>
                    <span class="sexRadioInput"></span> 男 </label>
                <label class="sexlabel">
                    <input id="sexWom" class="sexradio" type="radio" name="custSex" value="女"/>
                    <span class="sexRadioInput"></span> 女 </label>
            </li>
            <li class="clearfix"><span>生日</span>
                <input id="custBirthday" type="date" placeholder="选填" name="custBirthday"
                       onchange="sumAge()" />
            </li>
            <li class="clearfix"><span>年龄</span>
                <input id="custAge" type="text" placeholder="选填" name="custAge"
                      />
            </li>
            <%--<li class="clearfix"><span>面访时间</span>--%>
                <%--<input id="custVisittime" type="date" placeholder="选填" name="custVisittime"/>--%>
            <%--</li>--%>
            <%--<li class="clearfix"><span>3日内回访</span>--%>
                <%--<input id="custThreevisit" type="text" placeholder="选填" name="custThreevisit"--%>
                       <%--onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\.]/g,'')"/>--%>
            <%--</li>--%>
            <%--<li class="clearfix"><span>7日内系统回访</span>--%>
                <%--<input id="custSevenvisit" type="text" placeholder="选填" name="custSevenvisit"--%>
                       <%--onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\.]/g,'')"/>--%>
            <%--</li>--%>
            <li class="clearfix"><span>职业</span>
                <select id="custVocation" name="custVocation" class="l-select">
                    <option value="">选填</option>
                    <option value="公务员">公务员</option>
                    <option value="事业单位职员">事业单位职员</option>
                    <option value="公司职员">公司职员</option>
                    <option value="自由职业者">自由职业者</option>
                    <option value="其他">其他</option>
                </select>
            </li>
            <li class="clearfix"><span>购车之前车型</span>
                <input id="custBeforecar" readonly="readonly" type="text" placeholder="选填" name="custBeforecar"
                       onclick="changePage()"/>
            </li>
            <li class="clearfix"><span>备注</span>
                <input id="custRemark" type="text" placeholder="选填" name="custRemark"/>
            </li>
        </ul>
    </div>
    <div class="sale-submit">
        <input id="btnSubmit" class="active" type="button" value="提交"/>
    </div>
</form>

<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js" type="text/javascript"></script>
<script>
    function switchState() {
        var tid = ${tid};
        if ($("#gmzt option:selected").val() == 7) {
            if (tid != null) {
                $("#zhToo").hide();
            } else {
                $("#zhToo").show();
            }
        } else {
            $("#zhToo").hide();
        }
    }

    function selectExsit(obj) {
        var mobile = ${mobile};
        var custPhone = $("#custPhone").val();
        if ($("#custPhone").val().length == 11) {
            $.ajax({
                type: "post",
                url: "/carsokApi/TenureCustomer/selectCustIfExist.do",
                data: {
                    "mobile": mobile,
                    "custPhone": custPhone
                },
                dataType: 'json',
                success: function (data) {
                    $("#tellphone").attr("href", "tel:" + data.tenureMap.custPhone);
                    if (data.tenureMap != "" && data.tenureMap.purchaseStatus != "") {
                        if (data.tenureMap.purchaseStatus == null || data.tenureMap.purchaseStatus == "") {
                            $("#gmzt").val("3");
                        } else {
                            $("#gmzt").val(data.tenureMap.purchaseStatus);
                        }
                    }
                    $("#custName").val(data.tenureMap.custName);
                    $("#custAge").val(data.tenureMap.custAge);
                    $("#custBirthday").val(data.tenureMap.custBirthday == null ? "" : data.tenureMap.custBirthday.substring(0, 10));
                    $("input[type='date']").each(function () {
                        if ($(this).val().length > 0) {
                            $(this).addClass("full");
                        } else {
                            $(this).removeClass("full");
                        }
                    });
                    if (data.tenureMap.custSex == "男") {
                        $("input[type='radio'][id='sexMan']").attr("checked", true);
                        $("input[type='radio'][id='sexWom']").attr("checked", false);
                    } else if (data.tenureMap.custSex == "女") {
                        $("input[type='radio'][id='sexWom']").attr("checked", true);
                        $("input[type='radio'][id='sexMan']").attr("checked", false);
                    }
                    $("#custVocation").val(data.tenureMap.custVocation);
                    $("#custBeforecar").val(data.tenureMap.custBeforecar);
                    $("#custRemark").val(data.tenureMap.custRemark);
                    $("input[type='date']").on("input", function () {
                        if ($(this).val().length > 0) {
                            $(this).addClass("full");
                        } else {
                            $(this).removeClass("full");
                        }
                    });
                    if (data.tenureMap != "") {
                        var record = "";
                        for (var i = 0; i < data.beanlist.length; i++) {
                            var custMobiles = data.beanlist[i].custPhone;
                            var custNames = data.beanlist[i].custName;
                            record += "<div class='ck-zhihuan' >" +
                                "<dl>" +
                                "<dt>" +
                                custNames +
                                "</dt>" +
                                "<dd>" +
                                "<p>" + data.beanlist[i].tenureCarname + "</p>" +
                                "<p><b>售出时间</b><span>" + data.beanlist[i].saleTime.substring(0, 10) + "</span></p>" +
                                "<p><b>手机号码</b><span>" + custMobiles + "</span></p>" +
                                "</dd>" +
                                "</dl>" +
                                "<div class='radio-box'>" +
                                "<label class='label'>" +
                                "<input class='radio' type='radio' name='tenurecarId_radio' value='" + data.beanlist[i].tcid + "'><span class='radioInput '></span>" +
                                "</label>" +
                                "</div>" +
                                "</div>";
                        }
                        $("#records").html(record);
                        //$(".ck-zhihuan .radioInput:first").addClass("radio-active");
                        $(".ck-zhihuan").click(function () {
                            $(this).parent("#records").find(".radioInput").removeClass("radio-active");
                            $(this).parent("#records").find(".radio").attr("checked", false);
                            $(this).find(".radioInput").addClass("radio-active");
                            $(this).find(".radio").attr("checked", true);
                        })
                    }

                }
            });
        }
    }

    $(function () {
        $('.l-move').focus(function () {
            var scrollTop=$(document).scrollTop();
            $(document).scrollTop(scrollTop+2)

        });
        $("#zhToo").hide();
        $("#tid").val(${tid});
        $("#tcid").val(${tcid});
        var mobile = ${mobile};
        var custPhone = $("#custPhone").val();
        $.ajax({
            type: 'post',
            url: "/carsokApi/TenureCustomer/tenureCarPeopleMsg.do",
            dataType: 'json',
            data: {
                "tid": ${tid},
                "tcid":${tcid},
                "mobile":${mobile}
            },
            success: function (data) {
                $("#tellphone").attr("href", "tel:" + data.tenureMap.custPhone);
                if (data.tenureMap != "" && data.tenureMap.purchaseStatus != "") {
                    if (data.tenureMap.purchaseStatus == null || data.tenureMap.purchaseStatus == "") {
                        $("#gmzt").val("3");
                    } else {
                        $("#gmzt").val(data.tenureMap.purchaseStatus);
                    }
                }
                $("#custPhone").val(data.carMap.salePeople);
                $("#jiaoqiangxian").val(data.carMap.tenureCompulsory == null ? "" : data.carMap.tenureCompulsory.substring(0, 10));
                $("#shangyexian").val(data.carMap.tenureBusiness == null ? "" : data.carMap.tenureBusiness.substring(0, 10));
                $("#baoyang").val(data.carMap.tenureMaintain == null ? "" : data.carMap.tenureMaintain.substring(0, 10));
                $("#zhibao").val(data.carMap.tenureWarranty == null ? "" : data.carMap.tenureWarranty.substring(0, 10));
                $("#saledPeople").val(data.carMap.salePeople);
                $("#carVin").val(data.carMap.tenureVin);
                $("#carNum").val(data.carMap.tenureCarnum);
                $("#carName").html(data.carMap.tenureCarname);
                $("#carPrice").val(data.carMap.tenureCarprice);
                $("#carFar").val(data.carMap.carMales);
                $('#carPay').val(data.carMap.tenureCartype);
                var types = "";
                if (data.carMap.tenureCartype == 1) {
                    types = "全款";
                } else if (data.carMap.tenureCartype == 2) {
                    types = "按揭";
                }
                $("#custName").val(data.tenureMap.custName);
                $("#custAge").val(data.tenureMap.custAge);
                $("#custBirthday").val(data.tenureMap.custBirthday == null ? "" : data.tenureMap.custBirthday.substring(0, 10));
//                $("#custVisittime").val(data.tenureMap.custVisittime == null ? "" : data.tenureMap.custVisittime.substring(0, 10));
                $("input[type='date']").each(function () {
                    if ($(this).val().length > 0) {

                        $(this).addClass("full");
                    }
                    else {
                        $(this).removeClass("full");
                    }
                });
                $("#carVin").html("VIN：" + data.carMap.tenureVin + "   |   " + data.carMap.carMales + "公里" + "      |   " + types);
                if (data.tenureMap.custSex == "男") {
                    $("input[type='radio'][id='sexMan']").attr("checked", true);
                    $("input[type='radio'][id='sexWom']").attr("checked", false);
                } else if (data.tenureMap.custSex == "女") {
                    $("input[type='radio'][id='sexWom']").attr("checked", true);
                    $("input[type='radio'][id='sexMan']").attr("checked", false);
                }
//                $("#custThreevisit").val(data.tenureMap.custThreevisit);
//                $("#custSevenvisit").val(data.tenureMap.custSevenvisit);
                $("#custVocation").val(data.tenureMap.custVocation);
                $("#custBeforecar").val(data.tenureMap.custBeforecar);
                $("#custRemark").val(data.tenureMap.custRemark);
                $("input[type='date']").on("change", function () {
                    if ($(this).val().length > 0) {
                        $(this).addClass("full");
                    } else {
                        $(this).removeClass("full");
                    }
                });
            }
        });

        function _alert(mes) {
            layer.open({
                content: mes
                , skin: 'msg'
                , time: 2 //2秒后自动关闭
            });
        }


        $("#btnSubmit").click(function () {
            $("#tenurecarId_hidden").val($("input[name='tenurecarId_radio'][checked]").val());
//            if ($("#carNum").val() == null || $("#carNum").val() == "") {
//                _alert("车牌号码不能为空")
//                return;
//            } else
            if ($("#jiaoqiangxian").val() == null || $("#jiaoqiangxian").val() == "") {
                _alert("交强险到期日不能为空");
                return;
            } else if ($("#shangyexian").val() == null || $("#shangyexian").val() == "") {
                _alert("商业险到期日不能为空");
                return;
            } else if ($("#baoyang").val() == null || $("#baoyang").val() == "") {
                _alert("保养到期日不能为空");
                return;
            } else if ($("#zhibao").val() == null || $("#zhibao").val() == "") {
                _alert("质保到期日不能为空");
                return;
            } else if ($("#custName").val() == null || $("#custName").val() == "") {
                _alert("客户姓名不能为空");
                return;
            } else if ($("#custPhone").val() == null || $("#custPhone").val() == "") {
                _alert("客户电话不能为空");
                return;
            }
            $.ajax({
                type: "post",
                url: "/carsokApi/TenureCustomer/updateCustCarMsg.do",
                data: $("#forms").serializeArray(),
                dataType: 'json',
                success: function (data) {
                    window.location.href = "/carsokApi/TenureCustomer/tenurePage.do?mobile=" +${mobile};
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
                }
            });

            return false;
        });

    });
    //    $("#custPhone").blur(function(){
    //        var tells = $("#tellphone").parent().find('input').eq(0).val();
    //        $("#tellphone").attr("href", "tel:"+tells);
    //    });

    function makeHref() {
        var tells = $("#tellphone").parent().find('input').eq(0).val();
        $("#tellphone").attr("href", "tel:" + tells);
    }

    function showfun() {
        //底部对话框
        layer.open({
            title: "电话话术",
            content: '<div id="mes"><div class="speechcraft-list" style="margin-bottom:20px;">' +
            '<dl style="border-bottom:solid 5px #f4f4f4; padding-bottom:10px;"><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">您好，请问是x先生／女士吗?我是梧桐网辉程名车。您在x月买过xx，耽误您1-2分钟做个简单的服务回访方便吗？</div></dd></dl>' +
            '<div class="hs_box"  style="border-bottom:solid 5px #f4f4f4; overflow:hidden;"><div style="width:40%; float:left;"><dl><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">方便</div></dd></dl></div>' +
            '<div style="width:50%; background:#ededed; float:right; padding:0px 10px 10px 10px; " class="border-box"> <dl><dt><img src="/carsokApi/images/custom-no-icon.png" width="40"></dt><dd class="left-dialog02"><div class="dialog_box">不方便</div></dd></dl> <div class="hs_todo">与客户另约时间，如下午或明天</div></div></div>' +
            '<dl style="border-bottom:solid 5px #f4f4f4; padding-bottom:10px;"><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">您的爱车使用状态是否满意？</div></dd></dl>' +
            '<div class="hs_box clearfix"  style="border-bottom:solid 5px #f4f4f4;"><div style="width:40%; float:left;"><dl><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">满意</div></dd></dl></div>' +
            '<div style="width:50%; background:#ededed; float:right; padding:0px 10px 10px 10px; " class="border-box"> <dl><dt><img src="/carsokApi/images/custom-no-icon.png" width="40"></dt><dd class="left-dialog02"><div class="dialog_box">不满意</div></dd></dl> <div class="hs_todo">询问情况，并记录问题反馈线下店面</div></div></div>' +
            '<dl style="border-bottom:solid 5px #f4f4f4; padding-bottom:10px;"><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">非常感谢您的评价，那么，您对我成行的整体环境是否满意？</div></dd></dl>' +
            '<div class="hs_box clearfix"  style="border-bottom:solid 5px #f4f4f4;"><div style="width:40%; float:left;"><dl><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">满意</div></dd></dl></div>' +
            '<div style="width:50%; background:#ededed; float:right; padding:0px 10px 10px 10px; " class="border-box"> <dl><dt><img src="/carsokApi/images/custom-no-icon.png" width="40"></dt><dd class="left-dialog02"><div class="dialog_box">不满意</div></dd></dl> <div class="hs_todo">询问情况，并记录做出相关合理解释</div></div></div>' +
            '<dl style="border-bottom:solid 5px #f4f4f4; padding-bottom:10px;"><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">非常感谢您接受我的回访，还要麻烦您为我车行的各项服务是否满意？</div></dd></dl>' +
            '<div class="hs_box clearfix"  style="border-bottom:solid 5px #f4f4f4;"><div style="width:40%; float:left;"><dl><dt><img src="/carsokApi/images/custom-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">满意</div></dd></dl></div>' +
            '<div style="width:50%; background:#ededed; float:right; padding:0px 10px 10px 10px; " class="border-box"> <dl><dt><img src="/carsokApi/images/custom-no-icon.png" width="40"></dt><dd class="left-dialog02"><div class="dialog_box">不满意</div></dd></dl> <div class="hs_todo">询问情况并记录问题，做出相关合理解释。如：您认为我们欠缺在哪里？</div></div></div>' +
            '<dl style="border-bottom:solid 5px #f4f4f4; padding-bottom:10px;"><dt><img src="/carsokApi/images/kefu-icon.png" width="40"></dt><dd class="left-dialog"><div class="dialog_box">非常感谢对我们工作的认可与支持，我们会竭诚为您服务的，祝您用车愉快！再见！</div></dd></dl>' +
            '</div></div>'
            , btn: ['关闭']
        });

    }

    function sumAge() {
        var birthday = $("#custBirthday").val();
        if (birthday != "" && birthday != null) {
            var birthYear = birthday.split("-")[0];
            var date = new Date();
            var nowYear = date.getFullYear();
            var age = nowYear - birthYear;
            $("#custAge").val(age);
        }
    }

    function changePage() {
        window.location.href = "http://www.baidu.com/changePageUton?writer=zyg";
    }

    function importPhone() {
        window.location.href = "http://www.baidu.com/importPhoneUton?writer=zyg";
    }

</script>
</body>
</html>
