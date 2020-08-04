function excludeSpecial (param) {
    param = param.replace(/[\'\"\\\/\b\f\n\r\t]/g, '');
    param = param.replace(/[\@\#\$\%\^\&\*\{\}\:\"\“\;\:\|\、\,\，\'\’\<\>\?]/);
    param = param.replace(/(^\s*)|(\s*$)/g, "");
    return param;
}

function excludeSpace(param) {
    param = param.replace(/(^\s*)|(\s*$)/g, "");
    return param;
}

function userNameCheck(userName) {
    var check_userName = document.getElementById("check_userName");
    check_userName.innerHTML = "";
    var reg_userName = /^[\S\n\s]{0,4}$/;
    var reg_userName2 = /^([a-zA-Z0-9_-])/;
    var flag = reg_userName.test(userName);
    var flag2 = reg_userName2.test(userName);
    if(!flag){
        check_userName.innerHTML = "用户名字符长度有误";
        return flag;
    }
    if(!flag2){
        check_userName.innerHTML = "用户名字符格式有误";
        return flag2;
    }
}

function passwordCheck(password) {
    var check_password = document.getElementById("check_password");
    check_password.innerHTML = "";
    var reg_password = /^\w{6,12}$/;
    var flag = reg_password.test(password);
    if(!flag){
        check_password.innerHTML = "密码格式有误";
        return flag;
    }
}

function emailCheck(email) {
    var check_email = document.getElementById("check_email");
    check_email.innerHTML = "";
    var reg_email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    var flag = reg_email.test(email);
    if(!flag){
        check_email.innerHTML = "邮箱格式有误";
        return flag;
    }
}

function titleCheck(postTitle) {
    var check_title = document.getElementById("check_title");
    check_title.innerHTML = "";
    var reg_title = /^[\S\n\s]{0,10}$/;
    var flag = reg_title.test(postTitle);
    if(!flag){
        check_title.innerHTML = "标题字符长度有误";
        return flag;
    }
}

function bodyCheck(postBody) {
    var check_body = document.getElementById("check_body");
    check_body.innerHTML = "";
    var reg_body = /^[\S\n\s]{0,50}$/;
    var flag = reg_body.test(postBody);
    if(!flag){
        check_body.innerHTML = "内容字符长度有误";
        return flag;
    }
}

function signatureCheck(postBody) {
    var check_body = document.getElementById("check_body");
    check_body.innerHTML = "";
    var reg_body = /^[\S\n\s]{0,30}$/;
    var flag = reg_body.test(postBody);
    if(!flag){
        check_body.innerHTML = "内容字符长度有误";
        return flag;
    }
}