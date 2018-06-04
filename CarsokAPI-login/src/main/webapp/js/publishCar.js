//定义全局city
var nowProvince = "";  //省
var nowCity = "";  //城市
var nowPositon = nowProvince + nowCity;  //省市
//定义全局time
var chooseYear = "";
var chooseMonth = "";
var chooseTime = "";
//定义全局品牌车系车型
var carBrand = "";
var carType = "";
var carModel = "";
//地址栏取得id
var locationUrl = window.location.href;
var modifyArr = locationUrl.split("?");
var modifyId = modifyArr[1];

var myScroll1;
var myScroll2;
var myScroll3;
var myScroll4;
var myScroll5;

function initscroll(){
		myScroll1 = new IScroll('#layer-province', {
			click:true,
		    mouseWheel: true,
		    scrollbars: true,
		    hScroll:true,
		    vScroll:false,
		    x:0,
		    y:0,
		    bounce:true,
		    bounceLock:false,
		    interactiveScrollbars:true,
		    shrinkScrollbars:'scale',
		    fadeScrollbars:true
		});
		myScroll2 = new IScroll('#layer-city', {
		    click:true,
		    mouseWheel: true,
		    scrollbars: true,
		    hScroll:true,
		    vScroll:false,
		    x:0,
		    y:0,
		    bounce:true,
		    bounceLock:false,
		    interactiveScrollbars:true,
		    hasVerticalScroll:true,
		    shrinkScrollbars:'scale',
		    fadeScrollbars:true
		});
		myScroll3 = new IScroll('#layer-brand', {
			click:true,
		    mouseWheel: true,
		    scrollbars: true,
		    hScroll:true,
		    vScroll:false,
		    x:0,
		    y:0,
		    bounce:true,
		    bounceLock:false,
		    interactiveScrollbars:true,
		    shrinkScrollbars:'scale',
		    fadeScrollbars:true
		});
		myScroll4 = new IScroll('#layer-type', {
		    click:true,
		    mouseWheel: true,
		    scrollbars: true,
		    hScroll:true,
		    vScroll:false,
		    x:0,
		    y:0,
		    bounce:true,
		    bounceLock:false,
		    interactiveScrollbars:true,
		    hasVerticalScroll:true,
		    shrinkScrollbars:'scale',
		    fadeScrollbars:true
		});
		myScroll5 = new IScroll('#layer-model', {
			click:true,
		    mouseWheel: true,
		    scrollbars: true,
		    hScroll:true,
		    vScroll:false,
		    x:0,
		    y:0,
		    bounce:true,
		    bounceLock:false,
		    interactiveScrollbars:true,
		    shrinkScrollbars:'scale',
		    fadeScrollbars:true
		});
		myScroll6 = new IScroll('#layer-year', {
		    click:true,
		    mouseWheel: true,
		    scrollbars: true,
		    hScroll:true,
		    vScroll:false,
		    x:0,
		    y:0,
		    bounce:true,
		    bounceLock:false,
		    interactiveScrollbars:true,
		    hasVerticalScroll:true,
		    shrinkScrollbars:'scale',
		    fadeScrollbars:true
		});
		myScroll7 = new IScroll('#layer-month', {
			click:true,
		    mouseWheel: true,
		    scrollbars: true,
		    hScroll:true,
		    vScroll:false,
		    x:0,
		    y:0,
		    bounce:true,
		    bounceLock:false,
		    interactiveScrollbars:true,
		    shrinkScrollbars:'scale',
		    fadeScrollbars:true
		});
		
}

function initheight1(){
	var timer=window.setInterval(function(){
    var scrollerHeight = $("#layer-province")[0].clientHeight;
        if(scrollerHeight==myScroll1.scrollerHeight){
            window.clearInterval(timer);
        }
    myScroll1.refresh();
    },600);
}

