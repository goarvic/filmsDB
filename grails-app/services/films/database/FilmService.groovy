package films.database

import films.AudioTrack
import films.Model.Film
import films.Model.LanguageModel
import films.Model.Person
import films.Model.SavedFilm
import films.Model.SubtitleTrack
import grails.transaction.Transactional

@Transactional
class FilmService {

    PersonService personService
    SavedFilmService savedFilmService
    GenreService genreService
    CountryService countryService


    Film getFilmByOriginalName(String originalName) {
        films.Film filmDomain = films.Film.findByOriginalName(originalName)
        if (filmDomain == null)
            return null

        Film filmModel = new Film()

        filmModel.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class") && !propertyName.equals("country") && !propertyName.equals("savedFilms")
                    && !propertyName.equals("actors")&& !propertyName.equals("director"))
                filmModel.setProperty(propertyName, filmModel.getProperty(propertyName))
        }
        filmModel.director = new ArrayList<films.Model.Person>()

        for (films.Person directorDomain : filmDomain.director)
        {
            films.Model.Person directorModel = personService.bindPersonToModel(directorDomain)
            filmModel.director.add(directorModel)
        }

        filmModel.actors = new ArrayList<films.Model.Person>()

        for (films.Person actorDomain : filmDomain.actors)
        {
            films.Model.Person actorModel = personService.bindPersonToModel(actorDomain)
            filmModel.director.add(actorModel)
        }

        filmModel.savedFilms = new ArrayList<films.Model.SavedFilm>()

        for (films.SavedFilm savedFilmDomain : filmDomain.savedFilms)
        {
            films.Model.SavedFilm savedFilmModel = savedFilmService.bindSavedFilm(savedFilmDomain)
            filmModel.savedFilms.add(savedFilmModel)
        }

        filmModel.genres = new ArrayList<films.Model.GenreModel>()

        for (films.Genre genreDomain : filmDomain.genres)
        {
            films.Model.GenreModel genreModel = genreService.bindFromDomainToModel(genreDomain)
            filmModel.genres.add(genreModel)
        }

        filmModel.country = countryService.bindFromDomainToModel(filmDomain)

        return filmModel
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    films.Film getUpdateAndSaveInstance(films.Model.Film filmModel)
    {
        if (filmModel == null)
        {
            log.error "Error saving null film model object"
            return null
        }

        films.Film filmDomain
        if (filmModel.id < 0)
            filmDomain = new films.Film()
        else
        {
            filmDomain = films.Film.findById(filmModel.id)
            if (filmDomain == 0)
            {
                log.error "Error retrieving object from database"
                return null
            }
        }

        filmModel.properties.each{propertyName, propertyValue->
            if (!propertyName.equals("class")
                    && !propertyName.equals("country")
                    && !propertyName.equals("savedFilms")
                    && !propertyName.equals("director")
                    && !propertyName.equals("actors")
                    && !propertyName.equals("genres")
                    && !propertyName.equals("id")
            )
                filmDomain.setProperty(propertyName, filmModel.getProperty(propertyName))
        }

        filmDomain.country = countryService.getUpdateAndSaveDomainInstance(filmModel.country)

        filmDomain.savedFilms.removeAll()
        for (films.Model.SavedFilm savedFilmModel : filmModel.savedFilms)
        {
            filmDomain.savedFilms.add(savedFilmService.getAndUpdateDomainInstance(savedFilmModel))
        }

        filmDomain.actors.removeAll()
        for (films.Model.Person personModel : filmModel.actors)
        {
            filmDomain.actors.add(personService.getAndUpdatePersonDomainInstance(personModel))
        }

        filmDomain.director.removeAll()
        for (films.Model.Person personModel : filmModel.director)
        {
            filmDomain.director.add(personService.getAndUpdatePersonDomainInstance(personModel))
        }

        filmDomain.genres.removeAll()
        for (films.Model.GenreModel genreModel : filmModel.genres)
        {
            filmDomain.genres.add(genreService.getUpdateAndSavedDomainInstance(genreModel))
        }

        if (filmDomain.save(flush: true) == null)
        {
            log.error "Error saving film Instance: " + filmDomain.errors
            return null
        }
        else
            return filmDomain





    }
}
