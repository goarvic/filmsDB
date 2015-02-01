package films.database

import films.GenreNameLanguage
import films.Model.GenreModel
import films.Model.GenreNameLanguageModel
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class GenreNameService {

    LanguageService languageService

    GenreNameLanguageModel bindGenreNameLanguageToModel (GenreNameLanguage genreNameLanguageDomain)
    {
        if (genreNameLanguageDomain == null)
        {
            log.error "Error binding null genreNameLanguage domain instance"
            return null
        }

        GenreNameLanguageModel genreNameLanguageModel = new GenreNameLanguageModel()
        DataBindingUtils.bindObjectToInstance(genreNameLanguageModel, genreNameLanguageDomain)
        genreNameLanguageModel.language = languageService.bindFromDomainToModel(genreNameLanguageDomain.language)

        return genreNameLanguageModel
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    GenreNameLanguage getAndUpdateDomainInstance (GenreNameLanguageModel genreNameLanguageModel)
    {
        if (genreNameLanguageModel == null)
        {
            log.error "Error getting Domain instance from null genreNameLanguageModel"
            return null
        }

        GenreNameLanguage genreNameLanguageDomain

        if (genreNameLanguageModel.id >= 0)
        {
            genreNameLanguageDomain = GenreNameLanguage.findById(genreNameLanguageModel.id)
            if (genreNameLanguageDomain == null)
            {
                log.error "Error retrieving domain instance on database"
                return null
            }
        }
        else
            genreNameLanguageDomain = new GenreNameLanguage()

        DataBindingUtils.bindObjectToInstance(genreNameLanguageDomain,genreNameLanguageModel)
        genreNameLanguageDomain.language = languageService.getUpdateAndSaveDomainInstance(genreNameLanguageModel.language)

        return genreNameLanguageDomain
    }



}
