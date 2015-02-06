package films

class Language {

    String name
    String localName
    String pathToFlag
    String code

    static hasMany = [languageNames : LanguageName]

    static constraints = {
        name nullable : true
        pathToFlag nullable: true
        localName nullable: true
        languageNames bindable : false
    }
}
