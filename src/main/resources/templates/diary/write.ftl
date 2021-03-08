<!DOCTYPE html>
<html lang="en">
<#import "/templates/commons.ftl" as com>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <title>Diary</title>
    <@com.head />
</head>

<body>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <@com.nav />

    <style>
        #editor {
            min-height: 100px;
        }
        #input-group-title {
            margin-bottom: 5px;
        }
        #input-bottom {
            margin-top: 10px;
        }
    </style>


    <div id="input-group-title" class="input-group">
        <span class="input-group-addon" id="basic-addon3">제목</span>
        <input type="text" id="title" class="form-control" id="basic-url" aria-describedby="basic-addon3">
    </div>


    <div id="editor">

    </div>

    <script src="/js/ckeditor/ckeditor.js"></script>
    <script>ClassicEditor
            .create(document.querySelector('#editor'), {
                toolbar: ['heading', '|', 'bold', 'italic', 'link'],
                language: 'ko'
            })
            .then(editor => {
                window.editor = editor;
            })
            .catch(error => {
                console.error('Oops, something went wrong!');
                console.error('Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:');
                console.warn('Build id: h2awqj8p91id-mhr4okwa0fu3');
                console.error(error);
            });
    </script>

    <div id="input-bottom">
        <div class="btn-group" data-toggle="buttons">
            <label id="public" class="btn btn-primary">
              <input type="radio" name="options" id="option1">공개
            </label>
            <label class="btn btn-primary active">
              <input type="radio" name="options" id="option2" checked>비공개
            </label>
          </div>
        <button class="btn btn-default" onclick="writeDiary()">작성하기</button>
    </div>
    

    <script>
        /**

        * 스크립트 기반 POST 전송
        * @param action - URL
        * @param params - JSON
        * @returns
        */
        function sendPost(action, params) {
            var form = document.createElement('form');
            form.style.display = "none"
            form.setAttribute('method', 'post');
            form.setAttribute('action', action);
            document.charset = "utf-8";
            for (var key in params) {
                var field = document.createElement('input');
                field.setAttribute('type', 'text');
                field.setAttribute('name', key);
                field.value = params[key]
                form.appendChild(field);
            }
            document.body.appendChild(form);
            form.submit();
            
        }

        function writeDiary() {
            var title = document.querySelector("#title").value
            if(title == "") {
                alert("제목을 입력해주세요"); return;
            }
            if(title.length > 255) {
                alert("제목이 너무 깁니다."); return;
            }
            var isPublic = document.querySelector("#public").className.indexOf("active") > -1;
            var clone = document.querySelector(".ck-content").cloneNode(true)
            clone.setAttribute("contenteditable", "false")
            var text = clone.outerHTML.toString()
            if(text.length > 5000) {
                alert("글자수가 너무 많습니다."); return;
            }
            var obj = {
                "contents": text,
                "title": title,
                "public": isPublic
            }
            sendPost("/diary/write", obj)
        }
    </script>


    <@com.footer />
</body>

</html>