package films.database

import films.Language
import films.Model.SettingModel
import films.Setting
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class SettingService {

    SettingModel getSettingByName(String settingName) {

        SettingModel settingModel = new SettingModel()
        Setting settingDomain = Setting.findBySettingName(settingName)
        if (settingName == null || settingDomain == null)
            return null

        DataBindingUtils.bindObjectToInstance(settingModel, settingDomain)
        return settingModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    Setting getSaveAndUpdateDomainInstance(SettingModel settingModel)
    {
        if (settingModel == null)
        {
            log.error "Error saving null SettingModel Instance"
            return null
        }

        Setting settingDomain
        if (settingModel.id < 0)
            settingDomain = new Setting()
        else
        {
            settingDomain = Setting.findById(settingModel.id)
            if (settingDomain == null)
            {
                log.error "Error retrieving setting from database"
                return null
            }
        }
        DataBindingUtils.bindObjectToInstance(settingDomain, settingModel)
        if (settingDomain.save(flush:true) == null)
        {
            log.error "Error saving setting on database: " + settingDomain.errors
            return null
        }
        return settingDomain
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    int getNumberOfSettingsSaved()
    {
        int number = Setting.count()
        return number
    }
}
