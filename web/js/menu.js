function setStuMenu() {
    document.getElementById("menu").innerHTML = "<p class=title id=platform style=cursor:pointer;>在线课程注册系统</p><p class=option_left>免费课程</p><p class=option_left>付费课程</p><p class=option_right id=me>user_name</p><p class=option_right id=my_balance>我的余额</p><p class=option_right id=my_course>我的课程</p>";
    document.getElementById("platform").onclick = function() {
        window.location.href = "platform.html";
    }
    document.getElementById("me").onclick = function() {
        window.location.href = "my_info.html";
    }
    document.getElementById("my_course").onclick = function() {
        window.location.href = "my_course.html";
    }
    document.getElementById("my_balance").onclick = function() {
        window.location.href = "my_balance.html";
    }
}

function setTchMenu() {
    document.getElementById("menu").innerHTML = "<p class=title>在线课程注册系统（教师端）</p><p class=option_right id=me>user_name</p>";
    document.getElementById("me").onclick = function() {
        window.location.href = "my_info.html";
    }
}