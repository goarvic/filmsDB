package films.Model.ViewCollection

import films.Model.CountryModel
import films.Model.GenreModel
import films.Model.GenreNameLanguageModel
import films.Model.PersonModel

/**
 * Created by vickop on 13/12/14.
 */
class FilmBasicInfo {

    String originalName
    String localName
    CountryModel country
    List<PersonModel> actors
    List<PersonModel> director
    List<GenreNameLanguageModel> genresLanguage
    String posterName
    int year
    String filmVersion
    Date dateCreated
    int idSavedFilm
    int idFilm

}
