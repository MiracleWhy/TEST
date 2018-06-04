<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
    <title>意向价格</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet" />
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet" />
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet" />
</head>
<style type="text/css">
    @keyframes myShow{
        from{transform: scale(0);}
        to{transform: scale(1);}
    }
    @-moz-keyframes name{
        from{transform: scale(0);}
        to{transform: scale(1);}
    }
    @-ms-keyframes name{
        from{transform: scale(0);}
        to{transform: scale(1);}
    }
    @-webkit-keyframes name{
        from{transform: scale(0);}
        to{transform: scale(1);}
    }
    .show_anim{
        -webkit-animation: myShow .6s ease;
        -moz-animation: myShow .6s ease;
        -o-animation: myShow .6s ease;
        animation: myShow .6s ease;
    }
    .hint_body{
        position: fixed;
        top: 0;left: 0;
        z-index: 10000;
        background-color: rgba(0,0,0,0.6);
        text-align: center;
    }
    .hint_sure{
        width: 60.05333333333333%;
        background-color: #fff;
        padding: 3% 0;
        border-radius: 10px;
        display: inline-block;
        vertical-align: middle;
        position: absolute;
        margin: auto;
        height: 6.8rem;
        top: 0;left: 0;right: 0;bottom: 0;
        overflow: hidden;
    }
    .hint_sure>img{
        width: 90%;
    }
    .hint_top_boder{
        border-top: 2px solid rgb(157,121,72);
        margin-top: -2px;
    }
    .hint_btn{
        display: inline-block;
        height: 3rem;
        line-height: 3rem;
        width: 100%;
        /*color: rgb(51,51,51);*/
        font-size: 1.3rem;
    }
</style>

<body>
<form id="forms">
<div class="car-assess-header">
    请输入您的意向价格
</div>
<div class="custom-mes car-mes ">
    <ul id="input_all">
        <li class="clearfix"> <span>意向价格</span>
            <input type="number" step="1.00" placeholder="必填" value="" class="count required" id="loan_money" name="intentionalPrice">
        </li>
        <li class="clearfix"> <span>姓名</span>
            <input type="text" step="1.00" placeholder="必填" value="" class="count required" id="loan_name" name="name">
        </li>
        <li class="clearfix"> <span>联系电话</span>
            <input type="tel" step="1.00" placeholder="必填" value="" class="count required " id="loan_mobile" name="mobile">
        </li>
    </ul>
</div>
    <input type="hidden" id="productId" name="productId" value="${productId}"/>
<div style="margin-top: 9.18rem;text-align: center;">
    <a href="javascript:" class="car_button" style="background-color: rgb(189,164,119);">提交</a>
</div>
</form>
<div class="hint_body show_anim" style="display: none;">
    <div class="hint_sure">
        <img src="/carsokApi/images/hint.png"/>
        <div class="hint_top_boder">
            <a class="hint_btn" href="javascript:">知道了</a>
        </div>
    </div>
</div>
<div class="car-tip" id="car-tip"></div>
</body>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/jquery.mmenu.all.min.js" type="text/javascript"></script>
<script src="/carsokApi/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
    var h = document.body.scrollHeight;
    var w = document.body.clientWidth;
    function isEmojiCharacter(substring) {
        for (var i = 0; i < substring.length; i++) {
            var hs = substring.charCodeAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (substring.length > 1) {
                    var ls = substring.charCodeAt(i + 1);
                    var uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else if (substring.length > 1) {
                var ls = substring.charCodeAt(i + 1);
                    if (ls == 0x20e3) {
                    return true;
                }
            } else {
                if (0x2100 <= hs && hs <= 0x27ff) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030
                || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b
                || hs == 0x2b50) {
                    return true;
                }
            }
        }
    }
    function trim(str){
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    document.getElementsByClassName('hint_body')[0].style.height = h + 'px';
    document.getElementsByClassName('hint_body')[0].style.width = w + 'px';
    $('.car_button').click(function(){
        var bool = false;
        $('#input_all li').each(function () {
            var val = $(this).find('input').val();
            if(val == '' || trim(val) == ''){
                Uton.tip("请补全信息");
                bool = true;
                return false;
            }
        })
        if(bool){
            return false;
        }
        if(isEmojiCharacter($('#loan_name').val())){
            Uton.tip("姓名格式错误");
            return false;
        }
        var intentionalPriceNumber = $('input[name="intentionalPrice"]').val();
        var ctel = $('input[name="mobile"]') .val();
        var res = "^[0-9]+\.?[0-9]*$";
        var re = new RegExp(res);
        if (!re.test(intentionalPriceNumber)||!isNumber(intentionalPriceNumber)) {
            Uton.tip("意向价格只能为数字");
            return false;
        }
        if (!re.test(ctel) || ctel.length != 11) {
            Uton.tip("请正确输入电话号码");
            return false;
        }
        $.ajax({
            type: "post",
            url: "/carsokApi/utonwGK/bargainSubmit.do",
            data: $("#forms").serializeArray(),
            success: function (data) {
                $('.hint_body').show();
//                window.location.href="javascript:history.go(-1);";
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
            }
        });

    })
    $('.hint_btn').click(function(){
        $('.hint_body').hide();
        window.history.go(-1);
    })
    function isNumber(str) {
        var reg = /^[0-9]+.?[0-9]*$/;
        if (reg.test(str)) {
            return true;
        }
        return false;
    }

</script>

</html>