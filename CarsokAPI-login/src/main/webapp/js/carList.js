//地址栏取得searchKey
var searchKey = getQueryString("searchKey");
var realNameAudit = getQueryString("realNameAudit");
var merchantAudit = getQueryString("merchantAudit");
var accountType = getQueryString("accountType");

//搜索条件
var nowProvince = "";  //省
var nowCity = "";  //城市
var carBrand = "";  //品牌
var carType = "";  //车系
var carModel = "";  //车型
var category = "";  //车源类型
var carAge = ""; //车龄
var order = "0";  //排序规则

var myScroll1;
var myScroll2;
var myScroll3;
var myScroll4;
var myScroll5;

function initscroll(){
		// myScroll1 = new IScroll('#layer-province', {
		// 	click:true,
		//     mouseWheel: true,
		//     scrollbars: true,
		//     hScroll:true,
		//     vScroll:false,
		//     x:0,
		//     y:0,
		//     bounce:true,
		//     bounceLock:false,
		//     interactiveScrollbars:true,
		//     shrinkScrollbars:'scale',
		//     fadeScrollbars:true
		// });
		// myScroll2 = new IScroll('#layer-city', {
		//     click:true,
		//     mouseWheel: true,
		//     scrollbars: true,
		//     hScroll:true,
		//     vScroll:false,
		//     x:0,
		//     y:0,
		//     bounce:true,
		//     bounceLock:false,
		//     interactiveScrollbars:true,
		//     hasVerticalScroll:true,
		//     shrinkScrollbars:'scale',
		//     fadeScrollbars:true
		// });
		// myScroll3 = new IScroll('#layer-brand', {
		// 	click:true,
		//     mouseWheel: true,
		//     scrollbars: true,
		//     hScroll:true,
		//     vScroll:false,
		//     x:0,
		//     y:0,
		//     bounce:true,
		//     bounceLock:false,
		//     interactiveScrollbars:true,
		//     shrinkScrollbars:'scale',
		//     fadeScrollbars:true
		// });
		// myScroll4 = new IScroll('#layer-type', {
		//     click:true,
		//     mouseWheel: true,
		//     scrollbars: true,
		//     hScroll:true,
		//     vScroll:false,
		//     x:0,
		//     y:0,
		//     bounce:true,
		//     bounceLock:false,
		//     interactiveScrollbars:true,
		//     hasVerticalScroll:true,
		//     shrinkScrollbars:'scale',
		//     fadeScrollbars:true
		// });
		// myScroll5 = new IScroll('#layer-model', {
		// 	click:true,
		//     mouseWheel: true,
		//     scrollbars: true,
		//     hScroll:true,
		//     vScroll:false,
		//     x:0,
		//     y:0,
		//     bounce:true,
		//     bounceLock:false,
		//     interactiveScrollbars:true,
		//     shrinkScrollbars:'scale',
		//     fadeScrollbars:true
		// });
		
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

var searchHelper = {
        droploader: null,
         
        params: {
        	from: -1,
            size: 10,
            keyword: searchKey,
            province: nowProvince,
            city: nowCity,
            category: category,
            carBrand: carBrand,
            carType: carType,
            carModel: carModel,
            carAge: carAge,
            order: order,
            realNameAudit: realNameAudit,
            merchantAudit: merchantAudit,
            accountType: accountType
        },
         
        init: function() {
            var me = searchHelper;
             
            me.droploader = $('#car-list-con').dropload({
                scrollArea : window,
//                loadUpFn: function(wo) {
//                    //me.refreshFunc(wo);
//                },
                loadDownFn: function(wo) {
                    me.loadFunc(wo);
                }
            });
        },
         
        refreshFunc: function(droploader) {
            var me = searchHelper;
            me.params.from = 0;
            // $.ajax({
            //     type: 'POST',
            //     url: '/search/searchForProductPage',
            //     dataType: 'json',
            //     data: me.params,
            //     success: function(result) {
            //         //重置
            //         droploader.resetload();
            //
            //         if(result.retCode == "0" && result.data && result.data.length > 0) {
            //             me.resetRenderFunc();
            //             me.renderFunc(result.data);
            //             //有数据
            //             droploader.noData(false);
            //         } else {
            //         	//锁定
            //             droploader.lock();
            //             //无数据
            //             droploader.noData();
            //         }
            //         //重置
            //         //droploader.unlock();
            //         droploader.resetload();
            //
            //     },
            //     error: function(xhr, type) {
            //         console.log('加载数据错误-' + type);
            //         droploader.resetload();
            //     }
            // });
        },
         
        loadFunc: function(droploader) {
            var me = searchHelper;
            me.params.from++;
            // $.ajax({
            //     type: 'POST',
            //     url: '/search/searchForProductPage',
            //     dataType: 'json',
            //     data: me.params,
            //     success: function(result) {
            //         if(result.retCode == "0" && result.data && result.data.length > 0) {
            //             me.renderFunc(result.data);
            //         } else {
            //             //锁定
            //             droploader.lock();
            //             //无数据
            //             droploader.noData();
            //         }
            //         //重置
            //         droploader.resetload();
            //
            //     },
            //     error: function(xhr, type) {
            //         console.log('加载数据错误-' + type);
            //         droploader.resetload();
            //     }
            // });
        },
         
        renderFunc: function(data) {
            var info = '';
            var picSize = "?imageView2/1/w/250/h/148";
            $.each(data, function(index, val) {
                var firstUptime = val.firstUptime;
                var date = new Date(firstUptime);
                var dateTime = date.getFullYear() + "年" + (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + "月";
				var onshelfDate = new Date(val.onShelfTime);
				var onshelfTime = onshelfDate.getFullYear() + "-" + (onshelfDate.getMonth()+1 < 10 ? '0'+(onshelfDate.getMonth()+1) : onshelfDate.getMonth()+1) + "-" + onshelfDate.getDate()+"上架";
                info +=   '<li>'
                               +'<a href="/inc/carmarket/details/'+val.code+'.html">'
                                     +'<div class="img-box"><img src="'+val.mainPicPath + picSize +'"><span class="img-label">'+val.city+'</span></div>'
                                     +'<div class="result-content">'
                                         +'<h3 class="ell">'+val.productName+'</h3>'
                                         +'<p>'+dateTime+' | '+val.mile+'万公里</p>'
                                         +'<div class="price">'+val.price+'万元 | '+onshelfTime+'</div>'
                                     +'</div>'
                                     +'<div class="car-cert">'
                                         +((val.category==0 && val.realNameAudit==2)?'<span class="cert-user-yes">实名认证</span>':'')
                                         +((val.category==0 && val.merchantAudit==2)?'<span class="cert-merchant-yes">商家认证</span>':'')
                                         +(val.category==1?'<span class="source-user">个人车源</span>':'<em>'+val.merchantName+'</em>')
                                     +'</div>'
                                 +'</a>'
                             +'</li>';
                     
            });
            $('#car-pro-list ul').append(info);
        },
         
        resetRenderFunc: function() {
            $('#car-pro-list ul').empty();
        },
        
        initParams: function(){
        	var me = searchHelper;
        	
        	$('#car-pro-list ul').empty();
            
        	me.params.from = -1;
        	me.params.keyword = searchKey;
        	me.params.province = nowProvince;
        	me.params.city = nowCity;
        	me.params.category = category;
        	me.params.carBrand = carBrand;
        	me.params.carType = carType;
        	me.params.carModel = carModel;
        	me.params.carAge = carAge;
        	me.params.order = order;
        	me.params.realNameAudit = realNameAudit;
        	me.params.merchantAudit = merchantAudit;
        	me.params.accountType = accountType;
        	
            // 解锁
        	me.droploader.unlock();
        	me.droploader.noData(false);
        	me.droploader.resetload();
        	
        }
};

var Buy = (function(){
    var _buy = {};

    //筛选
    _buy.filter = function(){
        $(".car-filter>ul>li>a.car-sel").click(function(){
            var ulHeight = ($(this).next("ul").find("li").height()+1) * $(this).next("ul").find("li").length;
            $(".filter-son").removeClass("in").css("max-height",0);
            if($(this).parent("li").hasClass("active")){
                $(this).parent("li").removeClass("active");
                $(this).next("ul").removeClass("in").css("max-height",0);
                $(".car-mask").fadeOut();
            }else{
                $(".car-mask").fadeIn();
                $(this).parent("li").addClass("active").siblings("li").removeClass("active");
                $(".car-filter>ul>li>ul").removeClass("in");           
                $(this).next("ul").addClass("in");
                $(this).next("ul").css("max-height",ulHeight);
            }
            
        })
        $(".car-mask").click(function(){
            $(".car-filter>ul>li").removeClass("active");
            $(".car-filter>ul>li>ul").removeClass("in").css("max-height",0);
            $(this).fadeOut();
        })
    };

    //首页定位--获取省
    _buy.province = function(){
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
                    layerProvinceLi += "<li data-val='" + val.name + "' data-id='" + val.id + "'>" + val.name + "</li>";
                });
                layerProvinceCon.html(layerProvinceLi);
                initheight1();
                layerProvince.delegate("li","click",function(){
                    $("#whole-country").removeClass("active");
                    $(this).addClass("active").siblings().removeClass("active");
                    $("#layer-city li").removeClass("active");
                    $("#whole-city").removeClass("active");
                    provinceId = $(this).attr("data-id");
                    provinceName = $(this).html();
                    _buy.city(provinceId, provinceName);
                    initheight2();
                    $("#layer-city").addClass("in");
                })
                //全国
                $("#whole-country").click(function(){
                    $(this).addClass("active");
                    layerProvince.find("li").removeClass("active");
                });
            }
        });
    };

    //首页定位--获取市
    _buy.city = function(id, name){
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
                    layerCityLi += "<li data-val='" + val.name + "'>" + val.name + "</li>";
                });
                layerCityCon.html(layerCityLi);
                layerCity.delegate("li","click",function(){
                    $("#whole-city").removeClass("active");
                    $(this).addClass("active").siblings().removeClass("active");
                });
                //不限市
                $("#whole-city").click(function(){
                    $(this).addClass("active");
                    layerCity.find("li").removeClass("active");
                });
            }
        });
    };

    //选择品牌
    _buy.brand = function(){
        var layerBrand = $("#layer-brand");
        var layerBrandCon = layerBrand.find("ul");
        var layerBrandLi = "<li data-val=''>不限</li>";
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
                        layerBrandLi += "<li data-id='" + val.id + "' data-val='" + val.name+ "'><img src='" + val.logo + "'>" + val.name + "</li>";
                    });                   
                });
                layerBrandCon.html(layerBrandLi);
                initheight3()
                layerBrand.delegate("li","click",function(){
                    if($(this).hasClass("layer-category")){
                        return;
                    };
                    if($(this).attr("data-val")==""){
                        $(this).addClass("active").siblings().removeClass("active");
                        return;
                    };
                    carType = "";
                    carModel = "";
                    $(this).addClass("active").siblings().removeClass("active");
                    brandId = $(this).attr("data-id");
                    _buy.type(brandId);
                    initheight4();
                    $("#layer-type").addClass("in");
                    $("#layer-model").removeClass("in");
                });               
            }
        });
    };

    //选择车系
    _buy.type = function(brandId){
        var layerType = $("#layer-type");
        var layerTypeCon = layerType.find("ul");
        var layerTypeLi = "<li data-val=''>不限</li>";
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
                        layerTypeLi += "<li data-id='" + val.id + "' data-val='" + val.name + "'>" + val.name + "</li>";
                    });                   
                });
                layerTypeCon.html(layerTypeLi);
                layerType.delegate("li","click",function(){
                    if($(this).hasClass("layer-category")){
                        return;
                    };
                    if($(this).attr("data-val")==""){
                        $(this).addClass("active").siblings().removeClass("active");
                        return;
                    };
                    carModel = "";
                    $(this).addClass("active").siblings().removeClass("active");
                    typeId = $(this).attr("data-id");
                    _buy.model(typeId);
                    initheight5();
                    $("#layer-model").addClass("in");
                });  
            }
        });
    };

    //选择车型
    _buy.model = function(typeId){
        var layerModel = $("#layer-model");
        var layerModelCon = layerModel.find("ul");
        var layerModelLi = "<li data-val=''>不限</li>";
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
                        layerModelLi += "<li data-id='" + val.id + "' data-val='" + val.name + "'>" + val.name + "</li>";
                    });                   
                });
                layerModelCon.html(layerModelLi);
                layerModel.delegate("li","click",function(){
                    if($(this).hasClass("layer-category")){
                        return;
                    };
                    if($(this).attr("data-val")==""){
                        $(this).addClass("active").siblings().removeClass("active");
                        return;
                    };
                    $(this).addClass("active").siblings().removeClass("active");
                });  
            }
        });
    };

    return _buy;
})(jQuery, Buy)

