package films

class Country {

    String englishName
    String spanishName
    String countryCode
    String pathToFlag

    static constraints = {
        englishName nullable: false
        spanishName nullable: false
        countryCode nullable: false
        pathToFlag nullable: true
    }
}
