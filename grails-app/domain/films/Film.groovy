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


    static constraints = {
        originalName nullable : false
        countryCode nullable: true
        posterName nullable: true
    }
}
