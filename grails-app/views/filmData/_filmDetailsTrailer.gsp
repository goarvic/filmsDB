<g:set var="urlYoutubeTrailer" value="${"https://www.youtube.com/embed/" + filmDetailsLanguage.urlTrailerYoutube}" />

<div class="formularyPart" id="filmDetailsTrailer" style="display:none;">
    <div class="row">
        <div class="col-md-12 text-center">
            <iframe width="420" height="260"
                    src="${urlYoutubeTrailer}">
            </iframe>
        </div>
    </div>
</div>