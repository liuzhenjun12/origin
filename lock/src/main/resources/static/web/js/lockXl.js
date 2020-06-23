var lockLx=function () {
    this.version={version:'1.0'};
    this.userid='';
    this.username='';
    this.zh='';
    this.en='';
    this.xlid;
    this.roleId=0;
}
lockLx.fn=lockLx.prototype= {
    constructor: lockLx,
    getRootPath:function () {
        var pathName = window.location.pathname.substring(1);
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
        return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    },
    init:function () {
        $XL.getLockNumTable();
        $XL.formValidator();
    },
    rest:function(obj){
        if(obj==1){
            document.getElementById("defaultForm").reset();
            $('#defaultForm').data('bootstrapValidator', null);
            $XL.formValidator();
        }else {
            document.getElementById("updateForm").reset();
            $('#updateForm').data('bootstrapValidator', null);
            $XL.formValidator1();
        }
    },

    getLockNumTable:function(){
        $.post($XL.getRootPath()+"/xl/list",function(data){
            if(data.code==200) {
                var str=""
                $.each(data.data, function (index, items) {
                    var info = JSON.stringify(items).replace(/\"/g,"'");
                    str+="<tr >";
                    str+= "<td  class=\"middle\">"+ items.name+"</td>";
                    str+= "<td  class=\"middle\">"+ items.miaosu+"</td>";
                    str+= "<td  class=\"middle\"><div style=\"color: rgb(102, 102, 102);\"><a onclick=\"$XL.openXq("+info+")\" > <span class=\"blue ml-m pointer\">详情</span></a> <a onclick=\"$XL.editXq("+info+")\" ><span class=\"blue ml-m pointer\">编辑</span></a><a onclick=\"$XL.delete("+info+")\"><span class=\"blue ml-m pointer\">删除</span></a></td>";
                    str+="</tr>";
                });
                $("#zhanwu").hide();
                $("#boy").html(str);
            }else {
                $("#boy").html("");
                $("#zhanwu").show();
            }
        })
    },
    openXq:function(obj){
        $("#typeXq").modal();
        $("#xl_zh2").val(obj.name);
        $("#miao_zh2").val(obj.miaosu);
        $("#cjr").val(obj.createby);
        $("#cjrq").val(obj.createdate);
        $("#xgr").val(obj.updateby);
        $("#xgrq").val(obj.updatedate);
    },
    editXq:function(obj){
        $XL.xlid=obj.id;
        $("#edittype").modal();
        $("#xl_zh1").val(obj.name);
        $("#miao_zh1").val(obj.miaosu);
        $XL.formValidator1();
    },
    delete:function(obj){
            $.post($XL.getRootPath()+'/xl/delete', {
                id:obj.id
            },function(result) {
                if (result.code==200) {
                    $XL.getLockNumTable();
                }else{
                    alert(result.message);
                }
            }, 'JSON');
    },
    editLockXl:function(){
        var result = $('#updateForm').data('bootstrapValidator').validate().isValid();
        if(result){
            var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
                regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
            var xl_zh = $('#xl_zh1').val();
            if (regEn.test(xl_zh) || regCn.test(xl_zh)) {
                alert("设备系列名称不能包含特殊字符");
                return false;
            }
            var miao_zh = $('#miao_zh1').val();
            $.post($XL.getRootPath()+'/xl/update', {
                id:$XL.xlid,
                name:xl_zh,
                miaosu:miao_zh,
                updateby:$XL.userid,
            },function(result) {
                if (result.code==200) {
                    $("#updateForm").find('input').val('');
                    $("#edittype").modal("hide");
                    $("#updateForm").data('bootstrapValidator').destroy();
                    $('#updateForm').data('bootstrapValidator', null);
                    $XL.formValidator1();
                    $XL.getLockNumTable();
                }else{
                    alert(result.message);
                }
            }, 'JSON');
        }
    },
    addLockXl:function(){
        var result = $('#defaultForm').data('bootstrapValidator').validate().isValid();
        if(result) {
            var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
                regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
            var xl_zh = $('#xl_zh').val();
            if (regEn.test(xl_zh) || regCn.test(xl_zh)) {
                alert("设备系列名称不能包含特殊字符");
                return false;
            }
            var miao_zh = $('#miao_zh').val();
            $.ajaxSettings.async = false;
            $.post($XL.getRootPath()+'/xl/add', {
                name:xl_zh,
                miaosu:miao_zh,
                createby:$XL.userid,
            },function(result) {
                if (result.code==200) {
                    $("#defaultForm").find('input').val('');
                    $("#addtype").modal("hide");
                    $("#defaultForm").data('bootstrapValidator').destroy();
                    $('#defaultForm').data('bootstrapValidator', null);
                    $XL.formValidator();
                    $XL.getLockNumTable();
                }else{
                    alert(result.message);
                }
            }, 'JSON');
        }
    },
    formValidator:function () {
        $('#defaultForm').bootstrapValidator({
            message: '不能为空!',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                xl_zh: {
                    validators: {
                        notEmpty: {
                            message: '设备系列名称不能为空!'
                        },
                        stringLength: {
                            min: 1,
                            max: 20,
                            message: '设备系列名称的长度在1~20之间'
                        },
                        callback: {/*自定义，可以在这里与其他输入项联动校验*/
                            message: '不能有空格！',
                            callback: function (value, validator, $field) {
                                if (value.indexOf(" ") > -1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }
                },
                miao_zh: {
                    validators: {
                        notEmpty: {
                            message: '设备系列描述不能为空!'
                        },
                        stringLength: {
                            min: 4,
                            max: 100,
                            message: '设备系列描述的长度在4~100之间'
                        }
                    }
                }
            }
        });
    },
    formValidator1:function(){
        $('#updateForm').bootstrapValidator({
            message: '不能为空!',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                xl_zh1: {
                    validators: {
                        notEmpty: {
                            message: '设备系列名称不能为空!'
                        },
                        stringLength: {
                            min: 1,
                            max: 20,
                            message: '设备系列名称的长度在1~20之间'
                        },
                        callback: {/*自定义，可以在这里与其他输入项联动校验*/
                            message: '不能有空格！',
                            callback: function (value, validator, $field) {
                                if (value.indexOf(" ") > -1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }
                },
                miao_zh1: {
                    validators: {
                        notEmpty: {
                            message: '设备系列描述不能为空!'
                        },
                        stringLength: {
                            min: 4,
                            max: 100,
                            message: '设备系列描述的长度在4~100之间'
                        }

                    }
                }
            }
        });
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


}
window.$XL = lockLx = new lockLx();
