<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
    <title>市场人员离职转移</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/defaultTheme.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/layer.css" type="text/css" rel="stylesheet"/>
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet"/>

    <style>
        .layui-m-layerbtn span[yes] {
            color: #e29000;
        }
        /*body>.tab-list,body>.tab-list>.tab-table{*/
            /*height: 100%;*/
        /*}*/
        .layui-m-layer0 .layui-m-layerchild {
            /*max-height: 500px;*/
            overflow-y: auto;
        }
        .dropload-noData{
            width: 75px;
        }
        .table-responsive{
            overflow-y: scroll;
        }
        .layui-m-layercont {
            padding: 15px;
            text-align: left;
        ;
        }
        /*.div1,.fht-table-wrapper{*/
            /*height: 100% !important;*/
        /*}*/
        /*html{*/
            /*height: 100%;*/
        /*}*/
        /*body{*/
            /*height: calc(100% - 50px);*/
        /*}*/
        nav.car-filter>ul>li {
            width: 33.33%
        }

        .layui-m-layer-footer .layui-m-layercont {
            border-radius: 4px;
        }

        body {
            overflow: hidden;
            position: fixed;
        }

        tbody {
            background: #fff;
        }
    </style>
</head>

<body  class="white-body">
<section class="tab-list border-box"  >
    <div class="tab-table table-responsive" id="custom-con">
        <table class="table" id="myTable03">
            <thead>
            <tr>
                <th style="width: 70px">全选</th>
                <th>车行名称</th>
                <th>负责人</th>
                <th>子账户</th>
                <th>地址</th>
                <th>电话</th>
                <th>车行规模</th>
            </tr>
            </thead>
            <tbody id="spand">
            <%--<td id="spand">2017-3-9 13:10</td>--%>
            <%--<td>某某</td>--%>
            <%--<td>175825454121</td>--%>
            <%--<td>√</td>--%>
            <%--<td></td>--%>
            <%--<tr>--%>
            <%--<td width="20px" class="radio-box"><label class="label" style="margin: 0;">--%>
            <%--<input class="radio" type="checkbox" name="customSta" value="1">--%>
            <%--<span class="radioInput"></span>  </label></td>--%>
            <%--&lt;%&ndash;<td>2017-3-9 13:10</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>某某</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>175825454121</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>√</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td></td>&ndash;%&gt;--%>
            </tbody>
        </table>
        <div id="ddddd"></div>
    </div>
</section>
<section class="footer-btn" style="z-index: 1000;background: #ffffff;">
    <div class="checkAll">
        <div class="radio-box" ><label class="label" >
            <input class="radio" type="checkbox" name="customSta" value="1" id="all">
            <span class="radioInput"></span>  &nbsp;&nbsp;全选</label></div>
    </div>
    <button class="right-sub-btn" id="btn-submit">确定转移</button>
</section>
</body>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/dropload.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script src="/carsokApi/js/layer.js"></script>

<script src="/carsokApi/js/jquery.fixedheadertable.min.js" type="text/javascript" charset="utf-8"></script>
<script>
    var a = window.innerHeight - 60;
        a = a+'px';
    $('#custom-con').css('height',a);
</script>

