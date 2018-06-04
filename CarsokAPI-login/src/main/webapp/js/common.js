var Uton = (function(){
    var _uton = {};

    //搜索页清空搜索内容
    _uton.emptySearch = function(){
        
        $("body").delegate("#search_key","input propertychange",function(){
            if($(this).val() != ""){
                $(".search-delete").show();
            }else{
                $(".search-delete").hide();
            }
        })

        $("body").delegate(".search-delete","click",function(){
            $("#search_key").val("");
            $(this).hide();
        })
    };

    _uton.init = function(){
        var key = $("#search_key").val();
        if(key == ""){
            $(".search-delete").hide();
        }else{
            $(".search-delete").show();
        }
        _uton.emptySearch();
    };

    //提示弹层
    _uton.tip = function(txt){
        $("#car-tip").html(txt);
        $("#car-tip").fadeIn().delay(1500).fadeOut();
    };

    //控制只能输入数字并且最多允许小数点两位
    _uton.clearNoNum = function(obj){
        obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符   
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的   
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数   
        if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额  
            obj.value= parseFloat(obj.value);  
        };    
    }; 

    return _uton;
})(jQuery, Uton)

window.modalTip = function (msg, okCallback, cancelCallback) {
    var $ok = $('#alert-layer .btnOks'),
        $cancel = $('#alert-layer .btnCancel');
    if (msg) {
        $('#alert-layer h3').html(msg);
    }
    $ok.off('click');
    $cancel.off('click');
    $('#alert-layer').fadeIn();
    $('.msg-bg').fadeIn();

    if (typeof okCallback == 'function') {
        $ok.on('click', function () {
            setTimeout(function () {
                okCallback();
            }, 500);
            // $('.modal-backdrop.fade.in').remove();
            $('#alert-layer').fadeOut();
            $('.msg-bg').fadeOut();
        });
    } else {
        $ok.on('click', function () {
            // $('.modal-backdrop.fade.in').remove();
            $('#alert-layer').fadeOut();
            $('.msg-bg').fadeOut();
        });
    }
    if (typeof cancelCallback == 'function') {
        $('.btnCancels').show();
        $cancel.on('click', function(){
            setTimeout(function () {
                cancelCallback();
            }, 500);
            $('#alert-layer').fadeOut();
            $('.msg-bg').fadeOut(); 
        });
    } else {
        $('.btnCancels').hide();
    }
};

//从地址栏取参数
function getQueryString(name) {  
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    var r = location.search.substr(1).match(reg);  
    if (r != null) return unescape(decodeURI(r[2])); return "";  
}

String.prototype.endWith=function(str){     
  var reg=new RegExp(str+"$");     
  return reg.test(this);        
}

//判断是否为微信浏览器
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}

//判断是否为微信浏览器
if(isWeiXin()){
    $("footer").removeClass("footer-not-weixin");
    $("#setup-shop").addClass("setup-shop-not-weixin");
    $("#setup-shop1,#setup-shop2").hide();
    $("footer .footer-shadow").show();
    $("#see-publishcar").show();
    $("#setup-shop").attr("href","http://m.utonw.com/col.jsp?id=110");
}else{
    $("footer").addClass("footer-not-weixin");
    $("#setup-shop").addClass("setup-shop-not-weixin");
    $("#setup-shop1,#setup-shop2").hide();
    $("footer .footer-shadow").hide();
    $("#see-publishcar").hide();
    $("#setup-shop").attr("href","https://itunes.apple.com/cn/app/che-shangapp/id1181815923?l=zh&ls=1&mt=8");
}



