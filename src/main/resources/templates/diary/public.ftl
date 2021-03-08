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
        .nickname {
            color: gray;
            font-size: small;
        }
        .diaryA, .diaryA:hover, .diaryA:focus, .diaryA:active {
            text-decoration: none;
            color: inherit;
        }
    </style>
    
    <@com.nav/>


    <nav class="centered" aria-label="...">
        <ul class="pagination">
          <li><a onclick="priviousPage()" "priviousA" aria-label="Previous"><span aria-hidden="true">«</span></a></li>
          <li><a onclick="nextPage()" class="nextA" aria-label="Next"><span aria-hidden="true">»</span></a></li>
        </ul>
    </nav>

    <div class="diaries">
        <#list diaries as d>
            <a class="diaryA" href="/diary/view/${d.id?long?c}">
                <div class="panel panel-default diary" time="${d.createdTime?long?c}">
                    <div class="panel-body title">
                        <span class="title">${d.title}</span><br>
                        <span class="nickname">${d.writer.nickname}</span>
                        <hr>
                        <span class="contents">${d.contents}</span>
                    </div>
                </div>
            </a>
        </#list>
    </div>
    
    <script>
        initPaginations()
        initDiaries()

        function initPaginations() {
            var page = 1
            var pageParam = getParam("page")
            if(pageParam != "") {
                page = Number(pageParam)
            }
            
            var start = Math.floor((page-1)/10)*10+1
                
            var paginations = document.getElementsByClassName("pagination")
            for(key in paginations) {
                var pagination = paginations[key]
                var ref = pagination.childNodes[2]
                for(var i = 0; i<10; i++) {
                    var ele = document.createElement("li")
                    var a = document.createElement("a")
                    a.setAttribute("href", "/diary/public/?page=" + (start+i))
                    a.textContent = start+i
                    ele.appendChild(a)
                    pagination.insertBefore(ele, ref)
                }
                pagination.childNodes[2+page-start].setAttribute("class", "active")
                //<li><a href="/diary/public/?page=1">1</a></li>
            }
        }

        function nextPage() {
            var page = 1
            var pageParam = getParam("page")
            if(pageParam != "") {
                page = Number(pageParam)
            }
            var start = Math.floor((page-1)/10)*10+1
            window.location.href = "/diary/public/?page=" + (start+10)
        }

        function priviousPage() {
            var page = 1
            var pageParam = getParam("page")
            if(pageParam != "") {
                page = Number(pageParam)
            }
            if(page < 11) return;
            var start = Math.floor((page-1)/10)*10+1
            window.location.href = "/diary/public/?page=" + (start+10)
        }

        function initDiaries() {
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
        }
    </script>


    <@com.footer/>
</body>

</html>