package films.database

import films.FilmDetailsLanguage
import films.Model.FilmDetailsLanguageModel
import grails.transaction.Transactional
import org.apache.commons.codec.language.bm.Lang
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class FilmDetailsLanguageService {

    LanguageService languageService

    FilmDetailsLanguageModel bindFilmDetailsLanguageDomainToModel (FilmDetailsLanguage filmDetailsLanguageDomain)
    {
        if (filmDetailsLanguageDomain == null)
        {
            log.error "Error binding null filmDetailsLanguage domain instance"
            return null
        }

        FilmDetailsLanguageModel filmDetailsLanguageModel = new FilmDetailsLanguageModel()
        DataBindingUtils.bindObjectToInstance(filmDetailsLanguageModel, filmDetailsLanguageDomain)
        filmDetailsLanguageModel.language = languageService.bindFromDomainToModel(filmDetailsLanguageDomain.language)

        return filmDetailsLanguageModel
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    FilmDetailsLanguage getAndUpdateDomainInstance (FilmDetailsLanguageModel filmDetailsLanguageModel)
    {
        if (filmDetailsLanguageModel == null)
        {
            log.error "Error getting Domain instance from null filmDetailsLanguageModel"
            return null
        }

        FilmDetailsLanguage filmDetailsLanguageDomain

        if (filmDetailsLanguageModel.id >= 0)
        {
            filmDetailsLanguageDomain = FilmDetailsLanguage.findById(filmDetailsLanguageModel.id)
            if (filmDetailsLanguageDomain == null)
            {
                log.error "Error retrieving domain instance on database"
                return null
            }
        }
        else
            filmDetailsLanguageDomain = new FilmDetailsLanguage()

        DataBindingUtils.bindObjectToInstance(filmDetailsLanguageDomain,filmDetailsLanguageModel)
        filmDetailsLanguageDomain.language = languageService.getUpdateAndSaveDomainInstance(filmDetailsLanguageModel.language)

        return filmDetailsLanguageDomain
    }
}
