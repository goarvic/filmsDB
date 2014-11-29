package films.database

import films.Country
import films.Model.CountryModel
import grails.transaction.Transactional

@Transactional
class CountryService {


    List<CountryModel> getAllCountriesModel () {
        List<Country> countries = Country.list(sort: "spanishName", order: "asc")

        List<CountryModel> countriesModel = new ArrayList<CountryModel>()
        for (Country country : countries)
        {
            CountryModel countryModel = new CountryModel()
            countryModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class"))
                    countryModel.setProperty(propertyName, country.getProperty(propertyName))
            }
            countriesModel.add(countryModel)
        }
        return countriesModel
    }



    CountryModel getCountryByCountryCode (String countryCode)
    {
        Country country = Country.findByCountryCode(countryCode)
        CountryModel countryToReturn = null
        if (country != null)
        {
            countryToReturn = new CountryModel()
            countryToReturn.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class"))
                    countryToReturn.setProperty(propertyName, country.getProperty(propertyName))
            }
        }
        return countryToReturn
    }

    CountryModel getCountryBySpanishName (String spanishName)
    {
        Country country = Country.findBySpanishName(spanishName)
        CountryModel countryToReturn = null
        if (country != null)
        {
            countryToReturn = new CountryModel()
            countryToReturn.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class"))
                    countryToReturn.setProperty(propertyName, country.getProperty(propertyName))
            }
        }
        return countryToReturn
    }




}
