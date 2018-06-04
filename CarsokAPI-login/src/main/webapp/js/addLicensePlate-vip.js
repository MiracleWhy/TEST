var isMob = /^(([1-9]\d{8})|(1(3[0-9]|4[57]|5[0-35-9]|7[0-9]|8[0-9]|70)\d{8}))$/;
var Add = (function () {
    var _add = {};

    //添加车牌号
    _add.addNumber = function () {
        $("#carNumber1").click(function () {
            $(this).addClass("active").siblings().removeClass("active");
            $("#add-layer1").addClass("in");
            $("#add-layer2").removeClass("in");
        });
        $(".car-number").click(function () {
            $(this).addClass("active").siblings().removeClass("active");
            $("#add-layer1").removeClass("in");
            $("#add-layer2").addClass("in");
        });
        $("#add-layer1").find("li").click(function () {
            var thisValue1 = $(this).find("a").html();
            $("#add-all span.active").html(thisValue1);
            $("#add-all span.active").next().addClass("active").siblings().removeClass("active");
            $("#add-layer1").removeClass("in");
            $("#add-layer2").addClass("in");
        });
        $("#add-layer2").find("li").click(function () {
            var thisValue2 = $(this).find("a").html();
            $("#add-all span.active").html(thisValue2);
            $("#add-all span.active").next().addClass("active").siblings().removeClass("active");
        })
    };

    //获取验证码
    _add.getCode = function (obj) {
        var phoneNumber = $("#add-phone").val();
        if (!phoneNumber) {
            Uton.tip("请输入手机号码");
            return;
        } else if (!isMob.test(phoneNumber)) {
            Uton.tip("手机号码格式不正确");
            return;
        } else {
            var timeoutEr;
            var num = 60;

            timer();
            $.ajax({
                type: "post",
                url: "reqValidationCode.do",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({"account": phoneNumber}),
                success: function (data) {


                }
            });
            function timer() {
                num--;
                if (num <= 0) {
                    obj.removeClass("disabled");
                    obj.attr("disabled", false);
                    obj.attr("value", "获取验证码");
                    clearTimeout(timeoutEr);
                    return;
                }
                obj.addClass("disabled");
                obj.attr("disabled", true);
                obj.attr("value", num + "秒后重新获取");
                timeoutEr = setTimeout(function () {
                    timer();
                }, 1000);
            }
        }
    };

    //提交车牌号
    _add.submitCar = function () {
        var userName = $("#add-username").val();
        var phoneNumber = $("#add-phone").val();
        var code = $("#add-code").val();
        var inviter = $("#hdnInviter").val();
        var password = $("#add-password").val();
        var accountPassword = md5(phoneNumber + password).toUpperCase();
        if (!userName) {
            Uton.tip("请输入姓名");
            return;
        } else if (!phoneNumber) {
            Uton.tip("请输入手机号码");
            return;
        } else if (!isMob.test(phoneNumber)) {
            Uton.tip("手机号码格式不正确");
            return;
        } else if (!code) {
            Uton.tip("请输入验证码");
            return;
        } else {
            //校验验证码
            $.ajax({
                type: "post",
                url: "checkCode.do",
                dataType: "json",
                contentType: "application/json",
                async: false,
                data: JSON.stringify({"account": phoneNumber, "code": code}),
                success: function (data) {
                    if (data.retCode == "0000") {
                        //校验账户唯一性
                        $.ajax({
                            type: "post",
                            url: "isAccountExist.do",
                            dataType: "json",
                            contentType: "application/json",
                            async: false,
                            data: JSON.stringify({"account": phoneNumber}),
                            success: function (data) {
                                if (data.retCode == "0000") {
                                    //注册
                                    $.ajax({
                                        type: "post",
                                        url: "register.do",
                                        dataType: "json",
                                        contentType: "application/json",
                                        async: false,
                                        data: JSON.stringify({
                                            "account": phoneNumber,
                                            "code": code,
                                            "accountType": "1",
                                            "accountPassword": accountPassword,
                                            "inviter": inviter
                                        }),
                                        success: function (data) {
                                            if (data.retCode == "0000") {
                                                alert("注册成功");
                                                location.href = "http://m.91xiaoshidai.com/download/";
                                            }
                                            else if(data.retCode == "1013"){
                                                alert("用户已经存在")
                                            }
                                            else
                                            {
                                                alert("注册失败")
                                            }
                                        }
                                    });
                                }
                                else {
                                    alert("手机号已被占用!");
                                    location.reload();
                                }
                            }
                        });
                    }
                    else {
                        Uton.tip("验证码错误");
                    }
                }
            });
        }
    }

    return _add;
})(jQuery, Add)

$(function () {
    //添加车牌号
    Add.addNumber();
    //提交车牌号
    $("#add-submit").click(function () {
        Add.submitCar();
    });
    //获取验证码
    $("#getcode").click(function () {
        var _this = $(this);
        Add.getCode(_this);
    });
})

