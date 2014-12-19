package films

import films.Model.FilmModel
import films.database.FilmService

class FilmDataController {

    FilmService filmService

    def index(int id) {

        FilmModel film = filmService.getFilmById(id)
        if (film == null)
        {
            log.error "Not found film"
            render "ERROR"
            return
        }

        render film.localName
    }
}
