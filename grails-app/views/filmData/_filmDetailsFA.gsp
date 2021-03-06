<g:set var="countryName" value="${film.country.countryNamesLanguage.find{it.languageCode == activeLanguageCode} != null ?
        film.country.countryNamesLanguage.find{it.languageCode == activeLanguageCode}.name :
        film.country.countryNamesLanguage.find{it.languageCode == "eng"} != null ?
                film.country.countryNamesLanguage.find{it.languageCode == "eng"} : ""
}" />

<g:set var="posterName" value="${(filmDetailsLanguage.posterName != null && filmDetailsLanguage.posterName != "A") ?
                                  filmDetailsLanguage.posterName :
                                  film.filmDetailsLanguage.find{(it.posterName != null)&&(!it.posterName.equals("A"))}.posterName
}" />

<div class="formularyPart" id="filmDetailsFA">
    <div class="row">
        <div class="span6">
            <div class="col-md-3">
                <p class="text-center poster">
                    <a href="${createLink(controller: "viewMovies", action: "originalFilmPoster", params: [id : posterName])}">
                    <img class="img-responsive text-center posterMax" src="${createLink(controller: "viewMovies", action: "getMediumFilmPoster", params: [id : posterName])}"/>
                    </a>
                </p>
            </div>
        </div>


        <div class="col-md-2">
            <h4><g:message code="films.filmData.originalName"/></h4>
            ${film.originalName}
        </div>
        <div class="col-md-2">
            <h4><g:message code="films.filmData.localName"/></h4>
            ${filmDetailsLanguage.localName}
        </div>
        <div class="col-md-1">
            <h4><g:message code="films.filmData.year"/></h4>
            ${film.year}
        </div>
        <div class="col-md-1">
            <h4><g:message code="films.filmData.country"/></h4>
            ${countryName}
        </div>
        <div class="col-md-2">
            <h4><g:message code="films.filmData.genre"/></h4>
            <g:set var="counter" value="${0}" />
            <g:each in="${film.genres}" var="genre"
                ><g:set var="counter" value="${counter + 1}"
                /><g:set var="genreName" value="${genre.genreNameLanguage.find{it.language.code == activeLanguageCode} != null ?
                                                    genre.genreNameLanguage.find{it.language.code == activeLanguageCode}.name :
                                                    genre.genreNameLanguage.find{it.language.code == "eng"} != null ?
                                                            genre.genreNameLanguage.find{it.language.code == "eng"}.name :
                                                            ""}"
                /><g:if test="${counter>1}">, </g:if
                >${genreName}</g:each>
        </div>


        <div class="col-md-8">
            <h4>Director</h4>
            <g:set var="counter" value="${0}" />
            <g:each in="${film.director}" var="director">
                <g:set var="counter" value="${counter + 1}"/>
                <g:if test="${counter>1}"> / </g:if>
                <a href="${createLink(controller: "viewMovies", action: "searchMovies", params: ["search" : director.name])}">${director.name}</a>
            </g:each>

        </div>


        <div class="col-md-2">
            <h4><g:message code="films.filmData.actors"/></h4>
        </div>
        <g:set var="counter" value="${0}" />
        <div class="col-md-8">
            <g:each in="${film.actors}" var="actor"
                ><g:if test="${counter!=0}">, </g:if
                ><a href="${createLink(controller: "viewMovies", action: "searchMovies", params: ["search" : actor.name])}">${actor.name}</a
                ><g:set var="counter" value="${counter + 1}"
            /></g:each>
        </div>

    </div>
    <div class="row">
        <div class="col-md-12">
            <h4><g:message code="films.filmData.filmVersion"/></h4>
            ${savedFilm.filmVersion}
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <h4><g:message code="films.filmData.synopsis"/></h4>
            ${filmDetailsLanguage.synopsis}
        </div>
    </div>
</div>