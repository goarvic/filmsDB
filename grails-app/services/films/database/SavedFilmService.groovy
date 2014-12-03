package films.database

import films.SavedFilm
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

    films.SavedFilm getDomainInstance (films.Model.SavedFilm savedFilmModel)
    {
        if (savedFilmModel == null)
        {
            log.error "Error saving null SavedFilm"
            return null
        }

        films.SavedFilm savedFilmDomain

        if (savedFilmModel.id == null)
            savedFilmDomain = films.SavedFilm.findById(savedFilmModel.id)
        else
            savedFilmDomain = new films.SavedFilm()

        //TODO: falta hacer todo
        for (films.Model.AudioTrack audioTrackModel : savedFilmModel)
        {
            films.AudioTrack audioTrackDomain = audioTracksService.getAudioTrackDomainInstance(audioTrackModel)
            if ((savedFilmDomain.audioTracks == null)||(!savedFilmDomain.audioTracks.contains(audioTrackDomain)))
            {
                savedFilmDomain.audioTracks.add(audioTrackDomain)
            }
            else
            {
                savedFilmDomain.audioTracks.getAt()
            }

        }
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    int test (films.Model.SavedFilm savedFilmModel)
    {
        if (savedFilmModel == null)
        {
            log.error "Error saving null SavedFilm"
            return -1
        }

        if (savedFilmModel.id == null) //Se trata de una nueva instancia
        {
            films.SavedFilm savedFilmDomain = new SavedFilm()


            for (films.Model.AudioTrack audioTrackModel : savedFilmModel.audioTracks)
            {
                films.AudioTrack audioTrackDomain = new films.AudioTrack()
                audioTrackModel.properties.each{propertyName, propertyValue->
                    if (!propertyName.equals("class") && !propertyName.equals("id") && !propertyName.equals("language"))
                        audioTrackDomain.setProperty(propertyName, audioTrackModel.getProperty(propertyName))
                }
                if (audioTrackModel.language != null)
                {
                    audioTrackDomain.language = films.Language.findByCode(audioTrackModel.language.code)
                    if (audioTrackDomain.language == null)
                    {
                        log.error "El lenguaje de la pista no se encuentra en BBDD"
                        return null
                    }
                }
                savedFilmDomain.audioTracks.add(audioTrackDomain)
            }

            for (films.Model.SubtitleTrack subtitleTrackModel : savedFilmModel.subtitleTracks)
            {
                films.AudioTrack subtitleTrackDomain = new films.SubtitleTrack()
                subtitleTrackModel.properties.each{propertyName, propertyValue->
                    if (!propertyName.equals("class") && !propertyName.equals("id") && !propertyName.equals("language"))
                        subtitleTrackDomain.setProperty(propertyName, subtitleTrackModel.getProperty(propertyName))
                }
                if (subtitleTrackModel.language != null)
                {
                    subtitleTrackDomain.language = films.Language.findByCode(subtitleTrackModel.language.code)
                    if (subtitleTrackDomain.language == null)
                    {
                        log.error "El lenguaje de la pista no se encuentra en BBDD"
                        return null
                    }
                }
                savedFilmDomain.subtitleTracks.add(subtitleTrackDomain)
            }

            savedFilmModel.properties.each { propertyName, propertyValue ->
                if (!propertyName.equals("class") &&!propertyName.equals("audioTracks")
                        &&!propertyName.equals("subtitleTracks")&&!propertyName.equals("id")
                        &&!propertyName.equals("film"))
                    savedFilmDomain.setProperty(propertyName, savedFilmModel.getProperty(propertyName))
            }

            savedFilmDomain.film = films.Film.findByOriginalName()


        }
    }


}
