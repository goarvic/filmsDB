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
}
