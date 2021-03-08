<!DOCTYPE html>
<html lang="en">
<#import "/templates/commons.ftl" as com>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <title>Diary</title>
    <@com.head/>
</head>

<body>
    <@com.nav/>

    <div class="jumbotron" style="margin: 30px; padding: 30px;">
        <h1>환영합니다 <#if user??>, ${user.nickname}님!</#if></h1>
        <p>온라인 일기장입니다.</p>
        <p>Javalin, MySQL, Hibernate, Freemarker, Bootstrap을 이용하여 제작하였습니다.</p>
        <p>GitHub: <a href="https://github.com/kiiiiiim1005/JavalinDiary">https://github.com/kiiiiiim1005/JavalinDiary</a></p>
        <p><a class="btn btn-primary btn-lg" href="/diary/write" role="button">일기 쓰기</a></p>
      </div>

    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <@com.footer/>
</body>

</html>