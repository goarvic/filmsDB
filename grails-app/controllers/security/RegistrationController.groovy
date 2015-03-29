package security

import films.security.UserService

class RegistrationController {

    UserService userService

    def index() {

        render (view: "registrationForm", model: [])
    }






    def processRegistrationData(String email, String password, String completeName)
    {
        render (view: "registrationForm", model: [])
    }




    def isEmailAvailable(String email)
    {
        if (email == null)
            render "false"

        if (userService.isEmailOnDB(email))
            render "false"
        else
            render "true"

    }








}
