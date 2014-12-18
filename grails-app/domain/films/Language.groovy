package films

class Language {

    String name
    String localName
    String pathToFlag
    String code

    static constraints = {
        name nullable : true
        pathToFlag nullable: true
        localName nullable: true
    }
}
