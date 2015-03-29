import films.Model.SettingModel
import films.SystemService
import films.database.LanguageService
import films.database.SettingService
import films.security.SecurityService

class BootStrap {

    LanguageService languageService
    SettingService settingService
    SystemService systemService
    SecurityService securityService

    def grailsApplication

    def init = { servletContext ->

        //Vamos a añadir algunos idiomas si es que no están ya

        systemService.addDefaultLanguages()
        systemService.addDefaultCountries()


        if (settingService.getNumberOfSettingsSaved() == 0)
        {
            SettingModel pathOfPosters = new SettingModel(settingName: "pathOfPosters", value: grailsApplication.config.film.posterPath/*"C:\\Users\\X51104GO\\Downloads"*/)
            if (settingService.getSaveAndUpdateDomainInstance(pathOfPosters) == null)
                log.error "Error salvando setting"
        }

        if (systemService.getFlagsFolder() == null)
        {
            SettingModel pathOfFlags = new SettingModel(settingName: "pathOfFlags", value: grailsApplication.config.film.flagsPath/*"C:\\Users\\X51104GO\\Downloads"*/)
            if (settingService.getSaveAndUpdateDomainInstance(pathOfFlags) == null)
                log.error "Error salvando setting"
        }

        assert systemService.checkPosterFolderAccess()
        assert systemService.checkOrCreateSmallPostersFolder()
        assert systemService.checkFlagsFolderAccess()
        securityService.checkAndCreateDefaultRolesAndAdmin()

    }
    def destroy = {
    }
}
