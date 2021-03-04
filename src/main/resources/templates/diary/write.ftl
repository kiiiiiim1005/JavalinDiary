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
    <@com.nav/>

    <div class="editor">
        
    </div>

    <script src="/js/ckeditor/ckeditor.js"></script>
	<script>ClassicEditor
			.create( document.querySelector( '.editor' ), {
                 toolbar: [ 'heading', '|', 'bold', 'italic', 'link' ],
                 language: 'ko'
			} )
			.then( editor => {
				window.editor = editor;	
			} )
			.catch( error => {
				console.error( 'Oops, something went wrong!' );
				console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
				console.warn( 'Build id: h2awqj8p91id-mhr4okwa0fu3' );
				console.error( error );
			} );
	</script>


    <@com.footer/>
</body>

</html>