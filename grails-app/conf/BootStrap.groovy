import films.SystemService
import films.TelegramService
import films.database.LanguageService
import films.security.SecurityService
import films.telegram.FilmTelegramBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

class BootStrap {

    SystemService systemService
    SecurityService securityService
    TelegramService telegramService

    def init = { servletContext ->
        systemService.addDefaultLanguages()
        systemService.addDefaultCountries()
        assert systemService.checkPosterFolderAccess()
        assert systemService.checkOrCreateSmallPostersFolder()
        assert systemService.checkFlagsFolderAccess()
        securityService.checkAndCreateDefaultRolesAndAdmin()
        telegramService.initializeBot()
    }
    def destroy = {
    }
}
