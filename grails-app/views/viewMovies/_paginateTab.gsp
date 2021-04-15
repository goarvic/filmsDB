<script type="text/javascript">
    // $(document).on( 'click', '.btnPag',
    //         function()
    //         {
    //             var url = urlGoPage
    //             var page = $(this).html()
    //             url = updateQueryStringParameter(urlGoPage, "page", page);
    //             window.location.href = url;
    //         }
    // );

    $(document).ready(
            function()
            {
                pagesSize = ${numberOfPages}
            }
    );

</script>

<g:if test="${numberOfPages>1}">
    <div class="row">
        <div class="col-md-12 text-center">
            <div class="text-center pagination">
                <div class="btn-group" role="group" aria-label="...">
                    <g:if test="${actualPage == 1}">
                        <a href="#"><button type="button" class="btn btn-primary">1</button></a>
                    </g:if>
                    <g:else>
                        <a href="${createLink(controller: "viewMovies", params:["page": 1, "sortBy": sortBy, "filterGenre": filterApplied, "order": order])}"><button type="button" class="btn btn-default btnPag">1</button></a>
                    </g:else>
                    <g:if test="${actualPage == 2}">
                        <a href="#"><button type="button" class="btn btn-primary">2</button></a>
                    </g:if>
                    <g:elseif test="${(actualPage==1)||((actualPage==3)&&(numberOfPages==3))}">
                        <a href="${createLink(controller: "viewMovies", params:["page": 2, "sortBy": sortBy, "filterGenre": filterApplied, "order": order])}"><button type="button" class="btn btn-default btnPag">2</button></a>
                    </g:elseif>

                    <g:if test="${(actualPage == 2) && (numberOfPages>2)}">
                        <a href="${createLink(controller: "viewMovies", params:["page": 3, "sortBy": sortBy, "filterGenre": filterApplied, "order": order])}"><button type="button" class="btn btn-default btnPag">3</button></a>
                    </g:if>
                    <g:elseif test="${(actualPage == 3) && (numberOfPages==3)}">
                        <a href="#"><button type="button" class="btn btn-primary btnPag">3</button></a>
                    </g:elseif>
                </div>

                <g:if test="${(actualPage>2) && (numberOfPages > 3)}">
                    <div class="btn-group" role="group" aria-label="...">...</div>
                    <div class="btn-group" role="group" aria-label="...">

                        <a href="${createLink(controller: "viewMovies", params:["page": actualPage - 1, "sortBy": sortBy, "filterGenre": filterApplied, "order": order])}"><button type="button" class="btn btn-default btnPag">${actualPage - 1}</button></a>
                        <a href="#"><button type="button" class="btn btn-primary">${actualPage}</button></a>
                        <g:if test="${(actualPage + 1) <= numberOfPages}">
                            <a href="${createLink(controller: "viewMovies", params:["page": actualPage + 1, "sortBy": sortBy, "filterGenre": filterApplied, "order": order])}"><button type="button" class="btn btn-default btnPag">${actualPage + 1}</button></a>
                        </g:if>
                    </div>
                </g:if>

                <g:if test="${numberOfPages > actualPage+1}">
                    <div class="btn-group" role="group" aria-label="...">...</div>
                    <div class="btn-group" role="group" aria-label="...">
                        <a href="${createLink(controller: "viewMovies", params:["page": numberOfPages, "sortBy": sortBy, "filterGenre": filterApplied, "order": order])}"><button type="button" class="btn btn-default btnPag">${numberOfPages}</button></a>
                    </div>
                </g:if>

                <%--<g:if test="${numberOfPages > 5}">--%>
                    <%--<div class="btn-group" role="group" aria-label="...">...</div>
                    <div class="btn-group" role="group" aria-label="...">
                        <input type="number" class="btn btn-default btnPag" value=""/>
                    </div>--%>
                <%--</g:if>--%>
                --->
                <input type="text" class="form-control goPage" style="width: 42px; float: right;" value="">
            </div>

        </div>

    </div>
</g:if>

