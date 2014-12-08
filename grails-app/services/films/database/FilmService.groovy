package films.database

import films.Film
import films.Genre
import films.Model.FilmModel
import films.Model.GenreModel
import films.Model.PersonModel
import films.Model.SavedFilmModel
import films.Person
import films.SavedFilm
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class FilmService {

    PersonService personService
    SavedFilmService savedFilmService
    GenreService genreService
    CountryService countryService


    FilmModel getFilmByOriginalName(String originalName) {
        Film filmDomain = Film.findByOriginalName(originalName)
        if (filmDomain == null)
            return null

        FilmModel filmModel = new FilmModel()

        DataBindingUtils.bindObjectToInstance(filmModel,filmDomain)

        filmModel.director = new ArrayList<PersonModel>()
        for (Person directorDomain : filmDomain.director)
        {
            PersonModel directorModel = personService.bindPersonToModel(directorDomain)
            filmModel.director.add(directorModel)
        }

        filmModel.actors = new ArrayList<PersonModel>()
        for (Person actorDomain : filmDomain.actors)
        {
            PersonModel actorModel = personService.bindPersonToModel(actorDomain)
            filmModel.director.add(actorModel)
        }

        filmModel.savedFilms = new ArrayList<SavedFilmModel>()
        for (SavedFilm savedFilmDomain : filmDomain.savedFilms)
        {
            SavedFilmModel savedFilmModel = savedFilmService.bindSavedFilmFromDomainToModel(savedFilmDomain)
            filmModel.savedFilms.add(savedFilmModel)
        }

        filmModel.genres = new ArrayList<GenreModel>()
        for (Genre genreDomain : filmDomain.genres)
        {
            GenreModel genreModel = genreService.bindFromDomainToModel(genreDomain)
            filmModel.genres.add(genreModel)
        }

        filmModel.country = countryService.bindFromDomainToModel(filmDomain.country)

        return filmModel
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    Film getUpdateAndSaveInstance(FilmModel filmModel)
    {
        if (filmModel == null)
        {
            log.error "Error saving null film model object"
            return null
        }

        Film filmDomain
        if (filmModel.id < 0)
            filmDomain = new Film()
        else
        {
            filmDomain = Film.findById(filmModel.id)
            if (filmDomain == 0)
            {
                log.error "Error retrieving object from database"
                return null
            }
        }

        DataBindingUtils.bindObjectToInstance(filmDomain,filmModel)

        filmDomain.country = countryService.getUpdateAndSaveDomainInstance(filmModel.country)

        if  (filmDomain.savedFilms != null)
            filmDomain.savedFilms.removeAll()
        else
            filmDomain.savedFilms = new ArrayList<SavedFilm>()

        for (SavedFilmModel savedFilmModel : filmModel.savedFilms)
        {
            SavedFilm savedFilmDomain = savedFilmService.getAndUpdateDomainInstance(savedFilmModel)
            savedFilmDomain.film = filmDomain
            filmDomain.savedFilms.add(savedFilmDomain)
        }

        if (filmDomain.actors != null)
            filmDomain.actors.removeAll()
        else
            filmDomain.actors = new ArrayList<Person>()

        for (PersonModel personModel : filmModel.actors)
        {
            filmDomain.actors.add(personService.getAndUpdatePersonDomainInstance(personModel))
        }

        if (filmDomain.director != null)
            filmDomain.director.removeAll()
        else
            filmDomain.director = new ArrayList<Person>()

        for (PersonModel personModel : filmModel.director)
        {
            filmDomain.director.add(personService.getAndUpdatePersonDomainInstance(personModel))
        }

        if (filmDomain.genres != null)
            filmDomain.genres.removeAll()
        else
            filmDomain.genres = new ArrayList<Genre>()
        for (GenreModel genreModel : filmModel.genres)
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
