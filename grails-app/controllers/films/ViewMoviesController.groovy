package films

import films.Model.GenreModel
import films.Model.ViewCollection.FilmBasicInfo
import films.Model.ViewCollection.Results
import films.Model.ViewCollection.SearchResults
import films.database.GenreService
import films.database.SavedFilmService
import films.database.StaticsService
import grails.plugin.springsecurity.annotation.Secured

class ViewMoviesController {

    SavedFilmService savedFilmService
    SystemService systemService
    GenreService genreService
    StaticsService staticsService

    static allowedMethods = [removeFilm:'POST']

    def index(){
        Results allResults
        List<FilmBasicInfo> listFilms = (List<FilmBasicInfo>) savedFilmService.getAllFilmsSortedByDateCreated().clone()
        int pageSize = systemService.getPageSize()
        allResults = new Results(listFilms, pageSize)
        session.setAttribute("resultsPaginated", allResults)

        redirect(controller: "viewMovies", action: "viewMovies")
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    def viewMovies() {

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if (sessionObject == null) {
            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated()
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        } else {
            if (!(sessionObject instanceof Results)) {
                render(view: "error.gsp", model: [])
                return
            } else {

                allResults = (Results) sessionObject
            }
        }
        List<GenreModel> genres = genreService.getAllGenres()

        List<FilmBasicInfo> resultsPaginated = allResults.getResultsPerPage()
        render(view: "index", model: [resultsPaginated: resultsPaginated, order : allResults.getOrder(),
                                      filterApplied : allResults.filterGenre,
                                      numberOfPages: allResults.getNumberOfPages(),
                                      actualPage: allResults.pageNumber,
                                      genres : genres
        ])
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************



    def changePageNumber(String page) {
        int pageNumber
        try {
            pageNumber = Integer.parseInt(page)
        }
        catch (Exception e) {
            log.error "Error parsing pageNumber requested " + e
            flash.error = "Error parsing pageNumber requested"
            redirect(controller: "viewMovies", action: "index")
            return
        }


        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results)) {
            log.warn "Session error!"
            redirect(controller: "viewMovies", action: "index")
            return
        }
        allResults = (Results) sessionObject
        allResults.setPageNumber(pageNumber)
        redirect(controller: "viewMovies", action: "viewMovies")
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def changeOrder(int order) {

        if (order == null)
        {
            log.warn "Error on the parammeter order"
            redirect(controller: "viewMovies", action: "index")
            return
        }

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results)) {
            log.warn "Session error!"
            redirect(controller: "viewMovies", action: "index")
            return
        }
        allResults = (Results) sessionObject
        if (order == 0)
        {
            allResults.changeOrderToDateCreated()
        }
        else if (order == 1)
        {
            allResults.changeOrderToOriginalName()
        }
        else if (order == 2)
        {
            allResults.changeOrderToYear()
        }
        else
        {
            allResults.changeOrderToLocalName()
        }

        redirect(controller: "viewMovies", action: "viewMovies")
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def applyFilterGenre(int filterGenre)
    {
        if (filterGenre == null)
        {
            log.warn "Error on filter parammeter"
            redirect(controller: "viewMovies", action: "index")
            return
        }

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results)) {
            log.warn "Session error!"
            redirect(controller: "viewMovies", action: "index")
            return
        }
        allResults = (Results) sessionObject
        allResults.applyFilterGenre(filterGenre)

        redirect(controller: "viewMovies", action: "viewMovies")
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def searchMovies(String search)
    {
        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results))
        {
            log.warn "Session lost!"
            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated()
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        }
        else
        {
            allResults = (Results) sessionObject
        }

        if (search == null)
        {
            flash.error = "Search error"
            redirect(controller: "viewMovies", action: "index")
            return
        }
        else if (search.size() < 3)
        {
            flash.error = "Search needs almost 3 characters"
            redirect(controller: "viewMovies", action: "index")
            return
        }

        List<GenreModel> genres = genreService.getAllGenres()
        SearchResults searchResults = allResults.search(search)
        render(view: "searchResults", model : [searchResults : searchResults, genres : genres])
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def updateFilms()
    {
        session.removeAttribute("resultsPaginated")
        redirect(controller: "viewMovies", action: "index")
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFilmPoster(String posterName)
    {
        String imagePath = systemService.getSmallPostersFolder()
        if (imagePath == null)
        {
            return
        }
        imagePath += posterName

        File imagePoster = new File(imagePath)

        byte[] img = imagePoster.getBytes()
        response.setIntHeader('Content-length', img.length)
        response.contentType = 'image/jpg' // or the appropriate image content type
        response.outputStream << img
        response.outputStream.flush()
    }



    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFilmOfTheDay()
    {
        FilmBasicInfo filmOfTheDay = savedFilmService.getFilmOfDay()

        render(view: "filmOfTheDay" , model: [filmOfTheDay : filmOfTheDay])
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def filmStatics()
    {
        int numberOfFilms = staticsService.getNumberOfFilmsOnDB()
        long size = staticsService.getTotalFilmsSizeInBytes()
        int actors = staticsService.getTotalActors()

        render(view: "staticsPanel" , model: [numberOfFilms : numberOfFilms, size : size, actors : actors])
    }



    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    @Secured(['ROLE_ADMIN'])
    def removeFilm(int savedFilmId)
    {
        if (savedFilmService.removeSavedFilm(savedFilmId) < 0)
        {
            flash.error = "Ha ocurrido un error al borrar la película"
            render "-1"
            return
        }
        else
        {
            Object sessionObject = session.getAttribute("resultsPaginated")
            Results allResults
            if (!(sessionObject == null) && (sessionObject instanceof Results))
            {
                allResults = (Results) sessionObject
                allResults.removeFilmFromResults(savedFilmId)
            }
            flash.message = "Éxito borrando película"
        }

        render "1"
        //flash.message = "Hasta aqui bien"
        //redirect(action: "index", controller: "viewMovies")
    }
}
