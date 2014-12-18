package films.Model.ViewCollection

import films.Model.GenreModel
import films.Model.PersonModel

/**
 * Created by vickop on 13/12/14.
 */
class FilmBasicInfo {

    String originalName
    String localName
    List<PersonModel> actors
    List<PersonModel> director
    List<GenreModel> genres
    String posterName
    int year
    String filmVersion
    Date dateCreated

}
