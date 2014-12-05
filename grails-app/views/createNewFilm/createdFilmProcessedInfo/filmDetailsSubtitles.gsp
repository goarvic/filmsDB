<div class="row formularyPart" id="filmDetailsSubtitles" style="display:none;">
    <div class="col-md-12">
        <g:set var="counter" value="${0}" />

        <g:each in="${subtitleTracks}" var="subtitleTrack">
            <g:set var="counter" value="${counter + 1}"/>
            <h3>Subtitle Track ${counter}</h3>
            <div class="row">
                <div class="col-md-6">
                    <label for="languageSubTrack${counter}">Language</label>
                    <select class="form-control" name="languageSubTrack${counter}" id="language">
                        <g:if test="${subtitleTrack.language != null}">
                            <option value="${subtitleTrack.language.code}">${subtitleTrack.language.spanishName}</option>
                        </g:if>
                        <g:else>
                            <option value="">Unknown</option>
                        </g:else>
                        <g:each in="${languages}" var="language">
                            <g:if test="${(subtitleTrack.language == null)||(language.code != subtitleTrack.language.code)}">
                                <option value="${language.spanishName}">${language.spanishName}</option>
                            </g:if>
                        </g:each>
                    </select>
                    <%--<input type="email" class="form-control" id="language" value="${audioTrack.languageName}">--%>
                </div>
                <div class="col-md-6">
                    <label for="commentAudioTrack${counter}">Comments</label>
                    <input type="text" class="form-control" id="commentAudioTrack${counter}" value="${subtitleTrack.getComments()}">
                </div>
            </div>
        </g:each>
    </div>
</div>