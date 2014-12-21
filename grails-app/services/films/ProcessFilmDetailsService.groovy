package films

import films.Model.CountryModel
import films.Model.FilmDetailsFromFA
import films.Model.GenreModel
import films.Model.PersonModel
import films.database.CountryService
import films.database.GenreService
import films.database.PersonService
import grails.transaction.Transactional

@Transactional
class ProcessFilmDetailsService {

    /*def spanishCountry = ["Estados Unidos" : "USA" , "España" : "ESP", "Francia" : "FRA", "Reino Unido" : "GBR", "Alemania" : "DEU", "Italia" : "ITA"]*/


    def spanishSet = ["originalName" : "Título original", "duration" : "Duración", "year" : "Año", "country" : "País", "director" : "Director",
                     "actors" : "Reparto", "genre" : "Género", "synopsis" : "Sinopsis"]

    def wordsLanguageSet = ["spanishSet" : spanishSet]


    CountryService countryService
    GenreService genreService
    PersonService personService


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************


    String getDataFromHTML(String HTMLContent, String word)
    {
        def FullFieldWord = "<dt>" + word + "</dt>"
        def positionOfData = HTMLContent.indexOf(FullFieldWord) + 4
        positionOfData = HTMLContent.indexOf("<dd>", positionOfData) + 4
        String data = new String(HTMLContent[positionOfData .. HTMLContent.indexOf("</dd>", positionOfData)-1])

        return data
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************



    String dropHiperlink(String word)
    {
        def startPosition = word.indexOf("\">") + 2
        def endPosition = word.indexOf("</a>") - 1
        String data = new String(word[startPosition .. endPosition])

        return data
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************


    List<String> getPersons (String HTMLContent, String word)
    {
        String elements = getDataFromHTML(HTMLContent,word)
        List<String> persons = new ArrayList<String>()

        if (elements.length() <= 1)
            return persons

        int iterator = 0


        boolean continueIterate = true

        while (continueIterate)
        {
            int positionOfNextComma = elements.indexOf(",", iterator+1)
            String person

            if (positionOfNextComma <= 0)
            {
                person = new String(elements[iterator .. elements.length()-1])
                continueIterate = false
            }
            else
            {
                person = new String(elements[iterator .. positionOfNextComma-1])
            }

            if (person.indexOf("<a ") != -1)
            {
                person = dropHiperlink(person)
            }
            iterator = positionOfNextComma+1
            persons.add(person)
        }

        return persons

    }






    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    List<PersonModel> getDirectorsFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        List<String> personsString = getPersons(HTMLContent, wordsSet.director)
        List<PersonModel> persons = new ArrayList<PersonModel>()
        for (String personString : personsString)
        {
            PersonModel person
            person = personService.getPersonByName(personString)
            if (person==null)
            {
                person = new PersonModel()
                person.name = new String(personString)
            }
            persons.add(person)
        }
        return persons
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    List<PersonModel> getActorsFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        List<String> personsString = getPersons(HTMLContent, wordsSet.actors)
        List<PersonModel> persons = new ArrayList<PersonModel>()
        for (String personString : personsString)
        {
            PersonModel person
            person = personService.getPersonByName(personString)
            if (person==null)
            {
                person = new PersonModel()
                person.name = new String(personString)
            }
            persons.add(person)
        }
        return persons
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************



    List<GenreModel> getGenresFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        String extraInfoOnGenres = getDataFromHTML(HTMLContent, wordsSet.genre)

        List<GenreModel> genreModels = new ArrayList<GenreModel>()

        if (extraInfoOnGenres.length() <= 1)
            return genreModels

        if (extraInfoOnGenres.indexOf("|") > 0)
        {
            extraInfoOnGenres = new String(extraInfoOnGenres[0 .. extraInfoOnGenres.indexOf("|")-1])
        }

        int iterator = 0
        boolean continueIterating = true

        while(continueIterating)
        {
            int positionOfNextDot = extraInfoOnGenres.indexOf("</span>.", iterator+1)
            String genreName

            if (positionOfNextDot <= 0)
            {
                genreName = new String(extraInfoOnGenres[iterator .. extraInfoOnGenres.length()-1])
                continueIterating = false
            }
            else
            {
                genreName = new String(extraInfoOnGenres[iterator .. positionOfNextDot-1])
            }
            if (genreName.indexOf("<a ") != -1)
            {
                genreName = dropHiperlink(genreName)
            }
            iterator = positionOfNextDot+1

            GenreModel genre = genreService.getGenreByLocalName(genreName)
            if (genre == null)
                genre = new GenreModel(localName: genreName)

            genreModels.add(genre)
        }

        return genreModels
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getOriginalNameFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        return getDataFromHTML(HTMLContent, wordsSet.originalName)
    }



    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getLocalNameFromHTML(String HTMLContent)
    {
        def posIterator = HTMLContent.indexOf("main-title")
        posIterator = HTMLContent.indexOf("name", posIterator) + 6
        def localName = new String(HTMLContent[posIterator .. HTMLContent.indexOf("</span>", posIterator) - 1])

        //log.info spanishName
        log.info localName

        return localName
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    int getDurationFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        def duration = getDataFromHTML(HTMLContent, wordsSet.duration)
        duration = new String(duration[0 .. duration.indexOf(" min.")-1])
        return duration.toInteger()
        //return getDataFromHTML(HTMLContent, wordsSet.originalName)
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getYearFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        return getDataFromHTML(HTMLContent, wordsSet.year).toInteger()
        //def year = new String(HTMLContent[positionOfOriginalName .. HTMLContent.indexOf("</dd>", positionOfOriginalName)-1])
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    String getSynopsisFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        String synopsis = getDataFromHTML(HTMLContent, wordsSet.synopsis)

        if (synopsis.indexOf("(FILMAFFINITY)") > 0)
        {
            synopsis = synopsis.replace("(FILMAFFINITY)", "")
        }
        if (synopsis[synopsis.size()-1] == ' ')
            synopsis = new String (synopsis[0 .. synopsis.size()-2])
        return synopsis
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    CountryModel getCountryFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        String country = getDataFromHTML(HTMLContent, wordsSet.country)
        country = new String(country[country.indexOf("&nbsp;")+6 .. country.size()-1])
        CountryModel countryToReturn = countryService.getCountryByLocalName(country)

        return countryToReturn
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getBigPosterURLFromHTML(String HTMLContent)
    {
        def posIterator = HTMLContent.indexOf("<div id=\"movie-main-image-container\">")
        posIterator = HTMLContent.indexOf("href=",posIterator) + 6
        def posterURL = new String(HTMLContent[posIterator .. HTMLContent.indexOf("\"" , posIterator)-1])

        return posterURL
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getSmallPosterURLFromHTML(String HTMLContent)
    {
        def posIterator = HTMLContent.indexOf("<div id=\"movie-main-image-container\">")
        posIterator = HTMLContent.indexOf("src=",posIterator) + 5
        def posterURL = new String(HTMLContent[posIterator .. HTMLContent.indexOf("\"" , posIterator)-1])

        return posterURL
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getHTMLFromFilmAffinity (String urlFilmaffinity)
    {
        Wget httpGetter = new Wget();
        def htmlData = httpGetter.get(urlFilmaffinity);

        return htmlData
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    FilmDetailsFromFA getFilmDetailsFromURL(String urlFilmaffinity) {
        def htmlData = getHTMLFromFilmAffinity(urlFilmaffinity)

        String wordsSet

        if (urlFilmaffinity.indexOf("filmaffinity") < 0)
        {
            log.error "Error. This url is not from FilmAffinity"
            return null
        }


        if (urlFilmaffinity.indexOf("/es/") > 0)
        {
             log.info "Se escoge diccionario español."
             wordsSet = "spanishSet"
        }
        else
        {
            log.error "Only spanish language currently supported"
            return null
        }

        //log.info htmlData

        /*FilmModel filmFromFilmaffinity = new FilmModel()
        filmFromFilmaffinity.country*/

        FilmDetailsFromFA filmDetails = new FilmDetailsFromFA()

        filmDetails.country = getCountryFromHTML(htmlData, wordsSet)
        filmDetails.countryCode = filmDetails.country.countryCode
        filmDetails.duration = getDurationFromHTML(htmlData, wordsSet)
        filmDetails.originalName = getOriginalNameFromHTML(htmlData, wordsSet)
        filmDetails.year = getYearFromHTML(htmlData, wordsSet)
        filmDetails.director = getDirectorsFromHTML(htmlData, wordsSet)
        filmDetails.actors = getActorsFromHTML(htmlData, wordsSet)
        filmDetails.urlBigPoster = getBigPosterURLFromHTML(htmlData)
        filmDetails.urlSmallPoster = getSmallPosterURLFromHTML(htmlData)
        filmDetails.localName = getLocalNameFromHTML(htmlData)
        filmDetails.genres = getGenresFromHTML(htmlData,wordsSet)
        filmDetails.synopsis = getSynopsisFromHTML(htmlData, wordsSet)

        return filmDetails


    }

    FilmDetailsFromFA getTestFilmDetails() {
        FilmDetailsFromFA filmDetailsFromFA
        filmDetailsFromFA = new FilmDetailsFromFA()
        filmDetailsFromFA.actors = new ArrayList<PersonModel>()
        filmDetailsFromFA.director = new ArrayList<PersonModel>()
        filmDetailsFromFA.year = 1996

        PersonModel person
        Person personDomain = Person.findByName("Christopher Nolan")
        if (personDomain == null)
            person = new PersonModel(name: "Christopher Nolan")
        else
            person = personService.bindPersonToModel(personDomain)

        filmDetailsFromFA.director.add(person)

        personDomain = Person.findByName("Akari Enomoto")
        if (personDomain == null)
            person = new PersonModel(name: "Akari Enomoto")
        else
            person = personService.bindPersonToModel(personDomain)

        filmDetailsFromFA.actors.add(person)

        personDomain = Person.findByName("Lissete Moscoso León")
        if (personDomain == null)
            person = new PersonModel(name: "Lissete Moscoso León")
        else
            person = personService.bindPersonToModel(personDomain)
        filmDetailsFromFA.actors.add(person)

        personDomain = Person.findByName("Mónica Zori")
        if (personDomain == null)
            person = new PersonModel(name: "Mónica Zori")
        else
            person = personService.bindPersonToModel(personDomain)
        filmDetailsFromFA.actors.add(person)

        personDomain = Person.findByName("Ernesto Alterio")
        if (personDomain == null)
            person = new PersonModel(name: "Ernesto Alterio")
        else
            person = personService.bindPersonToModel(personDomain)
        filmDetailsFromFA.actors.add(person)

        filmDetailsFromFA.originalName = "Coco"
        filmDetailsFromFA.localName = "Coco"
        filmDetailsFromFA.genres = new ArrayList<GenreModel>()
        filmDetailsFromFA.genres.add(new GenreModel(localName: "Ciencia ficción"))
        filmDetailsFromFA.country = countryService.getCountryByLocalName("Estados Unidos")
        filmDetailsFromFA.urlSmallPoster = "http://pics.filmaffinity.com/Interstellar-366875261-main.jpg"
        filmDetailsFromFA.urlBigPoster = "http://pics.filmaffinity.com/Interstellar-366875261-large.jpg"
        filmDetailsFromFA.synopsis =
                "Al ver que la vida en la Tierra está llegando a su fin, un grupo de exploradores liderados por el piloto Cooper (McConaughey) y la científica Amelia (Hathaway) se embarca en la que puede ser la misión más importante de la historia de la humanidad y emprenden un viaje más allá de nuestra galaxia en el que descubrirán si las estrellas pueden albergar el futuro de la raza humana."

        return filmDetailsFromFA
    }







}
