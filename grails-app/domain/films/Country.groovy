package films

class Country {

    String englishName
    String localName
    String countryCode
    String pathToFlag

    static hasMany = [countryNamesLanguage : CountryName]

    static constraints = {
        englishName nullable: false
        localName nullable: false
        countryCode nullable: false
        pathToFlag nullable: true
        countryNamesLanguage bindable : false
    }
}
