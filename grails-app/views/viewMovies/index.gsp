<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - View Films Collection</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.min.js')}"></script>

</head>
<body>
<g:render template="settingURLs"/>
<script type="text/javascript">
    $(document).ready(
        function()
        {
            var orderSelected = parseInt(${order})
            $('#sortMovies option').each(function() {
                var value = parseInt($( this ).val())
                $(this).removeAttr("selected")
                if (value == orderSelected)
                {
                    $(this).attr("selected",true)
                }
            });

            var filterApplied = parseInt(${filterApplied})
            $('#filterMovies option').each(function() {
                var value = parseInt($( this ).val())
                $(this).removeAttr("selected")
                if (value == filterApplied)
                {
                    $(this).attr("selected",true)
                }
            });
        }
    );
</script>
    <div id="page-body" role="main" class="container-fluid">

        <div class="col-sm-2">
            <g:include controller="viewMovies" action="filmStatics"/>
            <g:include controller="viewMovies" action="getFilmOfTheDay"/>
        </div>

        <div class="col-sm-10">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Movie Collection</h3>
                </div>
                <div class="panel-body">
                    <g:render template="toolBar" model="[search : '', genres : genres]"/>
                    <g:render template="paginateTab" model="[actualPage: actualPage, numberOfPages : numberOfPages]"/>

                    <g:each in="${resultsPaginated}" var="film">
                        <g:render template="filmTemplate" model="[filmData: film]"/>
                    </g:each>

                    <g:render template="paginateTab" model="[actualPage: actualPage, numberOfPages : numberOfPages]"/>
                </div>
            </div>
        </div>
    </div>
</body>
</html>