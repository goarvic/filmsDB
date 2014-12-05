package films.database

import films.Country
import films.Model.CountryModel
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class CountryService {


    CountryModel bindFromDomainToModel(Country countryDomain)
    {
        if (countryDomain == null)
        {
            log.error "Error binding null object Country"
            return null
        }
        CountryModel countryModel = new CountryModel()
        DataBindingUtils.bindObjectToInstance(countryModel,countryDomain)
        return countryModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    Country getUpdateAndSaveDomainInstance (CountryModel countryModel)
    {
        if (countryModel == null)
        {
            log.error "Error getting domain instance from null object"
            return null
        }
        Country countryDomain

        if (countryModel.id >= 0)
        {
            countryDomain = Country.findById(countryModel.id)
            if (countryDomain == null)
            {
                log.error "Error getting domain instance not found on database"
                return null
            }
        }
        else
            countryDomain = new Country()

        DataBindingUtils.bindObjectToInstance(countryDomain,countryModel)

        if (countryDomain.save(flush : true) == null)
        {
            log.error "Error saving domain instance on database: " + countryDomain.errors
            return null
        }
        else
            return countryDomain
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************


    List<CountryModel> getAllCountries ()
    {
        List<Country> countriesDomain = Country.list(sort: "spanishName", order: "asc")
        List<CountryModel> countriesModel = new ArrayList<CountryModel>()
        for (Country countryDomain : countriesDomain)
        {
            CountryModel countryModel = bindFromDomainToModel(countryDomain)
            countriesModel.add(countryModel)
        }
        return countriesModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    CountryModel getCountryByCountryCode (String countryCode)
    {
        if (countryCode == null)
            return null
        Country countryDomain = Country.findByCountryCode(countryCode)
        CountryModel countryModel = bindFromDomainToModel(countryDomain)
        return countryModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    CountryModel getCountryBySpanishName (String spanishName)
    {
        if (spanishName == null)
            return null
        Country countryDomain = Country.findBySpanishName(spanishName)
        CountryModel countryModel = bindFromDomainToModel(countryDomain)
        return countryModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    int getNumberOfCountriesSaved ()
    {
        int number = Country.count()
        return number
    }
}
