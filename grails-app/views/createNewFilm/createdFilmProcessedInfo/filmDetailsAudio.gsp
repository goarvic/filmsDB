<div class="row formularyPart" id="filmDetailsAudio" style="display:none;">
    <div class="col-md-12">
        <g:set var="counter" value="${0}" />

        <g:each in="${audioTracks}" var="audioTrack">
            <g:set var="counter" value="${counter + 1}"/>
            <h3>Audio Track ${counter}</h3>
            <div class="row">
                <div class="col-md-4">
                    <label for="filmToSave.audioTracks[${counter-1}]">Language</label>
                    <select class="form-control trackSelectLanguage" name="audioTracks[${counter-1}].language.code" id="audioTracks[${counter-1}].language.code">
                        <g:if test="${audioTrack.language != null}">
                            <option value="${audioTrack.language.code}">${audioTrack.language.spanishName}</option>
                        </g:if>
                        <g:else>
                            <option value="Unknown">Unknown</option>
                        </g:else>

                        <g:each in="${languages}" var="language">
                            <g:if test="${(audioTrack.language == null)||(language.code != audioTrack.language.code)}">
                                <option value="${language.code}">${language.spanishName}</option>
                            </g:if>

                        </g:each>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="audioTrack[${counter-1}].codecId">Codec</label>
                    <input type="text" class="form-control" name="audioTracks[${counter-1}].codecId" id="audioTracks[${counter-1}].codecId" value="${audioTrack.getCodecId()}">
                </div>
                <div class="col-md-4">
                    <label for="audioTrack[${counter-1}].comments">Comments</label>
                    <input type="text" class="form-control" name="audioTracks[${counter-1}].comments" id="audioTrackd[${counter-1}].comments" value="${audioTrack.getComments()}">
                </div>
            </div>

        </g:each>

    </div>
</div>