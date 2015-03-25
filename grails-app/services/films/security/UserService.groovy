package films.security

import grails.transaction.Transactional
import security.User

@Transactional
class UserService {



    boolean isUserNameAvailable(String username)
    {
        User userFound = User.findByUsername(username)
        if (userFound == null)
            return true
        else
            return false
    }

    def createUser() {

    }
}
