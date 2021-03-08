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
        }
        .title {
            font-size: large;
        }
    </style>
    
    <@com.nav/>

    <div class="diaries">
        <#list diaries as d>
            <div class="panel panel-default diary" time="${d.createdTime?long?c}">
                <div class="panel-body title">
                    <span class="title">${d.title}</span>
                    <hr>
                    <span class="contents">${d.contents}</span>
                </div>
            </div>
        </#list>
    </div>

    
    
    <script>
        var diaries = document.getElementsByClassName("diary")
        for(d in diaries) {
            var diary = diaries[d]
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