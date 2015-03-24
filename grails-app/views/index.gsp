<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - Index</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
</head>
<body>
<div id="page-body" role="main" class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">User data</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-md-3">
                    User Name
                </div>
                <div class="col-md-3">
                    <sec:username/>
                </div>
                <div class="col-md-6 text-center">
                    <button id="changePasswordButton" type="button" class="btn btn-default" aria-label="Left Align" data-toggle="modal" data-target="#myModal">
                        <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Modify Password
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
