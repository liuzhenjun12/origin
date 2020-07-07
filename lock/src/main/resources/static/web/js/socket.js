var Sock=function () {
    this.version={version:'1.0'};
    this.userid='';
    this.ws=null;
    this.socketid='';
    this.ip='';
    this.count=0;
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
            $SO.ws = new WebSocket("ws://127.0.0.1:8040/wss/id/jinchu");
            // $SO.ws = new WebSocket("wss://safe.jinchu.com.cn/wss/id/jinchu");
            //打开事件
            $SO.ws.onopen = function() {

            };
            //获得消息事件
            $SO.ws.onmessage = function(msg) {
                var obj = JSON.parse(msg.data);
                console.log(obj);
                var str=""
                if(obj.code=='1001'||obj.code=='1002'){
                    str+="<tr >";
                    if(obj.code=='1001'){
                        $SO.count++;
                    }else {
                        $SO.count--;
                    }
                    str += "<td  class=\"middle\">" +obj.sn + "</td>";
                    str += "<td  class=\"middle\">" +obj.content + "</td>";
                    str += "</tr>";
                    $("#shul").text($SO.count);
                    $("#zhanwu").hide();
                    $("#boy").html(str);
                }else{
                    str+="<tr >";
                    str += "<td  style='text-align: left;line-height: 30px;white-space: normal;color: green' class=\"middle\">" +msg.data + "</td>";
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
}
window.$SO = Sock = new Sock();
