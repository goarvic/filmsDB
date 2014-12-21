<div class="row formularyPart" id="filmDetailsAudio" style="display:none;">
    <div class="col-md-12">
        <g:set var="counter" value="${0}" />

        <g:each in="${audioTracks}" var="audioTrack">
            <g:set var="counter" value="${counter + 1}"/>
            <h3>Audio Track ${counter}</h3>
            <div class="row">
                <div class="col-md-4">
                    <h4>Language</h4>
                    ${audioTrack.language.localName}
                </div>
                <div class="col-md-4">
                    <h4>Comments</h4>
                    ${audioTrack.comments}
                </div>
                <div class="col-md-4">
                    <h4>Codec</h4>
                    ${audioTrack.codecId}
                </div>
            </div>

        </g:each>

    </div>
</div>