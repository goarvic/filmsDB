package films.database

import films.Film
import films.Person
import films.SavedFilm
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

@Transactional
class StaticsService {


    @Cacheable('numberOfFilms')
    int getNumberOfFilmsOnDB() {
        int number = SavedFilm.count()
        return number
    }


    @Cacheable('totalSize')
    long getTotalFilmsSizeInBytes() {
        List<SavedFilm> savedFilms = SavedFilm.list()
        long size = 0
        for (SavedFilm savedFilm : savedFilms)
        {
            size += savedFilm.size
        }
        return size
    }

    @Cacheable('totalActors')
    long getTotalActors() {
        int number = Person.count()
        return number
    }

}
