var jin=function () {
    this.version={version:'1.0'};
    this.corpid='';
    this.corpName='';
    this.userid='';
    this.userName='';
    this.role=0;
    this.pagenum="0";
    this.height=0;
    this.currsum=0;
    this._imgLocalIdArray=[];
    this._imgServerIdArray=[];
    this._empArray=[];
    this._pskArray=[];
    this.haslock=false;
}
jin.fn=jin.prototype={
    constructor:jin,
    init:function () {
        setTimeout(function() {
            $(".page1_progress_3").css({'margin-top':'20%'})
        }, 1000);
    // $N.getToken('index');
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
    getuserlocks:function (corpid,userid) {
        $.ajax({
            type : "POST",
            url : $N.getRootPath()+"v1/getLocksByuserid",
            data: {"corpid":corpid,"userid":userid},
            dataType : "json",
            beforeSend:function(){
            // $N.jiazai();
            },
            success : function(data) {
               $("#lockinfo").empty();
               $("#jiazai").empty();
                if(data.rtnCode==200) {
                    $N.haslock=true;
                    $.noti({
                        title: "消息！",
                        text: "设备加载成功",
                        media: "<span  class=\"icon icon-80 f11\"></span>",
                        data: {
                            message: "欢迎使用胖哼智能产品"
                        },
                        time: 3000,
                        onClick: function(data) {
                        }
                    });
                    var str="";
                    $.each(data.data, function (index, items) {
                        var checkksfsText = [];
                        $N._empArray.push(items.sn);
                        $N._pskArray.push(items.psk);
                        if(items.code){
                            checkksfsText.push("键盘");
                        }
                        if(items.mac){
                            checkksfsText.push("蓝牙");
                        }
                        if(items.wifi){
                            checkksfsText.push("WIFI");
                        }
                        if(items.zhiwen){
                            checkksfsText.push("指纹");
                        }
                        if(items.shualian){
                            checkksfsText.push("刷脸");
                        }
                        if(items.yyy){
                            checkksfsText.push("摇一摇");
                        }
                        if(items.kaoqing){
                            checkksfsText.push("考勤");
                        }
                        var sx=items.expires_type==1?'无期限':items.expires_satrt+"~"+items.expires_end;
                        // str+="<li> <div style='height: 40%;padding: 10px'>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: burlywood'>企业名称:<span>"+$N.corpName+"</span></div>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: mediumaquamarine'>设备名称:<span>"+items.lockname+"</span></div>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: lightgreen'>设备编号:<span>"+items.sn+"</span></div>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: bisque'>设备功能:<span>"+checkksfsText.join(',')+"</span></div>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: fuchsia'>设备管理员:<span>"+items.lockMassterName+"</span></div>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: aqua'>使用时效:<span>"+sx+"</span></div>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: limegreen'>当天开锁次数:<span>"+sx+"</span></div>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: #98bff6'>当天首次开锁时间:<span>"+sx+"</span></div>";
                        // str+="<div style='text-align: left;font-family:cursive;font-size: 14px;color: chartreuse'>当天最后开锁时间:<span>"+sx+"</span></div>";
                        // str+="</div> ";
                        str+="<li><div style='height: 50%' id='jiazai'></div><div><span>"+items.lockname+"</span></div>";
                        str+="<div><a onclick='$N.wxsocket()'><div class='lockqu'><div  class=\"lock\"><div id='hdd' class=\"head\"><div class=\"notch\"></div></div><div id='zhuyao' class=\"body\"><span class='bisuo'>闭锁</span></div></div></div></div>";
                        str+="</a>";
                        str+="</div></li>";
                    });
                    $("#lockinfo").html(str);
                    $('#slide3').swipeSlide({
                        autoSwipe:false,//自动切换默认是
                        speed:3000,//速度默认4000
                        continuousScroll:false,//默认否
                        transitionType:'ease-in',
                        callback : function(i,sum,me){
                            $N.currsum=i;
                        }
                    });
                }else {
                    $N.haslock=false;
                    $N.currsum=0;
                    $N._empArray=[];
                    $N._pskArray=[];
                    $.noti({
                        title: "消息！",
                        text: "设备加载失败，未发现设备",
                        media: "<span  class=\"icon icon-73 f11\"></span>",
                        data: {
                            message: "欢迎使用胖哼智能产品"
                        },
                        time: 3000,
                        onClick: function(data) {
                        }
                    });
                    $("#lockinfo").empty();
                    var str="";
                    str+="<li><div style='height: 50%' id='jiazai'></div><div><span>未找到设备</span></div>";
                    str+="<div><a onclick='$N.jiazai()'><div class='lockqu'><div class=\"lock1\"><div class=\"head1\"><div class=\"notch\"></div></div><div class=\"body1\"><span class='bisuo'>无锁</span></div></div></div></div>";
                    str+="</a>";
                    str+="</div></li>";
                   $("#lockinfo").html(str);
                    $('#slide3').swipeSlide({
                        autoSwipe:false,//自动切换默认是
                        speed:3000,//速度默认4000
                        continuousScroll:false,//默认否
                        transitionType:'ease-in',
                        callback : function(i,sum,me){
                        }
                    });
                }
            },
            timeout : 15000
        });
    },

    showlock:function(){

    },
    openlock:function(){
        // console.log($N.corpid+","+$N.userid+","+$N._empArray[$N.currsum]+","+$N._pskArray[$N.currsum])
        $(".bisuo").text("稍等");
        // $(".bisuo").css("margin","15% 30%");
        $.post($N.getRootPath()+"/v1/openLock",{"corpid":$N.corpid,"userid":$N.userid,"sn":$N._empArray[$N.currsum],"psk":$N._pskArray[$N.currsum]},function(data){
            if(data.rtnCode==200) {
                $.noti({
                    title: "消息！",
                    text: "开锁成功",
                    media: "<span  class=\"icon icon-71 f11\"></span>",
                    time: 3000,
                    onClick: function(data) {
                    }
                });
                    // $(".bisuo").css("margin","15% 30%");
                    $(".bisuo").text("开锁");
                    $(".head").addClass("kaikai");
                    setTimeout(function () {
                        $(".head").removeClass("kaikai");
                        $(".bisuo").text("闭锁");
                    },2500)
            }else {
                $.noti({
                    title: "消息！",
                    text: data.msg_zh,
                    media: "<span  class=\"icon icon-73 f11\"></span>",
                    time: 3000,
                    onClick: function(data) {
                    }
                });
                $(".bisuo").css("margin","15% 30%");
                $(".bisuo").text("失败");
                setTimeout(function () {
                    $(".bisuo").text("闭锁");
                },2500)
            }
        })

    },
    yidong:function(){
        $("#suo").addClass("yao");
    },
    dengdai:function(){
        $("#lockqu").empty();
        // $(".page1_progress_3").empty();
        var str="<div  class=\"kaisuo ani bounceOut  animated\" style=\" -webkit-animation-delay:2s\" >";
        str+=" <span  class=\"image30 ani  bounceIn animated\"    style=\"visibility: visible;animation-duration:0.5s;-webkit-animation-duration:0.5s;animation-delay:0.5s;-webkit-animation-delay:0.5s;\"></span> </div>";
        $("#lockqu").html(str);
    },
    jiazai:function(){
        console.log($N.currsum+","+$N._empArray)
        console.log($N._empArray[$N.currsum]);
        if(!$N.haslock){
            $N.getuserlocks($N.corpid,$N.userid);
        }else {
            $N.openlock();
        }
    },
    autoTsq:function(){
        $(".mvSq").css("color","#F5FAFD");
        setTimeout(function(){$(".mvSq").eq(0).css("color","#29B6FF")},0);
        setTimeout(function(){$(".mvSq").eq(1).css("color","#29B6FF")},500);
        setTimeout(function(){$(".mvSq").eq(2).css("color","#29B6FF")},1000);
        setTimeout(function(){$(".mvSq").eq(3).css("color","#29B6FF")},1500);
    },
    wylog:function (corpid,userid,values,type) {
        $("#infoqu").empty();
        $.post($N.getRootPath()+"/v1/getLogsByuserid",{"corpid":corpid,"userid":userid,"date":values.toString(),"type":type.toString()},function(data){
            if(data.rtnCode==200) {
                var str=""
                $.each(data.data, function (index, items) {
                    str+="<a href=\"javascript:void(0);\" class=\"weui-media-box weui-media-box_appmsg\"><div class=\"weui-media-box__hd\">";
                    str+="<span  class=\"tupian image7\"></span></div>";
                    str+="<div class=\"weui-media-box__bd\"><h4 class=\"weui-media-box__title\">"+items.miaosu_zh+"</h4>";
                    str+="<p class=\"weui-media-box__desc\">"+items.startdae+"</p></div>";
                    str+="<div class=\"weui-media-box__bd\" style='text-align: right'><p class=\"weui-media-box__desc\">"+items.username+"</p></div></a>";
                });
                $("#logcount").html(data.total)
                $("#infoqu").html(str);
            }else {
                str="<div style='width: 100%;margin-top: 30%;text-align: center;font-size: 13px;position: absolute;color: white'>" +
                    "<img src='../imgage/meiyou.png' ><div style='color: #c0c0c0'>没有记录</div></div>";
                $("#logcount").html("0");
                $("#infoqu").html(str);
            }
        })
    },
    getcorplocks:function(corpid){
        $.post($N.getRootPath()+"/v1/getKaitong",{"corpid":corpid},function(data){
            var str="";
            if(data.rtnCode==200) {
                $("#she").val(data.data.unmber+"个")
                if(data.data.unmber==0){
                    // str="<div style='width: 100%;margin-top: 30%;text-align: center;font-size: 13px'><img src='../imgage/meiyou.png' ><div>未安装设备</div></div>" ;
                    // $("#lockguanli").html(str);
                }
            }else {
                $("#she").val("0个");
                // str="<div style='width: 100%;margin-top: 30%;text-align: center;font-size: 13px'><img src='../imgage/meiyou.png' ><div>未安装设备</div></div>" ;
                // $("#lockguanli").html(str);
            }
        })
    },
    addlock:function(){
        $.modal({
            title: "提示",
            text: "1、请打开无线局域网并连接WiFi,否则仅支持蓝牙。<br/> 2、确保智能设备已连接电源，且在手机附近。 <br/>3、确定设备是未激活状态。",
            buttons: [
                { text: "我知道了", onClick: function(){
                        wx.getConnectedWifi({
                            success: function(res){
                                alert(res.wifi.SSID+","+res.wifi.signalStrength)
                            },
                            fail:function () {
                                alert("没有发现可用wifi")
                            }
                        })
                    }
                },
            ]
        });
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
            socket = new WebSocket("ws://188.131.169.117:8040");
            //打开事件

            socket.onopen = function() {
                var obj=new Object();
                obj.sn="515068000077";
                obj.time="20161011125200";
                obj.success=true;
                obj.msg='连接服务器';
                obj.opencode='2352352';
                obj.ip='192.168.1.64';
                obj.type='href';
                socket.send("这是来自客户端的消息" + JSON.stringify(obj));

                // $.post($N.getRootPath()+"/v1/genDeviceSignature",{"sn":"515068000077","secretNo":"58ee0d77d6b9db9fc227862eb2d01289"},function(data){
                //     var str="";
                //     if(data.rtnCode==200) {
                //         console.log(data.data)
                //         socket.send("这是来自客户端的消息" + data.data);
                //     }
                // })
                // console.log("Socket 已打开ws://safe.jinchu.com.cn:8040/websocket/1");
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