$(function(){ 
	
	setTimeout("initscroll();",500);

    //筛选
    Buy.filter();

    //将地址栏带过来的searchKey赋值给input
    if(searchKey){
        $("#search_key").val(searchKey);
    };

    //查询列表
    searchHelper.init();

    //点击搜索按钮查询列表
    $("#search-btn").click(function(){
        searchKey = $("#search_key").val();
        //清空其他筛选条件
        carBrand = "";  //品牌
        carType = "";  //车系
        carModel = "";  //车型
        category = "";  //车源类型
        carAge = ""; //车龄
        order = "0";  //排序规则
        $("#car-category li:first").addClass("active").siblings().removeClass("active");
        $("#car-age li:first").addClass("active").siblings().removeClass("active");
        $("#car-order li:first").addClass("active").siblings().removeClass("active");
        //刷新列表
        //$('.dropload-down').remove();
        
        searchHelper.initParams();
    });

    //车源
    $("#car-category li").click(function(){
        $(this).parents("li").removeClass("active");
        $(this).parent("ul").removeClass("in").css("max-height",0);
        $(".car-mask").fadeOut();
        $(this).addClass("active").siblings().removeClass("active");
        category = $(this).attr("data-val");
        //$('.dropload-down').remove();
        
        searchHelper.initParams();

    });

    //车龄
    $("#car-age li").click(function(){
        $(this).parents("li").removeClass("active");
        $(this).parent("ul").removeClass("in").css("max-height",0);
        $(".car-mask").fadeOut();
        $(this).addClass("active").siblings().removeClass("active");
        carAge = $(this).attr("data-val");
        //$('.dropload-down').remove();
        
        searchHelper.initParams();
    });

    //排序
    $("#car-order li").click(function(){
        $(this).parents("li").removeClass("active");
        $(this).parent("ul").removeClass("in").css("max-height",0);
        $(".car-mask").fadeOut();
        $(this).addClass("active").siblings().removeClass("active");
        order = $(this).attr("data-val");
        //$('.dropload-down').remove();
        
        searchHelper.initParams();
    });

    //选择位置
    $("#search-position").click(function(){
        $("#layer-province,#layer-header-address").addClass("in");
        Buy.province();
    });

    //取消位置
    $("#cancel-position").click(function(){
        $("#layer-province,#layer-city,#layer-header-address").removeClass("in");
    });

    //选择位置点击确定
    $("#sure-position").click(function(){
        nowProvince = $("#layer-province").find(".active").attr("data-val");
        nowCity = $("#layer-city").find(".active").attr("data-val");
        $("#layer-province,#layer-city,#layer-header-address").removeClass("in");
        if(!nowProvince){
        	$("#search-position").html("全国");
        	nowProvince = '';
        	nowCity = '';
        }else{
        	if(nowCity){
                $("#search-position").html(nowCity);
            }else{
            	nowCity = '';
                $("#search-position").html(nowProvince);
            }
        }
        
        //$('.dropload-down').remove();
        
        searchHelper.initParams();
    });

    //选择品牌
    $("#car-brand").click(function(){
        $("#layer-header-car,#layer-brand").addClass("in"); 
        Buy.brand();
    });

    //取消品牌车系车型
    $("#cancel-brand").click(function(){
        $("#layer-header-car,#layer-brand,#layer-type,#layer-model").removeClass("in");
    });

    //选择品牌车系车型点击确定
    $("#sure-brand").click(function(){
        carBrand = $("#layer-brand").find(".active").attr("data-val");
        carType = $("#layer-type").find(".active").attr("data-val");
        carModel = $("#layer-model").find(".active").attr("data-val");
        $("#layer-header-car,#layer-brand,#layer-type,#layer-model").removeClass("in");
        //$('.dropload-down').remove();
        
        searchHelper.initParams();
    });

    //解决input获得焦点footer遮挡问题
    //ios
    $('input').bind('focus',function(){  
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

