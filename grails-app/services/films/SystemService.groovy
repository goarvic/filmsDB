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
                filmDetailsLanguage.language = languageService.getLanguageByCode("spa")
                filmDetailsLanguage.film = film
                film.filmDetailsLanguage.add(filmDetailsLanguage)
                film.save(flush:true, failOnError: true)
            }
        }
    }



}
