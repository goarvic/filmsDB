package films

import films.Model.FilmDetailsLanguageModel
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

        Locale locale = request.getLocale()
        FilmDetailsLanguageModel filmDetailsLanguage
        SavedFilmModel savedFilm =  film.savedFilms.find{savedFilm-> savedFilm.id == idSavedFilm }
        filmDetailsLanguage = film.filmDetailsLanguage.find{filmDetailsLanguageIt->
                    filmDetailsLanguageIt.language.code == locale.getISO3Language()
                }

        if (filmDetailsLanguage == null)
        {
            filmDetailsLanguage = film.filmDetailsLanguage.get(0)
        }

        session.setAttribute("filmDetailsLanguage", filmDetailsLanguage)
        session.setAttribute("filmData", film)
        session.setAttribute("savedFilmData", savedFilm)
        render(view : "filmInfo", model:[film : film, savedFilm: savedFilm, filmDetailsLanguage: filmDetailsLanguage])
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
