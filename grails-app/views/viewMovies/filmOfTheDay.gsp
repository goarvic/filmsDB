
<div class="panel panel-default">
    <g:if test="${filmOfTheDay != null}">
        <div class="panel-heading">
            <h3 class="panel-title text-center"><g:message code="films.filmOfTheDay"/></h3>
        </div>
        <div class="text-center">
            <a href="${createLink(controller: "filmData", action: "viewFilm", params:[lang:"es", "id" :filmOfTheDay.idSavedFilm])}">
                <img class="img-responsive text-center posterDay" src="${createLink(controller: "viewMovies", action: "getMediumFilmPoster", params: [  "id": filmOfTheDay.posterName])}"/>
            </a>

        </div>
    </g:if>
</div>