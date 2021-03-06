package films.database

import films.Model.PersonModel
import films.Person
import grails.transaction.Transactional
import org.apache.commons.lang3.StringUtils
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class PersonService {


    Person getPersonDomainInstance(PersonModel personModel)
    {
        if (personModel==null)
        {
            log.error "Error saving null PersonModel Model"
            return null
        }

        Person personDomain
        if (personModel.id != -1)
            personDomain = Person.findById(personModel.id)
        else
        {
            personDomain = Person.findByName(personModel.name)
            if (personDomain == null)
                personDomain = new Person()
        }
        DataBindingUtils.bindObjectToInstance(personDomain,personModel)

        return personDomain
    }


    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************



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

        personDomain.save(flush:true, failOnError: true)
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
        personModel.normalizedName = StringUtils.stripAccents(personModel.name)

        return personModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    PersonModel getPersonByName (String name)
    {
        Person personDomain = Person.findByName(name)
        if (personDomain == null)
            return null

        return bindPersonToModel(personDomain)
    }



}
