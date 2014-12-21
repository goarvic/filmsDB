package films

import films.Model.FilmModel
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
        session.setAttribute("filmData", film)
        render(view : "filmInfo", model:[film : film])
    }


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
        render(view : "filmDetailsFA", model:[film : film])
    }


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
