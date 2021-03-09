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
        .paginations {
            display: inline-block;
            width: 50%;
            margin-left: 25%;
        }
        .paginations * {
            display: flex;
            justify-content: center;
        }
        .dates {
            display: inline-block;
        }
    </style>
    
    <@com.nav/>

    <noscript>
        브라우저가 자바스크립트를 지원하지 않습니다.
        페이지를 정상적으로 표시하기 위해 자바스크립트가 지원되는 브라우저를 이용해주세요.
    </noscript>

    <div style="padding-left: 15px;">
        <h1>공개된 일기</h1>
        <h3>다른 사용자들과 일기를 공유할 수 있습니다.</h3>
        <br>
        기간 선택: 
        <div class="dates">
            <input id="startdate" type="date" value="xxx" min="yyy" max="zzz">&nbsp;~&nbsp; 
            <input id="enddate" type="date" value="xxx" min="yyy" max="zzz">
        </div>
        <hr>
    </div>
    
    <div class="diaries">
        <#list diaries?reverse as d>    
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
    <div class="paginations">
        <nav aria-label="...">
            <ul class="pagination">
                <li><a onclick="priviousPage()" "priviousA" aria-label="Previous"><span aria-hidden="true">«</span></a></li>
                <li><a onclick="nextPage()" class="nextA" aria-label="Next"><span aria-hidden="true">»</span></a></li>
            </ul>
        </nav>
    </div>
    <script>
        initDiaries()
        initPaginations()

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
            window.location.href = "/diary/public/?page=" + (start-10)
        }

        function initDiaries() {
            var diaries = document.getElementsByClassName("diary")
            for(const diary of diaries) {
                var time = diary.getAttribute("time")
                var ele = document.createElement('span')
                ele.className = "date"
                var date = new Date(Number(time))
                var datestr = date.getFullYear() + "." + date.getMonth() + "." + date.getDate() + ". " + date.getHours() + "시 " + date.getMinutes() + "분"
                ele.textContent = datestr
                diary.insertBefore(ele, diary.childNodes[1])
            }

            for(const span of document.getElementsByClassName("contents")) {
                var str = ""
                for(const child of span.childNodes) {
                    str += child.textContent
                    str += " "
                    if(str.length > 700) {
                        str = str.substring(0, 700) + "..."
                        break;
                    }
                }
                span.innerHTML = ""
                span.textContent = str
            }
        }
    </script>


    <@com.footer/>
</body>

</html>