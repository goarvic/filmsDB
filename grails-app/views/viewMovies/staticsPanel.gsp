<div class="panel panel-default">

    <script type="text/javascript">
        $(document).ready(
                function()
                {
                    urlTopDirector = "${createLink(controller: "viewMovies", action: "topDirector")}"
                    urlTopActor = "${createLink(controller: "viewMovies", action: "topActor")}"
                    urlTopGenre = "${createLink(controller: "viewMovies", action: "topGenre")}"
                }
        );
    </script>


    <div class="panel-heading">
        <h3 class="panel-title text-center"><g:message code="films.statics.databaseStatics"/></h3>
    </div>
    <div class="text-center">


        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-facetime-video"></span>
                <g:message code="films.statics.films"/>:
            </div>
            <div class="col-md-6">
                ${numberOfFilms}
            </div>
        </div>
        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-hdd"></span>
                <g:message code="films.statics.totalSize"/>:
            </div>
            <div class="col-md-6">
                ${(((double)(size))/1000000000).round(2)} GB
            </div>
        </div>
        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-user"></span>
                <g:message code="films.statics.actors"/>:
            </div>
            <div class="col-md-6">
                ${actors}
            </div>
        </div>
        <hr>


        <div class="row">
            <div class="col-md-12 text-center">
                <h4><g:message code="films.statics.onTheTop"/></h4>
            </div>
        </div>

        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-user"></span>
                <g:message code="films.statics.topDirector"/>:
            </div>
            <div class="col-md-6">
                <span class="glyphicon icon-loading" id="loadingTopDirector"></span>
                <div id="topDirector" style="display: none;"></div>
            </div>
        </div>
        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-user"></span>
                <g:message code="films.statics.topActor"/>:
            </div>
            <div class="col-md-6">
                <span class="glyphicon icon-loading" id="loadingTopActor"></span>
                <div id="topActor" style="display: none;"></div>
            </div>
        </div>
        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-user"></span>
                <g:message code="films.statics.topGenre"/>:
            </div>
            <div class="col-md-6">
                <span class="glyphicon icon-loading" id="loadingTopGenre"></span>
                <div id="topGenre" style="display: none;"></div>
            </div>
        </div>
    </div>
</div>