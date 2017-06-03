<!DOCTYPE html>

<html>
<head>
    <meta name="layout" content="main"/>
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