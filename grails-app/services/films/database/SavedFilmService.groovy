package films.database

import films.AudioTrack
import films.Genre
import films.Model.AJAXCalls.AvailableSpaceOnDisk
import films.Model.AJAXCalls.AvailableSpaceOnDiskResponse
import films.Model.AudioTrackModel
import films.Model.GenreModel
import films.Model.PersonModel
import films.Model.SavedFilmModel
import films.Model.SubtitleTrackModel
import films.Model.ViewCollection.FilmBasicInfo
import films.Person
import films.SavedFilm
import films.SubtitleTrack
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class SavedFilmService {

    AudioTracksService audioTracksService
    SubtitleTracksService subtitleTracksService

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
        List<SavedFilm> savedFilmsCurrentlyInDisc = SavedFilm.findAllById(paramsObject.discReference)

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

    @Cacheable('listFilms')
    List<FilmBasicInfo> getAllFilmsSortedByDateCreated()
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
            FilmBasicInfo filmToAdd = new FilmBasicInfo()
            DataBindingUtils.bindObjectToInstance(filmToAdd,savedFilm.film)
            DataBindingUtils.bindObjectToInstance(filmToAdd,savedFilm)

            filmToAdd.idFilm = savedFilm.film.id
            filmToAdd.idSavedFilm = savedFilm.id

            filmToAdd.actors = new ArrayList<PersonModel>()
            for(Person person : savedFilm.film.actors)
            {
                PersonModel personToAdd = new PersonModel()
                DataBindingUtils.bindObjectToInstance(personToAdd, person)
                filmToAdd.actors.add(personToAdd)
            }
            filmToAdd.director = new ArrayList<PersonModel>()
            for(Person person : savedFilm.film.director)
            {
                PersonModel personToAdd = new PersonModel()
                DataBindingUtils.bindObjectToInstance(personToAdd, person)
                filmToAdd.director.add(personToAdd)
            }
            filmToAdd.genres = new ArrayList<GenreModel>()
            for(Genre genre : savedFilm.film.genres)
            {
                GenreModel genreToAdd = new GenreModel()
                DataBindingUtils.bindObjectToInstance(genreToAdd, genre)
                filmToAdd.genres.add(genreToAdd)
            }
            filmListToReturn.add(filmToAdd)
        }
        return filmListToReturn
    }





}
