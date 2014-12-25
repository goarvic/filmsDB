<div class="row rowFilm">
    <div class="col-md-2 text-center">
        <a href="${createLink(controller: "filmData", action: "index", params:["idFilm" : filmData.idFilm, "idSavedFilm" :filmData.idSavedFilm])}">
            <img class="img-responsive text-center posterMin" src="${createLink(controller: "viewMovies", action: "getFilmPoster", params: ["posterName" : filmData.posterName])}"/>
        </a>
    </div>
    <div class="col-md-10">
        <h4>
            <a style="color: inherit;" href="${createLink(controller: "filmData", action: "index", params:["idFilm" : filmData.idFilm, "idSavedFilm" :filmData.idSavedFilm])}">
                ${filmData.localName} (${filmData.year})
                <g:if test="${!filmData.filmVersion.equals("Versión cinematográfica")}">
                     - ${filmData.filmVersion}
                </g:if>
            </a>
        </h4>
    </div>
    <div class="col-md-10 rowDirector">
        <g:set var="counter" value="${0}"/>
        <g:each in="${filmData.director}" var="person">
            <g:if test="${counter != 0}">
                /
            </g:if>
            <a href="${createLink(controller: "viewMovies", action: "searchMovies", params:["search" : person.name])}">
                ${person.name}
            </a>
            <g:set var="counter" value="${counter++}"/>
        </g:each>
    </div>
    <div class="col-md-10">
        <g:set var="counter" value="${0}"/>
        <g:each in="${filmData.actors}" var="person">
            <g:if test="${counter != 0}">
                /
            </g:if>
            <a href="${createLink(controller: "viewMovies", action: "searchMovies", params:["search" : person.name])}">
                ${person.name}
            </a>
            <g:set var="counter" value="${counter++}"/>
        </g:each>
    </div>
</div>