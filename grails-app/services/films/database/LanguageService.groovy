package films.database

import films.Language
import films.Model.LanguageModel
import org.codehaus.groovy.grails.web.binding.DataBindingUtils;
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
        LanguageModel languageModel = new LanguageModel()
        DataBindingUtils.bindObjectToInstance(languageModel, languageDomain)
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

        DataBindingUtils.bindObjectToInstance(languageDomain,languageModel)

        if (languageDomain.save(flush: true) == null)
        {
            log.error "Error saving domain instance on database"
            return null
        }
        else
            return languageDomain
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
        LanguageModel languageModel = null
        if (languageDomain != null)
        {
            languageModel = bindFromDomainToModel(languageDomain)
        }
        return languageModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    int getNumberOfLanguageSaved()
    {
        int number = Language.count()
        return number
    }


}
