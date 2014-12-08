package films

import films.Model.SettingModel
import films.database.SettingService
import grails.transaction.Transactional

@Transactional
class SystemService {

    SettingService settingService

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
}
