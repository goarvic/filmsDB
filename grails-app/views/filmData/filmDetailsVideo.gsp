
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
        <div class="col-md-12">
            <h4>File Name</h4>
            ${savedFilm.fileName}
        </div>
    </div>

</div>