<script>
    // 初加载

    $(function () {
        var mobile = ${mobile};
//        window.onscroll = function(){
//            if(getScrollTop() + getWindowHeight() == getScrollHeight()){
//                //alert("已经到最底部了！!");
//                window.scrollTo(0,0);// 无动画版本回到顶部
//            }
//        };
        // 页数
        // 每页展示5个
        // 拼接HTML
        var page = 0;
//        $('#myTable03').fixedHeaderTable('destroy');
        $(".tab-table").dropload({
            scrollArea: $('.tab-table'),
            autoLoad: true,
            distance: 50,
            loadDownFn: function (me) {
                page++;
        var id = ${id};
        $.ajax({
            type: 'GET',
            url: "/carsokApi/bussinesPage/selectMarket.do",
            dataType: 'json',
            data: {
                "id": id,
                "pc": page
            },
            success: function (data) {
                var html = '';
                if (data.beanlist.length != 0) {
                    for (var i = 0; i < data.beanlist.length; i++) {
                        if (null ==data.beanlist[i].carname) {
                            data.beanlist[i].carname = ""
                        }
                        if (null == data.beanlist[i].leadingofficial) {
                            data.beanlist[i].leadingofficial = ""
                        }
                        if (null == data.beanlist[i].address ) {
                            data.beanlist[i].address  = ""
                        }
                        if (null ==data.beanlist[i].name) {
                            data.beanlist[i].name = ""
                        }
                        if (null ==data.beanlist[i].mobile) {
                            data.beanlist[i].mobile = ""
                        }
                        if (null == data.beanlist[i].carGM) {
                            data.beanlist[i].carGM = ""
                        }

//                $('#myTable03').fixedHeaderTable({ width: 'auto', fixedColumns: 0 });

                        html += "<tr>"
                            + "<td width='70px' class='radio-box'><label class='label' style='margin: 0;'><input class='radio' type='checkbox' value='" + data.beanlist[i].id + "'><span class='radioInput'></span></label></td>"
                            + "<td>" + data.beanlist[i].carname + "</td>"
                            + "<td>" + data.beanlist[i].leadingofficial + "</td>"
                            + "<td>" + data.beanlist[i].name + "</td>"
                            + "<td>" + data.beanlist[i].address + "</td>"
                            + "<td>" + data.beanlist[i].mobile + "</td>"
                            + "<td>" + data.beanlist[i].carGM + "</td>"
                            + "</tr>";

                    }


//                $('#myTable03').fixedHeaderTable({ width: 'auto', fixedColumns: 0 });
                    $("#spand").append(html);
                    var ma = data;
                    // 锁定
                }else if(data.beanlist.length == 0){
                // 无数据
                me.lockdrop();
                me.noData();
//                me.resetload();
                } else {
                    return;
                }
                //me.lockdrop();
                me.resetload();
                //GuDingBiaoTou();

            }
        });
            }
        });
    });


   //全选或全不选
    $("#all").click(function () {

        console.log($('.radio').val())
        if ($(this).prop("checked") == true) {
            $("#custom-con input[type='checkbox']").prop("checked", true);
        } else {
            $("#custom-con input[type='checkbox']").prop("checked", false);
            $(this).find()
        }
    });
    $("#btn-submit").click(function(){
        var mobile = ${mobile};
        layer.open({
            content: '<h2 class="layer-tit">请输入待分配市场人员姓名</h2> <input type="text" class="layer-txt"  placeholder="请输入待分配市场人员姓名">',
            btn: ['确定', '取消'],
            yes: function(index) {
                var arrayObj = [];
//                arrayObj.push(123);
//                arrayObj.push(421);
                var list = $('.radio');

                for(var k in list){
                    if(list[k].checked){
                        arrayObj.push(list[k].value);
                    }
                }
                var name=$(".layer-txt").val();
                $.ajax({
                    type: 'POST',
                    url: "/carsokApi/bussinesPage/moveWorker.do",
                    dataType: 'json',
                    data: {
                        "id":JSON.stringify(arrayObj),
                        "name":name,
                        "mobile":mobile,
                        "beforeId":${id}
                    },
                    success: function (data) {
                        var dam=data;
                        if(data=="true"){
                            location.replace(location);
                        }else {



                            _alert("您输入的人员不存在请重新输入");

                        }
                    }
                });
            }
        });

    })

   function GuDingBiaoTou() {
       $('#myTable03').fixedHeaderTable({ width: 'auto', fixedColumns: 0 });
       //$('#tbl').find('tr').find('td').height('34px');
   }



    function _alert(mes) {
        layer.open({
            content: mes
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
        });
    }

</script>
</body>
</html>