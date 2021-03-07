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
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <@com.nav />

    <style>
        #editor {
            min-height: 100px;
        }
    </style>

    <strong>존재하지 않는 일기입니다.</strong>

    <@com.footer />
</body>

</html>