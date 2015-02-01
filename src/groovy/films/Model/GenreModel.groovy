package films.Model

/**
 * Created by X51104GO on 05/12/2014.
 */
class GenreModel {
    long id = -1
    String localName
    String englishName
    List<GenreNameLanguageModel> genreNameLanguage = new ArrayList<GenreNameLanguageModel>()

}
