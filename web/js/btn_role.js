function select_stu() {
    document.getElementById("role_stu").className = "btn_chosen";
    document.getElementById("role_tch").className = "btn_unchosen";
}

function select_tch() {
    document.getElementById("role_stu").className = "btn_unchosen";
    document.getElementById("role_tch").className = "btn_chosen";
}