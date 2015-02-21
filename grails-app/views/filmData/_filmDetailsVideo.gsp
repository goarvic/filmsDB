
<script type="text/javascript">
    $(document).ready(
            function()
            {
                size = ${savedFilm.size}
            }
    );
</script>


<div class="formularyPart" id="filmDetailsVideo" style="display:none;">
    <div class="row">
        <div class="col-md-2 text-center">
            <h4><g:message code="films.filmData.videoCodec"/></h4>
            ${savedFilm.videoCodec}
        </div>
        <div class="col-md-2 text-center">
            <h4><g:message code="films.filmData.fileContainer"/></h4>
            ${savedFilm.container}
        </div>
        <div class="col-md-2 text-center">
            <h4><g:message code="films.filmData.fileSize"/></h4>
            ${(((double)(savedFilm.size))/1000000000).round(2)} GB
        </div>
        <div class="col-md-2 text-center">
            <h4><g:message code="films.filmData.resolution"/></h4>
            ${savedFilm.xResolution} x ${savedFilm.yResolution}
        </div>
        <div class="col-md-3 text-center">
            <h4><g:message code="films.filmData.duration"/></h4>
            ${(int)(savedFilm.duration/60)} <g:message code="films.filmData.duration.minutes"/>
            (${(int)((double)(savedFilm.duration/3600)).trunc()}
            <g:if test="${savedFilm.duration/3600 >= 2}"><g:message code="films.filmData.duration.hours"/></g:if><g:else><g:message code="films.filmData.duration.hour"/></g:else>
            ${(int)(savedFilm.duration%3600)/60} <g:message code="films.filmData.duration.minutes"/>)
        </div>
    </div>
    <div class="row">
        <hr>
    </div>

    <div class="row">
        <div class="col-md-12 text-center">
            <h4><g:message code="films.filmData.fileName"/></h4>
            ${savedFilm.fileName}
        </div>
    </div>

    <div class="row">
        <hr>
    </div>

    <div class="row">
        <div class="col-md-4 text-center">
            <h4><g:message code="films.filmData.languages"/></h4>
            <g:languagesAudio audioTracks="${savedFilm.audioTracks}" displayLanguage="${activeLanguageCode}"/>
        </div>
        <div class="col-md-4 text-center">
            <h4><g:message code="films.filmData.subtitles"/></h4>
            <g:languagesSubtitle subtitleTracks="${savedFilm.subtitleTracks}" displayLanguage="${activeLanguageCode}"/>
        </div>
        <div class="col-md-4 text-center">
            <h4><g:message code="films.filmData.discReference"/></h4>
            ${savedFilm.discReference}
        </div>
    </div>
</div>