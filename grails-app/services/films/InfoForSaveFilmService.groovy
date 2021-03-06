package films

import films.Model.AudioTrackModel
import films.Model.CommandObjects.InfoForSaveFilm
import films.Model.FilmDetailsFromFA
import films.Model.FilmDetailsFromMKVInfo
import films.Model.FilmDetailsLanguageModel
import films.Model.FilmModel
import films.Model.SavedFilmModel
import films.Model.SubtitleTrackModel
import films.database.FilmService
import films.database.LanguageService
import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.binding.DataBindingUtils
import org.imgscalr.Scalr

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

@Transactional
class InfoForSaveFilmService {
    FilmService filmService
    LanguageService languageService
    SystemService systemService
    TelegramService telegramService
    GrailsApplication grailsApplication

    FilmModel processAllInfoAndSaveNewFilm(InfoForSaveFilm infoForSaveFilm,
                                           FilmDetailsFromMKVInfo filmDetailsFromMKVInfo,
                                           FilmDetailsFromFA filmDetailsFromFA,
                                           Locale locale) {

        FilmModel filmModel = filmService.getFilmByOriginalName(filmDetailsFromFA.originalName)
        if (filmModel == null || filmModel.year != filmDetailsFromFA.year)
            filmModel = new FilmModel()

        DataBindingUtils.bindObjectToInstance(filmModel, filmDetailsFromFA)
        SavedFilmModel savedFilmModel = new SavedFilmModel()
        List<FilmDetailsLanguageModel> filmDetailsLanguageModelList = filmDetailsFromFA.filmDetailsLanguageModels

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
            subtitleTrackModel.type = infoForSaveFilm.subtitleTracks.get(iterator).type
            iterator++
        }

        filmModel.savedFilms.add(savedFilmModel)

        String posterName = filmModel.originalName
        posterName = posterName.replaceAll("[^a-zA-Z0-9]+", "");
        posterName += " " + filmModel.year + " " + locale.getISO3Language()  + ".jpg"

        for (FilmDetailsLanguageModel filmDetailsLanguageModel : filmDetailsLanguageModelList)
        {
            if (filmDetailsLanguageModel.language.code == locale.getISO3Language())
                filmDetailsLanguageModel.posterName = posterName
            else
                filmDetailsLanguageModel.posterName = "A"

            filmModel.filmDetailsLanguage.add(filmDetailsLanguageModel)
        }

        String pathOfPosters = systemService.getPostersFolder()
        String smallPathOfPosters = systemService.getSmallPostersFolder()
        if ((pathOfPosters == null) || (smallPathOfPosters == null))
        {
            log.error "Error saving Film. Posters Path error"
            return null
        }
        def fos= new FileOutputStream(new File(pathOfPosters + posterName))
        infoForSaveFilm.poster.getBytes().each{ fos.write(it) }
        fos.flush()
        fos.close()
        log.info "Poster saved successful"

        byte[] byteArrayImage = infoForSaveFilm.poster.getBytes()

        ByteArrayInputStream inc = new ByteArrayInputStream(byteArrayImage);
        BufferedImage bImageFromConvert = ImageIO.read(inc);

        BufferedImage thumbnail = Scalr.resize(bImageFromConvert, 200);
        fos= new FileOutputStream(new File(smallPathOfPosters + posterName))
        try {
            ImageIO.write(thumbnail, "jpg", fos);
        }
        finally {
            fos.flush();
            fos.close()
        }
        Film filmOnDb
        try
        {
            filmOnDb = filmService.getUpdateAndSaveInstance(filmModel)
        }
        catch (RuntimeException e)
        {
            log.error e.localizedMessage
            return null
        }


        if (infoForSaveFilm.sendNotification){
            SavedFilm savedFilm = filmOnDb.savedFilms.getAt(filmOnDb.savedFilms.size()-1);
            String urlToNotify = grailsApplication.config.grails.serverURL + "/es/filmData/viewFilm/" + savedFilm.id
            telegramService.notifyNewFilm(urlToNotify)
        }

        return filmModel
    }
}