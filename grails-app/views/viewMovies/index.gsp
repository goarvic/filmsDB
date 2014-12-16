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
                <h3 class="panel-title">Movie Collection <a href="${createLink(controller: "viewMovies", action: "updateFilms")}"><span style="float:right;" class="glyphicon glyphicon-refresh"></span></a></h3>
            </div>
            <div class="panel-body">
                <div class="row rowFilters">
                    <div class="col-md-1">
                        <label for="sortMovies" style="margin-top:6px;">Sort By</label>
                    </div>
                    <div class="col-md-3">
                        <select class="form-control" name="sortMovies" id="sortMovies">
                            <option value="${0}" selected="true">Date of insertion</option>
                            <option value="${1}">Name</option>
                            <option value="${1}">Year</option>
                        </select>
                    </div>
                </div>

                <g:include controller="viewMovies" action="paginateTab"/>

                <g:each in="${resultsPaginated}" var="film">
                    <g:film filmData="${film}"></g:film>
                </g:each>


                <g:include controller="viewMovies" action="paginateTab"/>
            </div>
        </div>
    </div>
</body>
</html>