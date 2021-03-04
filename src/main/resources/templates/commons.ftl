<#macro head>

</#macro>

<#macro nav>
    <nav class="navbar navbar-default">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#bs-example-navbar-collapse-1" aria-expaned="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">DiaryOnline</a>
        </div>

        <div class="collapse navbar-collapse" id="#bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/">메인</a></li>
                <li><a href="/diary/list">나의 일기</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                        aria-expanded="false">계정<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <#if user??>
                            <li><a href="account">계정설정</a></li>
                            <li><a href="signout">로그아웃</a></li>
                        <#else>
                            <li><a href="signin">로그인</a></li>
                            <li><a href="signup">회원가입</a></li>
                        </#if>
                    </ul>
                </li>
            </ul>
        </div>
</nav>
</#macro>

<#macro footer>
    <style>
        .footer {
            margin-top: 100px;
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;
            padding: 15px 0;
            text-align: center;
            color: white;
            background: darkgray;
        }
        .footer a {
            text-decoration: underline;
            color: white;
        }
    </style>

    <div class="footer">
        Made with Javalin, MySQL, Hibernate, Bootstrap, JQuery
        <br>
        <right><a href="https://github.com/kiiiiiim1005">GitHub</a></right>
        
    </div>
</#macro>