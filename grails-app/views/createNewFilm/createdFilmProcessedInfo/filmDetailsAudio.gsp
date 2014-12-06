<div class="row formularyPart" id="filmDetailsAudio" style="display:none;">
    <div class="col-md-12">
        <g:set var="counter" value="${0}" />

        <g:each in="${audioTracks}" var="audioTrack">
            <g:set var="counter" value="${counter + 1}"/>
            <h3>Audio Track ${counter}</h3>
            <div class="row">
                <div class="col-md-4">
                    <label for="filmToSave.audioTracks[${counter-1}]">Language</label>
                    <select class="form-control" name="audioTracks[${counter-1}].language.code" id="audioTracks[${counter-1}].language.code">
                        <g:if test="${audioTrack.language != null}">
                            <option value="${audioTrack.language.code}">${audioTrack.language.spanishName}</option>
                        </g:if>
                        <g:else>
                            <option value="">Unknown</option>
                        </g:else>

                        <g:each in="${languages}" var="language">
                            <g:if test="${(audioTrack.language != null)&&(language.code != audioTrack.language.code)}">
                                <option value="${language.spanishName}">${language.spanishName}</option>
                            </g:if>

                        </g:each>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="audioTrack.codecId[${counter-1}]">Codec</label>
                    <input type="text" class="form-control" name="audioTrack.codecId[${counter-1}]" id="audioTrack.codecId[${counter-1}]" value="${audioTrack.getCodecId()}">
                </div>
                <div class="col-md-4">
                    <label for="audioTrack.comments[${counter-1}]">Comments</label>
                    <input type="text" class="form-control" name="audioTrack.comments[${counter-1}]" id="audioTrack.comments[${counter-1}]" value="${audioTrack.getComments()}">
                </div>
            </div>

        </g:each>

    </div>
</div>