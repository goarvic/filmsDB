
<div class="panel panel-default">
    <g:if test="${filmOfTheDay != null}">
        <div class="panel-heading">
            <h3 class="panel-title text-center">Film of the day</h3>
        </div>
        <div class="text-center">
            <a href="${createLink(controller: "filmData", action: "index", params:["idFilm" : filmOfTheDay.idFilm, "idSavedFilm" :filmOfTheDay.idSavedFilm])}">
                <img class="img-responsive text-center posterDay" src="${createLink(controller: "filmData", action: "getFilmPoster", params: ["posterName" : filmOfTheDay.posterName])}"/>
            </a>

        </div>
    </g:if>
</div>