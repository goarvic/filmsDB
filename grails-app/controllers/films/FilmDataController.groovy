package films

import films.Model.FilmModel
import films.Model.SavedFilmModel
import films.database.FilmService

class FilmDataController {

    FilmService filmService
    SystemService systemService

    def index(int idFilm, int idSavedFilm) {

        FilmModel film = filmService.getFilmById(idFilm)
        if (film == null)
        {
            log.error "Not found film"
            render "ERROR"
            return
        }
        SavedFilmModel savedFilm =  film.savedFilms.find{savedFilm-> savedFilm.id == idSavedFilm }
        session.setAttribute("filmData", film)
        session.setAttribute("savedFilmData", savedFilm)
        render(view : "filmInfo", model:[film : film])
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFilmDetailsFA()
    {
        Object sessionObject = session.getAttribute("filmData")
        if (!(sessionObject instanceof FilmModel))
        {
            render "Error"
            return
        }
        FilmModel film =  (FilmModel) sessionObject
        if (film == null) {
            request.error = "Error processing FilmModel. No data on session"
            return
        }

        sessionObject = session.getAttribute("savedFilmData")
        if (!(sessionObject instanceof SavedFilmModel))
        {
            render "Error"
            return
        }
        SavedFilmModel savedFilm =  (SavedFilmModel) sessionObject
        if (savedFilm == null) {
            request.error = "Error processing FilmModel. No data on session"
            return
        }
        render(view : "filmDetailsFA", model:[film : film, savedFilm: savedFilm])
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFilmDetailsVideo()
    {
        Object sessionObject = session.getAttribute("savedFilmData")
        if (!(sessionObject instanceof SavedFilmModel))
        {
            render "Error"
            return
        }
        SavedFilmModel savedFilm =  (SavedFilmModel) sessionObject
        if (savedFilm == null) {
            request.error = "Error processing SavedFilmModel. No data on session"
            return
        }
        render(view : "filmDetailsVideo", model:[savedFilm : savedFilm])
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFilmDetailsAudio()
    {
        Object sessionObject = session.getAttribute("savedFilmData")
        if (!(sessionObject instanceof SavedFilmModel))
        {
            render "Error"
            return
        }
        SavedFilmModel savedFilm =  (SavedFilmModel) sessionObject
        if (savedFilm == null) {
            request.error = "Error processing SavedFilmModel. No data on session"
            return
        }
        render(view : "filmDetailsAudio", model:[audioTracks : savedFilm.audioTracks])
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFilmDetailsSubtitles()
    {
        Object sessionObject = session.getAttribute("savedFilmData")
        if (!(sessionObject instanceof SavedFilmModel))
        {
            render "Error"
            return
        }
        SavedFilmModel savedFilm =  (SavedFilmModel) sessionObject
        if (savedFilm == null) {
            request.error = "Error processing SavedFilmModel. No data on session"
            return
        }
        render(view : "filmDetailsSubtitles", model:[subtitleTracks : savedFilm.subtitleTracks])
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFilmPoster(String posterName)
    {
        String imagePath = systemService.getPostersFolder()
        if (imagePath == null)
        {
            return
        }
        imagePath += posterName

        File imagePoster = new File(imagePath)

        byte[] img = imagePoster.getBytes()
        response.setIntHeader('Content-length', img.length)
        response.contentType = 'image/jpg' // or the appropriate image content type
        response.outputStream << img
        response.outputStream.flush()
    }

}
