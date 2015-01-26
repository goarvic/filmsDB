package films.database

import films.AudioTrack
import films.Film

import films.Genre
import films.Language
import films.Model.AJAXCalls.AvailableSpaceOnDisk
import films.Model.AJAXCalls.AvailableSpaceOnDiskResponse
import films.Model.AudioTrackModel
import films.Model.FilmDetailsLanguageModel
import films.Model.GenreModel
import films.Model.PersonModel
import films.Model.SavedFilmModel
import films.Model.SubtitleTrackModel
import films.Model.ViewCollection.FilmBasicInfo
import films.Person
import films.SavedFilm
import films.SubtitleTrack
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class SavedFilmService {

    AudioTracksService audioTracksService
    SubtitleTracksService subtitleTracksService
    CountryService countryService
    LanguageService languageService
    FilmDetailsLanguageService filmDetailsLanguageService

    SavedFilmModel bindSavedFilmFromDomainToModel (SavedFilm savedFilmDomain)
    {
        if (savedFilmDomain == null)
        {
            log.error "Error binding null SavedFilmModel domain instance"
            return null
        }
        SavedFilmModel savedFilmModel = new SavedFilmModel()
        DataBindingUtils.bindObjectToInstance(savedFilmModel,savedFilmDomain)

        savedFilmModel.audioTracks = new ArrayList<AudioTrackModel>()
        for(AudioTrack audioTrackDomain : savedFilmDomain.audioTracks)
        {
            AudioTrackModel audioTrackModel = audioTracksService.bindAudioTrackFromDomain(audioTrackDomain)
            savedFilmModel.audioTracks.add(audioTrackModel)
        }

        savedFilmModel.subtitleTracks = new ArrayList<SubtitleTrackModel>()
        for(SubtitleTrack subtitleTrackDomain : savedFilmDomain.subtitleTracks)
        {
            SubtitleTrackModel subtitleTrackModel = subtitleTracksService.bindSubtitleTrack(subtitleTrackDomain)
            savedFilmModel.subtitleTracks.add(subtitleTrackModel)
        }
        return savedFilmModel
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    SavedFilm getAndUpdateDomainInstance (SavedFilmModel savedFilmModel)
    {
        if (savedFilmModel == null)
        {
            log.error "Error saving null SavedFilmModel"
            return null
        }

        SavedFilm savedFilmDomain

        if (savedFilmModel.id >= 0)
        {
            savedFilmDomain = SavedFilm.findById(savedFilmModel.id)
            if (savedFilmDomain == null)
            {
                log.error "Error retrieving domains instance on database"
                return null
            }
        }
        else
            savedFilmDomain = new SavedFilm()

        DataBindingUtils.bindObjectToInstance(savedFilmDomain,savedFilmModel)

        if (savedFilmDomain.audioTracks != null)
            savedFilmDomain.audioTracks.removeAll(savedFilmDomain.audioTracks)
        else
            savedFilmDomain.audioTracks = new ArrayList<AudioTrack>()

        for (AudioTrackModel audioTrackModel : savedFilmModel.audioTracks)
        {
            AudioTrack audioTrackDomain = audioTracksService.getUpdatedAudioTrackDomainInstance(audioTrackModel)
            audioTrackDomain.savedFilm = savedFilmDomain
            savedFilmDomain.audioTracks.add(audioTrackDomain)
        }

        if (savedFilmDomain.subtitleTracks != null)
            savedFilmDomain.subtitleTracks.removeAll(savedFilmDomain.subtitleTracks)
        else
            savedFilmDomain.subtitleTracks = new ArrayList<SubtitleTrack>()

        for (SubtitleTrackModel subtitleTrackModel : savedFilmModel.subtitleTracks)
        {
            SubtitleTrack subtitleTrackDomain = subtitleTracksService.getAndUpdateSubtitleTrackDomainInstance(subtitleTrackModel)
            subtitleTrackDomain.savedFilm = savedFilmDomain
            savedFilmDomain.subtitleTracks.add(subtitleTrackDomain)
        }



        return savedFilmDomain
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    AvailableSpaceOnDiskResponse enoughSpaceForFilmInDisc(AvailableSpaceOnDisk paramsObject)
    {
        AvailableSpaceOnDiskResponse response = new AvailableSpaceOnDiskResponse()
        response.discReference = paramsObject.discReference
        long sizeOfDisk = 25050000000
        List<SavedFilm> savedFilmsCurrentlyInDisc = SavedFilm.findAllByDiscReference(paramsObject.discReference)

        long currentFreeSize = sizeOfDisk
        for (SavedFilm savedFilm : savedFilmsCurrentlyInDisc)
        {
            currentFreeSize = currentFreeSize - savedFilm.size
        }
        response.sizeFreeAvailable = currentFreeSize

        if (paramsObject.size > currentFreeSize)
            response.enoughSpace = false
        else
            response.enoughSpace = true

        return response
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    int getNextDisk()
    {
        List<SavedFilm> listLastSavedFilm = SavedFilm.list(sort:"discReference", order:"desc", max:1)
        if (listLastSavedFilm.size() == 0)
            return 1

        SavedFilm lastSavedFilm = listLastSavedFilm.get(0)
        AvailableSpaceOnDisk availableSpaceOnDiskParam = new AvailableSpaceOnDisk(discReference: lastSavedFilm.discReference, size: 6000000000)
        if (enoughSpaceForFilmInDisc(availableSpaceOnDiskParam))
            return lastSavedFilm.discReference
        else
            return lastSavedFilm.discReference + 1
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    FilmBasicInfo bindFromDomainToBasicInfo (SavedFilm savedFilmDomain, Locale locale)
    {
        FilmBasicInfo filmToBind = new FilmBasicInfo()

        Language language = Language.findByCode(locale.getISO3Language())

        FilmDetailsLanguageModel filmDetailsLanguageModel = filmDetailsLanguageService.getByFilmIdAndLanguageCode(savedFilmDomain.film, language)
        if (filmDetailsLanguageModel == null)
        {
            if (savedFilmDomain.film.filmDetailsLanguage.size() == 0)
            {
                log.error "No details language on film"
                return null
            }
            else
                filmDetailsLanguageModel = filmDetailsLanguageService.bindFilmDetailsLanguageDomainToModel(savedFilmDomain.film.filmDetailsLanguage.getAt(0))
        }

        DataBindingUtils.bindObjectToInstance(filmToBind,savedFilmDomain.film)
        DataBindingUtils.bindObjectToInstance(filmToBind,savedFilmDomain)
        DataBindingUtils.bindObjectToInstance(filmToBind,filmDetailsLanguageModel)

        filmToBind.idFilm = savedFilmDomain.film.id
        filmToBind.idSavedFilm = savedFilmDomain.id
        filmToBind.actors = new ArrayList<PersonModel>()
        filmToBind.country = countryService.bindFromDomainToModel(savedFilmDomain.film.country)
        for(Person person : savedFilmDomain.film.actors)
        {
            PersonModel personToAdd = new PersonModel()
            DataBindingUtils.bindObjectToInstance(personToAdd, person)
            filmToBind.actors.add(personToAdd)
        }
        filmToBind.director = new ArrayList<PersonModel>()
        for(Person person : savedFilmDomain.film.director)
        {
            PersonModel personToAdd = new PersonModel()
            DataBindingUtils.bindObjectToInstance(personToAdd, person)
            filmToBind.director.add(personToAdd)
        }
        filmToBind.genres = new ArrayList<GenreModel>()
        for(Genre genre : savedFilmDomain.film.genres)
        {
            GenreModel genreToAdd = new GenreModel()
            DataBindingUtils.bindObjectToInstance(genreToAdd, genre)
            filmToBind.genres.add(genreToAdd)
        }

        return filmToBind
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    @Cacheable('listFilms')
    List<FilmBasicInfo> getAllFilmsSortedByDateCreated(Locale locale)
    {
        List<FilmBasicInfo> filmListToReturn = new ArrayList<FilmBasicInfo>()
        List<SavedFilm> savedFilms = SavedFilm.list(sort:"dateCreated" , order:"desc")
        if (savedFilms == null)
        {
            log.warn "No films saved"
            return filmListToReturn
        }

        for (SavedFilm savedFilm : savedFilms)
        {
            FilmBasicInfo filmToAdd = bindFromDomainToBasicInfo(savedFilm, locale)
            filmListToReturn.add(filmToAdd)
        }
        return filmListToReturn
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    @CacheEvict(value=["listGenres", "listFilms", "films", "numberOfFilms", "totalSize",
            "totalActors", "topActor", "topDirector", "topGenre"], allEntries=true)
    int removeSavedFilm(int savedFilmId)
    {

        SavedFilm savedFilmToRemove = SavedFilm.findById(savedFilmId)
        if (savedFilmToRemove == null)
        {
            log.error "Error removing savedFilm. Not found by id: " + savedFilmId
            return -1
        }

        Film filmAssociated = savedFilmToRemove.film
        if (filmAssociated == null)
        {
            log.error "No film associated found for savedFilm id " + savedFilmId + ". Data incoherence!"
            return -2
        }


        if (filmAssociated.savedFilms.size() == 1) //Erase both film and savedFilm
        {
            try{
                filmAssociated.delete(flush:true)
            }
            catch(Exception e)
            {
                log.error "Error deleting film associated to savedfilm id " + savedFilmId + ". Error: " + e
                return -2
            }
            return 0
        }
        else
        {
            filmAssociated.removeFromSavedFilms(savedFilmToRemove)
            /*if (filmAssociated.save(flush : true) == null)
            {
                log.error "Error updating film associated to savedFilm id " + savedFilmId
                return -3
            }*/
            try{
                savedFilmToRemove.delete(flush: true)
            }
            catch(Exception e)
            {
                log.error "Error deleting savedFilm id " + savedFilmId + " error: " + e
                return -4
            }

            return 0
        }
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    FilmBasicInfo getRandomFilm(Locale locale)
    {
        int numberOfFilms = SavedFilm.count()
        if (numberOfFilms == 0)
            return null

        SavedFilm savedFilmToBind = null
        while (savedFilmToBind == null)
        {
            Random rand = new Random();
            int randomNum = rand.nextInt(numberOfFilms) + 1;
            savedFilmToBind = SavedFilm.get(randomNum)
        }

        return bindFromDomainToBasicInfo(savedFilmToBind, locale)
    }



}
