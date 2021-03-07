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
    
    <style>
        .diary {
            padding: 16px;
            margin: 10px;
            margin-bottom: 16px;
            background-color: #FFFAF0;
            border-radius: 4px;
            transition: box-shadow 280ms cubic-bezier(.4,0,.2,1);
        }
        .title {
            font-size: large;
        }
    </style>
    
    <@com.nav/>

    <div class="diaries">
        <#list diaries as d>
            <div class="diary">
                <span class="title">${d.title}</span>
                <span class="contents">${d.contents}</span>
            </div>
        </#list>
    </div>
    

    <@com.footer/>
</body>

</html>