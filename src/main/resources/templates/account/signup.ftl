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
    <@com.nav/>

    <div class="container">
        <div class="col-lg-4"></div>
        <div class="col-lg-4">
            <div class="jumbotron" style="padding-top: 20px;">
                <form method="post" action="signup" name="signupform" id="signupform">
                    <h3 style="text-align: center;"> 회원가입 </h3>

                    <div style="background-color: #f2dede;">
                        <span id="duplicate-email">- 이메일이 다른 회원과 중복됩니다.</span>
                        <br>
                        <span id="duplicate-nickname">- 닉네임이 다른 회원과 중복됩니다.</span>
                    </div>

                    <div class="form-group">
                        <input type="email" id="email" class="form-control" placeholder="이메일" hname="이메일" name="email" maxlength="20">
                    </div>
                    <div class="form-group">
                        <input type="password" hname="비밀번호" id="password" onchange="checkPassword()" onkeyup="checkPassword()" class="form-control" placeholder="비밀번호" name="password"
                            maxlength="20">
                        <input type="password" hname="비밀번호 확인" onchange="checkPassword()" onkeyup="checkPassword()" id="passwordcheck" class="form-control" placeholder="비밀번호 확인"
                            maxlength="20">
                        <div id="passwordchecknotify" style="display: none;"></div>
                    </div>
                    <div class="form-group">
                        <input type="text" id="nickname" class="form-control" placeholder="닉네임" hname="닉네임" name="nickname" maxlength="20">
                    </div>
                    <input type="submit" onclick="return submitForm()" class="btn btn-primary form-control" value="로그인">
                </form>
            </div>
        </div>
    </div>


    <script type="text/javascript">
        activeNav("nav-signup")

        const form = document.forms["signupform"]
        const pwInput = document.getElementById('password')
        const pwCheckInput = document.getElementById('passwordcheck')
        const pwState = document.getElementById('passwordchecknotify')

        function submitForm() {
            var p1 = pwInput.value
            var p2 = pwCheckInput.value;
            if( p1 != p2 ) {
                alert("비밀번호가 일치 하지 않습니다");
                return false;
            }

            var alertstr = ""
            var inputs = form.getElementsByTagName("input")
            for (var i = 0; i < inputs.length; i++) {
                if(inputs[i].value == "") {
                    alertstr += inputs[i].getAttribute("hname") + ", "
                }
            }
            if(alertstr != "") {
                if(alertstr.endsWith(", ")) {
                    alertstr = alertstr.substr(0, alertstr.length-2)
                }
                alert(alertstr + "칸을 입력해주세요")
                return false;
            }
            

            $.ajax({
                type: "POST",
                url: "/check-duplicate",
                data: "email=" + $("#email").val() + "&nickname=" + $("#nickname").val(),
                dataType: "text",
                success: function (data, textStatus, xhr) {
                    if(data == "") {
                        
                    } else {
                        if(data.startsWith("email")) {

                        }
                        if(data.endsWith) {

                        }
                    }
                }, 
                error: function (xhr, status, error) {   
                    alert("서버 오류로 회원가입에 실패하였습니다.")
                }
            })
            return false;
        }

        function checkPassword() {
            var p1 = pwInput.value;
            var p2 = pwCheckInput.value;
            if(p1 == "" || p2 =="") {
                pwState.style.display = "none";
                return
            }
            if(p1 != p2) {
                pwState.textContent = "비밀번호가 일치하지 않습니다"
                pwState.style.color = "red"
                pwState.style.display="inline"
            } else {
                pwState.textContent = "비밀번호가 일치합니다"
                pwState.style.color = "green"
                pwState.style.display="inline"
            }
        }
      </script>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <@com.footer/>
</body>

</html>