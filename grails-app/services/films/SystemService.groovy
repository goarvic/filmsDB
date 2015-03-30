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
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class SystemService {

    SettingService settingService
    LanguageService languageService
    CountryService countryService


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

        SettingModel pathOfPosters = settingService.getSettingByName("pathOfPosters")
        if (pathOfPosters == null)
        {
            log.error "Error checking posters folder. No setting found on database"
            return false
        }
        File dir = new File (pathOfPosters.value)
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

        SettingModel pathOfFlags = settingService.getSettingByName("pathOfFlags")
        if (pathOfFlags == null)
        {
            log.error "Error checking flags folder. No setting found on database"
            return false
        }
        File dir = new File (pathOfFlags.value)
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
        SettingModel pathOfPosters = settingService.getSettingByName("pathOfPosters")
        if (pathOfPosters == null)
        {
            log.error "Error getting posters folder. No setting found on database"
            return null
        }
        File dir = new File (pathOfPosters.value)
        if ((dir == null) || (!dir.exists()) || (!dir.isDirectory()) || (!dir.canRead()) || (!dir.canWrite()) || (!dir.canExecute()))
        {
            log.error "Error checking posters folder. No match conditions to operate"
            return null
        }
        if (pathOfPosters.value[pathOfPosters.value.size()-1] != '/')
            return (new String(pathOfPosters.value + '/'))
        else
            return pathOfPosters.value
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
        SettingModel pathOfFlags = settingService.getSettingByName("pathOfFlags")
        if (pathOfFlags == null)
        {
            log.error "Error getting flags folder. No setting found on database"
            return null
        }
        File dir = new File (pathOfFlags.value)
        if ((dir == null) || (!dir.exists()) || (!dir.isDirectory()) || (!dir.canRead()) || (!dir.canWrite()) || (!dir.canExecute()))
        {
            log.error "Error checking posters folder. No match conditions to operate"
            return null
        }
        if (pathOfFlags.value[pathOfFlags.value.size()-1] != '/')
            return (new String(pathOfFlags.value + '/'))
        else
            return pathOfFlags.value
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

    //Temporal function to port old data domain to new domain
    void convertFilmsToNewLanguageDomain()
    {
        List<Film> allFilms = Film.list()

        for (Film film : allFilms)
        {
            if ((film.filmDetailsLanguage == null)||(film.filmDetailsLanguage.size()==0))
            {
                film.filmDetailsLanguage = new ArrayList<FilmDetailsLanguage>()
                FilmDetailsLanguage filmDetailsLanguage = new FilmDetailsLanguage()

                DataBindingUtils.bindObjectToInstance(filmDetailsLanguage,film)
                filmDetailsLanguage.language = languageService.getUpdateAndSaveDomainInstance(languageService.getLanguageByCode("spa"))

                int extensionPos = filmDetailsLanguage.posterName.indexOf(".jpg")
                filmDetailsLanguage.posterName = new String(filmDetailsLanguage.posterName[0..extensionPos-1] + " " +
                                                            filmDetailsLanguage.language.code +
                                                            filmDetailsLanguage.posterName[extensionPos .. filmDetailsLanguage.posterName.size()-1])

                filmDetailsLanguage.film = film
                film.filmDetailsLanguage.add(filmDetailsLanguage)
                film.save(flush:true, failOnError: true)


                String postersFolder = getPostersFolder()
                String smallPostersFolder = getSmallPostersFolder()

                // File (or directory) with old name
                File poster = new File(postersFolder + film.posterName);
                File smallPoster = new File(smallPostersFolder + film.posterName);

                if (poster.exists() && smallPoster.exists())
                {
                    File posterEsp = new File(postersFolder + filmDetailsLanguage.posterName);
                    File smallPosterEsp = new File(smallPostersFolder + filmDetailsLanguage.posterName);
                    boolean successPoster = poster.renameTo(posterEsp);
                    boolean successSmallPoster = smallPoster.renameTo(smallPosterEsp);

                    if (!successPoster)
                        log.error "Error renaming poster name from " +  postersFolder + film.posterName + " to " + postersFolder + filmDetailsLanguage.posterName

                    if (!successSmallPoster)
                        log.error "Error renaming poster name from " +  smallPostersFolder + film.posterName + " to " + smallPostersFolder + filmDetailsLanguage.posterName
                }
            }
        }
    }



    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void convertGenresToNewDomainModel()
    {
        List<Genre> allGenres = Genre.list()

        Language spanishLanguage = Language.findByCode("spa")
        Language englishLanguage = Language.findByCode("eng")


        for (Genre genre : allGenres)
        {
            if (genre.genreNameLanguage == null ||genre.genreNameLanguage.size() == 0)
            {
                genre.genreNameLanguage = new ArrayList<GenreNameLanguage>()
                GenreNameLanguage genreNameLanguageSpa = new GenreNameLanguage()
                genreNameLanguageSpa.name = genre.localName
                genreNameLanguageSpa.language = spanishLanguage
                genreNameLanguageSpa.genre = genre
                GenreNameLanguage genreNameLanguageEng = new GenreNameLanguage()
                genreNameLanguageEng.language = englishLanguage
                genreNameLanguageEng.genre = genre
                switch ( genre.localName ) {
                    case "Ciencia ficción":
                        genreNameLanguageEng.name = "Sci-Fi"
                        break;
                    case "Aventuras":
                        genreNameLanguageEng.name = "Adventure"
                        break;
                    case "Bélico":
                        genreNameLanguageEng.name = "War"
                        break;
                    case "Acción":
                        genreNameLanguageEng.name = "Action"
                        break;
                    case "Fantástico":
                        genreNameLanguageEng.name = "Fantasy"
                        break;
                    case "Romance":
                        genreNameLanguageEng.name = "Romance"
                        break;
                    case "Thriller":
                        genreNameLanguageEng.name = "Thriller"
                        break;
                    case "Terror":
                        genreNameLanguageEng.name = "Horror"
                        break;
                    case "Intriga":
                        genreNameLanguageEng.name = "Mystery"
                        break;
                    case "Drama":
                        genreNameLanguageEng.name = "Drama"
                        break;
                    case "Comedia":
                        genreNameLanguageEng.name = "Comedy"
                        break;
                    case "Western":
                        genreNameLanguageEng.name = "Western"
                        break;
                    case "Cine negro":
                        genreNameLanguageEng.name = "Film-Noir"
                        break;
                    case "Animación":
                        genreNameLanguageEng.name = "Animation"
                        break;
                    case "Infantil":
                        genreNameLanguageEng.name = "Kids"
                        break;
                    default:
                        genreNameLanguageEng.name = "Pene"
                }

                genre.genreNameLanguage.add(genreNameLanguageSpa)
                genre.genreNameLanguage.add(genreNameLanguageEng)

                try
                {
                    genre.save(flush: true, failOnError: true)
                }
                catch(Exception e)
                {
                    log.error "Error updating genre " + genre.localName + " to new language domain: " + genre.errors
                }
            }
        }
    }



    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void fixSpecialCharactersOnSynopsis()
    {
        List <FilmDetailsLanguage> filmDetailsLanguageList = FilmDetailsLanguage.list()
        for (FilmDetailsLanguage filmDetailsLanguage : filmDetailsLanguageList)
        {
            if (filmDetailsLanguage.synopsis.indexOf("&quot;") != -1)
            {
                log.info "Fixin quot on synopsis on film: " + filmDetailsLanguage.localName
            }

            filmDetailsLanguage.synopsis = filmDetailsLanguage.synopsis.replace("&quot;", "\"")
            filmDetailsLanguage.save(flush:true)
        }
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void fixSpecialCharactersOnPersons()
    {
        List <Person> personList = Person.list()
        for (Person person : personList)
        {
            if (person.name.indexOf("&quot;") != -1)
            {
                log.info "Fixin quot on name of person: " + person.name
            }

            person.name = person.name.replace("&quot;", "\"")
            person.save(flush:true)
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
