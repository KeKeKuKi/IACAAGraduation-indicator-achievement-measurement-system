/*点击弹出按钮*/
function popBox() {
    var popBox = document.getElementById("popBox1");
    var popLayer = document.getElementById("popLayer2");
    popBox.style.display = "block";
    popLayer.style.display = "block";
}

/*点击关闭按钮*/
function closeBox() {
    var popBox = document.getElementById("popBox1");
    var popLayer = document.getElementById("popLayer2");
    popBox.style.display = "none";
    popLayer.style.display = "none";
}