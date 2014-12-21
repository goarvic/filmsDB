import films.Country
import films.Model.CountryModel
import films.Model.LanguageModel
import films.Model.SettingModel
import films.SystemService
import films.database.CountryService
import films.database.LanguageService
import films.database.SettingService

class BootStrap {

    LanguageService languageService
    CountryService countryService
    SettingService settingService
    SystemService systemService

    def init = { servletContext ->

        //Vamos a añadir algunos idiomas si es que no están ya


        if (languageService.numberOfLanguageSaved == 0)
        {
            LanguageModel languageToSave = new LanguageModel(name: "Spanish", localName: "Español", code: "spa")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)

            languageToSave = new LanguageModel(name: "English", localName: "Inglés", code: "eng")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)

            languageToSave = new LanguageModel(name: "Italian", localName: "Italiano", code: "ita")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)

            languageToSave = new LanguageModel(name: "French", localName: "Francés", code: "fra")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)

            languageToSave = new LanguageModel(name: "German", localName: "Alemán", code: "deu")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)
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
            SettingModel pathOfPosters = new SettingModel(settingName: "pathOfPosters", value: /*"C:\\Users\\X51104GO\\Downloads"*/"/home/vickop/images")
            if (settingService.getSaveAndUpdateDomainInstance(pathOfPosters) == null)
                log.error "Error salvando setting"
        }

        assert systemService.checkPosterFolderAccess()
        assert systemService.checkOrCreateSmallPostersFolder()


    }
    def destroy = {
    }
}
