<script type="text/javascript">
    $(document).on( 'click', '.btnPag',
            function()
            {
                var url = "${createLink(controller: 'viewMovies', action: 'changePageNumber')}"
                var page = $(this).html()
                url += "?page=" + page
                window.location.href = url;
            }
    );
</script>

<g:if test="${numberOfPages>1}">
    <div class="row">
        <div class="col-md-12 text-center">
            <div class="text-center pagination">
                <div class="btn-group" role="group" aria-label="...">
                    <g:if test="${actualPage == 1}">
                        <button type="button" class="btn btn-primary">1</button>
                    </g:if>
                    <g:else>
                        <button type="button" class="btn btn-default btnPag">1</button>
                    </g:else>
                    <g:if test="${actualPage == 2}">
                        <button type="button" class="btn btn-primary">2</button>
                    </g:if>
                    <g:elseif test="${(actualPage==1)||((actualPage==3)&&(numberOfPages==3))}">
                        <button type="button" class="btn btn-default btnPag">2</button>
                    </g:elseif>

                    <g:if test="${(actualPage == 2) && (numberOfPages>2)}">
                        <button type="button" class="btn btn-default btnPag">3</button>
                    </g:if>
                    <g:elseif test="${(actualPage == 3) && (numberOfPages==3)}">
                        <button type="button" class="btn btn-primary btnPag">3</button>
                    </g:elseif>
                </div>

                <g:if test="${(actualPage>2) && (numberOfPages > 3)}">
                    <div class="btn-group" role="group" aria-label="...">...</div>
                    <div class="btn-group" role="group" aria-label="...">

                        <button type="button" class="btn btn-default btnPag">${actualPage - 1}</button>
                        <button type="button" class="btn btn-primary">${actualPage}</button>
                        <g:if test="${(actualPage + 1) <= numberOfPages}">
                            <button type="button" class="btn btn-default btnPag">${actualPage + 1}</button>
                        </g:if>
                    </div>
                </g:if>

                <g:if test="${numberOfPages > actualPage+1}">
                    <div class="btn-group" role="group" aria-label="...">...</div>
                    <div class="btn-group" role="group" aria-label="...">
                        <button type="button" class="btn btn-default btnPag">${numberOfPages}</button>
                    </div>
                </g:if>

                <%--<g:if test="${numberOfPages > 5}">--%>
                    <%--<div class="btn-group" role="group" aria-label="...">...</div>
                    <div class="btn-group" role="group" aria-label="...">
                        <input type="number" class="btn btn-default btnPag" value=""/>
                    </div>--%>
                <%--</g:if>--%>
            </div>

        </div>

    </div>
</g:if>

