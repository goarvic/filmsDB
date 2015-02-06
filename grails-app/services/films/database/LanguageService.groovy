package films.database

import films.Language
import films.LanguageName
import films.Model.LanguageModel
import films.Model.LanguageNameModel
import grails.plugin.cache.Cacheable
import org.codehaus.groovy.grails.web.binding.DataBindingUtils;
import grails.transaction.Transactional

@Transactional
class LanguageService {

    LanguageNameService languageNameService

    LanguageModel bindFromDomainToModel (Language languageDomain)
    {
        if (languageDomain == null)
        {
            log.error "Error binding null domain object"
            return null
        }
        LanguageModel languageModel = new LanguageModel()
        DataBindingUtils.bindObjectToInstance(languageModel, languageDomain)

        for (LanguageName languageName : languageDomain.languageNames)
        {
            LanguageNameModel languageNameModel = languageNameService.bindLanguageNameToModel(languageName)
            languageModel.languageNames.add(languageNameModel)
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

        DataBindingUtils.bindObjectToInstance(languageDomain,languageModel)

        if  (languageDomain.languageNames != null)
            languageDomain.languageNames.removeAll(languageDomain.languageNames)
        else
            languageDomain.languageNames = new ArrayList<LanguageName>()

        for (LanguageNameModel languageNameModel : languageModel.languageNames)
        {
            LanguageName languageNameDomain = languageNameService.getAndUpdateDomainInstance(languageNameModel)
            languageNameDomain.language = languageDomain
            languageDomain.languageNames.add(languageNameDomain)
        }

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

    @Cacheable('listLanguages')
    List<LanguageModel> getAllLanguages() {
        List<Language> languagesDomain = Language.list(sort:"localName", order: "asc")

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
