import films.Model.CountryModel
import films.Model.CountryNameModel
import films.Model.LanguageModel
import films.Model.LanguageNameModel
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
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Español", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "Spanish", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Español", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "Spanish", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }

        languageToUpdate = languageService.getLanguageByCode("cat")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Catalan", localName: "Catalán", code: "cat")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Catalán", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "Catalan", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Catalán", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "Catalan", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }

        languageToUpdate = languageService.getLanguageByCode("eng")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "English", localName: "Inglés", code: "eng")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Inglés", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "English", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Inglés", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "English", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }
        languageToUpdate = languageService.getLanguageByCode("ita")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Italian", localName: "Italiano", code: "ita")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Italiano", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "Italian", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Italiano", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "Italian", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }
        languageToUpdate = languageService.getLanguageByCode("fra")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "French", localName: "Francés", code: "fra")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Francés", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "French", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Francés", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "French", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }
        languageToUpdate = languageService.getLanguageByCode("deu")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "German", localName: "Alemán", code: "deu")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Alemán", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "German", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Alemán", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "German", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }
        languageToUpdate = languageService.getLanguageByCode("rus")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Russian", localName: "Ruso", code: "rus")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Ruso", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "Russian", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Ruso", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "Russian", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }
        languageToUpdate = languageService.getLanguageByCode("chi")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Chinese", localName: "Chino", code: "chi")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Chino", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "Chinese", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Chino", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "Chinese", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }
        languageToUpdate = languageService.getLanguageByCode("por")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Portuguese", localName: "Portugués", code: "por")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Portugués", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "Portuguese", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Portugués", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "Portuguese", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }
        languageToUpdate = languageService.getLanguageByCode("pol")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Polish", localName: "Polaco", code: "pol")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Polaco", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "Polish", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Polaco", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "Polish", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }
        languageToUpdate = languageService.getLanguageByCode("cze")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Czech", localName: "Checo", code: "cze")
            LanguageNameModel languageNameModel = new LanguageNameModel(name: "Checo", languageCodeOfName: "spa")
            languageToUpdate.languageNames.add(languageNameModel)
            languageNameModel = new LanguageNameModel(name: "Czech", languageCodeOfName: "eng")
            languageToUpdate.languageNames.add(languageNameModel)
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }
        else
        {
            if (languageToUpdate.languageNames.size() == 0)
            {
                LanguageNameModel languageNameModel = new LanguageNameModel(name: "Checo", languageCodeOfName: "spa")
                languageToUpdate.languageNames.add(languageNameModel)
                languageNameModel = new LanguageNameModel(name: "Czech", languageCodeOfName: "eng")
                languageToUpdate.languageNames.add(languageNameModel)
                languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
            }
        }

        languageToUpdate = languageService.getLanguageByCode("und")
        if (languageToUpdate == null)
        {
            languageToUpdate = new LanguageModel(name: "Undetermined", localName: "Indeterminado", code: "und")
            languageService.getUpdateAndSaveDomainInstance(languageToUpdate)
        }

        //*******************************************************************
        //*******************************************************************
        //*******************************************************************

        CountryModel countryToUpdate
        countryToUpdate = countryService.getCountryByCountryCode("AUS")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Australia", localName: "Australia", countryCode: "AUS")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Australia", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Australia", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Australia", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Australia", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("AUT")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Austria", localName: "Austria", countryCode: "AUT")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Austria", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Austria", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Austria", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Austria", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("BEL")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Belgium", localName: "Bélgica", countryCode: "BEL")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Bélgica", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Belgium", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Bélgica", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Belgium", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("BRA")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Brazil", localName: "Brasil", countryCode: "BRA")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Brasil", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Brazil", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Brasil", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Brazil", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("CAN")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Canada", localName: "Canadá", countryCode: "CAN")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Canadá", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Canada", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Canadá", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Canada", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("CHN")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "China", localName: "China", countryCode: "CHN")
            CountryNameModel countryNameModel = new CountryNameModel(name: "China", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "China", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "China", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "China", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("CZE")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Czech Republic", localName: "República Checa", countryCode: "CZE")
            CountryNameModel countryNameModel = new CountryNameModel(name: "República Checa", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Czech Republic", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "República Checa", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Czech Republic", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("ECU")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Ecuador", localName: "Ecuador", countryCode: "ECU")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Ecuador", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Ecuador", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Ecuador", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Ecuador", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("FRA")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "France", localName: "Francia", countryCode: "FRA")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Francia", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "France", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Francia", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "France", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("DEU")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Germany", localName: "Alemania", countryCode: "DEU")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Alemania", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Germany", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Alemania", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Germany", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("ITA")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Italy", localName: "Italia", countryCode: "ITA")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Italia", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Italy", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Italia", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Italy", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("JPN")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Japan", localName: "Japón", countryCode: "JPN")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Japón", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Japan", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Japón", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Japan", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("MEX")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Mexico", localName: "Méjico", countryCode: "MEX")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Méjico", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Mexico", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Méjico", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Mexico", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("PRT")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Portugal", localName: "Portugal", countryCode: "PRT")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Portugal", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Portugal", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Portugal", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Portugal", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("RUS")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Russia", localName: "Rusia", countryCode: "RUS")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Rusia", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Russia", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Rusia", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Russia", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("ESP")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "Spain", localName: "España", countryCode: "ESP")
            CountryNameModel countryNameModel = new CountryNameModel(name: "España", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "Spain", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "España", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "Spain", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("GBR")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "United Kingdom", localName: "Reino Unido", countryCode: "GBR")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Reino Unido", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "United Kingdom", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Reino Unido", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "United Kingdom", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
        }

        countryToUpdate = countryService.getCountryByCountryCode("USA")
        if (countryToUpdate == null)
        {
            countryToUpdate = new CountryModel(englishName: "United States", localName: "Estados Unidos", countryCode: "USA")
            CountryNameModel countryNameModel = new CountryNameModel(name: "Estados Unidos", languageCode: "spa")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryNameModel = new CountryNameModel(name: "United States", languageCode: "eng")
            countryToUpdate.countryNamesLanguage.add(countryNameModel)
            countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
        }
        else
        {
            if (countryToUpdate.countryNamesLanguage.size() == 0)
            {
                CountryNameModel countryNameModel = new CountryNameModel(name: "Estados Unidos", languageCode: "spa")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryNameModel = new CountryNameModel(name: "United States", languageCode: "eng")
                countryToUpdate.countryNamesLanguage.add(countryNameModel)
                countryService.getUpdateAndSaveDomainInstance(countryToUpdate)
            }
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

        log.info "Checking new domain schema and converting"
        systemService.convertFilmsToNewLanguageDomain()
        systemService.convertGenresToNewDomainModel()
        log.info "Finished checking/converting new schema"

    }
    def destroy = {
    }
}
