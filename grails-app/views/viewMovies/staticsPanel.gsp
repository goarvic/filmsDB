
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title text-center">Database Statics</h3>
    </div>
    <div class="text-center">


        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-facetime-video"></span>
                Films:
            </div>
            <div class="col-md-6">
                ${numberOfFilms}
            </div>
        </div>
        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-hdd"></span>
                Total size:
            </div>
            <div class="col-md-6">
                ${(((double)(size))/1000000000).round(2)} GB
            </div>
        </div>
        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-user"></span>
                Actors:
            </div>
            <div class="col-md-6">
                ${actors}
            </div>
        </div>
        <%--
        <div class="row" style="margin-bottom: 0px; padding-bottom: 0px;">
            <hr>
        </div>

        <div class="row">
            <div class="col-md-12 text-center">
                <h4>On the top</h4>
            </div>

        </div>

        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-user"></span>
                Top director:
            </div>
            <div class="col-md-6">
                Steven Spielberg
            </div>
        </div>
        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-user"></span>
                Top actor:
            </div>
            <div class="col-md-6">
                Denzel Washington
            </div>
        </div>
        <div class="summaryDBRow row">
            <div class="col-md-5">
                <span class="glyphicon glyphicon-user"></span>
                Top Genre:
            </div>
            <div class="col-md-6">
                Science Fiction
            </div>
        </div>--%>
    </div>
</div>