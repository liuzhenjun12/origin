var console=function () {
    this.version={version:'1.0'};
    this.userid='';
    this.username='';
    this.locktype='';
    this.xl=false;
    this.roleId=0;
}
console.fn=console.prototype= {
    constructor: console,
    getRootPath:function () {
        var pathName = window.location.pathname.substring(1);
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
        return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    },
    init:function () {
        $C.getLockTypeTable();
        $C.getSelect();
        $C.formValidator();
    },
    guanbidingdan:function(obj){
        if(obj==1){
            document.getElementById("defaultForm").reset();
            $('#defaultForm').data('bootstrapValidator', null);
            $C.formValidator();
        }else {
            document.getElementById("updateForm").reset();
            $('#updateForm').data('bootstrapValidator', null);
            $C.formValidator1();
        }
    },
    getSelect:function(){
        $.post($C.getRootPath()+"/xl/list",function(data){
            if(data.code==200) {
                var str=""
                var str1="";
                $.each(data.data, function (index, items) {
                    str+="<option value="+items.id+" >"+items.name+"</option>";
                    str1+="<option value="+items.id+" >"+items.name+"</option>";
                });
                $("#sbxlid").html(str);
                $('#sbxlid').selectpicker('refresh');
                $("#sbxl2").html(str1);
                $('#sbxl2').selectpicker('refresh');
            }
        })
    },
    getLockTypeTable:function(){
        $.post($C.getRootPath()+"/type/list",function(data){
            if(data.code==200) {
                var str=""
                $.each(data.data, function (index, items) {
                    var info = JSON.stringify(items).replace(/\"/g,"'");
                    str+="<tr >";
                    str+= "<td  class=\"middle\">"+ items.sbtype+"</td>";
                    str+= "<td  class=\"middle\">"+ items.sbjc+"</td>";
                    str+= "<td  class=\"middle\">"+ items.sbxlname+"</td>";
                    str += "<td  class=\"middle\"><div style=\"color: rgb(102, 102, 102);\"><a onclick=\"$C.openXq(" + info + ")\" > <span class=\"blue ml-m pointer\">详情</span></a> <a onclick=\"$C.editXq(" + info + ")\" ><span class=\"blue ml-m pointer\">编辑</span></a><a onclick=\"$C.delete(" + items.id + ")\"><span class=\"blue ml-m pointer\">删除</span></a></td>";
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
    delete:function(id){
            $.post($C.getRootPath()+'/type/delete', {
                id:id,
            },function(result) {
                if (result.code==200) {
                    $C.getLockTypeTable();
                }else{
                    alert(result.message);
                }
            }, 'JSON');

    },
    openXq:function(obj){
        $("#typeXq").modal();
       $("#sbtype1").val(obj.sbtype);
       $("#sbjc1").val(obj.sbjc);
        $("#sbxl1").val(obj.sbxlname);
        $("#cjr").val(obj.createby);
        $("#cjrq").val(obj.createdate);
        $("#xgr").val(obj.updateby);
        $("#xgrq").val(obj.updatedate);
        $("#xstp").attr("src",obj.img);
    },
    editXq:function(obj){
            $("#edittype").modal();
            $("#typeid").val(obj.id);
            $("#sbtype2").val(obj.sbtype);
            $("#sbjc2").val(obj.sbjc);
            $('#sbxl2').selectpicker('val',obj.sbxlid);
            $('#sbxl2').selectpicker('refresh');
            $("#xstp1").attr("src",obj.img);
            $C.formValidator1();
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
    addLockType:function(){
        var result = $('#defaultForm').data('bootstrapValidator').validate().isValid();
        if(result){
            var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
                regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
            var sbtype=$('#sbtype').val();
            if(regEn.test(sbtype) || regCn.test(sbtype)) {
                alert("设备型号不能包含特殊字符");
                return false;
            }
            var empPhotoUrl = $("input[name='imgpath']").val();
            if(empPhotoUrl==""){
                alert("样例图片不能为空!");
                return false;
            }
            $('#defaultForm').ajaxSubmit({
                type: 'post', // 提交方式 get/post
                url: $C.getRootPath()+'/type/add', // 需要提交的 url
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    if(data.code==200){
                        $("#defaultForm").find('input').val('');
                        $("#addtype").modal("hide");
                        $("#defaultForm").data('bootstrapValidator').destroy();
                        $('#defaultForm').data('bootstrapValidator', null);
                        $C.formValidator();
                        $C.getLockTypeTable();
                    }else {
                        alert(data.message);
                    }
                }
        });
        }
    },
    editLockType:function(){
        var result = $('#updateForm').data('bootstrapValidator').validate().isValid();
        if(result){
            var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
                regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
            var sbtype=$('#sbtype2').val();
            if(regEn.test(sbtype) || regCn.test(sbtype)) {
                alert("设备型号不能包含特殊字符");
                return false;
            }
            if($("#tuqu1").find("img").length==0){
                alert("样例图片不能为空!");
                return false;
            }
            $('#updateForm').ajaxSubmit({
                type: 'post', // 提交方式 get/post
                url: $C.getRootPath()+'type/update', // 需要提交的 url
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    if(data.code==200){
                        $("#updateForm").find('input').val('');
                        $("#edittype").modal("hide");
                        $("#updateForm").data('bootstrapValidator').destroy();
                        $('#updateForm').data('bootstrapValidator', null);
                        $C.formValidator1();
                        $C.getLockTypeTable();
                    }else {
                        alert(data.message);
                    }
                }
            });
        }
    },
    settupq:function(){
        $("#tuqu").empty();
        var str="<div onclick=\"document.getElementById('imgpath').click()\" class=\"app_screenShot_list_item app_screenShot_list_item_Add\">";
        str+=" <i class=\"app_screenShot_list_addIcon\"></i> <span class=\"app_screenShot_list_addText\">添加截图</span></div>";
        $("#tuqu").html(str);
    },
    settupq1:function(){
        $("#tuqu1").empty();
        var str="<div onclick=\"document.getElementById('file').click()\" class=\"app_screenShot_list_item app_screenShot_list_item_Add\">";
        str+=" <i class=\"app_screenShot_list_addIcon\"></i> <span class=\"app_screenShot_list_addText\">添加截图</span></div>";
        $("#tuqu1").html(str);
    },
    showImg:function(val){
        var types=val.substr(val.lastIndexOf(".")+1).toLowerCase();
        if(types!="jpg" && types!="png"){
            alert("只能上传jpg,png格式的图片");
            return ;
        }
        if(val.indexOf("[")!=-1||val.indexOf("(")!=-1||val.indexOf("{")!=-1||val.indexOf("]")!=-1||val.indexOf(")")!=-1||val.indexOf("}")!=-1){
            alert("图片名称不能带有特殊符号，如:[]、()、{}等，正确名称如abc.png");
            return ;
        }
        //获得文件上传文本框对象
        var docObj = document.getElementById("imgpath");
        //获得放图片的img对象
        $("#tuqu").empty();
        var str="<div class=\"app_screenShot_list_item\">";
        str+="<img src="+window.URL.createObjectURL(docObj.files[0])+" alt=\"\" class=\"app_screenShot_list_img\"> ";
        str+="<a href=\"javascript:;\" onclick='$C.settupq()' class=\"app_screenShot_list_item_close\"><i class=\"ww_openDeveloper ww_openDeveloper_DeleteBtn\">";
        str+="</i></a></div>";
        $("#tuqu").html(str);
    },
    showImg1:function(val){
        var types=val.substr(val.lastIndexOf(".")+1).toLowerCase();
        if(types!="jpg" && types!="png"){
            alert("只能上传jpg,png格式的图片");
            return ;
        }
        if(val.indexOf("[")!=-1||val.indexOf("(")!=-1||val.indexOf("{")!=-1||val.indexOf("]")!=-1||val.indexOf(")")!=-1||val.indexOf("}")!=-1){
            alert("图片名称不能带有特殊符号，如:[]、()、{}等，正确名称如abc.png");
            return ;
        }
        //获得文件上传文本框对象
        var docObj = document.getElementById("file");
        //获得放图片的img对象
        $("#tuqu1").empty();
        var str="<div class=\"app_screenShot_list_item\">";
        str+="<img src="+window.URL.createObjectURL(docObj.files[0])+" alt=\"\" class=\"app_screenShot_list_img\"> ";
        str+="<a href=\"javascript:;\" onclick='$C.settupq1()' class=\"app_screenShot_list_item_close\"><i class=\"ww_openDeveloper ww_openDeveloper_DeleteBtn\">";
        str+="</i></a></div>";
        $("#tuqu1").html(str);
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
                sbtype: {
                    validators: {
                        notEmpty: {
                            message: '设备型号不能为空!'
                        },
                        stringLength: {
                            min: 5,
                            max: 22,
                            message: '设备型号的长度在5~22之间'
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
                sbjc: {
                    validators: {
                        notEmpty: {
                            message: '类型简称不能为空!'
                        },
                        stringLength: {
                            min: 3,
                            max: 10,
                            message: '类型简称的长度在3~10之间'
                        }
                    }
                }
            }
        });
    },
    formValidator1:function () {
        $('#updateForm').bootstrapValidator({
            message: '不能为空!',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                sbtype2: {
                    validators: {
                        notEmpty: {
                            message: '设备型号不能为空!'
                        },
                        stringLength: {
                            min: 5,
                            max: 22,
                            message: '设备型号的长度在5~22之间'
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
                sbjc2: {
                    validators: {
                        notEmpty: {
                            message: '类型简称不能为空!'
                        },
                        stringLength: {
                            min: 3,
                            max: 10,
                            message: '类型简称的长度在3~10之间'
                        }
                    }
                },
                ksfs2:{
                    validators: {
                        notEmpty: {
                            message: '开锁方式不能为空'
                        },
                        callback: {
                            message: '请选择开锁方式',
                            callback: function (value, validator) {
                                if (value == -1) { //-1是--请选择--选项
                                    return false;
                                } else {
                                    return true;
                                }

                            }
                        }

                    }
                }
            }
        });
    }
}
window.$C = console = new console();
