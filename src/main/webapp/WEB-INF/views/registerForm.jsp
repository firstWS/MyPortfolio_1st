<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page import="java.net.URLDecoder"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
        * { box-sizing:border-box; }
        form {
            width:400px;
            height:800px;
            display : flex;
            flex-direction: column;
            align-items:center;
            position : absolute;
            top:50%;
            left:50%;
            transform: translate(-50%, -50%) ;
            border: 1px solid rgb(89,117,196);
            border-radius: 10px;
        }
        .input-field,.input-field_id,.input-field_email {
            width: 300px;
            height: 40px;
            border : 1px solid rgb(89,117,196);
            border-radius:5px;
            padding: 0 10px;
            margin-bottom: 15px;
        }
        /*.input-field_id {*/
        /*    width: 300px;*/
        /*    height: 40px;*/
        /*    border : 1px solid rgb(89,117,196);*/
        /*    border-radius:5px;*/
        /*    padding: 0 10px;*/
        /*    margin-bottom: 10px;*/
        /*}*/
        /*.input-field_email {*/
        /*    width: 300px;*/
        /*    height: 40px;*/
        /*    border : 1px solid rgb(89,117,196);*/
        /*    border-radius:5px;*/
        /*    padding: 0 10px;*/
        /*    margin-bottom: 10px;*/
        /*}*/

        label {
            width:300px;
            height:30px;
            margin-top :4px;
        }
        button {
            background-color: rgb(89,117,196);
            color : white;
            width:300px;
            height:50px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 20px 0 30px 0;
        }
        .title {
            font-size : 50px;
            margin: 40px 0 30px 0;
        }
        .msg {
            height: 30px;
            text-align:center;
            font-size:16px;
            color:red;
            margin-bottom: 20px;
        }

        .sns-chk {
            margin-top : 5px;
        }

        /* 중복아이디 존재하지 않는경우 */
        .input-field_re_1,.input-field_re_3{
            font-size:10px;
            color : green;
            display : none;
        }
        /* 중복아이디 존재하는 경우 */
        .input-field_re_2,.input-field_re_4{
            font-size:10px;
            color : red;
            display : none;
        }
    </style>
    <title>Register</title>
</head>
<body>
 <form action="<c:url value="/register/add"/>" method="POST" onsubmit="return formCheck(this)">
<%--<form:form modelAttribute="userDto">--%>
    <div class="title">회원 가입</div>
    <div id="msg" class="msg"><form:errors path="id"/></div>
    <label>아이디</label>
    <div>
        <input class="input-field_id" type="text" name="id" placeholder="8~12자리의 영대소문자와 숫자 조합" autofocus>
    </div>

    <%-- 아이디중복 Test--%>
    <span class="input-field_re_1">사용 가능한 아이디입니다.</span>
    <span class="input-field_re_2">아이디가 이미 존재합니다.</span>
    <%--  ---------------------------------  ----%>

    <label>비밀번호</label>
    <input class="input-field" type="text" name="pwd" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <label>이름</label>
    <input class="input-field" type="text" name="name" placeholder="이름">
    <label>이메일</label>
    <div>
        <input class="input-field_email" type="text" name="email" placeholder="example@email.co.kr">
    </div>

    <%-- 아이디중복 Test--%>
    <span class="input-field_re_3">사용 가능한 이메일입니다.</span>
    <span class="input-field_re_4">이메일이 이미 존재합니다.</span>
    <%--  ---------------------------------  ----%>

    <label>생일</label>
    <input class="input-field" type="text" name="birth" placeholder="2020-10-21">

    <button>회원 가입</button>
 </form>
<script>
    //아이디 중복 검사
    $('.input-field_id').on("propertychange change keyup paste input", function (){
        // console.log("ID keyup 테스트");
        var userID = $('.input-field_id').val();    // .input-field_id에 입력되는 값
        var data = {userID : userID}    //'컨트롤러에 넘길 데이터 이름' : '데이터(.input-field_id에 입력되는 값)'
        $.ajax({
            type: "post",
            url: "register/userIdChk",
            data: data,
            success: function (result) {
                // console.log("성공 여부" + result);
                if (result != 'fail') {
                    $('.input-field_re_1').css("display", "inline-block");
                    $('.input-field_re_2').css("display", "none");
                } else {
                    $('.input-field_re_1').css("display", "none");
                    $('.input-field_re_2').css("display", "inline-block");
                }
            }// success
        }); //ajax
    });
    //이메일 중복 검사
    $('.input-field_email').on("propertychange change keyup paste input", function (){
        // console.log("Email keyup 테스트");
        var userEmail = $('.input-field_email').val();  // .input-field_email에 입력되는 값
        var data = {userEmail : userEmail}  //'컨트롤러에 넘길 데이터 이름' : '데이터(.input-field_email에 입력되는 값)'
        $.ajax({
           type: "post",
           url : "register/userEmailChk",
           data : data,
           success : function (result){
                // console.log("성공 여부" + result);
               if(result != 'fail'){
                   $('.input-field_re_3').css("display","inline-block");
                   $('.input-field_re_4').css("display","none");
               }else{
                   $('.input-field_re_3').css("display","none");
                   $('.input-field_re_4').css("display","inline-block");
               }
           }// success
        }); //ajax
    });

    function formCheck(frm) {
        let msg ='';

        //ID
        if(frm.id.value.length==0) {
            setMessage('ID를 입력해주세요.', frm.id);
            return false;
        }
        if(frm.id.value.length<3) {
            setMessage('ID의 길이는 3글자 이상이어야 합니다.', frm.id);
            return false;
        }
        //PW
        if(frm.pwd.value.length==0) {
            setMessage('password를 입력해주세요.', frm.pwd);
            return false;
        }
        if(frm.pwd.value.length<4) {
            setMessage('password의 길이는 4자 이상이어야 합니다.', frm.pwd);
            return false;
        }
        //Name
        if(frm.name.value.length==0) {
            setMessage('이름을 입력해주세요.', frm.name);
            return false;
        }
        //E-mail
        if(frm.email.value.length==0) {
            setMessage('이메일을 입력해주세요.', frm.email);
            return false;
        }
        //생년월일
        if(frm.birth.value.length==0) {
            setMessage('생년월일을 입력해주세요.', frm.birth);
            return false;
        }
        // if(frm.birth.value.length != 8) {
        //     setMessage('생년월일이 8자리를 입력해주세요.', frm.birth);
        //     return false;
        // }

        return true;
    }
    function setMessage(msg, element){
        document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;  //$EL{'$TL{}'}
        if(element) {
            element.select();
        }
    }
</script>
</body>
</html>