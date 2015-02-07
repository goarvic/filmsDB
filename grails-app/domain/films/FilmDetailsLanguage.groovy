package films

class FilmDetailsLanguage {

    Language language
    String localName
    String filmAffinityURL
    String synopsis
    Date dateCreated
    String posterName

    static mapping = {
        synopsis column: "synopsis", sqlType: "varchar(2000)"
    }

    static belongsTo = [film : Film]

    def beforeInsert() {
        dateCreated = new Date()
    }

    static constraints = {
        language unique : ['film'] , bindable : false
        filmAffinityURL nullable : true
        posterName nullable : true
    }


}
