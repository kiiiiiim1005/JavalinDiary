<!DOCTYPE html>
<html lang="en">

<#import "/templates/commons.ftl" as com>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>Diary</title>
    <@com.head/>
</head>

<body>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>

    <@com.nav/>

    <div class="container">
        <#if registered??>
            <div style="text-align: center; color: green;">회원가입이 정상적으로 완료되었습니다.</div>
        </#if>
        <div class="col-lg-4"></div>
        <div class="col-lg-4">
            <div class="jumbotron" style="padding-top: 20px;">
                <form>
                    <h3 style="text-align: center;"> 로그인 </h3>
                    <div class="form-group">
                        <input type="email" id="email" class="form-control" placeholder="이메일" hname="이메일" name="email"
                            maxlength="20">
                    </div>
                    <div class="form-group">
                        <input type="password" hname="비밀번호" id="password" class="form-control" placeholder="비밀번호"
                            name="password" maxlength="20">
                        <div id="passwordchecknotify" style="display: none;"></div>
                    </div>
                    <button id="btn_login" class="btn btn-primary form-control">로그인</button>
                    <div style="margin-top: 10px;" class=""><a href="/signup">회원가입</a></div>
                    
                </form>
            </div>
            <div id="failnotify" style="text-align: center; color: red; display: none;">가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.</div>
        </div>
    </div>


    <script type="text/javascript">
        activeNav("nav-signin")

        $('#btn_login').click(function () {
            var email = $('#email').val();
            var password = $('#password').val();
            $.ajax({
                type: "POST",
                url: "/login",
                data: "email=" + email + "&password=" + password,
                dataType: "text",
                success: function (data, textStatus, xhr) {
                    var red = findGetParameter("redirect")
                    if(red != null && red != "") {
                        window.location.href = red;        
                    } else {
                        window.location.href = "/";
                    }
                }, 
                error: function (xhr, textStatus, errorThrown) {
                    if(xhr.status == 500) {
                        alert("서버 오류로 로그인에 실패하였습니다.")
                    }
                    document.getElementById("failnotify").style.display = "block"
                },
            });
            return false;
        });

        function findGetParameter(parameterName) {
            var result = null,
                tmp = [];
            location.search
                .substr(1)
                .split("&")
                .forEach(function (item) {
                tmp = item.split("=");
                if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
                });
            return result;
        }

    </script>


<@com.footer/>
</body>

</html>