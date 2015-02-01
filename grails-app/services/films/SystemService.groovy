package films

import films.Model.SettingModel
import films.database.LanguageService
import films.database.SettingService
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class SystemService {

    SettingService settingService
    LanguageService languageService

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
                //assert genreNameLanguageSpa.validate()
                //assert genreNameLanguageEng.validate()

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








}
