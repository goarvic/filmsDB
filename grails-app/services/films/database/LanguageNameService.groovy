package films.database

import films.GenreNameLanguage
import films.LanguageName
import films.Model.GenreNameLanguageModel
import films.Model.LanguageNameModel
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class LanguageNameService {


    LanguageNameModel bindLanguageNameToModel (LanguageName languageNameDomain)
    {
        if (languageNameDomain == null)
        {
            log.error "Error binding null genreNameLanguage domain instance"
            return null
        }

        LanguageNameModel languageNameModel = new LanguageNameModel()
        DataBindingUtils.bindObjectToInstance(languageNameModel, languageNameDomain)

        return languageNameModel
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    LanguageName getAndUpdateDomainInstance (LanguageNameModel languageNameModel)
    {
        if (languageNameModel == null)
        {
            log.error "Error getting Domain instance from null languageNameModel"
            return null
        }

        LanguageName languageNameDomain

        if (languageNameModel.id >= 0)
        {
            languageNameDomain = LanguageName.findById(languageNameModel.id)
            if (languageNameDomain == null)
            {
                log.error "Error retrieving domain instance on database"
                return null
            }
        }
        else
            languageNameDomain = new LanguageName()

        DataBindingUtils.bindObjectToInstance(languageNameDomain,languageNameModel)

        return languageNameDomain
    }



}
