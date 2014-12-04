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

        filmDomain.country.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class"))
                filmModel.country.setProperty(propertyName, filmDomain.country.getProperty(propertyName))
        }
        return filmModel
    }





    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    int saveFilm(Film filmModel)
    {

        log.info "Saving new film on database"

        if (filmModel == null)
        {
            log.error "Error trying to save null film"
            return -1
        }

        films.Film filmDomain

        if (filmModel.id == -1)
            filmDomain = new films.Film()
        else
            filmDomain = films.Film.findById(filmModel.id)

        if (filmDomain == null)
            return -1


        filmModel.properties.each{propertyName, propertyValue->
            if (!propertyName.equals("class") && !propertyName.equals("country") && !propertyName.equals("savedFilms")
                    && !propertyName.equals("director")&& !propertyName.equals("actors"))
                filmDomain.setProperty(propertyName, filmModel.getProperty(propertyName))
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

        filmDomain.savedFilms.removeAll()

        for (films.Model.SavedFilm savedFilm : filmDomain.savedFilms)
        {
            filmDomain.savedFilms.add(savedFilmService.getAndUpdateDomainInstance(savedFilm))
        }

        filmDomain.country = films.Country.findByCountryCode(filmModel.countryCode)

        if (filmDomain.save(flush: true) == null)
        {
            log.error "Error saving film Instance: " + filmDomain.errors
            return -2
        }
        return 0

    }




}
