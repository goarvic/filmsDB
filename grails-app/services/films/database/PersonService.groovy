package films.database

import films.Model.PersonModel
import films.Person
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class PersonService {

    Person getAndUpdatePersonDomainInstance(PersonModel personModel)
    {
        if (personModel==null)
        {
            log.error "Error saving null PersonModel Model"
            return null
        }

        Person personDomain
        if (personModel.id == -1)
            personDomain = new Person()
        else
            personDomain = Person.findById(personModel.id)

        DataBindingUtils.bindObjectToInstance(personDomain,personModel)

        if (personDomain.save(flush:true) == null)
        {
            log.error "Error saving PersonModel Instance: " + personDomain.errors
            return null
        }
        return personDomain
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    PersonModel bindPersonToModel(Person personDomain)
    {
        if (personDomain == null)
        {
            log.error "Error binding null object PersonModel"
            return null
        }
        PersonModel personModel = new PersonModel()
        DataBindingUtils.bindObjectToInstance(personModel,personDomain)

        return personModel
    }

}
