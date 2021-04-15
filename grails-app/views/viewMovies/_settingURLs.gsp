

<script type="text/javascript">
    urlApplyFilterMovies = "${createLink(controller: "viewMovies")}?sortBy=${sortBy}&order=${order}&filterGenre=${filterApplied}&page=${actualPage}"
    urlSearchMovies = "${createLink(controller: "viewMovies", action: "searchMovies")}"
    urlChangeSortMovies = "${createLink(controller: "viewMovies")}?sortBy=${sortBy}&order=${order}&filterGenre=${filterApplied}&page=${actualPage}"
    urlRemoveMovies = "${createLink(controller: "viewMovies", action: "removeFilm")}"
    urlViewMovies = "${createLink(controller: "viewMovies")}?sortBy=${sortBy}&order=${order}&filterGenre=${filterApplied}&page=${actualPage}"
    urlGoPage = "${createLink(controller: 'viewMovies')}?sortBy=${sortBy}&order=${order}&filterGenre=${filterApplied}&page=${actualPage}"
</script>