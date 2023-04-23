        var role = 0;

        function select_stu() {
            document.getElementById("role_stu").className = "btn_chosen";
            document.getElementById("role_tch").className = "btn_unchosen";
            role = 0;
        }

        function select_tch() {
            document.getElementById("role_stu").className = "btn_unchosen";
            document.getElementById("role_tch").className = "btn_chosen";
            role = 1;
        }