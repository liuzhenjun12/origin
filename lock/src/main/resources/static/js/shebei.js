var shibei=function () {
    this.version={version:'1.0'};
    this.corpid='';
    this.sn='';
    this._adminArray=[];
    this._empArray=[];
    this.master="";
}
shibei.fn=shibei.prototype= {
    constructor: shibei,
    getRootPath:function () {
        var pathName = window.location.pathname.substring(1);
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
        return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    },
    setLockUser:function(obj){
        var flage=$(obj).is(":checked");
        if (flage) {
            $.post($SB.getRootPath()+"/v1/setUserLock",{"corpid":$SB.corpid,"sn":$SB.sn,"userid":$(obj).attr("id")},function(data){
                if(data.rtnCode==200) {
                    location.reload();
                }else {
                    $.noti({
                        title: "消息！",
                        text: "设备分配失败",
                        media: "<span  class=\"icon icon-80 f11\"></span>",
                        time: 3000,
                        onClick: function(data) {
                        }
                    });
                }
            });
        }
    },
    getLockUser:function(){
        $SB._adminArray=[];
        $.post($SB.getRootPath()+"/v1/userLockList",{"corpid":$SB.corpid,"sn":$SB.sn},function(data){
            if(data.rtnCode==200) {
                var str=""
                $.each(data.data, function (index, items) {
                    if(items.description==0){
                        $SB._adminArray.push(items.title);
                    }
                });
                $SB._adminArray.join(',');
                $("#in").val($SB._adminArray.toString());
                    $("#in").select({
                        title: "分配设备",
                        multi: true,
                        min: 1,  //最少选1个
                        max: 4,  //最多选3个
                        items: data.data,
                        beforeClose: function(values, titles) {
                            if(values){
                                if($S._adminArray.toString()!=values.toString()) {
                                    $S._adminArray = [];
                                    $.post($S.getRootPath() + "/v1/updateUserLock", {
                                        "corpid": corpid,
                                        "userids": values.toString()
                                    }, function (data) {
                                        if (data.rtnCode == 200) {
                                            window.location.reload();
                                        } else {
                                            $.toptip(data.msg, 'warning')
                                        }
                                    })
                                }
                            }else {
                                $("#in").val($SB._adminArray.toString());
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
            }
        })
    },
}
window.$SB = shibei = new shibei();
