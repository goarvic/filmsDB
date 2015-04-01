<div class="row formularyPart" id="filmDetailsSubtitles" style="display:none;">
    <div class="col-md-12">
        <g:set var="counter" value="${0}" />

        <g:each in="${subtitleTracks}" var="subtitleTrack">
            <g:set var="counter" value="${counter + 1}"/>
            <h3>Subtitle Track ${counter}</h3>
            <div class="row">
                <div class="col-md-4">
                    <label for="subtitleTracks[${counter-1}].language.code">Language</label>
                    <select class="form-control trackSelectLanguage" name="subtitleTracks[${counter-1}].language.code" id="subtitleTracks[${counter-1}].language.code">
                        <g:if test="${subtitleTrack.language != null}">
                            <option value="${subtitleTrack.language.code}">${subtitleTrack.language.localName}</option>
                        </g:if>
                        <g:else>
                            <option value="Unknown">Unknown</option>
                        </g:else>
                        <g:each in="${languages}" var="language">
                            <g:if test="${(subtitleTrack.language == null)||(language.code != subtitleTrack.language.code)}">
                                <option value="${language.code}">${language.localName}</option>
                            </g:if>
                        </g:each>
                    </select>
                </div>

                <div class="col-md-4">
                    <label for="subtitleTracks[${counter-1}].type">Type</label>
                    <select class="form-control trackSelectType" type="number" name="subtitleTracks[${counter-1}].type" id="subtitleTracks[${counter-1}].type">
                        <g:if test="${subtitleTrack.getType() == 0}">
                            <option value="${0}" selected="true">Forzados</option>
                            <option value="${1}">Completos</option>
                            <option value="${2}">Otro</option>
                        </g:if>
                        <g:elseif test="${subtitleTrack.getType() == 1}">
                            <option value="${0}">Forzados</option>
                            <option value="${1}" selected="true">Completos</option>
                            <option value="${2}">Otro</option>
                        </g:elseif>
                        <g:else>
                            <option value="${0}">Forzados</option>
                            <option value="${1}">Completos</option>
                            <option value="${2}" selected="true">Otro</option>
                        </g:else>
                    </select>
                </div>

                <div class="col-md-4">
                    <label for="subtitleTracks[${counter-1}].comments">Comments</label>
                    <input type="text" class="form-control" name="subtitleTracks[${counter-1}].comments" id="subtitleTracks[${counter-1}].comments" value="${subtitleTrack.getComments()}">
                </div>
            </div>
        </g:each>
    </div>
</div>