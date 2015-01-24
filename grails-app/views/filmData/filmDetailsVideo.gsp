
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
            <h4>Video Codec</h4>
            ${savedFilm.videoCodec}
        </div>
        <div class="col-md-2 text-center">
            <h4>File Container</h4>
            ${savedFilm.container}
        </div>
        <div class="col-md-2 text-center">
            <h4>File Size</h4>
            ${(((double)(savedFilm.size))/1000000000).round(2)} GB
        </div>
        <div class="col-md-2 text-center">
            <h4>Resolution</h4>
            ${savedFilm.xResolution} x ${savedFilm.yResolution}
        </div>
        <div class="col-md-3 text-center">
            <h4>Duration</h4>
            ${(int)(savedFilm.duration/60)} minutes
            (${(int)((double)(savedFilm.duration/3600)).trunc()}
            <g:if test="${savedFilm.duration/3600 >= 2}">hours</g:if><g:else>hour</g:else>
            ${(int)(savedFilm.duration%3600)/60} minutes)
        </div>
    </div>
    <div class="row">
        <hr>
    </div>

    <div class="row">
        <div class="col-md-12 text-center">
            <h4>File Name</h4>
            ${savedFilm.fileName}
        </div>
    </div>

    <div class="row">
        <hr>
    </div>

    <div class="row">
        <div class="col-md-4 text-center">
            <h4>Languages</h4>
            <g:languagesAudio audioTracks="${savedFilm.audioTracks}"/>
            <%--
            <g:set var="counter" value="${0}"/>
            <g:each in="${savedFilm.audioTracks}" var="audioTrack">
                <g:if test="${counter != 0}">, </g:if>
                ${audioTrack.language.localName}
                <g:set var="counter" value="${counter = counter+1}"/>
            </g:each>--%>
        </div>
        <div class="col-md-4 text-center">
            <h4>Subtitles</h4>
            <g:languagesSubtitle subtitleTracks="${savedFilm.subtitleTracks}"/>
        </div>
        <div class="col-md-4 text-center">
            <h4>Disc Reference</h4>
            ${savedFilm.discReference}
        </div>
    </div>
</div>