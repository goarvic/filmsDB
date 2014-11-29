package films.database

import films.AudioTrack
import films.Model.Film
import films.Model.LanguageModel
import films.Model.SavedFilm
import films.Model.SubtitleTrack
import grails.transaction.Transactional

@Transactional
class FilmService {


    private List<SubtitleTrack> bindSubtitleTracks(List<films.SubtitleTrack> subtitleTracksDomain)
    {
        if (subtitleTracksDomain == null)
            return null
        List<SubtitleTrack> subtitleTracksModel = new ArrayList<AudioTrack>()

        for (films.SubtitleTrack subtitleTrackDomain : subtitleTracksDomain)
        {
            SubtitleTrack subtitleTrackModel = new AudioTrack()
            subtitleTrackModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class")&&!propertyName.equals("language"))
                    subtitleTrackModel.setProperty(propertyName, subtitleTrackDomain.getProperty(propertyName))
            }
            if (subtitleTrackDomain.language != null)
            {
                LanguageModel language = new LanguageModel()
                language.properties.each{propertyName, propertyValue ->
                    if (!propertyName.equals("class"))
                        language.setProperty(propertyName, subtitleTrackDomain.language.getProperty(propertyName))
                }
            }
            subtitleTracksModel.add(subtitleTrackModel)
        }
        return subtitleTracksModel
    }



    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************



    private List<AudioTrack> bindAudioTracksFromDomain(List<films.AudioTrack> audioTracksDomain)
    {
        if (audioTracksDomain == null)
            return null
        List<AudioTrack> audioTracksModel = new ArrayList<AudioTrack>()

        for (films.AudioTrack audioTrackDomain : audioTracksDomain)
        {
            AudioTrack audioTrackModel = new AudioTrack()
            audioTrackModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class")&&!propertyName.equals("language"))
                    audioTrackModel.setProperty(propertyName, audioTrackDomain.getProperty(propertyName))
            }
            if (audioTrackDomain.language != null)
            {
                LanguageModel language = new LanguageModel()
                language.properties.each{propertyName, propertyValue ->
                    if (!propertyName.equals("class"))
                        language.setProperty(propertyName, audioTrackDomain.language.getProperty(propertyName))
                }
            }
            audioTracksModel.add(audioTrackModel)
        }
        return audioTracksModel
    }



    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************



    private List<SavedFilm> bindSavedFilmFromDomain (List<films.SavedFilm> savedFilmsDomain)
    {
        if (savedFilmsDomain == null)
            return null

        List<SavedFilm> savedFilmsModel = new ArrayList<SavedFilm>()

        for (films.SavedFilm savedFilmDomain : savedFilmsDomain)
        {
            SavedFilm savedFilm = new SavedFilm()
            savedFilmsModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class")&&!propertyName.equals("audioTracks")&&!propertyName.equals("subtitleTracks"))
                    savedFilm.setProperty(propertyName, savedFilmDomain.getProperty(propertyName))
            }

            savedFilmsModel.audioTracks = bindAudioTracksFromDomain(savedFilmDomain.audioTracks)
            savedFilmsModel.subtitleTracks = bindSubtitleTracks(savedFilmDomain.subtitleTracks)

        }
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    Film getFilmByOriginalName(String originalName) {
        films.Film filmDomain = films.Film.findByOriginalName(originalName)
        if (filmDomain == null)
            return null

        Film filmModel = new Film()

        filmModel.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class") && !propertyName.equals("country") && !propertyName.equals("savedFilms"))
                filmModel.setProperty(propertyName, filmModel.getProperty(propertyName))
        }

        filmDomain.country.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class"))
                filmModel.country.setProperty(propertyName, filmDomain.country.getProperty(propertyName))
        }

        List<films.SavedFilm> savedFilms = films.SavedFilm.findAllByFilm(filmDomain)
        filmModel.savedFilms = bindSavedFilmFromDomain(savedFilms)
        if (filmModel.savedFilms != null)
        {
            for (SavedFilm savedFilm : filmModel.savedFilms)
            {
                savedFilm.film = filmModel
            }
        }
        return filmModel
    }
}
