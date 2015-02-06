package films

import films.Model.GenreModel
import films.Model.GenreNameLanguageModel
import films.Model.ViewCollection.FilmBasicInfo
import films.Model.ViewCollection.FilmOfDay
import films.Model.ViewCollection.Results
import films.Model.ViewCollection.SearchResults
import films.database.GenreService
import films.database.SavedFilmService
import films.database.StaticsService
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.annotation.Secured
import org.imgscalr.Scalr
import org.springframework.web.servlet.support.RequestContextUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class ViewMoviesController {

    SavedFilmService savedFilmService
    SystemService systemService
    GenreService genreService
    StaticsService staticsService
    LinkGenerator grailsLinkGenerator

    static FilmOfDay filmOfDay = null

    static allowedMethods = [removeFilm:'POST']

    def index(){
        Results allResults
        Locale locale = RequestContextUtils.getLocale(request)
        List<FilmBasicInfo> listFilms = (List<FilmBasicInfo>) savedFilmService.getAllFilmsSortedByDateCreated(locale).clone()
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
        Locale locale = RequestContextUtils.getLocale(request)

        if (sessionObject == null) {
            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated(locale)
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
        List<GenreNameLanguageModel> genres = genreService.getAllGenresTranslated(locale)

        List<FilmBasicInfo> resultsPaginated = allResults.getResultsPerPage()
        render(view: "index", model: [resultsPaginated: resultsPaginated, order : allResults.getOrder(),
                                      filterApplied : allResults.filterGenre,
                                      numberOfPages: allResults.getNumberOfPages(),
                                      actualPage: allResults.pageNumber,
                                      genres : genres,
                                      activeLanguageCode : locale.getISO3Language()
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
            Locale locale = RequestContextUtils.getLocale(request)
            List<FilmBasicInfo> listFilms = (List<FilmBasicInfo>) savedFilmService.getAllFilmsSortedByDateCreated(locale).clone()
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        }
        else
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
            Locale locale = RequestContextUtils.getLocale(request)
            List<FilmBasicInfo> listFilms = (List<FilmBasicInfo>) savedFilmService.getAllFilmsSortedByDateCreated(locale).clone()
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        }
        else
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
        Locale locale = RequestContextUtils.getLocale(request)
        if (filterGenre == null)
        {
            log.warn "Error on filter parammeter"
            redirect(controller: "viewMovies", action: "index")
            return
        }

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results)) {
            List<FilmBasicInfo> listFilms = (List<FilmBasicInfo>) savedFilmService.getAllFilmsSortedByDateCreated(locale).clone()
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        }
        else
            allResults = (Results) sessionObject

        allResults.applyFilterGenre(filterGenre, locale.getISO3Language())

        redirect(controller: "viewMovies", action: "viewMovies")
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def searchMovies(String search)
    {
        request.setCharacterEncoding("iso-8859-1");

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults
        Locale locale = RequestContextUtils.getLocale(request)

        if ((sessionObject == null) || !(sessionObject instanceof Results))
        {

            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated(locale)
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
        render(view: "searchResults", model : [searchResults : searchResults, genres : genres,
                                               activeLanguageCode : locale.getISO3Language()])
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

    def getMediumFilmPoster(String posterName)
    {
        String imagePath = systemService.getPostersFolder()
        if (imagePath == null)
        {
            return
        }
        imagePath += posterName

        File imagePoster = new File(imagePath)

        byte[] img = imagePoster.getBytes()

        ByteArrayInputStream inc = new ByteArrayInputStream(img);
        BufferedImage bImageFromConvert = ImageIO.read(inc);
        BufferedImage thumbnail = Scalr.resize(bImageFromConvert, 400);


        ImageIO.write(thumbnail, "jpg", response.getOutputStream())
        response.contentType = 'image/jpg' // or the appropriate image content type
        response.outputStream.flush()
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFlag(String countryCode)
    {
        String imagePath = systemService.getFlagsFolder()
        if (imagePath == null)
        {
            return
        }
        imagePath += (countryCode + ".png")

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
        FilmBasicInfo filmToReturn
        if (filmOfDay == null)
        {
            filmOfDay = new FilmOfDay()
        }
        if  ((filmToReturn = filmOfDay.getSecureFilmOfTheDay()) == null)
        {
            Locale locale = RequestContextUtils.getLocale(request)
            filmOfDay.setSecureFilmOfTheDay(savedFilmService.getRandomFilm(locale))
            filmToReturn = filmOfDay.getSecureFilmOfTheDay()
        }

        render(view: "filmOfTheDay" , model: [filmOfTheDay : filmToReturn])
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

    @Cacheable('topActor')
    def topActor()
    {
        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results))
        {
            Locale locale = RequestContextUtils.getLocale(request)
            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated(locale)
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        }
        else
        {
            allResults = (Results) sessionObject
        }

        String topActor = allResults.getTopActorAndInitializeIfNecessary()
        String topActorURL =  grailsLinkGenerator.link([controller: "viewMovies", action: "searchMovies", params:["search" : topActor]])
        String topActorLink = "<a href=\"${topActorURL}\">${topActor}</a>"

        render topActorLink
    }



    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    @Cacheable('topDirector')
    def topDirector()
    {

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results))
        {
            Locale locale = RequestContextUtils.getLocale(request)
            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated(locale)
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        }
        else
        {
            allResults = (Results) sessionObject
        }

        String topDirector = allResults.getTopDirectorAndInitializeIfNecessary()
        String topDirectorURL =  grailsLinkGenerator.link([controller: "viewMovies", action: "searchMovies", params:["search" : topDirector]])
        String topDirectorLink = "<a href=\"${topDirectorURL}\">${topDirector}</a>"

        render topDirectorLink
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    @Cacheable('topGenre')
    def topGenre()
    {
        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results))
        {
            Locale locale = RequestContextUtils.getLocale(request)
            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated(locale)
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        }
        else
        {
            allResults = (Results) sessionObject
        }

        String topGenre = allResults.getTopGenreAndInitializeIfNecessary()

        render topGenre
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

    }
}
