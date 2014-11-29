package films

class Language {

    String name
    String spanishName
    String pathToFlag
    String code

    static constraints = {
        name nullable : true
        pathToFlag nullable: true
        spanishName nullable: true
    }
}
