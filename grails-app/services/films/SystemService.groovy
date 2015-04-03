package films

import films.Model.CountryModel
import films.Model.CountryNameModel
import films.Model.LanguageModel
import films.Model.LanguageNameModel
import films.Model.SettingModel
import films.database.CountryService
import films.database.LanguageService
import films.database.SettingService
import grails.transaction.Transactional

@Transactional
class SystemService {

    SettingService settingService
    LanguageService languageService
    CountryService countryService
    def grailsApplication


    List<HashMap> countrys = Arrays.asList(
            [englishName: "Australia", localName: "Australia", countryCode: "AUS",
             languageNames : [[name: "Australia", languageCode: "spa"],[name: "Australia", languageCode: "eng"]]],

            [englishName: "Austria", localName: "Austria", countryCode: "AUT",
             languageNames : [[name: "Austria", languageCode: "spa"],[name: "Austria", languageCode: "eng"]]],

            [englishName: "Belgium", localName: "Bélgica", countryCode: "BEL",
             languageNames : [[name: "Bélgica", languageCode: "spa"],[name: "Belgium", languageCode: "eng"]]],

            [englishName: "Brazil", localName: "Brasil", countryCode: "BRA",
             languageNames : [[name: "Brasil", languageCode: "spa"],[name: "Brazil", languageCode: "eng"]]],

            [englishName: "Canada", localName: "Canadá", countryCode: "CAN",
             languageNames : [[name: "Canadá", languageCode: "spa"],[name: "Canada", languageCode: "eng"]]],

            [englishName: "China", localName: "China", countryCode: "CHN",
             languageNames : [[name: "China", languageCode: "spa"],[name: "China", languageCode: "eng"]]],

            [englishName: "Czech Republic", localName: "República Checa", countryCode: "CZE",
             languageNames : [[name: "República Checa", languageCode: "spa"],[name: "Czech Republic", languageCode: "eng"]]],

            [englishName: "Ecuador", localName: "Ecuador", countryCode: "ECU",
             languageNames : [[name: "Ecuador", languageCode: "spa"],[name: "Ecuador", languageCode: "eng"]]],

            [englishName: "France", localName: "Francia", countryCode: "FRA",
             languageNames : [[name: "Francia", languageCode: "spa"],[name: "France", languageCode: "eng"]]],

            [englishName: "Germany", localName: "Alemania", countryCode: "DEU",
             languageNames : [[name: "Alemania", languageCode: "spa"],[name: "Germany", languageCode: "eng"]]],

            [englishName: "Italy", localName: "Italia", countryCode: "ITA",
             languageNames : [[name: "Italia", languageCode: "spa"],[name: "Italy", languageCode: "eng"]]],

            [englishName: "Japan", localName: "Japón", countryCode: "JPN",
             languageNames : [[name: "Japón", languageCode: "spa"],[name: "Japan", languageCode: "eng"]]],

            [englishName: "Mexico", localName: "Méjico", countryCode: "MEX",
             languageNames : [[name: "Méjico", languageCode: "spa"],[name: "Mexico", languageCode: "eng"]]],

            [englishName: "Portugal", localName: "Portugal", countryCode: "PRT",
             languageNames : [[name: "Portugal", languageCode: "spa"],[name: "Portugal", languageCode: "eng"]]],

            [englishName: "Russia", localName: "Rusia", countryCode: "RUS",
             languageNames : [[name: "Rusia", languageCode: "spa"],[name: "Russia", languageCode: "eng"]]],

            [englishName: "Spain", localName: "España", countryCode: "ESP",
             languageNames : [[name: "España", languageCode: "spa"],[name: "Spain", languageCode: "eng"]]],

            [englishName: "United Kingdom", localName: "Reino Unido", countryCode: "GBR",
             languageNames : [[name: "Reino Unido", languageCode: "spa"],[name: "United Kingdom", languageCode: "eng"]]],

            [englishName: "United States", localName: "Estados Unidos", countryCode: "USA",
             languageNames : [[name: "Estados Unidos", languageCode: "spa"],[name: "United States", languageCode: "eng"]]]
    )


