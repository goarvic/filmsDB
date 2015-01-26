package films

class Film {

    String localName
    String originalName
    int year
    int duration
    String countryCode
    Country country
    int filmAffinityScore
    String posterName
    String synopsis


    static mapping = {
        synopsis column: "synopsis", sqlType: "varchar(2000)"
    }

    static hasMany = [filmDetailsLanguage : FilmDetailsLanguage, genres : Genre, savedFilms : SavedFilm, director : Person, actors : Person]

    List<Person> actors
    List<Person> director

    Date dateCreated

    def beforeInsert() {
        dateCreated = new Date()
    }

    static constraints = {

        genres bindable : false
        savedFilms bindable : false
        director bindable : false
        actors bindable : false
        countryCode nullable: true
        posterName nullable: true
        country bindable : false, nullable : false
        originalName nullable : false
        originalName(unique: ['year'])
        synopsis nullable : true
        filmDetailsLanguage bindable : false
    }
}
