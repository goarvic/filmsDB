import films.Country
import films.Language

class BootStrap {

    def init = { servletContext ->

        //Vamos a añadir algunos idiomas si es que no están ya


        if (Language.count() == 0)
        {
            Language languageToSave = new Language(name: "Spanish", spanishName: "Español", code: "spa")
            languageToSave.save(flush:true)

            languageToSave = new Language(name: "English", spanishName: "Inglés", code: "eng")
            languageToSave.save(flush:true)

            languageToSave = new Language(name: "Italian", spanishName: "Italiano", code: "ita")
            languageToSave.save(flush:true)

            languageToSave = new Language(name: "French", spanishName: "Francés", code: "fra")
            languageToSave.save(flush:true)

            languageToSave = new Language(name: "German", spanishName: "Alemán", code: "deu")
            languageToSave.save(flush:true)
        }

        if (Country.count() == 0)
        {
            Country countryToSave = new Country(englishName: "Australia", spanishName: "Australia", countryCode: "AUS")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Austria", spanishName: "Austria", countryCode: "AUT")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Belgium", spanishName: "Bélgica", countryCode: "BEL")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Brazil", spanishName: "Brasil", countryCode: "BRA")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Canada", spanishName: "Canadá", countryCode: "CAN")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "China", spanishName: "China", countryCode: "CHN")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Czech Republic", spanishName: "República Checa", countryCode: "CZE")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Ecuador", spanishName: "Ecuador", countryCode: "ECU")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "France", spanishName: "Francia", countryCode: "FRA")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Germany", spanishName: "Alemania", countryCode: "DEU")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Italy", spanishName: "Italia", countryCode: "ITA")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Japan", spanishName: "Japón", countryCode: "JPN")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Mexico", spanishName: "Méjico", countryCode: "MEX")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Portugal", spanishName: "Portugal", countryCode: "PRT")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Russia", spanishName: "Rusia", countryCode: "RUS")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "Spain", spanishName: "España", countryCode: "ESP")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "United Kingdom", spanishName: "Reino Unido", countryCode: "GBR")
            countryToSave.save(flush: true)

            countryToSave = new Country(englishName: "United States", spanishName: "Estados Unidos", countryCode: "USA")
            countryToSave.save(flush: true)
        }



    }
    def destroy = {
    }
}
