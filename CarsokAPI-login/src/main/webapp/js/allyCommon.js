function acceptFriend(id, friendAccount, accountCode, flag,searchParam) {
    var json = {
        "id": id,
        "friendAccount": friendAccount,
        "accountCode": accountCode,
        "applyMessage": "",
        "relationStatus": 2
    };
    layer.open({
        content: '<h2 class="layer-tit">确定接受该盟友？</h2>',
        btn: ['确定', '取消'],
        yes: function () {
            $.ajax({
                type: 'post',
                url: "/carsokApi/ally/addFriend.do",
                "headers": {
                    "content-type": "application/json"
                },
                data: JSON.stringify(json),
                success: function (data) {
                    layer.open({
                        content: '<h2 class="layer-tit">添加成功</h2>',
                        btn: ['确定'],
                        yes: function () {
                            function closeLayer() {
                                $('.layui-m-layer').remove();
                            }

                            if (flag == 1) {
                                search1($("#searchParam").val(),closeLayer())
                            } else {
                                pageLoad(closeLayer());
                            }
                        }
                    })
                },
            })
        }
    });
}

function applyFriend(accountCode,flag,searchParam) {

    layer.open({
        content: '<h2 class="layer-tit">添加盟友信息</h2> <input type="text" class="layer-txt" id="applyMessage" placeholder="请填写申请信息(请输入少于三十个字符)"><p id="hiddenBox" style="color: red;display: none;margin-top: 5px;">输入信息多于三十个字符！</p>',
        btn: ['确定', '取消'],
        yes: function () {
            var applyMessage = $("#applyMessage").val();
            if (applyMessage.length > 30) {
                $("#hiddenBox").show();
                return false;
            } else {
                $("#hiddenBox").hide();
            }
            var json = {
                "friendAccount": accountCode,
                "accountCode": $("#accountCode").val(),
                "applyMessage": applyMessage,
                "relationStatus": 1
            };
            $.ajax({
                type: 'post',
                url: "/carsokApi/ally/addFriend.do",
                "headers": {
                    "content-type": "application/json"
                },
                data: JSON.stringify(json),
                success: function (data) {
                    layer.open({
                        content: '<h2 class="layer-tit">申请成功</h2>',
                        btn: ['确定'],
                        yes: function () {
                            function closeLayer() {
                                $('.layui-m-layer').remove();
                            }

                            if (flag == 1) {
                                search1($("#searchParam").val(),closeLayer());
                            } else {
                                pageLoad(closeLayer());
                            }
                        }
                    })
                },
            })
        }
    });
}

function search1(searchParam) {
    $("#friendList").html("");
    $("#searchParam").val(searchParam);
    searchBy(searchParam);
}
function searchBy(searchParam, fn) {
    $("#searchBtn").attr("disabled", true);
    var weiShopUrl = $("#weiShopUrl").val();
    var flag = 1;
    var page = 0;
    $("#friendList").dropload({
        scrollArea: window,
        loadDownFn: function (me) {
            var friendList = "";
            page++;
            $.ajax({
                type: 'post',
                url: "/carsokApi/ally/searchFriendList.do",
                dataType: 'json',
                data: {
                    "searchName": $("#search_key").val(),
                    "accountCode": $("#accountCode").val(),
                    "ver": 1,
                    "pageNum": page
                },
                success: function (data) {
                    if (searchParam == 'add') {
                        friendList += "<ul>";
                    }
                    if (data.data.length > 0 && data.data != null) {
                        var newdata = data.data;
                        for (var i = 0; i < newdata.length; i++) {
                            if (2 == newdata[i].relationStatus) {
                                friendList += "<a href=" + weiShopUrl + newdata[i].accountCode + ".html>"
                            }
                            if (1 == newdata[i].relationStatus) {
                                if (newdata[i].carAccountCode == $("#accountCode").val()) {
                                    friendList += "<li>";
                                } else if (newdata[i].friendAccount == $("#accountCode").val()) {
                                    friendList += "<li class='delete' data-id='" + newdata[i].id + "'>";
                                } else {
                                    friendList += "<li>";
                                }
                            } else {
                                friendList += "<li>";
                            }
                            friendList += "<div class='friendImg'>" +
                                "<img src=" + newdata[i].merchantImagePath + "?imageView2/1/w/140/h/95" + ">" +
                                "</div>" +
                                "<div class='friendDesc'>" +
                                "<ul>" +
                                "<li>" + newdata[i].merchantName + "</li>" +
                                "<li>负责人: <span>" + newdata[i].contactName + "</span></li>" +
                                "<li>在售车辆: <span>" + newdata[i].val + " 辆</span></li>" +
                                "<li class='liaddr'>" + newdata[i].merchantAddress + "</li>" +
                                "</ul>";
                            if (1 == newdata[i].relationStatus) {
                                if (newdata[i].carAccountCode == $("#accountCode").val()) {
                                    friendList += "<div class='linkBtn'>申请中</div>";
                                } else if (newdata[i].friendAccount == $("#accountCode").val()) {
                                    friendList += "<div class='linkBtn' onclick='acceptFriend(" + newdata[i].id + "," + newdata[i].friendAccount + "," + newdata[i].carAccountCode + "," + flag + ")'>接受</div>";
                                } else {
                                    friendList += "<div class='linkBtn' onclick='applyFriend(" + newdata[i].accountCode + "," + flag + ")'>添加盟友</div>";
                                }
                            } else if (2 == newdata[i].relationStatus) {
                                if (newdata[i].carAccountCode == $("#accountCode").val() || newdata[i].friendAccount == $("#accountCode").val()) {
                                    friendList += "";
                                } else {
                                    friendList += "<div class='linkBtn' onclick='applyFriend(" + newdata[i].accountCode + "," + flag + ")'>添加盟友</div>";
                                }
                            } else if (0 == newdata[i].relationStatus || null == newdata[i].relationStatus || 3 == newdata[i].relationStatus) {
                                friendList += "<div class='linkBtn' onclick='applyFriend(" + newdata[i].accountCode + "," + flag + ")'>添加盟友</div>";
                            } else {
                                friendList += "";
                            }
                            friendList += "</div></li>";
                            if (2 == newdata[i].relationStatus) {
                                friendList += "</a>"
                            }
                        }
                        if (searchParam == 'add') {
                            friendList += "</ul>";
                        }
                    } else if (data.data.length < 10) {

                        // 锁定
                        me.lockdrop();
                        // 无数据
                        me.noData();
                    } else if (data.data.length == 0) {
                        friendList += "<div style='width: 100%;text-align: center;position: relative'>" +
                            "<img src='../images/no-msg.png' alt='' style='width: 50%;margin-top: 15%'>" +
                            "</div>";
                        if (searchParam == 'add') {
                            friendList += "</ul>";
                        }
                        // 锁定
                        me.lockdrop();
                        // 无数据
                        me.noData();
                    } else {
                        return;
                    }
                    $(".dropload-down").before(friendList);
                    me.resetload();

                    if (fn) {
                        fn();
                    }
                    $("#searchBtn").attr("disabled", false);
                }
            });
        }
    });



}