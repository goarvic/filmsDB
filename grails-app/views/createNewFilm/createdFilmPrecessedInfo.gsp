<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - Insert New Film</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.min.js')}"></script>

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

        <div class="panel-body">

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


                <div class="col-md-3">
                    <h4>Original Name</h4>
                    <div class="input-group">
                        <input type="text" class="form-control" value="${filmDetailsFromFA.originalName}" >
                    </div>
                </div>
                <div class="col-md-3">
                    <h4>Spanish Name</h4>
                    <div class="input-group">
                        <input type="text" class="form-control" value="${filmDetailsFromFA.spanishName}" >
                    </div>
                </div>
                <div class="col-md-3">
                    <h4>Year</h4>
                    <div class="input-group">
                        <input type="text" class="form-control" value="${filmDetailsFromFA.year}" >
                    </div>
                </div>
                <div class="col-md-3">
                    <label for="country"><h4>Country</h4></label>
                    <div class="input-group">
                        <select class="form-control" name="country" id="country">
                            <option value="${filmDetailsFromFA.country.spanishName}">${filmDetailsFromFA.country.spanishName}</option>
                            <g:each in="${countrys}" var="country">
                                <g:if test="${country.countryCode != filmDetailsFromFA.country.countryCode}">
                                    <option value="${country.spanishName}">${country.spanishName}</option>
                                </g:if>

                            </g:each>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Codec Film Info</h3>
        </div>

        <g:uploadForm action="saveFilm" class="validateForm" controller="createNewFilm" method="POST" name="transactionForm">
            <div class="panel-body">
                <div class="col-md-12">
                    <g:set var="counter" value="${0}" />

                    <g:each in="${filmToSave.audioTracks}" var="audioTrack">
                        <g:set var="counter" value="${counter + 1}"/>
                        <h3>Audio Track ${counter}</h3>
                        <div class="row">
                            <div class="col-md-4">
                                <label for="language">Language</label>
                                <select class="form-control" name="language" id="language">
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
                                <label for="codecAudioTrack${counter}">Codec</label>
                                <input type="email" class="form-control" id="codecAudioTrack${counter}" value="${audioTrack.getCodecId()}">
                            </div>
                            <div class="col-md-4">
                                <label for="commentAudioTrack${counter}">Comments</label>
                                <input type="email" class="form-control" id="commentAudioTrack${counter}" value="${audioTrack.getComments()}">
                            </div>
                        </div>

                    </g:each>

                </div>
            </div>
            <div class="panel-footer">
            <button type="submit" class="btn btn-default" id="submitTransaction">Submit</button>
            </div>
        </g:uploadForm>
    </div>

</div>
</body>
</html>