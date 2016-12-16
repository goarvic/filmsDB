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
        def sqlResult = SavedFilm.executeQuery("select sum(sf.size) from SavedFilm sf")
        return Long.valueOf(sqlResult.get(0))
    }

    @Cacheable('totalActors')
    long getTotalActors() {
        int number = Person.count()
        return number
    }

}
