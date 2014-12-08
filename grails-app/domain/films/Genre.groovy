package films

class Genre {

    String localName //spanishName
    String englishName

    static constraints = {
        localName nullable : false, unique : true
        englishName nullable : true, unique: true
    }
}
