var lock=function () {
    this.version={version:'1.0'};
    this.corpid='';
    this.userid='';
    this._adminArray=[];
}
lock.fn=lock.prototype= {
    constructor: lock,
    getUsers:function(corpid){
        $.post($S.getRootPath()+"/v1/getUserList",{"corpid":corpid},function(data){
            if(data.rtnCode==200) {
                var str="";
                $.each(data.data, function (index, items) {
                    var is=items.admin==true?'管理员':'成员';
                    str+="<a class=\"weui-cell weui-cell_access\"   href=\"/wx/v1/setUser?corpid="+corpid+"&userid="+items.userid+"\" > ";
                    str+="<div class=\"weui-grid__icon\"><img src="+items.avatar+" ></div>";
                    str+="<div class=\"weui-cell__bd\"> <p style='margin-left: 5px'>"+items.name+"</p></div>";
                    str+=" <div class=\"weui-cell__ft\">"+is+"</div></a>";
                });
                $(".weui-cells").append(str);
            }else {

            }
        })
    },
    getlocks: function (corpid,userid) {
        $.post($S.getRootPath()+"/v1/getLocksByuserid",{"corpid":corpid,"userid":userid},function(data){
            if(data.rtnCode==200) {

            }else {
                $("#sbcount").html("0个");
                var str="<div style='width: 100%;margin-top: 30%;text-align: center;font-size: 13px'>" +
                    "<img src='../imgage/meiyou.png' ><div>没有设备</div></div>";
                $("#lockqu").html(str);
            }
        })
    },
    sheng:function (corpid) {
        $.post($S.getRootPath()+"/v1/getLocksBycorpid",{"corpid":corpid},function(data){
            if(data.rtnCode==200) {

            }else {
                $.actions({
                    title: "没有设备可以申请",
                    onClose: function() {
                        console.log("close");
                    }
                });
            }
        })
    },
    getMsgs:function (corpid,userid,page,pagesize) {
        $.ajax({
            type : "POST",
            url : $S.getRootPath()+"/v1/getMsgsByuserid",
            data: {"corpid":corpid,"userid":userid,"page":page,"maxpage":pagesize},
            dataType : "json",
            beforeSend:function(){
                $('#loading').show();
            },
            success : function(data) {
                $('#loading').hide();
                var str="";
                if(data.rtnCode==200) {
                    $.each(data.data, function (index, items) {
                        str+="<div class=\"weui-form-preview\" id="+items.id+"><div class=\"weui-form-preview__hd\"><label class=\"weui-form-preview__label\">标题</label>";
                        str+="<em class=\"weui-form-preview__value\">"+items.methodjc_zh+"</em></div><div class=\"weui-form-preview__bd\">";
                        str+="<div class=\"weui-form-preview__item\"><label class=\"weui-form-preview__label\">内容</label>";
                        str+="<span class=\"weui-form-preview__value\">"+items.miaosu_zh+"</span></div>";
                        str+="<div class=\"weui-form-preview__item\"><label class=\"weui-form-preview__label\">日期</label>";
                        str+="<span class=\"weui-form-preview__value\">"+items.username+"</span></div></div>";
                        str+="<div class=\"weui-form-preview__ft\"><a class=\"weui-form-preview__btn weui-form-preview__btn_default\" href=\"javascript:\">";
                        str+=" <span >详情</span>";
                        str+="</a><button  class=\"weui-form-preview__btn weui-form-preview__btn_primary\" href=\"javascript:\" onclick='$S.del("+items.id+")'>删除</button></div></div>";
                    });
                    $("#wyxx").append(str);
                    $("#delmo").show();
                    var maxpage = Math.ceil(data.total / pagesize);
                    sessionStorage['maxpage'] = maxpage;
                    if(page==maxpage){
                        $("#getmore").html("没有更多数据了");return false;
                    }
                }else {
                    str="<div style='width: 80%;margin-left: auto;margin-right: auto;margin-top: 30%;text-align: center;'>" +
                        "<img src='../imgage/meiyou.png' width='80px'><div style='color: #C7C7C7'>没有消息</div></div>";
                    $("#wyxx").html(str);
                    $("#delmo").hide();
                }
            },
            timeout : 15000
        });
    },
    del:function (id) {
        $.post($S.getRootPath()+"/v1/delMsgsByid",{"id":id},function(data){
            if(data.rtnCode==200) {
                $.toptip(data.msg_zh,'success')
                window.location.reload();
            }else {
                $.toptip(data.msg_zh,'warning')
            }
        })
    },
    deleteAll:function (corpid,userid) {
        $.post($S.getRootPath()+"/v1/delMsgsAll",{"corpid":corpid,"userid":userid},function(data){
            if(data.rtnCode==200) {
                $.toptip('删除成功','success')
                window.location.reload();
            }else {
                $.toptip('删除失败','warning')
            }
        })
    },
    getRootPath:function () {
        var pathName = window.location.pathname.substring(1);
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
        return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    },
    setCookie:function (name,value) {
        document.cookie = name + "="+ escape (value) + ";expires=60";
    },
    fhui:function () {
        $S.setCookie("num","3");
        history.back(-1);
    },
    getAdmin:function (corpid) {
        $S._adminArray=[];
        $.post($S.getRootPath()+"/v1/userList",{"corpid":corpid},function(data){
            if(data.rtnCode==200) {
                var str=""
                $.each(data.data, function (index, items) {
                    if(items.description==0){
                        $S._adminArray.push(items.title);
                    }
                });
                $S._adminArray.join(',');
                $("#in").val($S._adminArray.toString());
                $("#in").select({
                    title: "设置管理员",
                    multi: true,
                    min: 0,  //最少选1个
                    max: 4,  //最多选3个
                    items: data.data,
                    beforeClose: function(values, titles) {
                        if(values){
                            if($S._adminArray.toString()!=values.toString()) {
                                $S._adminArray = [];
                                $.post($S.getRootPath() + "/v1/updateAdminList", {
                                    "corpid": corpid,
                                    "userids": values.toString()
                                }, function (data) {
                                    if (data.rtnCode == 200) {
                                        window.location.reload();
                                        // var str = ""
                                        // $.each(data.data, function (index, items) {
                                        //     $S._adminArray.push(items.name);
                                        // });
                                        // $S._adminArray.join(',');
                                        // $("#in").val($S._adminArray.toString());
                                        // $.toast(data.msg);
                                    } else {
                                        $.toptip(data.msg, 'warning')
                                    }
                                })
                            }
                        }else {
                            $("#in").val($S._adminArray.toString());
                        }
                    },
                    onChange: function(d) {
                        console.log(this, d);
                    },
                    onClose: function (d) {
                        console.log('close')
                    }
                });
            }else {
                $.toptip(data.msg,'warning')
            }
        })
    },
    appverLock:function(type,str){
        $.confirm(str, "确认安装?", function() {
            var url="/wx/v1/order?corpid="+$S.corpid+"&userid="+$S.userid+"&type="+type;
            location.href=url;
        }, function() {
            //取消操作
        });
    }
}
window.$S = lock = new lock();
