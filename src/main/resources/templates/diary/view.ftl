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

    <style>
        .diary {
            padding-left: 5%;
            padding-right: 5%;
        }
        .title {
            font-size: 24px;
        }
        .contents {
            font-size: 19px;
        }
    </style>

    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <@com.nav/>

    
    
    <div class="diary">
        <div class="panel panel-default">
            <div class="panel-body title">
                ${diary.title}
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-body contents">
                ${diary.contents}
            </div>
        </div>
    </div>
    <@com.footer/>
</body>

</html>