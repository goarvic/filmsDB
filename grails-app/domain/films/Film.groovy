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

    static hasMany = [savedFilms : SavedFilm, director : Person, actors : Person]

    Date dateCreated

    def beforeInsert() {
        dateCreated = new Date()
    }




    static constraints = {
        originalName nullable : false, unique: true
        countryCode nullable: true
        posterName nullable: true
        country nullable : false
    }
}