function initheight2(){
	var timer=window.setInterval(function(){
    var scrollerHeight = $("#layer-city")[0].clientHeight;
        if(scrollerHeight==myScroll2.scrollerHeight){
            window.clearInterval(timer);
        }
    myScroll2.refresh();
    },600);
}
function initheight3(){
	var timer=window.setInterval(function(){
    var scrollerHeight = $("#layer-brand")[0].clientHeight;
        if(scrollerHeight==myScroll1.scrollerHeight){
            window.clearInterval(timer);
        }
    myScroll3.refresh();
    },600);
}

function initheight4(){
	var timer=window.setInterval(function(){
    var scrollerHeight = $("#layer-type")[0].clientHeight;
        if(scrollerHeight==myScroll4.scrollerHeight){
            window.clearInterval(timer);
        }
    myScroll4.refresh();
    },600);
}
function initheight5(){
	var timer=window.setInterval(function(){
    var scrollerHeight = $("#layer-model")[0].clientHeight;
        if(scrollerHeight==myScroll5.scrollerHeight){
            window.clearInterval(timer);
        }
    myScroll5.refresh();
    },600);
}
function initheight7(){
	var timer=window.setInterval(function(){
    var scrollerHeight = $("#layer-model")[0].clientHeight;
        if(scrollerHeight==myScroll7.scrollerHeight){
            window.clearInterval(timer);
        }
    myScroll7.refresh();
    },600);
}


