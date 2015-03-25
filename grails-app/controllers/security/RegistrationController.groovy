package security

class RegistrationController {

    def index() {

        render (view: "registrationForm", model: [])
    }

    def processRegistrationData(String email, String password, String completeName)
    {

    }
}
