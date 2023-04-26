function getStrFromGender(gender) {
    var str;
    if (gender == 0) {
        str = "女";
    } else if (gender == 1) {
        str = "男";
    } else if (gender == 2) {
        str = "保密";
    }
    return str;
}

function getGenderFromStr(str) {
    var gender;
    if (str == "女") {
        gender = 0;
    } else if (str == "男") {
        gender = 1;
    } else if (str == "保密") {
        gender = 2;
    }
    return gender;
}