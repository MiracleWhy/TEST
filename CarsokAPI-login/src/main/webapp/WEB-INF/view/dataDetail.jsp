<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport" name="viewport">
    <title>资料管理</title>
    <link href="/carsokApi/css/base.css" type="text/css" rel="stylesheet" />
    <link href="/carsokApi/css/common.css" type="text/css" rel="stylesheet" />
    <link href="/carsokApi/css/my.css" type="text/css" rel="stylesheet" />
    <link href="https://cdn.bootcss.com/photoswipe/4.1.2/photoswipe.min.css" type="text/css" rel="stylesheet" />
    <link href="https://cdn.bootcss.com/photoswipe/4.1.2/default-skin/default-skin.min.css" type="text/css" rel="stylesheet" />
</head>

<body class="white-body">

    <div id="imglist" >
    </div>

<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="pswp__bg"></div>
    <div class="pswp__scroll-wrap">
        <div class="pswp__container">
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
        </div>
        <div class="pswp__ui pswp__ui--hidden">
            <div class="pswp__top-bar">
                <div class="pswp__counter"></div>
                <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>
                <!--<button class="pswp__button pswp__button&#45;&#45;share" title="Share"></button>
                <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>-->
                <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>
                <div class="pswp__preloader">
                    <div class="pswp__preloader__icn">
                        <div class="pswp__preloader__cut">
                            <div class="pswp__preloader__donut"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                <div class="pswp__share-tooltip"></div>
            </div>
            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
            </button>
            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
            </button>
            <div class="pswp__caption">
                <div class="pswp__caption__center"></div>
            </div>
        </div>
    </div>
    <input type="hidden" id="productId" value=""/>
    <input type="hidden" id="picId" value=""/>
    <input type="hidden" id="type" value=""/>
