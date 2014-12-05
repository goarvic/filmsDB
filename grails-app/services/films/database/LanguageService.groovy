package films.database

import films.Language
import films.Model.LanguageModel
import grails.transaction.Transactional

@Transactional
class LanguageService {


    LanguageModel bindFromDomainToModel (Language languageDomain)
    {
        if (languageDomain == null)
        {
            log.error "Error binding null domain object"
            return null
        }
        LanguageModel languageModel
        languageModel.properties.each{propertyName, propertyValue->
            languageModel.setProperty(languageDomain.getProperty(propertyName))
        }
        return languageModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************



    Language getUpdateAndSaveDomainInstance (LanguageModel languageModel)
    {
        if (languageModel == null)
        {
            log.error "Error getting domain instance from null model object"
            return null
        }

        Language languageDomain
        if (languageModel.id >= 0)
        {
            languageDomain = Language.findById(languageModel.id)
            if (languageDomain == null)
            {
                log.error "Error retrieving domain object from database"
                return null
            }
        }
        else
            languageDomain = new Language()


        languageModel.properties.each{propertyName, propertyValue->
            languageDomain.setProperty(languageModel.getProperty(propertyName))
        }

        if (languageDomain.save(flush: true) == null)
        {
            log.error "Error saving domain instance on database"
            return null
        }
        else
            return languageModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    List<LanguageModel> getAllLanguages() {
        List<Language> languagesDomain = Language.list(sort:"spanishName", order: "asc")

        def languagesModel = new ArrayList<LanguageModel>();
        for(Language languageDomain : languagesDomain)
        {
            LanguageModel languageModel = bindFromDomainToModel(languageDomain)
            languagesModel.add(languageModel)
        }
        return languagesModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    LanguageModel getLanguageByCode(String code)
    {
        Language languageDomain = Language.findByCode(code)
        LanguageModel languageModel
        if (languageDomain != null)
        {
            languageModel = bindFromDomainToModel(languageDomain)
        }
        return languageModel
    }




}
