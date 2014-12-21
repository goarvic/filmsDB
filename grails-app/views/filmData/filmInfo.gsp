<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - ${film.localName}</title>
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
                            <button id="buttonVideoInfo" type="button" class="btn btn-default buttonPartFormulary">Video Info</button>
                            <button id="buttonAudioTracks" type="button" class="btn btn-default buttonPartFormulary">Audio Tracks</button>
                            <button id="buttonSubtitleTracks" type="button" class="btn btn-default buttonPartFormulary">Subtitle Tracks</button>
                        </div>
                    </div>
                </div>
            </div>

            <g:include controller="filmData" action="getFilmDetailsFA"/>
            <g:include controller="filmData" action="getFilmDetailsVideo"/>
            <g:include controller="filmData" action="getFilmDetailsAudio"/>

        </div>

    </div>

</div>
</body>
</html>