package films.database

import grails.transaction.Transactional

@Transactional
class SavedFilmService {

    AudioTracksService audioTracksService
    SubtitleTracksService subtitleTracksService

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

        List<films.SavedFilm> savedFilmsDomain = films.SavedFilm.findAllByFilm(filmDomain)
        if (savedFilmsDomain == null)
        {
            log.warn "Película no encontrada"
            return null
        }

        List<films.Model.SavedFilm> savedFilmsModel = new ArrayList<films.Model.SavedFilm>()

        for (films.SavedFilm savedFilmDomain : savedFilmsDomain)
        {
            films.Model.SavedFilm savedFilmModel = new films.Model.SavedFilm()

            savedFilmsModel.properties.each { propertyName, propertyValue ->
                if (!propertyName.equals("class")&&!propertyName.equals("audioTracks")&&!propertyName.equals("subtitleTracks"))
                    savedFilmModel.setProperty(propertyName, savedFilmDomain.getProperty(propertyName))
            }

            savedFilmsModel.audioTracks = audioTracksService.bindAudioTracksFromDomain(savedFilmDomain.audioTracks)
            savedFilmsModel.subtitleTracks = subtitleTracksService.bindSubtitleTracks(savedFilmDomain.subtitleTracks)
        }


    }
}
