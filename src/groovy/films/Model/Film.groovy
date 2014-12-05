package films.Model

/**
 * Created by vickop on 29/11/14.
 */
class Film {

    long id = -1
    String spanishName
    String originalName
    int year
    int duration
    String countryCode
    CountryModel country
    int filmAffinityScore
    String posterName

    List<SavedFilm> savedFilms = new ArrayList<SavedFilm>()
    List<Person> director = new ArrayList<Person>()
    List<Person> actors = new ArrayList<Person>()
    List<GenreModel> genres = new ArrayList<GenreModel>()


}
