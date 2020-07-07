var index=function () {
    this.version={version:'1.0'};
    this.phone='';
    this.name='';
    this.id='';
    this.nmer='';
}

index.fn=index.prototype= {
    constructor: index,
    getRootPath:function () {
        var pathName = window.location.pathname.substring(1);
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
        return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    },
    showImg:function(val){
        console.log("----------"+val)
        var types=val.substr(val.lastIndexOf(".")+1).toLowerCase();
        if(types!="jpg" && types!="png" && types!="gif" && types!="jpeg"){
            alert("只能上传jpg,png格式的图片");
            return ;
        }
        if(val.indexOf("[")!=-1||val.indexOf("(")!=-1||val.indexOf("{")!=-1||val.indexOf("]")!=-1||val.indexOf(")")!=-1||val.indexOf("}")!=-1){
            alert("图片名称不能带有特殊符号，如:[]、()、{}等，正确名称如abc.png");
            return ;
        }
        document.getElementById('shang').click();

    },
    saveTx:function(){
        $('#userEditForm').ajaxSubmit(function (data) {
            if(data.code==200){
                location.reload();
            }else {
                alert(data.message)
            }
        })
        return false;
    },
    delmsgById:function(id){
        $.post('/web/log/delete', {
            id:id
        },function(result) {
            if (result.code==200) {
                location.reload();
            }else{
                alert(result.message);
            }
        }, 'JSON');
    },
    toggleIframe:function(){
        $("#show-feedback-submit, #show-feedback-result").toggleClass("secondary");
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
window.$I = index = new index();
