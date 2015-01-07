import films.Model.CountryModel
import films.Model.LanguageModel
import films.Model.SettingModel
import films.SystemService
import films.database.CountryService
import films.database.LanguageService
import films.database.SettingService
import films.security.SecurityService

class BootStrap {

    LanguageService languageService
    CountryService countryService
    SettingService settingService
    SystemService systemService
    SecurityService securityService

    def grailsApplication

    def init = { servletContext ->

        //Vamos a añadir algunos idiomas si es que no están ya

        LanguageModel languageToUpdate = languageService.getLanguageByCode("spa")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Spanish", localName: "Español", code: "spa")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("cat")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Catalan", localName: "Catalán", code: "cat")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("eng")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "English", localName: "Inglés", code: "eng")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("ita")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Italian", localName: "Italiano", code: "ita")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("fra")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "French", localName: "Francés", code: "fra")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("deu")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "German", localName: "Alemán", code: "deu")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("rus")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Russian", localName: "Ruso", code: "rus")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("chi")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Chinese", localName: "Chino", code: "chi")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("por")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Portuguese", localName: "Portugués", code: "por")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("pol")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Polish", localName: "Polaco", code: "pol")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        languageToUpdate = languageService.getLanguageByCode("cze")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Czech", localName: "Checo", code: "cze")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }

        languageToUpdate = languageService.getLanguageByCode("und")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Undetermined", localName: "Indeterminado", code: "und")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }




        if (countryService.getNumberOfCountriesSaved() == 0)
        {
            CountryModel countryToSave = new CountryModel(englishName: "Australia", localName: "Australia", countryCode: "AUS")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Austria", localName: "Austria", countryCode: "AUT")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Belgium", localName: "Bélgica", countryCode: "BEL")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Brazil", localName: "Brasil", countryCode: "BRA")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Canada", localName: "Canadá", countryCode: "CAN")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "China", localName: "China", countryCode: "CHN")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Czech Republic", localName: "República Checa", countryCode: "CZE")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Ecuador", localName: "Ecuador", countryCode: "ECU")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "France", localName: "Francia", countryCode: "FRA")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Germany", localName: "Alemania", countryCode: "DEU")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Italy", localName: "Italia", countryCode: "ITA")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Japan", localName: "Japón", countryCode: "JPN")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Mexico", localName: "Méjico", countryCode: "MEX")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Portugal", localName: "Portugal", countryCode: "PRT")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Russia", localName: "Rusia", countryCode: "RUS")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Spain", localName: "España", countryCode: "ESP")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "United Kingdom", localName: "Reino Unido", countryCode: "GBR")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "United States", localName: "Estados Unidos", countryCode: "USA")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)
        }
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
