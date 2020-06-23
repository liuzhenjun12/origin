var jin=function () {
    this.version={version:'1.0'};
    this.corpid='';
    this.corpName='';
    this.userid='';
    this.Lockid='';
    this.role=0;
    this.lockStatus=false;
}
jin.fn=jin.prototype={
    constructor:jin,
    init:function () {
        var top=screen.height;
        if(top<650){
            $("#suo").css("margin-top","9%");
        }
        if (top>650&&top<730){
            $("#suo").css("margin-top","25%");
        }
        if (top>730&&top<800){
            $("#suo").css("margin-top","35%");
        }
        if (top>800){
            $("#suo").css("margin-top","45%");
        }
        $N.corpid=$N.getCookie("CorpId");
        $N.userid=$N.getCookie("LoginId");
        $N.shuaxin();
    },
    shuaxin:function(){
        $N.getuserlocks($N.corpid,$N.userid);
    },
    backy:function(){
        $("body").css("background","url('../imgage/home5.jpeg\') no-repeat ");
        $("body").css("background-size","cover");
        $(".weui-tabbar").css("background-color","none");
        // $(".weui-tabbar__icon").css("color","white");
    },
    backn:function(){
        $("body").css("background","none");
        $("body").css("background-size","cover");
        $(".weui-tabbar").css("background-color","white");
        // $(".weui-tabbar__icon").css("color","#c4c4c4");
    },
    logcount:function (corpid,userid,values,type) {
        $.post($N.getRootPath()+"/v1/getLogsByuserid",{"corpid":corpid,"userid":userid,"date":values.toString(),"type":type.toString()},function(data){
            if(data.rtnCode==200) {
                $("#logcount").html(data.total)
            }else {
                $("#logcount").html("0")
            }
        })
    },
    getcurrdate:function(is){
        var day1 = new Date();
        var month=day1.getMonth()+1;
        if(month<10){
            month="0"+month;
        }
        var day=day1.getDate();
        if(day<10){
            day="0"+day;
        }
        if(is){
            return day1.getFullYear()+"-" + month + "-" + day;
        }else {
            return day1.getFullYear()+"" + month + "" + day;
        }
    },
    getUrl:function(){
        return location.href.split("#")[0];
    },
    getToken:function (obj) {
        $.ajax({
            url : $N.getRootPath()+"/v1/getSignature",
            type : "POST",
            dataType : "json",
            data:{
                url:$N.getUrl(),page:obj,corpid:$N.corpid
            },
            async:false,
            success : function(data) {
                var json = data.data;
                wx.config({
                    beta : true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
                    debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId : json.appId, // 必填，企业微信的corpID
                    timestamp : json.timestamp, // 必填，生成签名的时间戳
                    nonceStr : json.nonceStr, // 必填，生成签名的随机串
                    signature : json.signature,// 必填，签名，见附录1
                    jsApiList : [ 'checkJsApi', 'startWifi','getConnectedWifi','openLocation','getLocation','startAutoLBS','stopAutoLBS','onLocationChange']
                    // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                });
                wx.ready(function() {
                    wx.invoke('startAutoLBS',{
                            type: 'gcj02', // wgs84是gps坐标，gcj02是火星坐标
                        },
                        function(res) {
                            if(res.err_msg == "startAutoLBS:ok"){
                                //调用成功
                            }else {
                                //错误处理
                            }
                        });

                });
                wx.error(function(res){
                    alert(res)
                });
            }
        })
        $.ajax({
            url : $N.getRootPath()+"/v1/agent_config",
            type : "POST",
            dataType : "json",
            data:{
                url:$N.getUrl(),page:obj,corpid:$N.corpid
            },
            async:false,
            success : function(data) {
                var json = data.data;
                agentid=json.agentid;
                wx.agentConfig({
                    corpid : json.appId, // 必填，企业微信的corpID
                    agentid: json.agentid, // 必填，企业微信的应用id
                    timestamp : json.timestamp, // 必填，生成签名的时间戳
                    nonceStr : json.nonceStr, // 必填，生成签名的随机串
                    signature : json.signature,// 必填，签名，见附录1
                    jsApiList : [ 'startWifi','getConnectedWifi','openLocation','getLocation','startAutoLBS','stopAutoLBS','onLocationChange'],
                    success: function(res) {
                    },
                    fail: function(res) {
                        if(res.errMsg.indexOf('function not exist') > -1){
                            alert('版本过低请升级')
                        }
                    }
                });
            }
        })
    },
    /**
     * 锁具列表
     * @param corpid
     * @param userid
     */
    getuserlocks:function (corpid,userid) {
        $.ajax({
            type : "POST",
            url : "/web/lock/getByCorpAndUser",
            data: {"corpid":corpid,"userid":userid},
            dataType : "json",
            beforeSend:function(){
            },
            success : function(data) {
               $("#lockinfo").empty();
                if(data.code==200) {
                    var str="";
                    $.each(data.data, function (index, items) {
                        var name="";
                        if(items.name==null||items.name==''){
                            name=items.deviceSn;
                        }else {
                            name=items.name;
                        }
                        str+="<div class='swiper-slide' style='margin-left: auto;margin-right: auto;'><a onclick='$N.openlock($N.corpid,$N.userid,"+items.deviceSn+")'><div class='lockqu'><div  class=\"lock\"><div id='hdd' class=\"head\"><div class='notch'></div></div><div id='zhuyao' class=\"body\"><span class='bisuo'>闭锁</span></div></div></div></a><div style='text-align: center;font-size: 14px;color: darkblue' id='xiaoxi'>"+name+"</div></div>";
                        // str+="<div class='swiper-slide' style='margin-left: auto;margin-right: auto;'><a onclick='$N.socket()'><div class='lockqu'><div  class=\"lock\"><div id='hdd' class=\"head\"></div><div id='zhuyao' class=\"body\"><span class='bisuo'>闭锁</span></div></div></div></a><div style='text-align: center;font-size: 14px;color: darkblue' id='xiaoxi'>"+name+"</div></div>";

                    });
                    $("#lockinfo").html(str);
                }else {
                    var str="";
                    str+="<div class='swiper-slide' style='margin-left: auto;margin-right: auto;'><a onclick='$N.getuserlocks($N.corpid,$N.userid)'><div class='lockqu'><div  class=\"lock\"><div id='hdd' class=\"head\"><div class=\"notch\"></div></div><div id='zhuyao' class=\"body\"><span class='bisuo'>稍等</span></div></div></div></a><div style='text-align: center;font-size: 14px;color: red' id='xiaoxi'></div></div>";
                   $("#lockinfo").html(str);
                    setTimeout(function () {
                        $.toast(data.message);
                        $(".bisuo").text("没锁");
                        $("#xiaoxi").html("没有设备");
                    },2500)
                }
            },
            timeout : 15000
        });
    },
    /**
     * 开锁
     * @param corpid
     * @param userid
     * @param sn
     */
    openlock:function(corpid,userid,sn){
        if(!$N.lockStatus) {
            $N.lockStatus=true;
            $(".bisuo").text("稍等");
            $.post("/web/lock/openLock", {"corpid": corpid, "userid": userid, "deviceSn": sn}, function (data) {
                if (data.code == 200) {
                    $(".bisuo").text("开锁");
                    $(".head").addClass("kaikai");
                    $.toast(data.message);
                    setTimeout(function () {
                        // $(".head").removeClass("kaikai");
                        $(".bisuo").text("闭锁");
                    }, 1500)
                } else {
                    $(".head").addClass("guan");
                    setTimeout(function () {
                        $(".head").removeClass("guan");
                        $(".bisuo").text("闭锁");
                        $.toast(data.message);
                    }, 1500)
                }
            })
            $N.lockStatus=false;
        }
    },
    /**
     * 日志列表
     * @param corpid
     * @param userid
     * @param type
     * @param values
     */
    wylog:function (corpid,userid,type,values) {
        $("#loginfo").empty();
        $.post("/web/log/getLogsByuserid",{
            "corpid":corpid,
            "userid":userid,
            "methodtype":type.toString(),
            "date":values.toString()
        },function(data){
            if(data.code==200) {
                var str=""
                $.each(data.data, function (index, items) {
                    str+=" <li> <div class=\"card\"><div class=\"card-content\"> <div class=\"list-block media-list\"> <ul><li class=\"item-content\"><div class=\"item-media\">";
                    str+="  <span class='openlock'></span></div>";
                    str+=" <div class=\"item-inner\"> <div class=\"item-title-row\"> <div class=\"item-title\"> 类型:<span style='color: #777'>"+items.methodjc+"</span></div></div> <div class=\"item-subtitle\"> 结果:<span style='color: #777'>"+items.result+"</span></div></div></li></ul>";
                    str+="</div></div> <div class=\"card-footer\"><span>"+items.createdate+"</span> <a onclick=\"$N.deleteLog('"+items.id+"','"+corpid+"','"+userid+"','"+type+"','"+values.toString()+"')\" style='color: #0c66a5'>删除</a></div></div></li>";
                });
                var top=screen.height;
                if(top<650){
                    $("#loginfo").css("margin-top","310px");
                }
                if (top>650&&top<730){
                    $("#loginfo").css("margin-top","310px");
                }
                if (top>730&&top<800){
                    $("#loginfo").css("margin-top","340px");
                }
                if (top>800){
                    $("#loginfo").css("margin-top","310px");
                }
                $("#loginfo").html(str);
            }else {
                str="<li><div class=\"card\"> <div class=\"card-content\"><div class=\"card-content-inner\" style='text-align: center;'>" +
                    "<img src='/static/imgage/meiyou.png' ><div style='color: #c0c0c0'>没有匹配记录</div></div></div></div></li>";
                var top=screen.height;
                if(top<650){
                    $("#loginfo").css("margin-top","310px");
                }
                if (top>650&&top<730){
                    $("#loginfo").css("margin-top","310px");
                }
                if (top>730&&top<800){
                    $("#loginfo").css("margin-top","340px");
                }
                if (top>800){
                    $("#loginfo").css("margin-top","310px");
                }
                $("#loginfo").html(str);
            }
        })
    },
    /**
     * 删除日志
     * @param logid
     * @param corpid
     * @param userid
     * @param methodtype
     * @param date
     */
    deleteLog:function(logid,corpid,userid,methodtype,date){
        $.post('/web/log/delete', {
            id:logid
        },function(result) {
            if (result.code==200) {
                $N.wylog(corpid, userid, methodtype, date);
            }else{
                $.toast(result.message);
            }
        }, 'JSON');
    },
    getRootPath:function () {
        var pathName = window.location.pathname.substring(1);
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
        return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    },
    setCookie:function (name,value) {
        document.cookie = name + "="+ escape (value) + ";expires=60000";
    },
    getCookie:function (name) {
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    },
    delCookie:function (name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval=getCookie(name);
        if(cval!=null)
            document.cookie= name + "="+cval+";expires="+exp.toGMTString();
    },
    socket:function () {
        var socket;
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            //等同于socket = new WebSocket("ws://localhost:8083/checkcentersys/websocket/20");
            socket = new WebSocket("ws://188.131.169.117:8040/wg/1");
            //打开事件

            socket.onopen = function() {
                socket.send('{"toUserId":"'+$N.userid+'","contentText":"'+$N.corpid+'"}');
                 console.log("Socket 已打开ws://safe.jinchu.com.cn:8040/websocket/1");
            };
            //获得消息事件
            socket.onmessage = function(msg) {
                console.log("获得消息事件"+msg.data);
                //发现消息进入    开始处理前端触发逻辑
            };
            //关闭事件
            socket.onclose = function() {
                console.log("Socket已关闭");
            };
            //发生了错误事件
            socket.onerror = function() {
                alert("Socket发生了错误");
                //此时可以尝试刷新页面
            }
        }

    },
    wxsocket:function () {
        if ("WebSocket" in window)
        {
            console.log("您的浏览器支持WebSocket");

            // 打开一个 web socket
            var ws = new WebSocket("wss://openhw.work.weixin.qq.com/");

            ws.onopen = function(){
                // var json="{\"cmd\":\"get_secret_no\",\"headers\":{\"req_id\":\"12094417505251386371\"},\"body\":{\"device_signature\":\"126589D3EDC256E90158655FA00CB5EC8352514A\",\"nonce\":\"123451\",\"timestamp\":\"1571985217\",\"sn\":\"515068000077\"}}";
                // $.post($N.getRootPath()+"/v1/wxopenlock",{"userid":"LiuZhenJun","type":1},function(data){
                //     var str="";
                //     if(data.rtnCode==200) {
                //         console.log(data.data)
                //         ws.send(data.data);
                //     }
                // })
                // $.post($N.getRootPath()+"/v1/genDeviceSignature",{"sn":"515068000004","secretNo":"eb61b38e066dbc54574fdc1957211424"},function(data){
                //     if(data.rtnCode==200) {
                //                 console.log(data.data)
                //                 ws.send(data.data);
                //             }
                // });
                // $.post($N.getRootPath()+"/v1/deviceRegister",{"sn":"515068000004","secretNo":"c99f74fbc72138229e9453b00d91c528"},function(data){
                //     if(data.rtnCode==200) {
                //         console.log(data.data)
                //         ws.send(data.data);
                //     }
                // });
                $.post($N.getRootPath()+"/v1/deviceActive",{"active_code":"AI3VIJt5S4R4NqcuBHVA6ff4YHCJCkU68WUDbCivOprropbhbeSe-v4DnWf_BvwY"},function(data){
                    if(data.rtnCode==200) {
                        console.log(data.data)
                        ws.send(data.data);
                    }
                });
                console.log("Socket 已打开wss://openhw.work.weixin.qq.com/连接");
                // ws.send(json);
            };

            ws.onmessage = function (evt)
            {
                var obj = JSON.parse(evt.data);
                console.log(obj)
        };

            ws.onclose = function()
            {
                console.log("连接已关闭...")
            };
        }

        else
        {
            // 浏览器不支持 WebSocket
            console.log("您的浏览器不支持WebSocket");
        }
    }
}
window.$N = jin = new jin();




