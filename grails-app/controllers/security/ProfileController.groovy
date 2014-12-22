package security

import films.security.SecurityService

class ProfileController {

    static allowedMethods = [changePassword:'POST']

    SecurityService securityService


    def index()
    {
        render (view: "profile", model:[])
    }

    def changePassword(String password)
    {
        if (securityService.changeActualUserPass(password) < 0)
        {
            flash.error = "Unknown error updating password"
            redirect(controller: "profile", action: "index")
        }
        else
        {
            flash.message = "Success! Password updated"
            redirect(controller: "profile", action: "index")
        }
    }

}
