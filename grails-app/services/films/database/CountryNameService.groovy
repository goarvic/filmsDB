package films.database

import films.CountryName
import films.Model.CountryNameModel
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class CountryNameService {


    CountryNameModel bindCountryNameToModel (CountryName countryNameDomain)
    {
        if (countryNameDomain == null)
        {
            log.error "Error binding null countryNameDomain domain instance"
            return null
        }

        CountryNameModel countryNameModel = new CountryNameModel()
        DataBindingUtils.bindObjectToInstance(countryNameModel, countryNameDomain)

        return countryNameModel
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    CountryName getAndUpdateDomainInstance (CountryNameModel countryNameModel)
    {
        if (countryNameModel == null)
        {
            log.error "Error getting Domain instance from null countryNameModel"
            return null
        }

        CountryName countryNameDomain

        if (countryNameModel.id >= 0)
        {
            countryNameDomain = CountryName.findById(countryNameModel.id)
            if (countryNameDomain == null)
            {
                log.error "Error retrieving domain instance on database"
                return null
            }
        }
        else
            countryNameDomain = new CountryName()

        DataBindingUtils.bindObjectToInstance(countryNameDomain,countryNameModel)

        return countryNameDomain
    }



}
