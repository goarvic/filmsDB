package films.database

import films.Genre
import films.GenreNameLanguage
import films.Language
import films.Model.GenreModel
import films.Model.GenreNameLanguageModel
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class GenreService {


    GenreNameService genreNameService


    GenreModel bindFromDomainToModel(Genre genreDomain) {
        if (genreDomain == null)
        {
            log.error "Error binding null Genre Object"
            return null
        }
        GenreModel genreModel = new GenreModel()
        DataBindingUtils.bindObjectToInstance(genreModel,genreDomain)
        for (GenreNameLanguage genreNameLanguageDomain : genreDomain.genreNameLanguage)
        {
            GenreNameLanguageModel genreNameLanguageModel = new GenreNameLanguageModel()
            DataBindingUtils.bindObjectToInstance(genreNameLanguageModel,genreNameLanguageDomain)
            genreModel.genreNameLanguage.add(genreNameLanguageModel)
        }

        return genreModel
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    GenreModel getGenreByNameAndLanguageCode(String name,String languageCode)
    {
        if (languageCode== null || name == null)
        {
            log.warn "Imposible find genre from null parammeter languageCode or name"
            return null
        }

        Language language = Language.findByCode(languageCode)
        if (language == null)
        {
            log.warn "No language has found with languageCode " + languageCode
            return null
        }

        GenreNameLanguage genreNameLanguage = GenreNameLanguage.findByLanguageAndName(language, name)
        if (genreNameLanguage == null)
        {
            log.info "No genre found for name " + name + " and languageCode " + languageCode
            return null
        }

        Genre genre = genreNameLanguage.genre
        return bindFromDomainToModel(genre)
    }



    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    @Deprecated
    GenreModel getGenreByEnglishName(String name)
    {
        Genre genreDomain = Genre.findByEnglishName(name)
        return bindFromDomainToModel(genreDomain)
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    @Deprecated
    GenreModel getGenreByLocalName(String name)
    {
        Genre genreDomain = Genre.findByLocalName(name)
        if (genreDomain == null)
            return null
        else
            return bindFromDomainToModel(genreDomain)
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    @CacheEvict(value='listGenres', allEntries=true)
    Genre getUpdateAndSavedDomainInstance(GenreModel genreModel)
    {
        if (genreModel == null)
        {
            log.error "Error getting domain instance of null object GenreModel"
            return null
        }

        Genre genreDomain
        if (genreModel.id >= 0)
        {
            genreDomain = Genre.findById(genreModel.id)
            if (genreDomain == null)
            {
                log.error "Error retrieving genreDomain from Database"
                return null
            }
        }
        else
            genreDomain = new Genre()

        DataBindingUtils.bindObjectToInstance(genreDomain,genreModel)
        if  (genreDomain.genreNameLanguage != null)
            genreDomain.genreNameLanguage.removeAll(genreDomain.genreNameLanguage)
        else
            genreDomain.genreNameLanguage = new ArrayList<GenreNameLanguage>()

        for (GenreNameLanguageModel genreNameLanguageModel : genreModel.genreNameLanguage)
        {
            GenreNameLanguage genreNameLanguageDomain = genreNameService.getAndUpdateDomainInstance(genreNameLanguageModel)
            genreNameLanguageDomain.genre = genreDomain
            genreDomain.genreNameLanguage.add(genreNameLanguageDomain)
        }

        if (genreDomain.save(flush:true) == null)
        {
            log.error "Error saving Genre Domain instance: " + genreDomain.errors
            return null
        }
        else
        {
            return genreDomain
        }
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    Genre getDomainInstance(GenreModel genreModel)
    {
        if (genreModel == null)
        {
            log.error "Error getting domain instance of null object GenreModel"
            return null
        }

        Genre genreDomain
        if (genreModel.id >= 0)
        {
            genreDomain = Genre.findById(genreModel.id)
            if (genreDomain == null)
            {
                log.error "Error retrieving genreDomain from Database"
                return null
            }
        }
        else
            genreDomain = new Genre()

        DataBindingUtils.bindObjectToInstance(genreDomain,genreModel)


        if  (genreDomain.genreNameLanguage != null)
            genreDomain.genreNameLanguage.removeAll(genreDomain.genreNameLanguage)
        else
            genreDomain.genreNameLanguage = new ArrayList<GenreNameLanguage>()

        for (GenreNameLanguageModel genreNameLanguageModel : genreModel.genreNameLanguage)
        {
            GenreNameLanguage genreNameLanguageDomain = genreNameService.getAndUpdateDomainInstance(genreNameLanguageModel)
            genreNameLanguageDomain.genre = genreDomain
            genreDomain.genreNameLanguage.add(genreNameLanguageDomain)
        }


        return genreDomain
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************



    @Cacheable('listGenres')
    List<GenreModel> getAllGenres()
    {
        List<GenreModel> genresToReturn = new ArrayList<GenreModel>()
        List<Genre> genres = Genre.list(sort:"localName" , order:"desc")
        if (genres == null)
        {
            log.warn "No genres saved"
            return genresToReturn
        }
        for (Genre genre : genres)
        {
            genresToReturn.add(bindFromDomainToModel(genre))
        }

        return genresToReturn
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    @Cacheable('listGenresTranslated')
    List<GenreNameLanguageModel> getAllGenresTranslated(Locale locale)
    {
        if (locale == null)
        {
            log.error "Error getting genres translated from null locale"
            return null
        }
        Language language = Language.findByCode(locale.getISO3Language())

        if (language == null)
        {
            log.info "No language found by code " + locale.getISO3Language()
            language = Language.findByCode("eng")
            if (language == null)
            {
                log.error "No english language on database"
                return null
            }
        }

        List<GenreNameLanguageModel> genresToReturn = new ArrayList<GenreNameLanguageModel>()
        List<GenreNameLanguage> genresLanguage = GenreNameLanguage.findAllByLanguage(language, [sort:"name", order: 'desc'])

        if (genresLanguage == null)
        {
            log.warn "No genresLanguage found"
            return genresToReturn
        }
        for (GenreNameLanguage genreNameLanguage : genresLanguage)
        {
            genresToReturn.add(genreNameService.bindGenreNameLanguageToModel(genreNameLanguage))
        }

        return genresToReturn
    }
}
