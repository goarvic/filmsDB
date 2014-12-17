<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - View Films Collection</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.min.js')}"></script>

</head>
<body>

<script type="text/javascript">
    urlSearchMovies = "${createLink(controller: "viewMovies", action: "searchMovies")}"
</script>


    <div id="page-body" role="main" class="container">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Movie Collection <a href="${createLink(controller: "viewMovies", action: "updateFilms")}"><span style="float:right;" class="glyphicon glyphicon-refresh"></span></a></h3>
            </div>
            <div class="panel-body">
                <div class="row rowFilters">
                    <div class="col-md-1">
                    </div>
                    <div class="col-md-3">
                    </div>
                    <div class="col-md-4 text-right">
                        <label for="searchMovies" style="margin-top:6px;">Search</label>
                    </div>
                    <div class="col-md-3">
                        <input type="text" class="form-control" name="searchMovies" id="searchMovies" value="${searchResults.search}">
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-default" id="buttonSearch" >
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </div>

                </div>
                <div class="row "><div class = "col-md-12 text-center rowTitleSearch">RESULTS BY NAME</div></div>
                <g:each in="${searchResults.resultsByName}" var="film">
                    <g:film filmData="${film}"></g:film>
                </g:each>
                <div class="row "><div class = "col-md-12 text-center rowTitleSearch">RESULTS BY ACTOR</div></div>
                <g:each in="${searchResults.resultsByActors}" var="film">
                    <g:film filmData="${film}"></g:film>
                </g:each>
                <div class="row "><div class = "col-md-12 text-center rowTitleSearch">RESULTS BY DIRECTOR</div></div>
                <g:each in="${searchResults.resultsByDirector}" var="film">
                    <g:film filmData="${film}"></g:film>
                </g:each>
            </div>
        </div>
    </div>
</body>
</html>