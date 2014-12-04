package films.database

import grails.transaction.Transactional

@Transactional
class PersonService {

    films.Person getAndUpdatePersonDomainInstance(films.Model.Person personModel)
    {
        if (personModel==null)
        {
            log.error "Error saving null Person Model"
            return null
        }

        films.Person personDomain
        if (personModel.id == -1)
            personDomain = new films.Person()
        else
            personDomain = films.Person.findById(personModel.id)

        personModel.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class")&&!propertyName.equals("id"))
                personDomain.setProperty(propertyName, personModel.getProperty(propertyName))
        }

        if (personDomain.save(flush:true) == null)
        {
            log.error "Error saving Person Instance: " + personDomain.errors
            return null
        }
        return personDomain
    }

    films.Model.Person bindPersonToModel(films.Person personDomain)
    {
        if (personDomain == null)
        {
            log.error "Error binding null object Person"
            return null
        }
        films.Model.Person personModel = new films.Model.Person()
        personModel.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class"))
                personModel.setProperty(propertyName, personDomain.getProperty(propertyName))
        }
        return personModel
    }

}
