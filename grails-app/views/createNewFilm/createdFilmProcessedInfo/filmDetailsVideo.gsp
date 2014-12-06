<div class="row formularyPart" id="filmDetailsVideo" style="display:none;">
    <div class="col-md-2">
        <h4>Video Codec</h4>
        ${filmDetailsFromMKVInfo.videoCodec}
    </div>
    <div class="col-md-2">
        <h4>File Size</h4>
        ${(((double)(filmDetailsFromMKVInfo.size))/1000000000).round(2)} GB
    </div>
    <div class="col-md-2">
        <h4>Resolution</h4>
        ${filmDetailsFromMKVInfo.xResolution} x ${filmDetailsFromMKVInfo.yResolution}
    </div>
    <div class="col-md-2">
        <h4>Duration</h4>
        ${(int)((double)(filmDetailsFromMKVInfo.duration/3600)).trunc()} hour ${(filmDetailsFromMKVInfo.duration%3600)/60} minutes
    </div>
</div>