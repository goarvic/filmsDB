package films

class Film {

    String spanishName
    String originalName
    int year
    int duration
    String countryCode
    Country country
    int filmAffinityScore
    String posterName

    static hasMany = [genres : Genre, savedFilms : SavedFilm, director : Person, actors : Person]

    Date dateCreated

    def beforeInsert() {
        dateCreated = new Date()
    }

    static constraints = {
        originalName nullable : false, unique: true
        genres bindable : false
        savedFilms bindable : false
        director bindable : false
        actors bindable : false
        countryCode nullable: true
        posterName nullable: true
        country nullable : false
    }
}
