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
    searchWord = "${searchResults.search}"
</script>

    <div id="page-body" role="main" class="container">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Movie Collection <a href="${createLink(controller: "viewMovies", action: "updateFilms")}"><span style="float:right;" class="glyphicon glyphicon-refresh"></span></a></h3>
            </div>
            <div class="panel-body">

                <g:include controller="viewMovies" action="toolBar"/>

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
                    <g:render template="filmTemplate" model="[filmData: film]"/>
                </g:each>
            </div>
        </div>
    </div>
</body>
</html>