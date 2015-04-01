import films.SystemService
import films.database.LanguageService
import films.security.SecurityService

class BootStrap {

    LanguageService languageService
    SystemService systemService
    SecurityService securityService

    def init = { servletContext ->
        systemService.addDefaultLanguages()
        systemService.addDefaultCountries()
        assert systemService.checkPosterFolderAccess()
        assert systemService.checkOrCreateSmallPostersFolder()
        assert systemService.checkFlagsFolderAccess()
        securityService.checkAndCreateDefaultRolesAndAdmin()
    }
    def destroy = {
    }
}
