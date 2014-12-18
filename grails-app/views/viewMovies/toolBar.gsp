<script type="text/javascript">
    urlApplyFilterMovies = "${createLink(controller: "viewMovies", action: "applyFilterGenre")}"
    urlSearchMovies = "${createLink(controller: "viewMovies", action: "searchMovies")}"
    urlChangeSortMovies = "${createLink(controller: "viewMovies", action: "changeOrder")}"
</script>

<div class="row rowFilters">
    <div class="col-md-4">
        <div id="sortDiv">
            <div class="col-md-3">
                <label for="sortMovies" style="margin-top:6px;">Sort By</label>
            </div>
            <div class="col-md-9">
                <select class="form-control" name="sortMovies" id="sortMovies">
                    <option value="${0}" selected="true">Date of insertion</option>
                    <option value="${3}">Name</option>
                    <option value="${2}">Year</option>
                </select>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div id="filterDiv">
            <div class="col-md-4">
                <label for="sortMovies" style="margin-top:6px;">Filter By</label>
            </div>
            <div class="col-md-8">
                <select class="form-control" name="filterMovies" id="filterMovies">
                    <option value="${0}" selected="true">All</option>
                    <g:each in="${genres}" var="genre">
                        <option value="${genre.id}">${genre.localName}</option>
                    </g:each>
                </select>
            </div>
        </div>
    </div>

    <div class="col-md-4 text-center">
        <div class="col-md-3 text-right">
            <label for="searchMovies" style="margin-top:6px;">Search</label>
        </div>

        <div class="col-md-7">
            <input type="text" class="form-control" name="searchMovies" id="searchMovies" value="">
        </div>
        <div class="col-md-2">
            <button type="button" class="btn btn-default" id="buttonSearch" >
                <span class="glyphicon glyphicon-search"></span>
            </button>
        </div>
    </div>
</div>

