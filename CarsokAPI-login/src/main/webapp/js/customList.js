$(function(){
    // 页数
    var page = 0;
    // 每页展示10个
    var size = 10;

    // dropload
    $('#friend-con').dropload({
        scrollArea : window,
        loadDownFn : function(me){
            page++;
            // 拼接HTML
            var result = '';
            $.ajax({
                type: 'post',
                url: '/user/getMyFansInfo',
                dataType: 'json',
                data: {
                    "page": page,
                    "pageSize": size
                },
                success: function(data){
                    var dataList = data.data;
                    if(data.retCode == "0000"){
                        $.each(dataList, function(index,val){
                            result +=   '<tr><td>2017-3-9 12:10</td><td>2017-3-9 13:10</td><td>某某</td><td>175825454121</td><td>√</td><td></td><td></td><td>路过</td><td>A3</td><td>重点</td></tr>';

                        });
                    // 如果没有数据
                    }else if(data.retCode == "1000"){
                        // 锁定
                        me.lockdrop();
                        // 无数据
                        me.noData();
                    }else{
                        return;
                    }                    
                    // 插入数据到页面，放到最后面
                    $('#custom-con table tbody').append(result);
                    // 每次数据插入，必须重置
                    me.resetload();
                },
                error: function(xhr, type){
                    //alert('Ajax error!');
                    // 即使加载出错，也得重置
                    //me.resetload();
                }
            });
        }
    });
});