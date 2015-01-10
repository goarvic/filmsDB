package films.database

import films.Genre
import films.Model.GenreModel
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class GenreService {


    GenreModel bindFromDomainToModel(Genre genreDomain) {
        if (genreDomain == null)
        {
            log.error "Error binding null Genre Object"
            return null
        }
        GenreModel genreModel = new GenreModel()
        DataBindingUtils.bindObjectToInstance(genreModel,genreDomain)
        return genreModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    GenreModel getGenreByEnglishName(String name)
    {
        Genre genreDomain = Genre.findByEnglishName(name)
        return bindFromDomainToModel(genreDomain)
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

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

}
