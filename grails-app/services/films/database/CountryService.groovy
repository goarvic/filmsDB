package films.database

import films.Country
import films.CountryName
import films.Model.CountryModel
import films.Model.CountryNameModel
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class CountryService {

    CountryNameService countryNameService

    CountryModel bindFromDomainToModel(Country countryDomain)
    {
        if (countryDomain == null)
        {
            log.error "Error binding null object Country"
            return null
        }
        CountryModel countryModel = new CountryModel()
        DataBindingUtils.bindObjectToInstance(countryModel,countryDomain)

        countryModel.countryNamesLanguage = new ArrayList<CountryNameModel>()
        for (CountryName countryName : countryDomain.countryNamesLanguage)
        {
            CountryNameModel countryNameModel = new CountryNameModel()
            DataBindingUtils.bindObjectToInstance(countryNameModel,countryName)
            countryModel.countryNamesLanguage.add(countryNameModel)
        }


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
        if  (countryDomain.countryNamesLanguage != null)
            countryDomain.countryNamesLanguage.removeAll(countryDomain.countryNamesLanguage)
        else
            countryDomain.countryNamesLanguage = new ArrayList<CountryName>()

        for (CountryNameModel countryNameModel : countryModel.countryNamesLanguage)
        {
            CountryName countryNameDomain = countryNameService.getAndUpdateDomainInstance(countryNameModel)
            countryNameDomain.country = countryDomain
            countryDomain.countryNamesLanguage.add(countryNameDomain)
        }


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
        List<Country> countriesDomain = Country.list(sort: "localName", order: "asc")
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
        if (countryDomain == null)
            return null
        CountryModel countryModel = bindFromDomainToModel(countryDomain)
        return countryModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    CountryModel getCountryByLocalName (String localName, String languageCode)
    {
        if (localName == null || languageCode == null)
            return null

        CountryName countryNameDomain = CountryName.findByLanguageCodeAndName(languageCode, localName)
        if (countryNameDomain == null)
            return null

        Country countryDomain = countryNameDomain.country
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
