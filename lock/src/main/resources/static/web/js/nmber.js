var Number=function () {
    this.version={version:'1.0'};
    this.userid='';
    this.username='';
    this.zh='';
    this.en='';
    this.roleId=0;
    this.id='';
}
Number.fn=Number.prototype= {
    constructor: Number,
    getRootPath:function () {
        var pathName = window.location.pathname.substring(1);
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
        return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    },
    init:function () {
        $A.getLangTable();
        $A.getSelect();
        $A.formValidator();
    },
    getSelect:function(){
        $.post($A.getRootPath()+"/type/list",function(data){
            if(data.code==200) {
                var str=""
                $.each(data.data, function (index, items) {
                    str+="<option value="+items.sbtype+" >"+items.sbjc+"</option>";
                });
                $("#modelId").html(str);
                $('#modelId').selectpicker('refresh');
            }
        })
    },
    rest:function(){
        document.getElementById("defaultForm").reset();
        $('#defaultForm').data('bootstrapValidator', null);
        $A.formValidator();
    },
    getLangTable:function(){
        var table=$("#table").bootstrapTable({ // 对应table标签的id
            url: $A.getRootPath()+"lock/list", // 获取表格数据的url
            cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
            striped: true,  //表格显示条纹，默认为false
            pagination: true, // 在表格底部显示分页组件，默认false
            pageNumber: 1, // 首页页码
            pageSize: 100, // 页面数据条数
            pageList: [100, 200, 300, 400,500],        //可供选择的每页的行数（*）
            smartDisplay:false,
            // search:true,                      //是否显示表格搜索
            // searchOnEnterKey:true,
            // strictSearch: true,
//       singleSelect : true, // 单选checkbox
            sidePagination: 'server', // 设置为服务器端分页server,client
            sortName: 'updatedate', // 要排序的字段
            sortOrder: 'desc', // 排序规则
            idField: "id",
            cardView: false, //是否显示详细视图
            queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
                return {
                    rows: params.limit,                         //页面大小
                    page: (params.offset / params.limit) + 1,   //页码
                    sort: params.sort,      //排序列名
                    order: params.order, //排位命令（desc，asc）
                    deviceSn:$("#searchText").val()
                }
            },
            columns: [
                {title : 'No',
                    align: "center",
                    width: 60,
                    formatter: function (value, row, index) {
                        //获取每页显示的数量
                        var pageSize=$('#table').bootstrapTable('getOptions').pageSize;
                        //获取当前是第几页
                        var pageNumber=$('#table').bootstrapTable('getOptions').pageNumber;
                        //返回序号，注意index是从0开始的，所以要加上1
                        return pageSize * (pageNumber - 1) + index + 1;
                    }
                },
                {
                    field: 'modelId',
                    title: '设备型号',
                    align: 'center',
                    valign: 'middle',
                }, {
                    field: 'deviceSn',
                    title: '设备sn',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'status',
                    title: '是否激活',
                    align: 'center',
                    valign: 'middle',
                    formatter : function(value, row, index) {
                        if(value==true){
                            return "<span style='color:#008dfc'>已激活</span>";
                        }else {
                            return "<span style='color:red'>未激活</span>";
                        }
                    }
                },  {
                    field: 'connect',
                    title: '网络是否连接',
                    align: 'center',
                    valign: 'middle',
                    formatter : function(value, row, index) {
                        if(value==true){
                            return "<span style='color:#008dfc'>已连接</span>";
                        }else {
                            return "<span style='color:red'>未连接</span>";
                        }
                    }
                }, {
                    field: 'loginid',
                    title: '激活用户',
                    align: 'center',
                    valign: 'middle',
                    formatter : function(value, row, index) {
                        if(value==""||value==null){
                            return "<span style='color:red'>暂无</span>";
                        }else {
                            return "<span style='color:#008dfc'>"+value+"</span>";
                        }
                    }
                },
                {
                    field : 'action',
                    title : '操作',
                    align: 'center',
                    valign: 'middle',
                    formatter : function(value, row, index) {
                        var str="";
                        str += $A.formatString('<a href="javascript:void(0)"  onclick="$A.openXq(\'{0}\');" > <span class=\"blue ml-m pointer\">详情</span></a><a target=\"_blank\" href="/web/lock/creatRrCode?id='+row.id+'" > <span class=\"blue ml-m pointer\">安装</span></a><a href="javascript:void(0)"  onclick="$A.delete('+row.id+');" > <span class=\"blue ml-m pointer\">删除</span></a>', row.id);
                        return str;
                    }
                }
            ],
            onLoadSuccess: function(){  //加载成功时执行
                console.info("加载成功");
            },
            onLoadError: function(){  //加载失败时执行
                console.info("加载数据失败");
            },
            onClickRow:function (row,$element) {
            }

        });
    },
    sousuo:function(){
        var searchText=$("#searchText").val();
        if(searchText){
            $('#table').bootstrapTable('refreshOptions',{pageNumber:1});
            $("#table").bootstrapTable("refresh");
        }else{
            $("#table").bootstrapTable("refresh");
        }
    },
    formatString:function(str){
        for ( var i = 0; i < arguments.length - 1; i++) {
            str = str.replace("{" + i + "}", arguments[i + 1]);
        }
        return str;
    },
    openXq:function(id){
        $.post($A.getRootPath()+'/lock/get', {
            id:id
        },function(result) {
            if (result.code==200) {
                $("#typeXq").modal();
                $("#sbtype1").val(result.data.modelId);
                $("#deviceSn1").val(result.data.deviceSn);
                $("#name").val(result.data.name);
                $("#mac1").val(result.data.mac);
                $("#secretNo").val(result.data.secretNo);
                $("#wifi").val(result.data.wifi==true?'WIFI':'蓝牙')

                $("#deviceId").val(result.data.deviceId);
                $("#qrCode").val(result.data.qrCode);
                $("#status").val(result.data.status==true?'已激活':'未激活');

                $("#createdatetime").val(result.data.createdatetime);
                $("#lastupdatetime").val(result.data.lastupdatetime);
                $("#loginid").val(result.data.loginid==null?'暂未绑定':result.data.loginid);

                $("#corpname").val(result.data.corpname==null?'暂未绑定':result.data.corpname);
                $("#battery").val(result.data.battery+"%");
                $("#xstp").attr("src",result.data.icon);
            }else{
                alert(result.message);
            }
        }, 'JSON');
    },
    addSn:function(){
        $("#defaultForm").bootstrapValidator('validate');//提交验证
        if ($("#defaultForm").data('bootstrapValidator').isValid()) {
            var sn=$('#deviceSn').val();
            if(sn.indexOf("'")>-1){
                alert("设备编号不能有单引号!");
                return false;
            }
            var modelId=$('#modelId').val();
            var mac=$("#mac").val();
            $.ajaxSettings.async = false;
            $.post($A.getRootPath()+'/lock/add', {
                deviceSn:sn,
                modelId:modelId,
                mac:mac
            },function(result) {
                if (result.code==200) {
                    $("#addtype").modal("hide");
                    $("#defaultForm").data('bootstrapValidator').destroy();
                    $('#defaultForm').data('bootstrapValidator', null);
                    $A.formValidator();
                    $("#table").bootstrapTable("refresh");
                }else{
                    alert(result.message);
                }
            }, 'JSON');
        }
    },
    delete:function(id){
            $.post($A.getRootPath()+'lock/delete', {
                id:id
            },function(result) {
                if (result.code==200) {
                    $("#table").bootstrapTable("refresh");
                }else{
                    alert(result.message);
                }
            }, 'JSON');

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
    formValidator:function () {
        $('#defaultForm').bootstrapValidator({
            message: '不能为空!',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                deviceSn: {
                    validators: {
                        notEmpty: {
                            message: '设备编号不能为空!'
                        },
                        stringLength: {
                            min: 2,
                            max: 50,
                            message: '设备编号的长度在8~20之间'
                        },
                        regexp: {
                            regexp:  /^[A-Z\d]+$/,
                            message: '只能输入大写字母和数字'
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
                mac: {
                    validators: {
                        notEmpty: {
                            message: '蓝牙地址不能为空!'
                        },
                        stringLength: {
                            min: 17,
                            max: 30,
                            message: '蓝牙地址的长度在17~30之间'
                        },
                        regexp: { //正则表达式
                            regexp:  /^([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}$/,
                            message: '请输入正确的蓝牙格式'
                        }
                    }
                }
            }
        });
    }
}
window.$A = Number = new Number();
