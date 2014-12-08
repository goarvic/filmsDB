<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - Insert New Film</title>
</head>
<body>

<script type="text/javascript">
    $(document).ready(
            function()
            {
                controllerAvailableSpaceUrl = '<g:createLink controller="createNewFilm" action="isAvailableSpaceOnDisk"/>'

                <%--


                <g:set var="counter" value="${0}"/>
                languagesObj = [
                    <g:each in="${languages}" var="language">
                    <g:set var="counter" value="${counter + 1}"/>
                    <g:if test="${counter != 1}">,</g:if>
                    {
                        id : ${language.id},
                        code : "${language.code}"
                    }
                   </g:each>
                    ]

                console.log(languagesObj[0].id)
                --%>
            }
    );

    $(document).on( 'click', '.buttonPartFormulary',
            function()
            {
                var idOfButton = this.id
                buttonFormulary(idOfButton)
            }
    );

    $(document).on( 'change', '.trackSelectLanguage',
            function()
            {
                enableSubmitButtonIfMatchConditions()
            }
    );
    $(document).on( 'change', '.btn-file',
            function()
            {
                enableSubmitButtonIfMatchConditions()
            }
    );
    $(document).on( 'change', '#discReference',
            function()
            {
                enableSubmitButtonIfMatchConditions()
            }
    );
</script>


<div id="page-body" role="main" class="container">

    <g:if test="${warningDuplicate != null}">
        <div class="alert alert-warning" role="alert">
            ${warningDuplicate}
        </div>
    </g:if>



    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Film Info</h3>

        </div>
        <g:uploadForm action="saveFilm" class="validateForm" controller="createNewFilm" method="POST" name="filmForm">
            <div class="panel-body">
                <div class="row" id="rowButtonSelectFormulary">
                    <div class="col-md-12">
                        <div class="text-center">
                            <div class="btn-group buttonSelectFormularyGroup" role="group" aria-label="...">
                                <button id="buttonFilmInfo" type="button" class="btn btn-primary buttonPartFormulary">Film Info</button>
                                <button id="buttonVideoInfo" type="button" class="btn btn-default buttonPartFormulary">Video Info</button>
                                <button id="buttonAudioTracks" type="button" class="btn btn-default buttonPartFormulary">Audio Tracks</button>
                                <button id="buttonSubtitleTracks" type="button" class="btn btn-default buttonPartFormulary">Subtitle Tracks</button>
                            </div>
                        </div>
                    </div>
                </div>

                <g:include controller="createNewFilm" action="getFilmProcessedInfoFromFA"/>
                <g:include controller="createNewFilm" action="getVideoInfoFormulary"/>
                <g:include controller="createNewFilm" action="getAudioTracksFormulary"/>
                <g:include controller="createNewFilm" action="getSubtitleTracksFormulary"/>

            </div>
            <div class="panel-footer">
                <button type="submit" class="btn btn-default" id="submitFilmFormulary" disabled="true">Submit</button>

            </div>

        </g:uploadForm>
    </div>

</div>
</body>
</html>