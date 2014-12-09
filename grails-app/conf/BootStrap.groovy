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
            LanguageModel languageToSave = new LanguageModel(name: "Spanish", spanishName: "Español", code: "spa")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)

            languageToSave = new LanguageModel(name: "English", spanishName: "Inglés", code: "eng")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)

            languageToSave = new LanguageModel(name: "Italian", spanishName: "Italiano", code: "ita")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)

            languageToSave = new LanguageModel(name: "French", spanishName: "Francés", code: "fra")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)

            languageToSave = new LanguageModel(name: "German", spanishName: "Alemán", code: "deu")
            languageService.getUpdateAndSaveDomainInstance(languageToSave)
        }

        if (countryService.getNumberOfCountriesSaved() == 0)
        {
            CountryModel countryToSave = new CountryModel(englishName: "Australia", spanishName: "Australia", countryCode: "AUS")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Austria", spanishName: "Austria", countryCode: "AUT")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Belgium", spanishName: "Bélgica", countryCode: "BEL")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Brazil", spanishName: "Brasil", countryCode: "BRA")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Canada", spanishName: "Canadá", countryCode: "CAN")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "China", spanishName: "China", countryCode: "CHN")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Czech Republic", spanishName: "República Checa", countryCode: "CZE")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Ecuador", spanishName: "Ecuador", countryCode: "ECU")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "France", spanishName: "Francia", countryCode: "FRA")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Germany", spanishName: "Alemania", countryCode: "DEU")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Italy", spanishName: "Italia", countryCode: "ITA")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Japan", spanishName: "Japón", countryCode: "JPN")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Mexico", spanishName: "Méjico", countryCode: "MEX")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Portugal", spanishName: "Portugal", countryCode: "PRT")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Russia", spanishName: "Rusia", countryCode: "RUS")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "Spain", spanishName: "España", countryCode: "ESP")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "United Kingdom", spanishName: "Reino Unido", countryCode: "GBR")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)

            countryToSave = new CountryModel(englishName: "United States", spanishName: "Estados Unidos", countryCode: "USA")
            countryService.getUpdateAndSaveDomainInstance(countryToSave)
        }
        if (settingService.getNumberOfSettingsSaved() == 0)
        {
            SettingModel pathOfPosters = new SettingModel(settingName: "pathOfPosters", value: "C:\\Users\\X51104GO\\Downloads"/*"/home/vickop/images"*/)
            if (settingService.getSaveAndUpdateDomainInstance(pathOfPosters) == null)
                log.error "Error salvando setting"
        }

        assert systemService.checkPosterFolderAccess()


    }
    def destroy = {
    }
}
