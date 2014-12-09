
<script type="text/javascript">
    $(document).ready(
            function()
            {
                size = ${filmDetailsFromMKVInfo.size}
            }
    );
</script>

<div class="row formularyPart" id="filmDetailsVideo" style="display:none;">
    <div class="col-md-2 text-center">
        <h4>Video Codec</h4>
        ${filmDetailsFromMKVInfo.videoCodec}
    </div>
    <div class="col-md-2 text-center">
        <h4>File Container</h4>
        ${filmDetailsFromMKVInfo.container}
    </div>
    <div class="col-md-2 text-center">
        <h4>File Size</h4>
        ${(((double)(filmDetailsFromMKVInfo.size))/1000000000).round(2)} GB
    </div>
    <div class="col-md-2 text-center">
        <h4>Resolution</h4>
        ${filmDetailsFromMKVInfo.xResolution} x ${filmDetailsFromMKVInfo.yResolution}
    </div>
    <div class="col-md-3 text-center">
        <h4>Duration</h4>
        ${(int)(filmDetailsFromMKVInfo.duration/60)} minutes
        (${(int)((double)(filmDetailsFromMKVInfo.duration/3600)).trunc()}
        <g:if test="${filmDetailsFromMKVInfo.duration/3600 >= 2}">hours</g:if><g:else>hour</g:else>
        ${(int)(filmDetailsFromMKVInfo.duration%3600)/60} minutes)

    </div>
</div>