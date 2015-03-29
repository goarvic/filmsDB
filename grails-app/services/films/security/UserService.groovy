package films.security

import grails.transaction.Transactional
import security.Role
import security.User
import security.UserRole

@Transactional
class UserService {

    boolean isEmailOnDB(String email)
    {
        User userFound = User.findByEmail(email)
        if (userFound == null)
            return false
        else
            return true
    }

    boolean isUserNameAvailable(String username)
    {
        User userFound = User.findByUsername(username)
        if (userFound == null)
            return true
        else
            return false
    }

    def createUser (String username, String completeName, String password) throws Exception {
        Role userRole = Role.findByAuthority('ROLE_USER')
        if (userRole == null)
        {
            throw new Exception("Error buscando rol de usuario en BBDD")
        }
        User user = new User()
        user.username = username
        user.completeName = completeName
        user.email = username

        UserRole.create user, userRole, true
        user.save(flush: true, failOnError: true)


    }
}
