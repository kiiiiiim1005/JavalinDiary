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

    <#if user??>
        환영합니다, ${user.nickname}님.
    </#if>

    <a href="/diary/write">일기쓰기</a>

    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <@com.footer/>
</body>

</html>