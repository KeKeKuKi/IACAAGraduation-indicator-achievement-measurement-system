function cleanWrongMessage(){
    $("#message").html("");
}

function logincheck(id){
    if($("#name").val()==""){
        alert("请输入账户名！");
        loginform.name.select();
        return false;
    }
    if($("#pass").val()==""){
        alert("请输入密码");
        loginform.passWord.select();
        return false;
    }
    var name = $("#name").val();
    var passWord = $("#pass").val();
    var url = "/admin/adminlogin";
    var positionurl = "/admin/adminmian";
    if(id==2){
        url = "/teacher/teacherlogin";
        positionurl = "/teacher/teachermain";
    }
    if(id==3){
        url = "/student/studentlogin";
        positionurl = "/student/studentmain";
    }

    $.ajax({
        url:url,
        async:false,
        method:"post",
        data:{name:name,passWord:passWord},
        success:function (result) {
            if(result=="1"){
                window.location.href = positionurl;
            }else if(result=="0"){
                $("#message").html("密码输入错误");
            }else if(result=="-1"){
                $("#message").html("用户名不存在");
            }else if(result=="-2"){
                $("#message").html("系统异常！");
            }else {
                $("#message").html("异常操作！");
            }
        },
        error:function () {
            alert("系统异常，登录失败，请联系管理员！");
        }
    })
}