    List<HashMap> languages = Arrays.asList(
            [name: "Spanish", localName: "Español", code: "spa",
             languageNames : [[name: "Español", languageCodeOfName: "spa"],[name: "Spanish", languageCodeOfName: "eng"]]],

            [name: "Catalan", localName: "Catalán", code: "cat",
             languageNames : [[name: "Catalán", languageCodeOfName: "spa"],[name: "Catalan", languageCodeOfName: "eng"]]],

            [name: "English", localName: "Inglés", code: "eng",
             languageNames : [[name: "Inglés", languageCodeOfName: "spa"],[name: "English", languageCodeOfName: "eng"]]],

            [name: "Italian", localName: "Italiano", code: "ita",
             languageNames : [[name: "Italiano", languageCodeOfName: "spa"],[name: "Italian", languageCodeOfName: "eng"]]],

            [name: "French", localName: "Francés", code: "fra",
             languageNames : [[name: "Francés", languageCodeOfName: "spa"],[name: "French", languageCodeOfName: "eng"]]],

            [name: "German", localName: "Alemán", code: "deu",
             languageNames : [[name: "Alemán", languageCodeOfName: "spa"],[name: "German", languageCodeOfName: "eng"]]],

            [name: "Russian", localName: "Ruso", code: "rus",
             languageNames : [[name: "Ruso", languageCodeOfName: "spa"],[name: "Russian", languageCodeOfName: "eng"]]],

            [name: "Chinese", localName: "Chino", code: "chi",
             languageNames : [[name: "Chino", languageCodeOfName: "spa"],[name: "Chinese", languageCodeOfName: "eng"]]],

            [name: "Portuguese", localName: "Portugués", code: "por",
             languageNames : [[name: "Portugués", languageCodeOfName: "spa"],[name: "Portuguese", languageCodeOfName: "eng"]]],

            [name: "Polish", localName: "Polaco", code: "pol",
             languageNames : [[name: "Polaco", languageCodeOfName: "spa"],[name: "Polish", languageCodeOfName: "eng"]]],

            [name: "Czech", localName: "Checo", code: "cze",
             languageNames : [[name: "Checo", languageCodeOfName: "spa"],[name: "Czech", languageCodeOfName: "eng"]]],

            [name: "Czech", localName: "Checo", code: "cze",
             languageNames : [[name: "Checo", languageCodeOfName: "spa"],[name: "Czech", languageCodeOfName: "eng"]]],

            [name: "Undetermined", localName: "Indeterminado", code: "und",languageNames : []]
    )


