<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - Insert New Film</title>


    <script type="text/javascript">
        $(document).on( 'click', '.buttonPartFormulary',
                function()
                {
                    var idOfButton = this.id
                    buttonFormulary(idOfButton)
                }
        );
    </script>




</head>
<body>


<div id="page-body" role="main" class="container">

    <div class="alert alert-warning" role="alert">
        <b>Warning!</b> There is an existing instance of this film already saved in database. Pay attention on the version of the film
    </div>


    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Film Info</h3>
        </div>
        <g:form action="saveFilm" class="validateForm" controller="createNewFilm" method="POST" name="filmForm">
            <div class="panel-body">
                <div class="row" id="rowButtonSelectFormulary">
                    <div class="col-md-12">
                        <div class="text-center">
                            <div class="btn-group buttonSelectFormularyGroup" role="group" aria-label="...">
                                <button id="buttonFilmInfo" type="button" class="btn btn-primary buttonPartFormulary">Film Info</button>
                                <button id="buttonAudioTracks" type="button" class="btn btn-default buttonPartFormulary">Audio Tracks</button>
                                <button id="buttonSubtitleTracks" type="button" class="btn btn-default buttonPartFormulary">Subtitle Tracks</button>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="formularyPart" id="filmDetailsFA">
                    <div class="row">
                        <div class="span6">
                            <div class="col-md-3">
                                <%--<div class="jumbotron">--%>
                                <p class="text-center"><g:img uri="${filmDetailsFromFA.urlSmallPoster}"/></p>
                                <p class="text-center">${filmDetailsFromFA.spanishName}</p>
                                <p>
                                <h4>File Poster</h4>
                                <div class="input-group">
                                    <span class="input-group-btn">
                                        <span class="btn btn-primary btn-file">
                                            Browse&hellip; <input type="file" name="poster" multiple>
                                        </span>
                                    </span>
                                    <input type="text" class="form-control">
                                </div>
                            </p>

                                <%--</div>--%>
                            </div>
                        </div>


                        <div class="col-md-2">
                            <h4>Original Name</h4>
                            ${filmDetailsFromFA.originalName}
                        </div>
                        <div class="col-md-2">
                            <h4>Spanish Name</h4>
                            ${filmDetailsFromFA.spanishName}
                        </div>
                        <div class="col-md-2">
                            <h4>Year</h4>
                            ${filmDetailsFromFA.year}
                        </div>
                        <div class="col-md-2">
                            <h4>Country</h4>
                            ${filmDetailsFromFA.country.spanishName}
                        </div>

                        <div class="col-md-3">
                            <h4>Film Version (Spanish)</h4>
                            <input type="text" class="form-control" name="filmVersion" id="filmVersion" value="Versión cinematográfica" >
                        </div>

                    </div>

                    <div class="row">
                        <div class="col-md-2">
                            <h4>Director</h4>
                        </div>
                        <g:set var="counter" value="${0}" />
                        <div class="col-md-12">
                            <g:each in="${filmDetailsFromFA.director}" var="director">
                                <g:set var="counter" value="${counter + 1}"/>
                                <g:if test="${counter>1}"> / </g:if>
                                ${director.name}
                            </g:each>
                        </div>
                    </div>
                </div>








                <div class="row formularyPart" id="filmDetailsAudio" style="display:none;">
                    <div class="col-md-12">
                        <g:set var="counter" value="${0}" />

                        <g:each in="${filmToSave.audioTracks}" var="audioTrack">
                            <g:set var="counter" value="${counter + 1}"/>
                            <h3>Audio Track ${counter}</h3>
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="filmToSave.audioTracks[${counter-1}]">Language</label>
                                    <select class="form-control" name="audioTracks[${counter-1}].language.code" id="audioTracks[${counter-1}].language.code">
                                        <option value="${audioTrack.language.code}">${audioTrack.language.spanishName}</option>
                                        <g:each in="${languages}" var="language">
                                            <g:if test="${language.code != audioTrack.language.code}">
                                                <option value="${language.spanishName}">${language.spanishName}</option>
                                            </g:if>

                                        </g:each>
                                    </select>
                                    <%--<input type="email" class="form-control" id="language" value="${audioTrack.languageName}">--%>
                                </div>
                                <div class="col-md-4">
                                    <label for="audioTrack.codecId[${counter-1}]">Codec</label>
                                    <input type="text" class="form-control" name="audioTrack.codecId[${counter-1}]" id="audioTrack.codecId[${counter-1}]" value="${audioTrack.getCodecId()}">
                                </div>
                                <div class="col-md-4">
                                    <label for="audioTrack.comments[${counter-1}]">Comments</label>
                                    <input type="text" class="form-control" name="audioTrack.comments[${counter-1}]" id="audioTrack.comments[${counter-1}]" value="${audioTrack.getComments()}">
                                </div>
                            </div>

                        </g:each>

                    </div>
                </div>






                <div class="row formularyPart" id="filmDetailsSubtitles" style="display:none;">
                    <div class="col-md-12">
                        <g:set var="counter" value="${0}" />

                        <g:each in="${filmToSave.subtitleTracks}" var="subtitleTrack">
                            <g:set var="counter" value="${counter + 1}"/>
                            <h3>Subtitle Track ${counter}</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="languageSubTrack${counter}">Language</label>
                                    <select class="form-control" name="languageSubTrack${counter}" id="language">
                                        <g:if test="${subtitleTrack.language != null}">
                                            <option value="${subtitleTrack.language.code}">${subtitleTrack.language.spanishName}</option>
                                        </g:if>
                                        <g:each in="${languages}" var="language">
                                            <g:if test="${(subtitleTrack.language == null)||(language.code != subtitleTrack.language.code)}">
                                                <option value="${language.spanishName}">${language.spanishName}</option>
                                            </g:if>
                                        </g:each>
                                    </select>
                                    <%--<input type="email" class="form-control" id="language" value="${audioTrack.languageName}">--%>
                                </div>
                                <div class="col-md-6">
                                    <label for="commentAudioTrack${counter}">Comments</label>
                                    <input type="text" class="form-control" id="commentAudioTrack${counter}" value="${subtitleTrack.getComments()}">
                                </div>
                            </div>

                        </g:each>

                    </div>
                </div>

            </div>
            <div class="panel-footer">
                <button type="submit" class="btn btn-default" id="submitFilmFormulary">Submit</button>

            </div>

        </g:form>
    </div>

</div>
</body>
</html>