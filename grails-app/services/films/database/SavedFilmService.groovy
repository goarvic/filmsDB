package films.database

import films.AudioTrack
import grails.transaction.Transactional

@Transactional
class SavedFilmService {

    AudioTracksService audioTracksService
    SubtitleTracksService subtitleTracksService
    LanguageService languageService


    films.Model.SavedFilm bindSavedFilm (films.SavedFilm savedFilmDomain)
    {
        films.Model.SavedFilm savedFilmModel = new films.Model.SavedFilm()

        savedFilmModel.properties.each { propertyName, propertyValue ->
            if (!propertyName.equals("class")
                    &&!propertyName.equals("audioTracks")&&!propertyName.equals("subtitleTracks"))
                savedFilmModel.setProperty(propertyName, savedFilmDomain.getProperty(propertyName))
        }

        savedFilmModel.audioTracks = audioTracksService.bindAudioTracksFromDomain(savedFilmDomain.audioTracks)
        savedFilmModel.subtitleTracks = subtitleTracksService.bindSubtitleTracks(savedFilmDomain.subtitleTracks)

        return savedFilmModel
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    List<films.Model.SavedFilm> getSavedFilmsOfFilm(films.Model.Film film) {
        if ((film == null)||(film.originalName == null))
        {
            log.error "Error recuperando lista de películas. Argumento nulo"
            return null
        }

        films.Film filmDomain = films.Film.findByOriginalName(film.originalName)
        if (filmDomain == null)
        {
            log.warn "Película no encontrada"
            return null
        }

        List<films.SavedFilm> savedFilmsDomain = filmDomain.savedFilms
        if (savedFilmsDomain == null)
        {
            log.warn "Película no encontrada"
            return null
        }

        List<films.Model.SavedFilm> savedFilmsModel = new ArrayList<films.Model.SavedFilm>()

        for (films.SavedFilm savedFilmDomain : savedFilmsDomain)
        {
            savedFilmsModel.add(bindSavedFilm(savedFilmDomain))
        }

        return savedFilmsModel
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    films.SavedFilm getAndUpdateDomainInstance (films.Model.SavedFilm savedFilmModel)
    {
        if (savedFilmModel == null)
        {
            log.error "Error saving null SavedFilm"
            return null
        }

        films.SavedFilm savedFilmDomain

        if (savedFilmModel.id == -1)
            savedFilmDomain = films.SavedFilm.findById(savedFilmModel.id)
        else
            savedFilmDomain = new films.SavedFilm()


        savedFilmDomain.audioTracks.removeAll()

        for (films.Model.AudioTrack audioTrackModel : savedFilmModel.audioTracks)
        {
            films.AudioTrack audioTrackDomain = audioTracksService.getUpdatedAudioTrackDomainInstance(audioTrackModel)
            savedFilmDomain.audioTracks.add(audioTrackDomain)
        }

        savedFilmDomain.subtitleTracks.removeAll()

        for (films.Model.SubtitleTrack subtitleTrackModel : savedFilmModel.subtitleTracks)
        {
            films.AudioTrack subtitleTrackDomain = subtitleTracksService.getAndUpdateSubtitleTrackDomainInstance(subtitleTrackModel)
            savedFilmDomain.audioTracks.add(subtitleTrackDomain)
        }

        savedFilmModel.properties.each{propertyName, propertyValue->
            if (!propertyName.equals("class"))
                savedFilmDomain.setProperty(propertyName, savedFilmModel.getProperty(propertyName))
        }
        return savedFilmDomain
    }


}