    Boolean checkPosterFolderAccess() {
        SettingModel pathOfPostersSetting = settingService.getSettingByName("pathOfPosters")

        String pathOfPosters
        if (pathOfPostersSetting == null)
        {
            log.info "No pathOfPosters setting found on database. Using default value: " + grailsApplication.config.film.defaultPostersFolder
            pathOfPosters = grailsApplication.config.film.defaultPostersFolder
        }
        else
            pathOfPosters = pathOfPostersSetting.value

        File dir = new File (pathOfPosters)
        if ((dir == null) || (!dir.exists()) || (!dir.isDirectory()) || (!dir.canRead()) || (!dir.canWrite()) || (!dir.canExecute()))
        {
            log.error "Error checking posters folder. No match conditions to operate"
            return false
        }
        return true
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    Boolean checkFlagsFolderAccess() {

        SettingModel pathOfFlagsSetting = settingService.getSettingByName("pathOfFlags")
        String pathOfFlags
        if (pathOfFlagsSetting == null)
        {
            log.info "No pathOfFlags setting found on database. Using default value: " + grailsApplication.config.film.defaultFlagsFolder
            pathOfFlags = grailsApplication.config.film.defaultFlagsFolder
        }
        else
            pathOfFlags = pathOfFlagsSetting.value

        File dir = new File (pathOfFlags)
        if ((dir == null) || (!dir.exists()) || (!dir.isDirectory()) || (!dir.canRead()) || (!dir.canWrite()) || (!dir.canExecute()))
        {
            log.error "Error checking flags folder. No match conditions to operate"
            return false
        }
        return true
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************



    String getPostersFolder()
    {
        SettingModel pathOfPostersSetting = settingService.getSettingByName("pathOfPosters")
        String pathOfPosters
        if (pathOfPostersSetting == null)
        {
            log.info "No Posters Folder setting defined on database. Returning default value"
            pathOfPosters = grailsApplication.config.film.defaultPostersFolder
        }
        else
            pathOfPosters = pathOfPostersSetting.value

        File dir = new File (pathOfPosters)
        if ((dir == null) || (!dir.exists()) || (!dir.isDirectory()) || (!dir.canRead()) || (!dir.canWrite()) || (!dir.canExecute()))
        {
            log.error "Error checking posters folder. No match conditions to operate"
            return null
        }
        if (pathOfPosters[pathOfPosters.size()-1] != '/')
            return (new String(pathOfPosters + '/'))
        else
            return pathOfPosters
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    Boolean checkOrCreateSmallPostersFolder() {
        String postersFolder = getPostersFolder()
        if (postersFolder == null)
        {
            return false
        }

        File dir = new File (postersFolder + "small/")
        if (dir == null)
        {
            return false
        }
        else if (!dir.exists()) {
            log.info "Small posters folder doesnt exist. Creating..."
            dir.mkdir();
            return true
        }
        else if ((!dir.isDirectory()) || (!dir.canRead()) || (!dir.canWrite()) || (!dir.canExecute()))
        {
            log.error "Error checking small posters folder. No match conditions to operate"
            return false
        }
        else
            return true
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    String getSmallPostersFolder()
    {
        String postersFolder = getPostersFolder()
        if (postersFolder == null)
            return null

        String smallPostersFolder = postersFolder + "small/"
        File dir = new File (smallPostersFolder)
        if ((dir == null) || (!dir.exists()) || (!dir.isDirectory()) || (!dir.canRead()) || (!dir.canWrite()) || (!dir.canExecute()))
        {
            log.error "Error checking small posters folder. No match conditions to operate"
            return null
        }
        return smallPostersFolder
    }



    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    String getFlagsFolder()
    {
        SettingModel pathOfFlagsSetting = settingService.getSettingByName("pathOfFlags")
        String pathOfFlags

        if (pathOfFlagsSetting == null)
        {
            log.error "No flags folder setting found on database. Using default value"
            pathOfFlags = grailsApplication.config.film.defaultFlagsFolder
        }
        else
            pathOfFlags = pathOfFlagsSetting.value

        File dir = new File (pathOfFlags)
        if ((dir == null) || (!dir.exists()) || (!dir.isDirectory()) || (!dir.canRead()) || (!dir.canWrite()) || (!dir.canExecute()))
        {
            log.error "Error checking posters folder. No match conditions to operate"
            return null
        }
        if (pathOfFlags[pathOfFlags.size()-1] != '/')
            return (new String(pathOfFlags + '/'))
        else
            return pathOfFlags
    }



    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    int getPageSize()
    {
        SettingModel pageSize = settingService.getSettingByName("pageSize")
        if (pageSize == null)
        {
            log.warn "Error getting page size. No setting found on database"
            return 20
        }
        else
        {
            int pageSizeInt
            try
            {
                pageSizeInt = Integer.parseInt(pageSize.value)
            }
            catch (NumberFormatException e)
            {
                log.error "Error parsing to integer page size value. Setting default value"
                pageSizeInt = 20
            }

            return pageSizeInt
        }
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void addDefaultCountries()
    {
        for (HashMap country: countrys)
        {
            CountryModel countryToInsert

            countryToInsert = countryService.getCountryByCountryCode(country.get("countryCode"))
            if (countryToInsert == null)
            {
                countryToInsert = new CountryModel(englishName: country.get("englishName"), localName : country.get("localName"),
                                                    countryCode: country.get("countryCode"))
                for (HashMap languageName : country.get("languageNames"))
                {
                    CountryNameModel countryNameModel = new CountryNameModel(name: languageName.get("name"),
                            languageCode: languageName.get("languageCode"))
                    countryToInsert.countryNamesLanguage.add(countryNameModel)
                }
            }
            countryService.getUpdateAndSaveDomainInstance(countryToInsert)
        }
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void addDefaultLanguages()
    {
        for (HashMap language: languages)
        {
            LanguageModel languageToInsert

            languageToInsert = languageService.getLanguageByCode(language.get("code"))
            if (languageToInsert == null)
            {
                languageToInsert = new LanguageModel(name: language.get("name"), localName: language.get("localName"),
                        code: language.get("code"))

                List<HashMap> languageNames = language.get("languageNames")

                for (HashMap languageName : languageNames)
                {
                    LanguageNameModel languageNameModel = new LanguageNameModel(name: languageName.get("name"),
                            languageCodeOfName: languageName.get("languageCodeOfName"))
                    languageToInsert.languageNames.add(languageNameModel)
                }
            }
            languageService.getUpdateAndSaveDomainInstance(languageToInsert)
        }
    }

}
