/*点击弹出按钮*/
function popBox(id) {
    var span = document.getElementById(setTargetVlue);
    span.val(id);
    var popBox = document.getElementById("popBox");
    var popLayer = document.getElementById("popLayer");
    popBox.style.display = "block";
    popLayer.style.display = "block";
};

/*点击关闭按钮*/
function closeBox() {
    var popBox = document.getElementById("popBox");
    var popLayer = document.getElementById("popLayer");
    popBox.style.display = "none";
    popLayer.style.display = "none";
}

function saveTask(id) {
    var taskId = id;
    var taskDiscriblestr = "#"+id+"taskdiscrible";
    var taskDiscrible = $(taskDiscriblestr).val();
    var taskMixstr = "#"+id+"taskmix";
    var taskMix = $(taskMixstr).val();

    if(taskMix==""&&taskDiscrible==""){
        alert("数据输入不能为空");
        window.location.reload();
        return false;
    }
    $.ajax({
        url:"/admin/saveCourseTask",
        async:true,
        method:"post",
        data:{taskId:taskId,taskDiscribe:taskDiscrible,targetMix:taskMix},
        success:function (result) {
            if(result=="true"){
                var divbox = "#"+id+"divbox";
                $(taskDiscriblestr).css("background-color","lightgreen");
                $(taskMixstr).css("background-color","lightgreen");
                window.location.reload();
            }else if(result=="false") {
                alert("请检查您的数据！注：课程教学目标权重总和范围为0-1");
                window.location.reload();
            }else {
                window.location.reload();
                alert("服务器异常，请稍后再试！");
            }
        },
        error:function () {
            window.location.reload();
            alert("系统服务器异常，请稍后再试！");
        }
    })
}

function delTask(id) {
    if(confirm("确定要删除此课程目标及对应分数信息？")){
        $.ajax({
            url:"/admin/deleteCourseTask",
            async:true,
            method:"post",
            data:{taskId:id},
            success:function (result) {
                if(result=="true"){
                    alert("已删除，请注意更新成绩信息！");
                    window.location.reload();
                }else {
                    window.location.reload();
                    alert("服务器异常，请稍后再试！");
                }
            },
            error:function () {
                window.location.reload();
                alert("系统服务器异常，请稍后再试！");
            }
        })
    }

}

function addTrbar(courseId,targetId) {
    var addbarid = "#"+targetId+"addbar";
    $(addbarid).css({ "display": "none" });
    var div = "#"+targetId+"div";
    $(div).append("<tr>\n" +
        "              <div>\n" +
        "                      <div>\n" +
        "                          <td>●</td>\n" +
        "                          <td><input style=\"width: 450px\" id='"+targetId+"newdiscribe'></td>\n" +
        "                          <td><input style=\"width: 30px\" id='"+targetId+"newmix'></td>\n" +
        "                          <td>\n" +
        "                               <button type=\"button\" class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"newsaveTask("+courseId+","+targetId+")\">保存</button>\n" +
        "                          </td>\n" +
        "                     </div>\n" +
        "               </div>\n" +
        "          </tr>");
}


function newsaveTask(courseId,targetId) {
    var disid = "#"+targetId+"newdiscribe";
    var dis = $(disid).val();
    var mixid = "#"+targetId+"newmix";
    var mix = $(mixid).val();
    $.ajax({
        url:"/admin/addCourseTask",
        async:true,
        method:"post",
        data:{courseId:courseId,targetId:targetId,dis:dis,mix:mix},
        success:function (result) {
            if(result=="true"){
                window.location.reload();
            }else {
                alert("请检查您的数据！注：权重总和范围为0~1");
            }
        },
        error:function () {
            window.location.reload();
            alert("系统服务器异常，请稍后再试！");
        }
    })

}