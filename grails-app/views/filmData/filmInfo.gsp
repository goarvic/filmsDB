<!DOCTYPE html>

<g:set var="posterName" value="${(filmDetailsLanguage.posterName != null && filmDetailsLanguage.posterName != "A") ?
        filmDetailsLanguage.posterName :
        film.filmDetailsLanguage.find{(it.posterName != null)&&(!it.posterName.equals("A"))}.posterName
}" />

<html>
<head>

    <meta name="layout" content="main"/>
    <meta property="og:site_name" content="VGAFilms" />
    <meta name="description" content="${filmDetailsLanguage.synopsis}">
    <meta property="og:title" content="${filmDetailsLanguage.localName} (${film.year})" />
    <meta property="og:image" content="${createLink(controller: "viewMovies", action: "getMediumFilmPoster", absolute: true, params: [id : posterName])}">
    <meta property="og:description" content="${filmDetailsLanguage.synopsis}">
    <meta property="og:type" content="video.movie" />
    <meta property="og:locale" content="${ogLocale}"/>
    <meta property="og:url" content="${createLink(controller: "filmData", action: "viewFilm", absolute: true, params: ["id": savedFilm.id])}"/>
    <g:each in="${altOgLocales}" var="ogAltLocale">
        <meta property="og:locale:alternate" content="${ogAltLocale}"/>
    </g:each>



    <title>${film.localName} - VGAFilms DB</title>
</head>
<body>

<script type="text/javascript">
    $(document).on( 'click', '.buttonPartFormulary',
            function()
            {
                var idOfButton = this.id
                buttonFormulary(idOfButton)
            }
    );
</script>

<div id="page-body" role="main" class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Film Info</h3>
        </div>
        <div class="panel-body">
            <div class="row" id="rowButtonSelectFormulary">
                <div class="col-md-12">
                    <div class="text-center">
                        <div class="btn-group buttonSelectFormularyGroup" role="group" aria-label="...">
                            <button id="buttonFilmInfo" type="button" class="btn btn-primary buttonPartFormulary">Film Info</button>
                            <g:if test="${filmDetailsLanguage.urlTrailerYoutube != null && filmDetailsLanguage.urlTrailerYoutube != ""}">
                                <button id="buttonTrailer" type="button" class="btn btn-default buttonPartFormulary">Trailer</button>
                            </g:if>
                            <button id="buttonVideoInfo" type="button" class="btn btn-default buttonPartFormulary">Extra Info</button>
                        </div>
                    </div>
                </div>
            </div>
            <g:render template="filmDetailsFA" model="[film : film, savedFilm: savedFilm, filmDetailsLanguage : filmDetailsLanguage,
                                                       activeLanguageCode : activeLanguageCode]"/>
            <g:render template="filmDetailsVideo" model="[savedFilm: savedFilm, activeLanguageCode : activeLanguageCode]"/>

            <g:if test="${filmDetailsLanguage.urlTrailerYoutube != null && filmDetailsLanguage.urlTrailerYoutube != ""}">
                <g:render template="filmDetailsTrailer" model="[filmDetailsLanguage : filmDetailsLanguage, activeLanguageCode : activeLanguageCode]"/>
            </g:if>
        </div>
    </div>

</div>
</body>
</html>