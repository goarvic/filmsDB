package films

class Genre {

    String localName //spanishName
    String englishName
    static hasMany = [genreNameLanguage : GenreNameLanguage]

    static constraints = {
        localName nullable : false, unique : true
        englishName nullable : true, unique: true
        genreNameLanguage bindable : false
    }
}
