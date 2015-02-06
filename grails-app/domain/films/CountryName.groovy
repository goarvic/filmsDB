package films

class CountryName {

    String name
    String languageCode

    static belongsTo = [country : Country]

    static constraints = {
        country unique : ['languageCode'] , bindable : false
        name nullable : false
    }
}
