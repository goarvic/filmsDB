<div class="row formularyPart" id="filmDetailsSubtitles" style="display:none;">
    <div class="col-md-12">
        <g:set var="counter" value="${0}" />

        <g:each in="${subtitleTracks}" var="subtitleTrack">
            <g:set var="counter" value="${counter + 1}"/>
            <h3>Subtitle Track ${counter}</h3>
            <div class="row">
                <div class="col-md-4">
                    <h4>Language</h4>
                    ${subtitleTrack.language.localName}
                </div>

                <div class="col-md-4">
                    <h4>Type</h4>
                    <g:if test="${subtitleTrack.getType() == 0}">Forced</g:if>
                    <g:elseif test="${subtitleTrack.getType() == 1}">Complete</g:elseif>
                    <g:else>Other</g:else>
                </div>

                <div class="col-md-4">
                    <h4>Comments</h4>
                    ${subtitleTrack.getComments()}
                </div>
            </div>
        </g:each>
    </div>
</div>