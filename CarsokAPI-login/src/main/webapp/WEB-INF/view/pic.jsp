<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" id="viewport"
          name="viewport">
    <title></title>
    <style type="text/css">
        html{ height:100%;}
        body{}
        *{ padding:0; margin:0;}

        div.pinch-zoom {
            position: relative;
        }
        div.pinch-zoom img{
            width: 100%; max-height:100%;
            -webkit-user-drag: none;
            padding:0; margin:0;
        }

    </style>
</head>
<body>
<table style="width: 100%; border:none; position:fixed; height:100%;">
    <tr>
        <td style="text-align: center; vertical-align: middle;">
            <div class='pinch-zoom'><img id="pic" src='/carsokApi/images/banner.png'/></div>
        </td>
    </tr>
</table>
</body>
<!-- pinchzoom requires: jquery and underscore -->
<script src="/carsokApi/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/carsokApi/js/underscore.js"></script>
<script type="text/javascript" src="/carsokApi/js/pinchzoom.js"></script>
<script type="text/javascript">

    $(function () {
        $('div.pinch-zoom').each(function () {
            new RTP.PinchZoom($(this), {});
        });
    })
//接收图片路径参数
    var pictures = "<%=request.getParameter("pic")%>";
    //展示图片
    $("#pic").attr("src",pictures);


</script>
</html>
