<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - View Films Collection</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.min.js')}"></script>

</head>
<body>
    <div id="page-body" role="main" class="container">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Initial data files</h3>
            </div>

            <div class="row">
                <div class="col-md-3">
                    <img src="<g:createLink controller="viewMovies" action="getFilmPoster"
                                            params="[posterName: 'Interstellar.jpg']"/>"/>
                </div>

            </div>

            <g:include controller="viewMovies" action="paginateTab"/>

        </div>
    </div>
</body>
</html>