</div>
</body>
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="https://cdn.bootcss.com/photoswipe/4.1.2/photoswipe.min.js"></script>
<!-- UI JS file -->
<script src="https://cdn.bootcss.com/photoswipe/4.1.2/photoswipe-ui-default.min.js"></script>
<script src="/carsokApi/js/jquery.collapse.js"></script>
<script type="text/javascript">
    function auto_data_size() {
        $(".img-list a").each(function(v,k) {
            (function(v){
                var imgs = new Image();
                imgs.src = v.getAttribute('href');
                imgs.onload = function () {
                    var w = imgs.width,
                        h = imgs.height;
                    v.setAttribute("data-size", w + "x" + h);
                    v.setAttribute("data-med-size", w + "x" + h);
                }

            })(k)
        })

    };

    (function() {

        var initPhotoSwipeFromDOM = function(gallerySelector) {

            var parseThumbnailElements = function(el) {
                var thumbElements = el.childNodes,
                    numNodes = thumbElements.length,
                    items = [],
                    el,
                    childElements,
                    thumbnailEl,
                    size,
                    item;

                for(var i = 0; i < numNodes; i++) {
                    el = thumbElements[i];

                    // include only element nodes
                    if(el.nodeType !== 1) {
                        continue;
                    }

                    childElements = el.children;

                    size = el.getAttribute('data-size').split('x');
                    // create slide object
                    item = {
                        src: el.getAttribute('href'),
                        w: parseInt(size[0], 10),
                        h: parseInt(size[1], 10),
                        author: el.getAttribute('data-author'),
                        id:el.getAttribute('data-id')
                    };

                    item.el = el; // save link to element for getThumbBoundsFn

                    if(childElements.length > 0) {
                        item.msrc = childElements[0].getAttribute('src'); // thumbnail url
                        if(childElements.length > 1) {
                            item.title = childElements[1].innerHTML; // caption (contents of figure)
                        }
                    }

                    var mediumSrc = el.getAttribute('data-med');
                    var apicId = el.getAttribute('data-id');
                    if(mediumSrc) {
                        size = el.getAttribute('data-med-size').split('x');
                        // "medium-sized" image
                        item.m = {
                            src: mediumSrc,
                            w: parseInt(size[0], 10),
                            h: parseInt(size[1], 10),
                            id: apicId
                        };
                    }
                    // original image
                    item.o = {
                        src: item.src,
                        w: item.w,
                        h: item.h,
                        id: apicId
                    };

                    items.push(item);
                }

                return items;
            };

            // find nearest parent element
            var closest = function closest(el, fn) {
                return el && (fn(el) ? el : closest(el.parentNode, fn));
            };

            var onThumbnailsClick = function(e) {
                e = e || window.event;
                e.preventDefault ? e.preventDefault() : e.returnValue = false;

                var eTarget = e.target || e.srcElement;

                var clickedListItem = closest(eTarget, function(el) {
                    return el.tagName === 'A';
                });

                if(!clickedListItem) {
                    return;
                }

                var clickedGallery = clickedListItem.parentNode;

                var childNodes = clickedListItem.parentNode.childNodes,
                    numChildNodes = childNodes.length,
                    nodeIndex = 0,
                    index;

                for(var i = 0; i < numChildNodes; i++) {
                    if(childNodes[i].nodeType !== 1) {
                        continue;
                    }

                    if(childNodes[i] === clickedListItem) {
                        index = nodeIndex;
                        break;
                    }
                    nodeIndex++;
                }

                if(index >= 0) {
                    openPhotoSwipe(index, clickedGallery);
                }
                return false;
            };

            var photoswipeParseHash = function() {
                var hash = window.location.hash.substring(1),
                    params = {};

                if(hash.length < 5) { // pid=1
                    return params;
                }

                var vars = hash.split('&');
                for(var i = 0; i < vars.length; i++) {
                    if(!vars[i]) {
                        continue;
                    }
                    var pair = vars[i].split('=');
                    if(pair.length < 2) {
                        continue;
                    }
                    params[pair[0]] = pair[1];
                }

                if(params.gid) {
                    params.gid = parseInt(params.gid, 10);
                }

                return params;
            };

            var openPhotoSwipe = function(index, galleryElement, disableAnimation, fromURL) {
                var pswpElement = document.querySelectorAll('.pswp')[0],
                    gallery,
                    options,
                    items;

                items = parseThumbnailElements(galleryElement);

                // define options (if needed)
                options = {

                    galleryUID: galleryElement.getAttribute('data-pswp-uid'),

                    getThumbBoundsFn: function(index) {
                        // See Options->getThumbBoundsFn section of docs for more info
                        var thumbnail = items[index].el.children[0],
                            pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
                            rect = thumbnail.getBoundingClientRect();

                        return { x: rect.left, y: rect.top + pageYScroll, w: rect.width };
                    },

                    addCaptionHTMLFn: function(item, captionEl, isFake) {
                        if(!item.title) {
                            captionEl.children[0].innerText = '';
                            return false;
                        }
                        captionEl.children[0].innerHTML = item.title;
                        return true;
                    }

                };

                if(fromURL) {
                    if(options.galleryPIDs) {
                        // parse real index when custom PIDs are used
                        // http://photoswipe.com/documentation/faq.html#custom-pid-in-url
                        for(var j = 0; j < items.length; j++) {
                            if(items[j].pid == index) {
                                options.index = j;
                                break;
                            }
                        }
                    } else {
                        options.index = parseInt(index, 10) - 1;
                    }
                } else {
                    options.index = parseInt(index, 10);
                }

                // exit if index not found
                if(isNaN(options.index)) {
                    return;
                }

                var radios = document.getElementsByName('gallery-style');
                for(var i = 0, length = radios.length; i < length; i++) {
                    if(radios[i].checked) {
                        if(radios[i].id == 'radio-all-controls') {

                        } else if(radios[i].id == 'radio-minimal-black') {
                            options.mainClass = 'pswp--minimal--dark';
                            options.barsSize = { top: 0, bottom: 0 };
                            options.captionEl = false;
                            options.fullscreenEl = false;
                            options.shareEl = false;
                            options.bgOpacity = 0.85;
                            options.tapToClose = true;
                            options.tapToToggleControls = false;
                        }
                        break;
                    }
                }

                if(disableAnimation) {
                    options.showAnimationDuration = 0;
                }

                // Pass data to PhotoSwipe and initialize it

                gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);

                // see: http://photoswipe.com/documentation/responsive-images.html
                var realViewportWidth,
                    useLargeImages = false,
                    firstResize = true,
                    imageSrcWillChange;

                gallery.listen('beforeResize', function() {

                    var dpiRatio = window.devicePixelRatio ? window.devicePixelRatio : 1;
                    dpiRatio = Math.min(dpiRatio, 2.5);
                    realViewportWidth = gallery.viewportSize.x * dpiRatio;

                    if(realViewportWidth >= 1200 || (!gallery.likelyTouchDevice && realViewportWidth > 800) || screen.width > 1200) {
                        if(!useLargeImages) {
                            useLargeImages = true;
                            imageSrcWillChange = true;
                        }

                    } else {
                        if(useLargeImages) {
                            useLargeImages = false;
                            imageSrcWillChange = true;
                        }
                    }

                    if(imageSrcWillChange && !firstResize) {
                        gallery.invalidateCurrItems();
                    }

                    if(firstResize) {
                        firstResize = false;
                    }

                    imageSrcWillChange = false;

                });

                gallery.listen('gettingData', function(index, item) {
                    if(useLargeImages) {
                        item.src = item.o.src;
                        item.w = item.o.w;
                        item.h = item.o.h;
                        item.id = item.o.id;
                    } else {
                        item.src = item.m.src;
                        item.w = item.m.w;
                        item.h = item.m.h;
                        item.id = item.m.id;
                    }
                });
                gallery.listen('afterChange', function() {
                    var id = gallery.currItem.id;
                    $("#picId").val(id);
                });
                gallery.listen('close', function() {
                    $("#type").val("");
                    $("#picId").val("");
                });
                gallery.init();
            };

            // select all gallery elements
            var galleryElements = document.querySelectorAll(gallerySelector);
            for(var i = 0, l = galleryElements.length; i < l; i++) {
                galleryElements[i].setAttribute('data-pswp-uid', i + 1);
                galleryElements[i].onclick = onThumbnailsClick;
            }

            // Parse URL and open gallery if it contains #&pid=3&gid=1
            var hashData = photoswipeParseHash();
            if(hashData.pid && hashData.gid) {
                openPhotoSwipe(hashData.pid, galleryElements[hashData.gid - 1], true, true);
            }
        };

        $("#productId").val(${productId});
        var productId = $("#productId").val();
        var imglist = '';
        $.ajax({
            type: 'post',
            url: "/carsokApi/datumManage/pictureList.do",
            dataType: 'json',
            data: {
                "productId":productId
            },
            success: function (data) {
                auto_data_size();
                if (data.data.length != 0) {
                    if (data.data.carPicList.length != 0 && data.data.carPicList != null) {
                        imglist = "<section class='border-box data-pic-list' data-collapse='accordion'>" +
                            "<h3 class='border-box'>车辆照片</h3>" +
                            "<div class='clearfix img-list'  itemscope itemtype='http://schema.org/ImageGallery'>";
                        for (var i = 0; i < data.data.carPicList.length; i++) {
                            imglist += "<a href=" + data.data.carPicList[i].picPath + " data-med=" + data.data.carPicList[i].picPath + " class='col-3  border-box demo-gallery__img--main' onclick='setId(0)'> " +
                                "<div class='data-pic-box ' style='background-image: url(" + data.data.carPicList[i].picPath + ");'></div> " +
                                " <p>车辆照片</p></a>";
                        }
                        imglist += "</div></section>";
                    }
                    var boolAll = data.data.zbPicList.length != 0 && data.data.zbPicList != "";
                    var boolFirst = data.data.zbPicList.length>=1 && data.data.zbPicList[0]!="";
                    var boolEach = false;
                    var boolBill = false;
                    if(boolFirst){
                        boolEach = data.data.zbPicList[0].arcPath != ""  ||
                            data.data.zbPicList[0].dlPath != "" || data.data.zbPicList[0].policyPath != ""
                            || data.data.zbPicList[0].idcardPath != "";
                        boolBill  = data.data.zbPicList[0].zbBillPicResponse.length != 0 && data.data.zbPicList[0].zbBillPicResponse != "";
                    }
                   if ((boolAll && boolFirst) && (boolEach | boolBill)) {
                        imglist += "<section class='border-box data-pic-list' data-collapse='accordion'>" +
                            "<h3 class='border-box'>整备照片</h3>" +
                            "<div class='clearfix img-list' itemscope itemtype='http://schema.org/ImageGallery'>";
                        for (var i = 0; i < data.data.zbPicList.length; i++) {
                            if(data.data.zbPicList[i].arcPath != null && data.data.zbPicList[i].arcPath !=""){
                                imglist += "<a href=" + data.data.zbPicList[i].arcPath + " data-med=" + data.data.zbPicList[i].arcPath + " class='col-3  border-box demo-gallery__img--main' onclick='setId(0)'> " +
                                    "<div class='data-pic-box ' style='background-image: url(" + data.data.zbPicList[i].arcPath + ");'></div> " +
                                    " <p>车辆大照</p></a>";
                            }
                            if(data.data.zbPicList[i].dlPath != null && data.data.zbPicList[i].dlPath != "" ){
                                imglist += "<a href=" + data.data.zbPicList[i].dlPath + " data-med=" + data.data.zbPicList[i].dlPath + " class='col-3  border-box demo-gallery__img--main' onclick='setId(0)'> " +
                                    "<div class='data-pic-box ' style='background-image: url(" + data.data.zbPicList[i].dlPath + ");'></div> " +
                                    " <p>行驶证</p></a>";
                            }
                            if(data.data.zbPicList[i].policyPath != null && data.data.zbPicList[i].policyPath != ""){
                                imglist += "<a href=" + data.data.zbPicList[i].policyPath + " data-med=" + data.data.zbPicList[i].policyPath + " class='col-3  border-box demo-gallery__img--main' onclick='setId(0)'> " +
                                    "<div class='data-pic-box ' style='background-image: url(" + data.data.zbPicList[i].policyPath + ");'></div> " +
                                    " <p>保单</p></a>";
                            }
                            if(data.data.zbPicList[i].idcardPath != null && data.data.zbPicList[i].idcardPath != ""){
                                imglist +=  "<a href=" + data.data.zbPicList[i].idcardPath + " data-med=" + data.data.zbPicList[i].idcardPath + " class='col-3  border-box demo-gallery__img--main' onclick='setId(0)'> " +
                                    "<div class='data-pic-box ' style='background-image: url(" + data.data.zbPicList[i].idcardPath + ");'></div> " +
                                    " <p>身份证复印件</p></a>";
                            }
                            if (data.data.zbPicList[i].zbBillPicResponse.length != 0 && data.data.zbPicList[i].zbBillPicResponse != null) {
                                var zbBill = data.data.zbPicList[i].zbBillPicResponse;
                                for (var j = 0; j < zbBill.length; j++) {
                                    imglist += "<a href=" + zbBill[j].billPic + " data-med=" + zbBill[j].billPic + " class='col-3  border-box demo-gallery__img--main' onclick='setId(0)'> " +
                                        "<div class='data-pic-box ' style='background-image: url(" + zbBill[j].billPic + ");'></div> " ;
                                        if(zbBill[j].billName != null && zbBill[j].billName != ""){

                                            imglist += " <p>" + zbBill[j].billName + "</p></a>";
                                        }else{
                                            imglist += " <p>单据图片</p></a>";
                                        }
                                }
                            }
                        }
                        imglist += "</div></section>";
                    }
                    if((data.data.contractPicList.length != 0 && data.data.contractPicList != null) || (data.data.newContractPicList.length != 0 && data.data.newContractPicList != null)){
                        imglist += "<section class='border-box data-pic-list'  data-collapse='accordion'>" +
                        "<h3 class='border-box'>合同展示</h3><div>";
                        var pdfList = "<div class='clearfix pdf-list'>";
                        var picList = "<div class='clearfix img-list' itemscope itemtype='http://schema.org/ImageGallery'>";
                        if(data.data.contractPicList.length != 0 && data.data.contractPicList != null){
                            var contractPicList = data.data.contractPicList;
                            for(var i = 0; i < contractPicList.length; i++){
                                pdfList += "<a href="+contractPicList[i].contractUrl+" class='col-3  border-box' onclick='setId(0)'>" +
                                    "<div class='data-pic-box data-pdf'></div>" +
                                    "<p>"+contractName(contractPicList[i].contractType)+"</p></a>";
                            }
                        }
                        if(data.data.newContractPicList.length != 0 && data.data.newContractPicList != null){
                               var newpicList = data.data.newContractPicList;
                               for(var i = 0; i < newpicList.length; i++){
                                   var contractUrl = newpicList[i].contractUrl.replace("[","").replace("]","").split(",");
                                   if(contractUrl.length > 0){
                                       for(var j = 0 ; j < contractUrl.length; j++){
                                           picList += "<a href=" + contractUrl[j] +"data-med="+ contractUrl[j] +" class='col-3  border-box demo-gallery__img--main' onclick='setId(0)'>" +
                                               "<div class='data-pic-box' style='background-image: url("+ contractUrl[j] +");'></div>" +
                                               "<p>"+newpicList[i].contractName +"</p></a>";
                                       }
                                   }
                               }
                        }

                        pdfList += "</div>";
                        picList += "</div></div></section>";
                        imglist += pdfList + picList;
                    }
                    if (data.data.newPicList.length != 0 && data.data.newPicList != null) {
                        imglist += "<section class='border-box data-pic-list' data-collapse='accordion'>" +
                            "<h3 class='border-box'>新增照片</h3>" +
                            "<div class='clearfix img-list' itemscope itemtype='http://schema.org/ImageGallery'>";
                        for (var i = 0; i < data.data.newPicList.length; i++) {
                            imglist += "<a href=" + data.data.newPicList[i].picUrl + " data-med=" + data.data.newPicList[i].picUrl + " class='col-3  border-box demo-gallery__img--main' data-id='"+data.data.newPicList[i].id+"' onclick='setId("+data.data.newPicList[i].id+")'> " +
                                "<div class='data-pic-box ' style='background-image: url(" + data.data.newPicList[i].picUrl + ");'></div> " +
                                " <p>" + data.data.newPicList[i].picName + "</p></a>";
                        }
                        imglist += "</div></section>";

                    }
                    $("#imglist").append(imglist);
                    auto_data_size();
                    initPhotoSwipeFromDOM('.img-list');
                }
                $('body').collapse(false, true);
            }
        });

    })();
    function contractName(contractType) {
        var contract='';
        switch (contractType){
            case 1:
                contract = "试驾协议";
                break;
            case 2:
                contract = "交车确认单";
                break;
            case 3:
                contract = "二手车交易合同书";
                break;
            case 4:
                contract = "二手车寄售合同书";
                break;
            case 5:
                contract = "寄售车取回确认单";
                break;
            case 6:
                contract = "汽车保修合同";
                break;
            case 7:
                contract = "定金收据";
                break;
            case 8:
                contract = "机动车交易合同书";
                break;
            case 9:
                contract = "TPS交易促进系统-保密协议书";
                break;
            case 10:
                contract = "新车合同";
                break;
        }
        return contract;
    }

    function setId(a){
        if(a == 0){
            $("#type").val("");
        }else{
            $("#picId").val(a);
            $("#type").val("newpic");
        }
    }
</script>

</html>