var Publish = (function(){
    var _publish = {};

    //高度等于宽度
    _publish.autoHeight = function(){
        var finalWidth = $(".upload-add").width();
        $(".auto-height").height(finalWidth);
    };

    //修改发布车辆
    _publish.modify = function(){        
        $.ajax({
            type: "post",
            url: "/user/toUpdPublishCar",
            dataType: "json",
            data: {
                "id": modifyId
            },
            success: function(data){
                if(data.retCode != "0000"){
                    return;
                }
                var modifyData = data.data;
                //上传图片
                var defaultImg = "";
                $.each(modifyData.picList,function(index,val){
                    defaultImg += '<div class="upload-img border-box auto-height"><img src="' + val + '"><a href="javascript:;" class="delete-img"></a></div>';
                    picList.push(val);
                });
                $("#upload-img").append(defaultImg);

                //品牌系列车型
                carBrand = modifyData.cbrand;
                carType = modifyData.ctype;
                carModel = modifyData.cmodel; 
                $("#publish-brand").val(carBrand+" "+carType+" "+carModel);
                //省市
                nowProvince = modifyData.province;
                nowCity = modifyData.city;
                nowPositon = nowProvince + nowCity;
                $("#publish-position").val(nowPositon); 
                //初次上牌时间
                chooseTime = modifyData.upTimeStr;
                $("#publish-time").val(chooseTime);
                //里程、价格、联系人、联系电话、描述                
                $("#publish-mileage").val(modifyData.miles);
                $("#publish-price").val(modifyData.price);  
                $("#publish-contactPerson").val(modifyData.linkman); 
                $("#publish-contactNumber").val(modifyData.linkphone);
                $("#publish-describe").val(modifyData.descr);
            }
        });
    };

    //所在地--获取省
    _publish.province = function(){
        var layerProvince = $("#layer-province");
        var layerProvinceCon = layerProvince.find("ul");
        var layerProvinceLi = "";
        var provinceId = "";
        var provinceName = "";
        $.ajax({
            type: "get",
            url: "/tools/province",
            dataType: "json",
            data: {},
            success: function(data){
                if(data.retCode != "0000"){
                    return;
                }
                $.each(data.data, function(index, val){
                    layerProvinceLi += "<li data-id='" + val.id + "'>" + val.name + "</li>";
                });
                layerProvinceCon.html(layerProvinceLi);
                initheight1();
                layerProvince.delegate("li","click",function(){
                    $(this).addClass("active").siblings().removeClass("active");
                    provinceId = $(this).attr("data-id");
                    provinceName = $(this).html();
                    _publish.city(provinceId, provinceName);
                    initheight2();
                    $("#layer-city").addClass("in");
                })               
            }
        });
    };

    //所在地--获取市
    _publish.city = function(id, name){
        var layerCity = $("#layer-city");
        var layerCityCon = layerCity.find("ul");
        var layerCityLi = "";
        $.ajax({
            type: "get",
            url: "/tools/city",
            dataType: "json",
            data: {
                "id": id
            },
            success: function(data){
                if(data.retCode != "0000"){
                    return;
                }
                $.each(data.data, function(index, val){
                    layerCityLi += "<li>" + val.name + "</li>";
                });
                layerCityCon.html(layerCityLi);
                layerCity.delegate("li","click",function(){
                    nowCity = $(this).html(); 
                    nowProvince = name;   
                    nowPositon =  nowProvince + nowCity;              
                    $("#layer-province,#layer-city,#layer-header-address").removeClass("in");
                    $("#publish-position").val(nowPositon);                   
                })
            }
        });
    };

    //选择年
    _publish.year = function(){
        var layerYear = $("#layer-year");
        var layerYearCon = layerYear.find("ul");
        var layerYearLi = "";
        var myDate = new Date();
        var nowYear = myDate.getFullYear();
        for(var i=2000;i<=nowYear;i++){
            layerYearLi += "<li>" + i + "年" + "</li>";
        };
        layerYearCon.html(layerYearLi);
        layerYear.delegate("li","click",function(){
            $(this).addClass("active").siblings().removeClass("active");
            $("#layer-month").addClass("in");
            var selYear = $(this).html();
            chooseYear = selYear.substring(0,4);
            _publish.month();
        });
    };

    //选择月
    _publish.month = function(){
        var layerMonth = $("#layer-month");
        var layerMonthCon = layerMonth.find("ul");
        var layerMonthLi = "";
        for(var i=1;i<=12;i++){
            layerMonthLi += "<li>" + i + "月" + "</li>";
        };
        layerMonthCon.html(layerMonthLi);
        initheight7();
        layerMonth.delegate("li","click",function(){
            var selMonth = $(this).html();
            chooseMonth = selMonth.substring(0,selMonth.length-1);
            chooseTime = chooseYear+"-"+chooseMonth;
            $("#publish-time").val(chooseYear+"年"+chooseMonth+"月");
            $("#layer-year,#layer-month,#layer-header-time").removeClass("in");
        });
    };

    //选择品牌
    _publish.brand = function(){
        var layerBrand = $("#layer-brand");
        var layerBrandCon = layerBrand.find("ul");
        var layerBrandLi = "";
        var brandId = "";
        $.ajax({
            type: "get",
            url: "/tools/carBrand",
            dataType: "json",
            data: {},
            success: function(data){
                if(data.retCode != "0000"){
                    return;
                }                
                $.each(data.data, function(key, values){
                    layerBrandLi += "<li class='layer-category'>" + key + "</li>";
                    $.each(values, function(index, val){
                        layerBrandLi += "<li data-id='" + val.id + "' data-name='" + val.name+ "'><img src='" + val.logo + "'>" + val.name + "</li>";
                    });                   
                });
                layerBrandCon.html(layerBrandLi);
                initheight3();
                layerBrand.delegate("li","click",function(){
                    if($(this).hasClass("layer-category")){
                        return;
                    };
                    $(this).addClass("active").siblings().removeClass("active");
                    brandId = $(this).attr("data-id");
                    carBrand = $(this).attr("data-name");
                    _publish.type(brandId);
                    initheight4();
                    $("#layer-type").addClass("in");
                    $("#layer-model").removeClass("in");
                });               
            }
        });
    };

    //选择车系
    _publish.type = function(brandId){
        var layerType = $("#layer-type");
        var layerTypeCon = layerType.find("ul");
        var layerTypeLi = "";
        $.ajax({
            type: "get",
            url: "/tools/carType",
            dataType: "json",
            data: {
                "id": brandId
            },
            success: function(data){
                if(data.retCode != "0000"){
                    return;
                }
                $.each(data.data, function(key, values){
                    layerTypeLi += "<li class='layer-category'>" + key + "</li>";
                    $.each(values, function(index, val){
                        layerTypeLi += "<li data-id='" + val.id + "'>" + val.name + "</li>";
                    });                   
                });
                layerTypeCon.html(layerTypeLi);
                layerType.delegate("li","click",function(){
                    if($(this).hasClass("layer-category")){
                        return;
                    };
                    $(this).addClass("active").siblings().removeClass("active");
                    typeId = $(this).attr("data-id");
                    carType = $(this).html();
                    _publish.model(typeId);
                    initheight5();
                    $("#layer-model").addClass("in");
                });  
            }
        });
    };

    //选择车型
    _publish.model = function(typeId){
        var layerModel = $("#layer-model");
        var layerModelCon = layerModel.find("ul");
        var layerModelLi = "";
        $.ajax({
            type: "get",
            url: "/tools/carModel",
            dataType: "json",
            data: {
                "id": typeId
            },
            success: function(data){
                if(data.retCode != "0000"){
                    return;
                }
                $.each(data.data, function(key, values){
                    layerModelLi += "<li class='layer-category'>" + key + "</li>";
                    $.each(values, function(index, val){
                        layerModelLi += "<li data-id='" + val.id + "'>" + val.name + "</li>";
                    });                   
                });
                layerModelCon.html(layerModelLi);
                layerModel.delegate("li","click",function(){
                    if($(this).hasClass("layer-category")){
                        return;
                    };
                    $(this).addClass("active").siblings().removeClass("active");
                    carModel = $(this).html();
                    $("#publish-brand").val(carBrand+" "+carType+" "+carModel);
                    $("#layer-header-car,#layer-brand,#layer-type,#layer-model").removeClass("in");
                });  
            }
        });
    };

    //提交
    _publish.submitForm = function(){
        var publishMileage = $("#publish-mileage").val();  //里程
        var publishPrice = $("#publish-price").val();  //价格
        var publishContactPerson = $("#publish-contactPerson").val();  //联系人
        var publishContactNumber = $("#publish-contactNumber").val();  //联系电话
        var publishDescribe = $("#publish-describe").val();  //车辆描述
        var ruleAgree = $("#rule-agree");  //勾选同意

        var submitTip = 0;
        var canSubmit = false;
        var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
        var isMob = /^(([1-9]\d{8})|(1(3[0-9]|4[57]|5[0-35-9]|7[0-9]|8[0-9]|70)\d{8}))$/;
        var phoneNumber = $("#publish-contactNumber").val();
        var phoneValue = $.trim(phoneNumber);

        //校验
        if(picList == null || picList.length==0){
            submitTip = 1;
            canSubmit = false;
        }else if(!carBrand || !carType || !carModel){
            submitTip = 2;
            canSubmit = false;
        }else if(!nowProvince || !nowCity){
            submitTip = 3;
            canSubmit = false;
        }else if(!chooseTime){
            submitTip = 4;
            canSubmit = false;
        }else if(!publishMileage){
            submitTip = 5;
            canSubmit = false;
        }else if(!publishPrice){
            submitTip = 6;
            canSubmit = false;
        }else if(!publishContactPerson){
            submitTip = 7;
            canSubmit = false;
        }else if(!publishContactNumber){
            submitTip = 8;
            canSubmit = false;
        }else if((!isMob.test(phoneValue)) && (!isPhone.test(phoneValue))){
            submitTip = 9;
            canSubmit = false;
        }else if(!ruleAgree.hasClass("active")){
            submitTip = 10;
            canSubmit = false;
        }else{
            canSubmit = true;
        }

        switch(submitTip){
            case 1: Uton.tip("请至少上传一张图片");break;
            case 2: Uton.tip("请选择品牌车系");break;
            case 3: Uton.tip("请选择所在地");break;
            case 4: Uton.tip("请选择初次上牌时间");break;
            case 5: Uton.tip("请输入里程");break;
            case 6: Uton.tip("请输入价格");break;
            case 7: Uton.tip("请输入联系人");break;
            case 8: Uton.tip("请输入联系电话");break;
            case 9: Uton.tip("请输入正确的联系电话");break;
            case 10: Uton.tip("请勾选同意《梧桐网发布规范》");break;
        }

        if(canSubmit){
            $.ajax({
                type: "post",
                url: "/user/updPublishCar",
                dataType: "json",
                data: {
                    "id": modifyId,
                    "picList": picList,
                    "cbrand": carBrand,
                    "ctype": carType,
                    "cmodel": carModel,
                    "province": nowProvince,
                    "city": nowCity,
                    "upTime": chooseTime,
                    "miles": publishMileage,
                    "price": publishPrice,
                    "linkman": publishContactPerson,
                    "linkphone": publishContactNumber,
                    "descr": publishDescribe
                },
                traditional: true, 
                success: function(data){
                    if(data.retCode == "0000"){
                        window.location.href = "publishSuccess.html";
                    }else if(data.retCode == "0002"){
                        Uton.tip(data.retMsg);
                        return;
                    }else{
                        Uton.tip("系统异常");
                        return;
                    };
                    
                }
            });
        }
    };

    return _publish;
})(jQuery, Publish)

