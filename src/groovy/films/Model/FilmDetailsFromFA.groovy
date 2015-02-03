package films.Model
/**
 * Created with IntelliJ IDEA.
 * User: vickop
 * Date: 23/01/14
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
class FilmDetailsFromFA

{
    int duration

    List<PersonModel> actors
    List<PersonModel> director
    List<GenreModel> genres
    List<FilmDetailsLanguageModel> filmDetailsLanguageModels

    String urlBigPoster
    String urlSmallPoster
    String originalName
    String localName
    int year
    String countryCode
    CountryModel country
    String synopsis
    LanguageModel language
    String filmAffinityURL
}
