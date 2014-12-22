package films.security

import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import security.Role
import security.User
import security.UserRole

@Transactional
class SecurityService {

    SpringSecurityService springSecurityService


    def checkAndCreateDefaultRolesAndAdmin() {
        Role adminRole = Role.findByAuthority('ROLE_ADMIN')
        if (adminRole == null)
        {
            adminRole = new Role(authority: 'ROLE_ADMIN')
            if (adminRole.save(flush:true) == null)
                log.error("Error creando rol de administrador: " + adminRole.errors)
        }

        Role userRole = Role.findByAuthority('ROLE_USER')
        if (userRole == null)
        {
            userRole = new Role(authority: 'ROLE_USER')
            if (userRole.save(flush:true) == null)
                log.error("Error creando rol de usuario: " + userRole.errors)
        }

        User adminUser = User.findByUsername("admin")
        if (adminUser == null)
        {
            adminUser = new User(username: 'admin', password: 'admin1234', name: 'administrator', enabled : true)
            if (adminUser.save(flush:true) == null)
                System.out.println(adminUser.errors)

            UserRole.create adminUser, adminRole, true
        }
        assert Role.count() == 2
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    int changeActualUserPass(String newPassword)
    {
        User actualUser = User.get(springSecurityService.getPrincipal().id)
        if (actualUser == null)
        {
            log.error "Actual user not found!"
            return -1
        }

        actualUser.password = newPassword

        if (actualUser.save(flush:true) == null)
        {
            log.error "Error updating password " + actualUser.errors
            return -2
        }
        return 0
    }

}