$(function(){
	
	setTimeout("initscroll();",500);
	
    //高度等于宽度
    Publish.autoHeight();
    $(window).resize(function(){
        Publish.autoHeight();
    });

    //修改发布车辆
    Publish.modify();

    //选择省市
    Publish.province();

    $("#publish-position").focus(function(){
        //禁止手机自带键盘弹出
        document.activeElement.blur();
        $("#layer-province,#layer-header-address").addClass("in"); 
    });

    //点击取消隐藏layer
    $("#cancel-layer").click(function(){
        $("#layer-province,#layer-city,#layer-header-address").removeClass("in");
    });

    //选择时间
    Publish.year();

    $("#publish-time").focus(function(){
        //禁止手机自带键盘弹出
        document.activeElement.blur();
        $("#layer-year,#layer-header-time").addClass("in"); 
    });
    //点击取消隐藏layer
    $("#cancel-time").click(function(){
        $("#layer-year,#layer-month,#layer-header-time").removeClass("in");
    });

    //选择品牌
    Publish.brand();
    $("#publish-brand").focus(function(){
        //禁止手机自带键盘弹出
        document.activeElement.blur();
        $("#layer-header-car,#layer-brand").addClass("in");
    });
    //点击取消隐藏layer
    $("#cancel-brand").click(function(){
        $("#layer-header-car,#layer-brand,#layer-type,#layer-model").removeClass("in");
    });

    //勾选同意
    $("#rule-agree").click(function(){
        $(this).toggleClass("active");
    });

    //点击梧桐网发布规范
    $("#show-rule").click(function(){
        $(".msg-bg,.layer-rule").fadeIn();
    });
    $(".msg-bg,#close-rule").click(function(){
        $(".msg-bg,.layer-rule").fadeOut();
    });

    //提交
    $("#publish-submit").click(function(){      
        Publish.submitForm();
    });

    //删除图片
    $("#upload-img").delegate(".delete-img","click",function(){
        var thisSrc = $(this).attr("src");
        $(this).parent(".upload-img").remove();
        var thisIndex = picList.indexOf(thisSrc);
        picList.splice(thisIndex, 1);
    });

    //解决input获得焦点footer遮挡问题
    //ios
    $('input,textarea').bind('focus',function(){  
        $('footer').hide();  
        //或者$('#viewport').height($(window).height()+'px');  
    }).bind('blur',function(){  
        $('footer').show();  
        //或者$('#viewport').height('auto');  
    }); 
    //安卓
    var windowHeight = $(window).height();
    $(window).resize(function(){  
        var nowHeight = $(window).height();
        if(nowHeight<windowHeight){
            $('footer').hide();
        }else{
            $('footer').show();
        };  
    });


})

