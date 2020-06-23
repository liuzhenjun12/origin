var Sock=function () {
    this.version={version:'1.0'};
    this.userid='';
    this.ws=null;
    this.socketid='';
    this.ip='';
}
Sock.fn=Sock.prototype= {
    constructor: Sock,
    init:function () {
     $SO.socket();
    },
    openfenxi:function(id,obj){
        if($("#"+id+"").css("display")=="none"){
            $("#"+id+"").show();
            $("#"+obj+"").removeClass().addClass("bg-sprites toggle-icon up");
        }else {
            $("#"+id+"").hide();
            $("#"+obj+"").removeClass().addClass("bg-sprites toggle-icon down");
        }
    },
    socket:function () {
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("您的浏览器支持WebSocket");
            // $SO.ws = new WebSocket("ws://127.0.0.1:8040/wss/id/jinchu");
            $SO.ws = new WebSocket("wss://safe.jinchu.com.cn/wss/id/jinchu");
            //打开事件
            $SO.ws.onopen = function() {
                var obj=new Object();
                obj.msg='连接服务器';
                $SO.ws.send(JSON.stringify(obj));
            };
            //获得消息事件
            $SO.ws.onmessage = function(msg) {
                var obj = JSON.parse(msg.data);
                console.log(obj);
                var str=""
                if(obj.code==101){
                    str+="<tr >";
                    $.each(obj.data, function (index, items) {
                        str += "<td  class=\"middle\">" +items.sockid + "</td>";
                        str += "<td  class=\"middle\">" +items.ip + "</td>";
                        str += "<td  class=\"middle\"><div style=\"color: rgb(102, 102, 102);\"> <a onclick=\"$SO.sendbom('" + items.sockid + "','" + items.ip + "')\"><span class=\"blue ml-m pointer\">发送报文</span></a></td>";
                        str += "</tr>";
                    });
                    $("#zhanwu").hide();
                    $("#boy").html(str);
                }else if(obj.code==103||obj.code==105||obj.code==106){
                    str+="<tr >";
                    str += "<td  style='text-align: left;line-height: 30px;white-space: normal;color: green' class=\"middle\">" +obj.message + "</td>";
                    str += "</tr>";
                    $("#meiyou").hide();
                    $("#info").append(str);
                }
                //发现消息进入    开始处理前端触发逻辑
            };
            //关闭事件
            $SO.ws.onclose = function() {
                console.log("Socket已关闭");
            };
            //发生了错误事件
            $SO.ws.onerror = function() {
                alert("Socket发生了错误");
                //此时可以尝试刷新页面
            }
        }

    },
    sendbom:function (id,ip) {
        $SO.socketid=id;
        $SO.ip=ip;
        $.actions({
            title: "选择操作",
            onClose: function() {
                console.log("close");
            },
            actions: [
                {
                    text: "下发蓝牙连接",
                    className: "color-primary",
                    onClick: function() {
                        $("#addtype").modal();
                        $("#id").val(id);
                        $("#ip").val(ip);
                        var obj=new Object();
                        obj.command='41';
                        obj.gatewayId='1000060';
                        obj.target='gateway';
                        obj.contentType='byte';
                        obj.content='下发蓝牙连接';
                        $("#result").val(JSON.stringify(obj));
                    }
                },
                {
                    text: "下发蓝牙发送",
                    className: "color-warning",
                    onClick: function() {
                        $("#addtype").modal();
                        $("#id").val(id);
                        $("#ip").val(ip);
                        var obj=new Object();
                        obj.command='43';
                        obj.gatewayId='1000060';
                        obj.target='gateway';
                        obj.contentType='byte';
                        obj.content='下发蓝牙发送';
                        $("#result").val(JSON.stringify(obj));
                    }
                },
                {
                    text: "下发蓝牙读取",
                    className: 'color-danger',
                    onClick: function() {
                        $("#addtype").modal();
                        $("#id").val(id);
                        $("#ip").val(ip);
                        var obj=new Object();
                        obj.command='49';
                        obj.gatewayId='1000060';
                        obj.target='gateway';
                        obj.contentType='byte';
                        obj.content='下发蓝牙读取';
                        $("#result").val(JSON.stringify(obj));
                    }
                },
                {
                    text: "下发蓝牙断开",
                    className: 'color-primary',
                    onClick: function() {
                        $("#addtype").modal();
                        $("#id").val(id);
                        $("#ip").val(ip);
                        var obj=new Object();
                        obj.command='42';
                        obj.gatewayId='1000060';
                        obj.target='gateway';
                        obj.contentType='byte';
                        obj.content='下发蓝牙断开';
                        $("#result").val(JSON.stringify(obj));
                    }
                }
            ]
        });
    },
    rest:function () {
        $("#result").val('');
    },
    send:function () {
        var id=$("#id").val();
        var result=$("#result").val();
        if(result==''){
            $.toast("请输入发送报文", "forbidden");
            return false;
        }
        if(!$SO.isJson(result)){
            $.toast("请输入json格式报文", "forbidden");
            return false;
        }
        $("#addtype").modal("hide");
        $.ajax({
            async : true,
            // url : "http://127.0.0.1:8040/wss/socket/push",
            url : "https://safe.jinchu.com.cn/socket/push",
            type : "post",
            dataType : "jsonp", // 返回的数据类型，设置为JSONP方式
            jsonp : 'callback', //指定一个查询参数名称来覆盖默认的 jsonp 回调参数名 callback
            jsonpCallback: 'handleResponse', //设置回调函数名
            data : {
                message : result,
                id : id
            },
            success: function(response, status, xhr){
                console.log('状态为：' + status + ',状态是：' + xhr.statusText);
                console.log(response);
            }
        });
    },
    handleResponse:function (res) {
        console.log(res)
    },
    isJson:function (str) {
        if (typeof str == 'string') {
            try {
                var obj=JSON.parse(str);
                if(typeof obj == 'object' && obj ){
                    return true;
                }else{
                    return false;
                }

            } catch(e) {
                console.log('error：'+str+'!!!'+e);
                return false;
            }
        }
        console.log('It is not a string!')
    }



}
window.$SO = Sock = new Sock();
