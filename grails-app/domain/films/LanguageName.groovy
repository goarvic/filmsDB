package films

class LanguageName {

    String name
    String languageCodeOfName

    static belongsTo = [language : Language]


    static constraints = {
        language unique : ['languageCodeOfName'] , bindable : false
        name nullable : false
    }
}
