package films.database

import films.Language
import films.Model.LanguageModel
import grails.transaction.Transactional

@Transactional
class LanguageService {

    List<LanguageModel> getAllLanguages() {
        List<Language> languages = Language.list(sort:"spanishName", order: "asc")

        def languagesModel = new ArrayList<LanguageModel>();
        for(Language language : languages)
        {
            LanguageModel languageModel = new LanguageModel()
            languageModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class"))
                    languageModel.setProperty(propertyName, language.getProperty(propertyName))

            }
            languagesModel.add(languageModel)
        }
        return languagesModel
    }


    LanguageModel getLanguageByCode(String code)
    {
        Language language = Language.findByCode(code)
        LanguageModel languageModel = null
        if (language != null)
        {
            languageModel = new LanguageModel()
            languageModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class"))
                    languageModel.setProperty(propertyName, language.getProperty(propertyName))
            }
        }
        return languageModel
    }


    def insertLanguageInDataBase(LanguageModel language)
    {
        if (language != null)
        {
            Language languageToSave = new Language()
            language.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class"))
                    languageToSave.setProperty(propertyName, language.getProperty(propertyName))
            }

            if (languageToSave.save(flush : true) == null)
            {
                log.error "Error saving new Language: " + languageToSave.errors
                return -1
            }
            return 0
        }
        else
            return -2
    }


}
