function setStuMenu() {
    document.getElementById("menu").innerHTML = "<p class=title>在线课程注册系统</p><p class=option_left>免费课程</p><p class=option_left>付费课程</p><p class=option_right id=me>user_name</p><p class=option_right>我的订单</p><p class=option_right>我的课程</p>";
}

function setTchMenu() {
    document.getElementById("menu").innerHTML = "<p class=title>在线课程注册系统（教师端）</p><p class=option_right id=me>user_name</p>";
    document.getElementById("me").onclick = function() {
        window.location.href = "my_info.html";
    }
}