<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - View Films Collection </title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
</head>
<body>
    <g:render template="settingURLs"/>
    <script type="text/javascript">
        searchWord = "${searchResults.search}"
    </script>

    <div id="page-body" role="main" class="container-fluid">
        <div class="col-sm-2">
            <g:include controller="viewMovies" action="filmStatics"/>
            <g:include controller="viewMovies" action="getFilmOfTheDay"/>
        </div>


        <div class="col-sm-10">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">Movie Collection</h3>
                </div>
                <div class="panel-body">

                    <g:render template="toolBar" model="[search : searchResults.search , genres : genres]"/>

                    <div class="row "><div class = "col-md-12 text-center rowTitleSearch"><g:message code="films.search.resultsByName"/></div></div>
                    <g:each in="${searchResults.resultsByName}" var="film">
                        <g:render template="filmTemplate" model="[filmData: film, activeLanguageCode : activeLanguageCode]"/>
                    </g:each>
                    <div class="row "><div class = "col-md-12 text-center rowTitleSearch"><g:message code="films.search.resultsByActor"/></div></div>
                    <g:each in="${searchResults.resultsByActors}" var="film">
                        <g:render template="filmTemplate" model="[filmData: film, activeLanguageCode : activeLanguageCode]"/>
                    </g:each>
                    <div class="row "><div class = "col-md-12 text-center rowTitleSearch"><g:message code="films.search.resultsByDirector"/></div></div>
                    <g:each in="${searchResults.resultsByDirector}" var="film">
                        <g:render template="filmTemplate" model="[filmData: film, activeLanguageCode : activeLanguageCode]"/>
                    </g:each>
                </div>
            </div>
        </div>
    </div>
</body>
</html>