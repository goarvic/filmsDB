package films

class Person {

    String name
    Date dateCreated



    def beforeInsert() {
        dateCreated = new Date()
    }

    static constraints = {
        name nullable : false
    }
}
