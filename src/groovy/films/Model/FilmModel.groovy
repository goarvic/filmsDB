package films.Model

/**
 * Created by vickop on 29/11/14.
 */
class FilmModel {

    long id = -1
    String localName
    String originalName
    int year
    int duration
    String countryCode
    CountryModel country
    int filmAffinityScore
    String posterName
    String synopsis

    List<SavedFilmModel> savedFilms = new ArrayList<SavedFilmModel>()
    List<PersonModel> director = new ArrayList<PersonModel>()
    List<PersonModel> actors = new ArrayList<PersonModel>()
    List<GenreModel> genres = new ArrayList<GenreModel>()


}
