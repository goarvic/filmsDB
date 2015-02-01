package films

class GenreNameLanguage {

    String name
    Language language

    static belongsTo = [genre : Genre]


    static constraints = {
        language unique : ['genre'] , bindable : false
        name nullable : false
    }
}
