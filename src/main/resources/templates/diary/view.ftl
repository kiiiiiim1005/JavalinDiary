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
            padding-top: 20px;
            padding-left: 5%;
            padding-right: 5%;
            font-size: xx-large;
        }
        .title {
            font-size: x-large;
        }
        .nickname {
            color: gray;
            font-size: large;
        }
    </style>

    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <@com.nav/>
    
    <div class="panel panel-default diary" time="${diary.createdTime?long?c}" style="margin: 5px;">
        <div class="panel-body title">
            <span class="title">${diary.title}</span><br>
            <span class="nickname">${diary.writer.nickname}</span>
            <hr>
            <span class="contents">${diary.contents}</span>
        </div>
    </div>

    <script>
        initDiariy()
        function initDiariy() {
            var diary = document.getElementsByClassName("diary")[0]
            var time = diary.getAttribute("time")
            var ele = document.createElement('span')
            ele.className = "date"
            var date = new Date(Number(time))
            var datestr = date.getFullYear() + "." + date.getMonth() + "." + date.getDate() + ". " + date.getHours() + "시 " + date.getMinutes() + "분"
            ele.textContent = datestr
            diary.insertBefore(ele, diary.childNodes[1])
        }
    </script>

    <@com.footer/>
</body>

</html>