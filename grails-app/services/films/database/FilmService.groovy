package films.database

import films.Film
import films.FilmDetailsLanguage
import films.Genre
import films.Language
import films.Model.FilmDetailsLanguageModel
import films.Model.FilmModel
import films.Model.GenreModel
import films.Model.PersonModel
import films.Model.SavedFilmModel
import films.Person
import films.SavedFilm
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

import java.util.stream.Collectors

@Transactional
class FilmService {

    PersonService personService
    SavedFilmService savedFilmService
    GenreService genreService
    CountryService countryService
    FilmDetailsLanguageService filmDetailsLanguageService



    FilmModel bindFromDomainToModel(Film filmDomain)
    {
        if (filmDomain == null)
        {
            log.error "Error binding null Film domain instance"
            return null
        }

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
            filmModel.actors.add(actorModel)
        }

        filmModel.savedFilms = new ArrayList<SavedFilmModel>()
        for (SavedFilm savedFilmDomain : filmDomain.savedFilms)
        {
            SavedFilmModel savedFilmModel = savedFilmService.bindSavedFilmFromDomainToModel(savedFilmDomain)
            filmModel.savedFilms.add(savedFilmModel)
        }

        filmModel.filmDetailsLanguage = new ArrayList<FilmDetailsLanguageModel>()
        for(FilmDetailsLanguage filmDetailsLanguage : filmDomain.filmDetailsLanguage)
        {
            FilmDetailsLanguageModel filmDetailsLanguageModel = filmDetailsLanguageService.bindFilmDetailsLanguageDomainToModel(filmDetailsLanguage)
            filmModel.filmDetailsLanguage.add(filmDetailsLanguageModel)
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

    FilmModel bindFromDomainToModel(Film filmDomain, SavedFilm savedFilm, List<FilmDetailsLanguage> filmDetailsLanguage)
    {
        if (filmDomain == null)
        {
            log.error "Error binding null Film domain instance"
            return null
        }

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
            filmModel.actors.add(actorModel)
        }

        filmModel.savedFilms = Arrays.asList(savedFilmService.bindSavedFilmFromDomainToModel(savedFilm))

//        filmModel.filmDetailsLanguage = Arrays.asList(filmDetailsLanguageService.bindFilmDetailsLanguageDomainToModel(filmDetailsLanguage))
        filmModel.filmDetailsLanguage = filmDetailsLanguage.stream().map{
            r-> filmDetailsLanguageService.bindFilmDetailsLanguageDomainToModel(r)
        }.collect(Collectors.toList());

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

    @CacheEvict(value=["listGenres", "listFilms", "films","numberOfFilms", "totalSize",
            "totalActors", "topActor", "topDirector", "topGenre"], allEntries=true)
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
            filmDomain.savedFilms.removeAll(filmDomain.savedFilms)
        else
            filmDomain.savedFilms = new ArrayList<SavedFilm>()

        for (SavedFilmModel savedFilmModel : filmModel.savedFilms)
        {
            SavedFilm savedFilmDomain = savedFilmService.getAndUpdateDomainInstance(savedFilmModel)
            savedFilmDomain.film = filmDomain
            filmDomain.savedFilms.add(savedFilmDomain)
        }


        if  (filmDomain.filmDetailsLanguage != null)
            filmDomain.filmDetailsLanguage.removeAll(filmDomain.filmDetailsLanguage)
        else
            filmDomain.filmDetailsLanguage = new ArrayList<FilmDetailsLanguage>()

        for (FilmDetailsLanguageModel filmDetailsLanguageModel : filmModel.filmDetailsLanguage)
        {
            FilmDetailsLanguage filmDetailsLanguageToAdd = filmDetailsLanguageService.getAndUpdateDomainInstance(filmDetailsLanguageModel)
            filmDetailsLanguageToAdd.film = filmDomain
            filmDomain.filmDetailsLanguage.add(filmDetailsLanguageToAdd)
        }


        if (filmDomain.actors != null)
            filmDomain.actors.removeAll(filmDomain.actors)
        else
            filmDomain.actors = new ArrayList<Person>()

        for (PersonModel personModel : filmModel.actors)
        {
            Person personToAdd = personService.getPersonDomainInstance(personModel)
            personToAdd.save(flush:true, failOnError: true)
            filmDomain.actors.add(personToAdd)

        }

        if (filmDomain.director != null)
            filmDomain.director.removeAll(filmDomain.director)
        else
            filmDomain.director = new ArrayList<Person>()

        for (PersonModel personModel : filmModel.director)
        {
            Person personToAdd = personService.getPersonDomainInstance(personModel)
            personToAdd.save(flush:true, failOnError: true)
            filmDomain.director.add(personToAdd)
        }

        if (filmDomain.genres != null)
            filmDomain.genres.removeAll(filmDomain.genres)
        else
            filmDomain.genres = new ArrayList<Genre>()

        for (GenreModel genreModel : filmModel.genres)
        {
            Genre genreToAdd = genreService.getDomainInstance(genreModel)
            genreToAdd.save(flush:true, failOnError: true)
            filmDomain.genres.add(genreToAdd)
        }


        filmDomain.save(flush: true, failOnError: true)
        return filmDomain
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

   /* @Cacheable('films')
    SavedFilmModel getFilmBySavedFilmId(int id)
    {
        SavedFilm savedFilm = SavedFilm.findById(id.toLong())
        if (savedFilm == null)
        {
            log.error "No savedFilm found by id " + id
            return null
        }

        Film filmDomain = savedFilm.film

        FilmModel filmModel = bindFromDomainToModel(filmDomain)
        return filmModel
    }*/




    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

//    @Cacheable('films')
//    FilmModel getFilmBySavedFilmIdAndLocale(int id, Locale locale)
//    {
//        SavedFilm savedFilm = SavedFilm.findById(id)
//        Language language = Language.findByCode(locale.getISO3Language())
//
//        if (savedFilm == null){
//            log.error "No saved film domain found by id " + id
//            return null
//        }
//        Film filmDomain = savedFilm.film
//        FilmDetailsLanguage filmDetailsLanguage = FilmDetailsLanguage.findByFilmAndLanguage(filmDomain, language)
//        if (filmDetailsLanguage == null){
//            language = Language.findByCode("eng")
//            filmDetailsLanguage = FilmDetailsLanguage.findByFilmAndLanguage(filmDomain, language)
//        }
//
//        FilmModel filmModel = bindFromDomainToModel(filmDomain, savedFilm, filmDetailsLanguage)
//        return filmModel
//    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    @Cacheable('films')
    FilmModel getFilmBySavedFilmId(int id)
    {
        SavedFilm savedFilm = SavedFilm.findById(id)

        if (savedFilm == null){
            log.error "No saved film domain found by id " + id
            return null
        }

        Film filmDomain = savedFilm.film
        List<FilmDetailsLanguage> filmDetailsLanguage = FilmDetailsLanguage.findAllByFilm(filmDomain)

        FilmModel filmModel = bindFromDomainToModel(filmDomain, savedFilm, filmDetailsLanguage)
        return filmModel
    }

}
