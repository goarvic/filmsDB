package films

import films.Model.GenreModel
import films.Model.GenreNameLanguageModel
import films.Model.PersonModel
import films.Model.ViewCollection.FilmBasicInfo
import films.Model.ViewCollection.FilmOfDay
import films.Model.ViewCollection.Results
import films.Model.ViewCollection.SearchResults
import films.database.GenreService
import films.database.SavedFilmService
import films.database.StaticsService
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.imgscalr.Scalr
import org.springframework.web.servlet.support.RequestContextUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import java.util.stream.Collectors

class ViewMoviesController {

    SavedFilmService savedFilmService
    SystemService systemService
    GenreService genreService
    StaticsService staticsService
    LinkGenerator grailsLinkGenerator
    GrailsApplication grailsApplication

    static FilmOfDay filmOfDay = null

    static allowedMethods = [removeFilm:'POST']

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    def viewMovies(Integer page, Integer sortBy, String order, Integer filterGenre) {
        redirect(controller: "viewMovies", action: "index", params: [page, sortBy, order, filterGenre])
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def index(Integer page, Integer sortBy, String order, Integer filterGenre) {
        Locale locale = RequestContextUtils.getLocale(request);
        String realOrder = order != null ? order : "desc";
        Integer realSortBy = sortBy!=null ? sortBy : 0;
        Integer realFilterGenre = filterGenre != null ? filterGenre : 0;
        int totalPages = savedFilmService.getFilmsPages(locale, systemService.getPageSize(), realSortBy, realOrder, realFilterGenre)
        Integer realPage = page != null ? page > totalPages ? totalPages : page : 1;
        List<FilmBasicInfo> resultsPaginated = savedFilmService.getFilmsPaginated(locale, realPage, systemService.getPageSize(), realSortBy, realOrder, realFilterGenre);
        List<GenreNameLanguageModel> genres = genreService.getAllGenresTranslated(locale);


        render(view: "index", model: [resultsPaginated: resultsPaginated,
                                      sortBy : realSortBy,
                                      order : realOrder,
                                      filterApplied : realFilterGenre,
                                      numberOfPages: totalPages,
                                      actualPage: realPage,
                                      genres : genres,
                                      activeLanguageCode : locale.getISO3Language()
        ])
    }







    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def searchMovies(String search, Integer order, Boolean desc, Integer filterGenre)
    {
        Locale locale = RequestContextUtils.getLocale(request);

        List<GenreModel> genres = genreService.getAllGenres();
        SearchResults searchResults = savedFilmService.searchFilms(locale,  order,  desc,  search);

        render(view: "searchResults", model : [searchResults : searchResults,
                                               genres : genres,
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

    def getMediumFilmPoster(String id)
    {
        String imagePath = systemService.getPostersFolder()
        if (imagePath == null)
        {
            return
        }
        imagePath += id

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

    def getFilmPoster(String id)
    {
        String imagePath = systemService.getSmallPostersFolder()
        if (imagePath == null)
        {
            return
        }
        imagePath += id

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

    def originalFilmPoster(String id)
    {
        String imagePath = systemService.getPostersFolder();
        if (imagePath == null)
        {
            return
        }
        imagePath += id

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
        Locale locale = RequestContextUtils.getLocale(request);
        HashMap<String, Integer>  actors = new HashMap<String, Integer>();
        List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreatedDesc(locale);
        def listAllActors = listFilms.stream().flatMap({ r -> r.getActors().stream() });
        listAllActors.forEach({r->
            if (actors.get(r.name ) == null){
                actors.put(r.name, 1)
            }
            else{
                actors.put(r.name, actors.get(r.name)+1)
            }
        })
        String topActor = null
        int times = 0
        Iterator it = actors.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (topActor == null)
            {
                topActor = (String) pair.getKey()
                times = (int) pair.getValue()
            }
            else if (((int) pair.getValue()) > times)
            {
                topActor = (String) pair.getKey()
                times = (int) pair.getValue()
            }
        }

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
        Locale locale = RequestContextUtils.getLocale(request);
        HashMap<String, Integer>  directors = new HashMap<String, Integer>();
        List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreatedDesc(locale);
        def listAllDirectors = listFilms.stream().flatMap({ r -> r.getDirector().stream() });
        listAllDirectors.forEach({r->
            if (directors.get(r.name ) == null){
                directors.put(r.name, 1)
            }
            else{
                directors.put(r.name, directors.get(r.name)+1)
            }
        })

        String topDirector = null
        int times = 0
        Iterator it = directors.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (topDirector == null)
            {
                topDirector = (String) pair.getKey()
                times = (int) pair.getValue()
            }
            else if (((int) pair.getValue()) > times)
            {
                topDirector = (String) pair.getKey()
                times = (int) pair.getValue()
            }
        }

        String topDirectorURL =  grailsLinkGenerator.link([controller: "viewMovies", action: "searchMovies", params:["search" : topDirector]])
        String topDirectorLink = "<a href=\"${topDirectorURL}\">${topDirector}</a>"
        render topDirectorLink;
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    @Cacheable('topGenre')
    def topGenre()
    {
        Locale locale = RequestContextUtils.getLocale(request);
        HashMap<String, Integer>  genres = new HashMap<String, Integer>();
        List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreatedDesc(locale);
        def listAllGenres = listFilms.stream().flatMap({ r -> r.getGenresLanguage().stream() });
        listAllGenres.forEach({r->
            if (genres.get(r.name ) == null){
                genres.put(r.name, 1)
            }
            else{
                genres.put(r.name, genres.get(r.name)+1)
            }
        })
        String topGenre = null
        int times = 0
        Iterator it = genres.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (topGenre == null)
            {
                topGenre = (String) pair.getKey()
                times = (int) pair.getValue()
            }
            else if (((int) pair.getValue()) > times)
            {
                topGenre = (String) pair.getKey()
                times = (int) pair.getValue()
            }
        }
        render topGenre;
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
            savedFilmService.removeCaches()
            flash.message = "Éxito borrando película"
        }

        render "1"

    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    @Secured(['ROLE_ADMIN'])
    def removeCache()
    {
        savedFilmService.removeCaches()
        redirect(controller: "viewMovies", action: "index")
    }

}
