<div class="row rowFilters">
    <div class="col-md-4">
        <g:if test="${actionName != "searchMovies"}">
            <div id="sortDiv">
                <div class="col-md-3">
                    <label for="sortMovies" style="margin-top:6px;"><g:message code="films.toolBar.sortBy"/></label>
                </div>
                <div class="col-md-9">
                    <select class="form-control" name="sortMovies" id="sortMovies">
                        <option value="${0}" selected="true"><g:message code="films.toolBar.dateOfInsertion"/></option>
                        <option value="${3}"><g:message code="films.toolBar.name"/></option>
                        <option value="${2}"><g:message code="films.toolBar.year"/></option>
                    </select>
                </div>
            </div>
        </g:if>
    </div>

    <div class="col-md-4">
        <g:if test="${actionName != "searchMovies"}">
            <div id="filterDiv">
                <div class="col-md-4">
                    <label for="sortMovies" style="margin-top:6px;"><g:message code="films.toolBar.filterBy"/></label>
                </div>
                <div class="col-md-8">
                    <select class="form-control" name="filterMovies" id="filterMovies">
                        <option value="${0}" selected="true"><g:message code="films.toolBar.all"/></option>
                        <g:each in="${genres}" var="genre">
                            <option value="${genre.id}">${genre.name}</option>
                        </g:each>
                    </select>
                </div>
            </div>
        </g:if>
    </div>

    <div class="col-md-4 text-center">
        <div class="col-md-3 text-right">
            <label for="searchMovies" style="margin-top:6px;"><g:message code="films.toolBar.search"/></label>
        </div>

        <div class="col-md-7">
            <input type="text" class="form-control" name="searchMovies" id="searchMovies" value="${search}">
        </div>
        <div class="col-md-2">
            <button type="button" class="btn btn-default" id="buttonSearch" >
                <span class="glyphicon glyphicon-search"></span>
            </button>
        </div>
    </div>
</div>