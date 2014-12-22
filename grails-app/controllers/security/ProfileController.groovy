package security

class ProfileController {

    static allowedMethods = [changePassword:'POST']



    def index()
    {
        render (view: "profile", model:[])
    }

    def changePassword(String password)
    {
        redirect(controller: "profile", action: "index")
    }

}
