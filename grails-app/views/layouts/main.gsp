<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="VGA Films DB"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--<link rel="shortcut icon" href="${resource(dir: 'images', file: 'vcLogo.png')}" type="image/x-icon">--%>
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main-bootstrap.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">

    <g:javascript library="jquery" plugin="jquery"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->

    <g:layoutHead/>
    <r:layoutResources />

    <script type="text/javascript">

        $(document).ready(function () {

            var pathname = window.location.pathname;
            $('.active').removeClass('active');


            if (pathname.indexOf("createNewFilm")>0)
                $("#insertNewFilmMenu").addClass("active");
            else
                $("#CSVMenu").addClass("active");
        });

    </script>


</head>
<body>


<!-- Fixed navbar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="#">VGAFilms</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                    <li id="viewFilmsMenu" class="active"><a href="${createLink(action: 'viewCSVFiles', controller: 'stateServer')}"><span class="navmenu glyphicon glyphicon-folder-open"></span>View Collection</a></li>
                    <li id="insertNewFilmMenu" class="active"><a href="${createLink(action: 'index', controller: 'createNewFilm')}"><span class="navmenu glyphicon glyphicon-folder-open"></span>Insert New Film</a></li>
            </ul>
        </div><!--/.nav-collapse -->

    </div>
</div>


<g:if test="${request.error}">
    <div id="page-body" role="main" class="container">
        <div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span class="glyphicon glyphicon-remove" style="margin-right: 15px;"></span> ${request.error}</div>
    </div>
</g:if>
<g:elseif test="${flash.error}">
    <div id="page-body" role="main" class="container">
        <div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span class="glyphicon glyphicon-remove" style="margin-right: 15px;"></span> ${flash.error}</div>
    </div>
</g:elseif>
<g:elseif test="${request.message}">
    <div id="page-body" role="main" class="container">
        <div class="alert alert-success"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span class="glyphicon glyphicon-ok" style="margin-right: 15px;"></span> ${request.message}</div>
    </div>
</g:elseif>
<g:elseif test="${flash.message}">
    <div id="page-body" role="main" class="container">
        <div class="alert alert-success"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span class="glyphicon glyphicon-ok" style="margin-right: 15px;"></span> ${flash.message}</div>
    </div>
</g:elseif>

<g:layoutBody/>




</body>
</html>
