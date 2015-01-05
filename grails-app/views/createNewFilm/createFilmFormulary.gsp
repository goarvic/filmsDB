<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - Insert New Film</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
</head>
<body>



<div id="page-body" role="main" class="container">


    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Initial data files</h3>
        </div>

        <g:uploadForm action="processData" class="validateForm" controller="createNewFilm" method="POST" name="transactionForm">
            <div class="panel-body">
                <div class="row">

                    <div class="col-lg-6 col-sm-6 col-12">
                        <div class="jumbotron">
                            <h1>Specify The files for data insertion</h1>
                            <p class="text-muted">mkvinfo and FilmAffinity</p>
                        </div>
                    </div>
                    <div class="col-lg-6 col-sm-6 col-12">
                        <h4>mkvinfo File</h4>
                        <div class="input-group">
                            <span class="input-group-btn">
                                <span class="btn btn-primary btn-file">
                                    Browse&hellip; <input type="file" name="mkvinfoFile" multiple>
                                </span>
                            </span>
                            <input type="text" class="form-control">
                        </div>
                        <span class="help-block">
                            Select the file generated with mkvinfo (mkvtools package)
                        </span>
                    </div>
                    <div class="col-lg-6 col-sm-6 col-12">
                        <h4>Filmaffinity URL</h4>
                        <div class="input-group">
                            <input type="text" class="form-control" name="filmaffinityURL" value="">
                        </div>
                        <span class="help-block">
                            Please type the filmaffinity details URL of the film
                        </span>
                    </div>

                </div>

            </div>
            <div class="panel-footer">
                <button type="submit" class="btn btn-default" id="submitTransaction">Submit</button>
            </div>
        </g:uploadForm>
    </div>

</div>
</body>
</html>