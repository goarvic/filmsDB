<div class="formularyPart" id="filmDetailsFA">
    <div class="row">
        <div class="span6">
            <div class="col-md-3">
                <%--<div class="jumbotron">--%>
                <p class="text-center poster">
                    <g:img id="posterFA" uri="${filmDetailsFromFA.urlSmallPoster}"/>
                    <img id="posterLocal" src="#" style="display: none; width: 100%; height: 100%;" alt="Poster image" />
                </p>
                <p class="text-center">${filmDetailsFromFA.localName}</p>

                <%--</div>--%>
            </div>
        </div>


        <div class="col-md-2">
            <h4>Original Name</h4>
            ${filmDetailsFromFA.originalName}
        </div>
        <div class="col-md-2">
            <h4>Spanish Name</h4>
            ${filmDetailsFromFA.localName}
        </div>
        <div class="col-md-1">
            <h4>Year</h4>
            ${filmDetailsFromFA.year}
        </div>
        <div class="col-md-1">
            <h4>Country</h4>
            ${filmDetailsFromFA.country.localName}
        </div>
        <div class="col-md-2">
            <h4>Genre</h4>
            <g:set var="counter" value="${0}" />
            <g:each in="${filmDetailsFromFA.genres}" var="genre"><g:set var="counter" value="${counter + 1}"/><g:if test="${counter>1}">, </g:if>${genre.localName}</g:each>
        </div>


        <div class="col-md-8">
            <h4>Director</h4>
        </div>
        <g:set var="counter" value="${0}" />
        <div class="col-md-8">
            <g:each in="${filmDetailsFromFA.director}" var="director">
                <g:set var="counter" value="${counter + 1}"/>
                <g:if test="${counter>1}"> / </g:if>
                ${director.name}
            </g:each>
        </div>



        <div class="col-md-2">
            <h4>Actors</h4>
        </div>
        <g:set var="counter" value="${0}" />
        <div class="col-md-8">
            <g:each in="${filmDetailsFromFA.actors}" var="actor"><g:set var="counter" value="${counter + 1}"/><g:if test="${counter>1}">, </g:if>${actor.name}</g:each>
        </div>

        <div class="col-md-10">
            <h4>File Poster</h4>
            <div class="input-group">
                <span class="input-group-btn">
                    <span class="btn btn-primary btn-file">
                        Browse&hellip; <input id="posterFileInput" type="file" name="poster" multiple>
                    </span>
                </span>
                <input type="text" class="form-control">
            </div>
        </div>
        <div class="col-md-2">
            <h4>Disc Reference</h4>
            <input type="number" class="form-control" name="discReference" id="discReference" value="${nextDiscReference}">
        </div>

        <div class="col-md-6">
            <h4>Film Version (Spanish)</h4>
            <input type="text" class="form-control" name="filmVersion" id="filmVersion" value="Versión cinematográfica" >
        </div>
        <div class="col-md-6">
            <h4>File Name</h4>
            <input type="text" class="form-control" name="fileName" id="fileName" value="" >
        </div>
        <div class="col-md-6">
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="sendNotification" name="sendNotification" checked>
                <label class="form-check-label" for="sendNotification">Send notification</label>
            </div>

            %{--<input type="checkbox" class="form-control" name="" id="sendNotification" value="true" >--}%
        </div>
        <div class="col-md-6">
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="overrideExisting" name="overrideExisting">
                <label class="form-check-label" for="sendNotification">Override existing instance if exist</label>
            </div>
        </div>
    </div>
</div>