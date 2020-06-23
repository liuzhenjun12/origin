var login=function () {
    this.version={version:'1.0'};
    this._adminArray=[];
    this.phone='';
    this.check='';
    this.nums=30;
    this.btn=null;
    this.lang='zh-cn';
}

login.fn=login.prototype= {
    constructor: login,
    init:function(){
        setTimeout("$('#danchu1').remove()",2100);
    },
    registInit:function (){
    if($L.lang=='zh-cn'){
        $("#name").attr("placeholder","请输入介于 2 至 10 个字");
        $("#userpassword").attr("placeholder","请输入介于 6 至 16 个字");
        $("#userpassword2").attr("placeholder","请再输入一次密码");
        $("#phone").attr("placeholder","手机号");
        $("#code").attr("placeholder","验证码");
        $("#getVCode").attr("value","点击发送验证码");
        $("#regist_btn").attr("value","注册");
        $.fn.bootstrapValidator.i18n = $.extend(true, $.fn.bootstrapValidator.i18n, {
            notEmpty: {
                'default': '栏位不能为空'
            },
            callback: {
                'default': '不能有空格'
            },
            stringLength: {
                'default': '请输入符合长度限制的值',
                less: '请输入小于 %s 个字',
                more: '请输入大于 %s 个字',
                between: '请输入介于 %s 至 %s 个字'
            },
            identical: {
                'default': '请输入和密码一样的值'
            },
            regexp:{
                'default': '请输入正确的手机格式'
            }
        });
    }else {
        $("#name").attr("placeholder","Please enter value between 2 and 10 characters long");
        $("#userpassword").attr("placeholder","Please enter value between 6 and 6 characters long");
        $("#userpassword2").attr("placeholder","Please re-enter your password");
        $("#phone").attr("placeholder","phone number");
        $("#code").attr("placeholder","send Verification code");
        $("#getVCode").attr("value","verification code");
        $("#regist_btn").attr("value","register");
        $.fn.bootstrapValidator.i18n = $.extend(true, $.fn.bootstrapValidator.i18n, {
            notEmpty: {
                'default': 'Please enter a value'
            },
            callback: {
                'default': 'No spaces'
            },
            stringLength: {
                'default': 'Please enter a value with valid length',
                less: 'Please enter less than %s characters',
                more: 'Please enter more than %s characters',
                between: 'Please enter value between %s and %s characters long'
            },
            identical: {
                'default': 'Please enter the same value as the password'
            },
            regexp:{
                'default': 'Please enter the correct phone format'
            }
        });
    }
       $L.formValidator();
        $("#phone").blur(function () {
            var phone=$("#phone");
                var a=/^1[345678]\d{9}$/.test(phone.val());
                if(a){
                    $.post($L.getRootPath()+'/v1/yanPhone', {phone:phone.val()},function(result) {
                        if( result.data){
                            if($L.lang=='zh-cn'){
                                $.toptip("此手机号码已注册过",'error');
                            }else {
                                $.toptip("This mobile number is already registered",'error');
                            }
                            $("#phone").val(phone.val().substr(0,10));
                            $("#defaultForm").bootstrapValidator("addField","phone");
                            $L.nums=30;
                        }else {
                                $L.phone=phone.val();
                        }
                    }, 'JSON');
                }
        });
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
                name: {
                    validators: {
                        notEmpty: {
                        },
                        stringLength: {
                            min: 2,
                            max: 10,
                        },
                        callback: {/*自定义，可以在这里与其他输入项联动校验*/
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
                userpassword: {
                    validators: {
                        notEmpty: {
                        },
                        stringLength: {
                            min: 6,
                            max: 16,
                        }
                    }
                },
                userpassword2: {
                    validators: {
                        notEmpty: {
                        },
                        stringLength: {
                            min: 6,
                            max: 16,
                        },
                        identical: {
                            field: 'userpassword',
                        },
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {
                        },
                        regexp : {
                            regexp : /^1[345678]\d{9}$/,
                        },
                    }
                },

            }
        });
    },
    setLang:function(obj){
        $.post($L.getRootPath()+'/v1/setLang', {code:obj},function(result) {
            if(result.rtnCode==200){
                location.reload();
            }
        }, 'JSON');
    },
    getRootPath:function () {
        var pathName = window.location.pathname.substring(1);
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
        return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    },
    yanPhone:function(phone){
        $.ajaxSettings.async = false;
        $.post($L.getRootPath()+'/v1/yanPhone', {phone:phone,userid:0},function(result) {
        }, 'JSON');
        $.ajaxSettings.async = true;
    },
    sendxin:function (thisBtn) {
        $("#defaultForm").bootstrapValidator('validate');//提交验证
        if ($("#defaultForm").data('bootstrapValidator').isValid()) {
            $L.btn = thisBtn;
            $L.btn.disabled = true; //将按钮置为不可点击
            $L.btn.value = '重新获取（'+$L.nums+'）';
            $L.clock = setInterval($L.doLoop, 1000); //一秒执行一次
            $.post($L.getRootPath()+'/v1/sendxin', {phone:$L.phone},function(data) {
                if(data.rtnCode==200){
                    $("#code").attr("disabled",false);
                    $("#code").focus();
                }else {
                    $("#code").attr("disabled",true);
                }
                if($L.lang=='zh-cn'){
                    $.toptip(data.msg_zh,'error');
                }else {
                    $.toptip(data.msg_en,'error');
                }
            }, 'JSON');
        }
    },
    doLoop:function () {
        $L.nums--;
        if ($L.nums > 0) {
            $L.btn.value = '重新获取（'+$L.nums+'）';
        } else {
            clearInterval($L.clock); //清除js定时器
            $L.btn.disabled = false;
            $L.btn.value = '点击发送验证码';
            $L.nums = 10; //重置时间
        }
    },
    yancode:function (code) {
        $.post($L.getRootPath()+'/v1/yancode', {phone:$L.phone,code:code},function(result) {
            if(result.rtnCode==200){
                $("#regist_btn").attr("disabled",false);
            }else {
                $("#regist_btn").attr("disabled",true);
            }
        }, 'JSON');
    },

    regist:function () {
        $("#defaultForm").bootstrapValidator('validate');//提交验证
        if ($("#defaultForm").data('bootstrapValidator').isValid()) {
            var code= $("#code").val();
            if(code==''){
                $.toptip("验证码不能为空",'error');
            }
            if(code.length!=6){
                $.toptip("验证码长度为6位数字",'error');
            }
            $.post($L.getRootPath()+'/v1/yancode', {phone:$L.phone,code:code},function(result) {
                if(result.rtnCode==200){
                    if ($('#agreement').is(':checked')) {
                        var name = $("#name").val();
                        var pwd = $("#userpassword2").val();
                        $.post($L.getRootPath() + '/v1/regist', {phone: $L.phone, name: name, userpassword2: pwd}, function (result) {
                            if (result.rtnCode == 200) {
                                location.href = $L.getRootPath() + "/v1/toLoginPang";
                            } else {
                                if($L.lang=='zh-cn'){
                                    $.toptip(result.msg_zh,'error');
                                }else {
                                    $.toptip(result.msg_en,'error');
                                }
                            }
                        }, 'JSON');
                        return false;
                    }else {
                        if($L.lang=='zh-cn'){
                            $.toptip("请阅读注册协议并同意",'error');
                        }else {
                            $.toptip("Please read the registration agreement and agree",'error');
                        }
                    }
                }else {
                    if($L.lang=='zh-cn'){
                        $.toptip(result.msg_zh,'error');
                    }else {
                        $.toptip(result.msg_en,'error');
                    }
                }
            }, 'JSON');
        }
    },
    loginInit:function(){
        if($L.lang=='zh-cn') {
            $("#login_btn").attr("value", "登录");
        }else {
            $("#login_btn").attr("value", "LOGIN");
        }
        $('#remebers').click(function () {
            if ($('#remebers').is(':checked')) {
                if ($("#login_number").val() == "") {
                    $('#remebers').attr("checked", false);
                    if($L.lang=='zh-cn'){
                        $.toptip("账号不能为空",'error');
                    }else {
                        $.toptip("Account cannot be empty",'error');
                    }
                    return false;
                }
                if($("#login_password").val() == "") {
                    $('#remebers').attr("checked", false);
                    if($L.lang=='zh-cn'){
                        $.toptip("密码不能为空",'error');
                    }else {
                        $.toptip("password can not be blank",'error');
                    }
                    return false;
                }
                $L.setCookie("uname", $("#login_number").val(), 60);
                $L.setCookie("upwd", $("#login_password").val(), 60);
            }else {
                $L.delCookie("uname");
                $L.delCookie("upwd");
            }
        });
        if ($L.getCookie("uname") != null) {
            $('#remebers').attr("checked", "checked");
            $('#login_number').val($L.getCookie("uname"));
            $('#login_password').val($L.getCookie("upwd"));
        }else {
            $('#login_number').val("");
        }
    },
    alertMsg:function(obj){
        var str="<div class=\"ani  bounceOutRight animated\" style=\"-webkit-animation-delay:2s;\">";
        str+="<div class=\"ani  bounceInDown animated\" ><div  class=\" img_zhmmcw \"  ><span >提示信息</span> </div>";
        str+="<div  class=\" Validform_info \" >";
        str+="<span >"+obj+"</span> </div></div></div>";
        $(".tanchang").html(str);
        setTimeout('$(".tanchang").empty()',2500);
    },
    qie:function(num){
        if(num==1){
            $("#mi").removeClass("active");
            $("#sao").addClass("active");
            $("#login_container").hide();
        }else {
            $("#sao").removeClass("active");
            $("#mi").addClass("active");
            $("#login_container").show();
            $("#login_number").focus();
        }
    },
    zhangdl:function () {
        var name=$("#login_number").val();
        if(name==""){
            $("#login_number").focus();
            if($L.lang=='zh-cn'){
                $.toptip("账号不能为空",'error');
            }else {
                $.toptip("Account cannot be empty",'error');
            }
            return false;
        }
        var pwd=$("#login_password").val();
        if(pwd==""){
            $("#login_password").focus();
            if($L.lang=='zh-cn'){
                $.toptip("密码不能为空",'error');
            }else {
                $.toptip("password can not be blank",'error');
            }
            return false;
        }
        $.post($L.getRootPath()+'/v1/zhanglogin', {name:name,pwd:pwd},function(result) {
            if(result.rtnCode==200){
                location.href=$L.getRootPath()+"v1/toIndex";
            }else {
                if($L.lang=='zh-cn'){
                    $.toptip(result.msg_zh,'error');
                }else {
                    $.toptip(result.msg_en,'error');
                }
            }
        }, 'JSON');
    },
    wjmmInit:function(){
        if($L.lang=='zh-cn'){
            $("#userpassword").attr("placeholder","请输入介于 6 至 16 个字");
            $("#userpassword2").attr("placeholder","请再输入一次密码");
            $("#phone").attr("placeholder","手机号");
            $("#code").attr("placeholder","验证码");
            $("#getVCode").attr("value","点击发送验证码");
            $("#regist_btn").attr("value","确定");
            $.fn.bootstrapValidator.i18n = $.extend(true, $.fn.bootstrapValidator.i18n, {
                notEmpty: {
                    'default': '栏位不能为空'
                },
                callback: {
                    'default': '不能有空格'
                },
                stringLength: {
                    'default': '请输入符合长度限制的值',
                    less: '请输入小于 %s 个字',
                    more: '请输入大于 %s 个字',
                    between: '请输入介于 %s 至 %s 个字'
                },
                identical: {
                    'default': '请输入和密码一样的值'
                },
                regexp:{
                    'default': '请输入正确的手机格式'
                }
            });
        }else {
            $("#userpassword").attr("placeholder","Please enter value between 6 and 6 characters long");
            $("#userpassword2").attr("placeholder","Please re-enter your password");
            $("#phone").attr("placeholder","phone number");
            $("#code").attr("placeholder","send Verification code");
            $("#getVCode").attr("value","verification code");
            $("#regist_btn").attr("value","OK");
            $.fn.bootstrapValidator.i18n = $.extend(true, $.fn.bootstrapValidator.i18n, {
                notEmpty: {
                    'default': 'Please enter a value'
                },
                callback: {
                    'default': 'No spaces'
                },
                stringLength: {
                    'default': 'Please enter a value with valid length',
                    less: 'Please enter less than %s characters',
                    more: 'Please enter more than %s characters',
                    between: 'Please enter value between %s and %s characters long'
                },
                identical: {
                    'default': 'Please enter the same value as the password'
                },
                regexp:{
                    'default': 'Please enter the correct phone format'
                }
            });
        }
        $L.formValidator();
        $("#phone").blur(function () {
            var phone=$("#phone");
            var a=/^1[345678]\d{9}$/.test(phone.val());
            if(a){
                $.post($L.getRootPath()+'/v1/yanPhone', {phone:phone.val()},function(result) {
                    if(! result.data){
                        if($L.lang=='zh-cn'){
                            $.toptip("此手机号码没有注册过",'error');
                        }else {
                            $.toptip("This phone number has not been registered",'error');
                        }
                        $("#phone").val('');
                        $("#defaultForm").bootstrapValidator("addField","phone");
                        $L.nums=30;
                    }else {
                        $L.phone=phone.val();
                    }
                }, 'JSON');
            }
        });
    },

    wjsubmit:function(){
        $("#defaultForm").bootstrapValidator('validate');//提交验证
        if ($("#defaultForm").data('bootstrapValidator').isValid()) {
            var code= $("#code").val();
            if(code==''){
                if($L.lang=='zh-cn'){
                    $.toptip("验证码不能为空",'error');
                }else {
                    $.toptip("verification code must be filled",'error');
                }
            }
            if(code.length!=6){
                $.toptip("验证码长度为6位数字",'error');
            }
            $.post($L.getRootPath()+'/v1/yancode', {phone:$L.phone,code:code},function(result) {
                if(result.rtnCode==200){
                    var pwd = $("#userpassword2").val();
                    $.post($L.getRootPath() + '/v1/updatemm', {phone: $L.phone, userpassword2: pwd}, function (result) {
                        if (result.rtnCode == 200) {
                            location.href = $L.getRootPath() + "/v1/toLogin";
                        } else {
                            if($L.lang=='zh-cn'){
                                $.toptip(result.msg_zh,'error');
                            }else {
                                $.toptip(result.msg_en,'error');
                            }
                        }
                    }, 'JSON');
                    return false;
                }else {
                    if($L.lang=='zh-cn'){
                        $.toptip(result.msg_zh,'error');
                    }else {
                        $.toptip(result.msg_en,'error');
                    }
                }
            }, 'JSON');
        }
    },
    sendxin_1:function(thisBtn){
        var phone=$("#phone");
        if(phone.val()==''){
            if($L.lang=='zh-cn'){
                $.toptip("手机号码不能为空",'error');
            }else {
                $.toptip("Phone number can not be blank",'error');
            }
            $.toptip(result.msg,'error');
            return false;
        }
        var a=/^1[345678]\d{9}$/.test(phone.val());
        if(a){
            $.post($L.getRootPath()+'/v1/yanPhone', {phone:phone.val()},function(result) {
                if(! result.data){
                    if($L.lang=='zh-cn'){
                        $.toptip("此手机号码没有注册过",'error');
                    }else {
                        $.toptip("This phone number has not been registered",'error');
                    }
                    $("#phone").val(phone.val().substr(0,10));
                    $("#defaultForm").bootstrapValidator("addField","phone");
                    $L.nums=30;
                }else {
                    $L.phone=phone.val();
                    $L.btn = thisBtn;
                    $L.btn.disabled = true; //将按钮置为不可点击
                    $L.btn.value = '重新获取（'+$L.nums+'）';
                    $L.clock = setInterval($L.doLoop, 1000); //一秒执行一次
                    $.post($L.getRootPath()+'/v1/sendxin', {phone:$L.phone},function(data) {
                        if(data.rtnCode==200){
                            $("#code").attr("disabled",false);
                            $("#code").focus();
                        }else {
                            $("#code").attr("disabled",true);
                        }
                        if($L.lang=='zh-cn'){
                            $.toptip(result.msg_zh,'success');
                        }else {
                            $.toptip(result.msg_en,'success');
                        }
                    }, 'JSON');
                }
            }, 'JSON');
        }else {
            if($L.lang=='zh-cn'){
                $.toptip("请输入正确的手机格式",'error');
            }else {
                $.toptip("Please enter the correct phone format",'error');
            }
        }
    },
    setCookie:function (name,value) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    },
    getCookie:function (name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)) return unescape(arr[2]);
        else return null;
    },
    delCookie:function (name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = $L.getCookie(name);
        if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
}
window.$L = login = new login();