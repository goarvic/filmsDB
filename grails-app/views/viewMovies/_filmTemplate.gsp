

<div class="row rowFilm">
    <div class="col-md-2 text-center">
        <a href="${createLink(controller: "filmData", action: "index", params:["idFilm" : filmData.idFilm, "idSavedFilm" :filmData.idSavedFilm])}">
            <img class="img-responsive text-center posterMin" src="${createLink(controller: "viewMovies", action: "getFilmPoster", params: ["posterName" : filmData.posterName])}"/>
        </a>
    </div>
    <sec:ifAllGranted roles="ROLE_ADMIN">
        <div class="col-md-9">
    </sec:ifAllGranted>
    <sec:ifNotGranted roles="ROLE_ADMIN">
        <div class="col-md-10">
    </sec:ifNotGranted>
            <div class="col-md-12">
                <h4>
                    <a class="filmTitle" style="color: inherit;" href="${createLink(controller: "filmData", action: "index", params:["idFilm" : filmData.idFilm, "idSavedFilm" :filmData.idSavedFilm])}">
                        ${filmData.localName} (${filmData.year})
                        <g:if test="${!filmData.filmVersion.equals("Versión cinematográfica")}">
                             - ${filmData.filmVersion}
                        </g:if>
                    </a>
                    <img class="img-responsive text-center flag" src="${createLink(controller: "viewMovies", action: "getFlag", params: ["countryCode" : filmData.country.countryCode])}"/>
                </h4>
            </div>
            <div class="col-md-12 rowDirector">
                <g:set var="counter" value="${0}"/>
                <g:each in="${filmData.director}" var="person">
                    <g:if test="${counter != 0}">
                        /
                    </g:if>
                    <a href="${createLink(controller: "viewMovies", action: "searchMovies", params:["search" : person.name])}">
                        ${person.name}
                    </a>
                    <g:set var="counter" value="${counter = counter+1}"/>
                </g:each>
            </div>
            <div class="col-md-12">
                <g:set var="counter" value="${0}"/>
                <g:each in="${filmData.actors}" var="person"
                    ><g:if test="${counter != 0}">, </g:if
                    ><a href="${createLink(controller: "viewMovies", action: "searchMovies", params:["search" : person.name])}">${person.name}</a
                    ><g:set var="counter" value="${counter = counter+1}"
                /></g:each>
            </div>
        </div>
    <sec:ifAllGranted roles="ROLE_ADMIN">

            <div class="col-md-1 text-center vertical-center" style="margin-top:4%;">
                <button type="button" class="btn btn-default btn-lg btn-remove-film" id="rm_${filmData.idSavedFilm}">
                    <span class="glyphicon glyphicon-remove" style="color: red;"></span>
                </button>
            </div>
    </sec:ifAllGranted>
</div>