package films

import films.Model.AudioTrackModel
import films.Model.CommandObjects.InfoForSaveFilm
import films.Model.FilmDetailsFromFA
import films.Model.FilmDetailsFromMKVInfo
import films.Model.FilmModel
import films.Model.SavedFilmModel
import films.Model.SubtitleTrackModel
import films.database.FilmService
import films.database.LanguageService
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class InfoForSaveFilmService {
    FilmService filmService
    LanguageService languageService
    SystemService systemService

    FilmModel processAllInfoAndSaveNewFilm(InfoForSaveFilm infoForSaveFilm, FilmDetailsFromMKVInfo filmDetailsFromMKVInfo, FilmDetailsFromFA filmDetailsFromFA) {

        FilmModel filmModel = filmService.getFilmByOriginalName(filmDetailsFromFA.originalName)
        if (filmModel == null)
            filmModel = new FilmModel()


        DataBindingUtils.bindObjectToInstance(filmModel, filmDetailsFromFA)
        SavedFilmModel savedFilmModel = new SavedFilmModel()

        DataBindingUtils.bindObjectToInstance(savedFilmModel, infoForSaveFilm)
        DataBindingUtils.bindObjectToInstance(savedFilmModel, filmDetailsFromMKVInfo)

        //Tenemos que actualizar la información de las pistas
        int iterator = 0
        for (AudioTrackModel audioTrackModel : savedFilmModel.audioTracks)
        {
            audioTrackModel.comments = infoForSaveFilm.audioTracks.get(iterator).comments
            audioTrackModel.language = languageService.getLanguageByCode(infoForSaveFilm.audioTracks.get(iterator).language.code)
            iterator++
        }
        iterator = 0
        for (SubtitleTrackModel subtitleTrackModel : savedFilmModel.subtitleTracks)
        {
            subtitleTrackModel.comments = infoForSaveFilm.subtitleTracks.get(iterator).comments
            subtitleTrackModel.language = languageService.getLanguageByCode(infoForSaveFilm.subtitleTracks.get(iterator).language.code)
            iterator++
        }

        filmModel.savedFilms.add(savedFilmModel)
        filmModel.posterName = filmDetailsFromFA.originalName + ".jpg"

        String pathOfPosters = systemService.getPostersFolder()
        if (pathOfPosters == null)
        {
            log.error "Error saving Film. Posters Path error"
            return null
        }
        def fos= new FileOutputStream(new File(pathOfPosters + filmModel.posterName))
        infoForSaveFilm.poster.getBytes().each{ fos.write(it) }
        fos.flush()
        fos.close()


        log.info "Poster saved successful"

        if (filmService.getUpdateAndSaveInstance(filmModel) == null)
        {
            return null
        }
        else
            return filmModel
    }
}