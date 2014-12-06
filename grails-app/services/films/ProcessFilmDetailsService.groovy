package films

import films.Model.CountryModel
import films.Model.FilmDetailsFromFA
import films.Model.GenreModel
import films.Model.PersonModel
import films.database.CountryService
import films.database.GenreService
import grails.transaction.Transactional

@Transactional
class ProcessFilmDetailsService {

    def spanishCountry = ["Estados Unidos" : "USA" , "España" : "ESP", "Francia" : "FRA", "Reino Unido" : "GBR", "Alemania" : "DEU", "Italia" : "ITA"]


    //TODO: Hay que actualizar la clave genero. Parece necesario refactorizar esto para que acepte el juego de caracteres lógico
    def spanishSet = ["originalName" : "Título original", "duration" : "Duración", "year" : "Año", "country" : "País", "director" : "Director",
                     "actors" : "Reparto", "genre" : "Género"]

    def wordsLanguageSet = ["spanishSet" : spanishSet]


    CountryService countryService
    GenreService genreService




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
            PersonModel person = new PersonModel()
            person.name = new String(personString)
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
            PersonModel person = new PersonModel()
            person.name = new String(personString)
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

    def getSpanishNameFromHTML(String HTMLContent)
    {
        def posIterator = HTMLContent.indexOf("main-title")
        posIterator = HTMLContent.indexOf("name", posIterator) + 6
        def spanishName = new String(HTMLContent[posIterator .. HTMLContent.indexOf("</span>", posIterator) - 1])

        //log.info spanishName
        log.info spanishName

        return spanishName
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

    CountryModel getCountryFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        String country = getDataFromHTML(HTMLContent, wordsSet.country)
        country = new String(country[country.indexOf("&nbsp;")+6 .. country.size()-1])
        CountryModel countryToReturn = countryService.getCountryBySpanishName(country)

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

        def wordsSet

        if (urlFilmaffinity.indexOf("/es/") > 0)
        {
             log.info "Se escoge diccionario español."
             wordsSet = "spanishSet"
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
        filmDetails.spanishName = getSpanishNameFromHTML(htmlData)
        filmDetails.genres = getGenresFromHTML(htmlData,wordsSet)


        return filmDetails


    }

    FilmDetailsFromFA getTestFilmDetails() {
        FilmDetailsFromFA filmDetailsFromFA
        filmDetailsFromFA = new FilmDetailsFromFA()
        filmDetailsFromFA.actors = new ArrayList<PersonModel>()
        filmDetailsFromFA.director = new ArrayList<PersonModel>()
        filmDetailsFromFA.year = 1915

        PersonModel person = new PersonModel(name: "RidleyScott")
        filmDetailsFromFA.director.add(person)

        person = new PersonModel(name: "Akari Enomoto")
        filmDetailsFromFA.actors.add(person)
        person = new PersonModel(name: "Lissete Moscoso León")
        filmDetailsFromFA.actors.add(person)
        person = new PersonModel(name: "Coco Loco")
        filmDetailsFromFA.actors.add(person)
        person = new PersonModel(name: "Ernesto Alterio")
        filmDetailsFromFA.actors.add(person)

        filmDetailsFromFA.originalName = "My Pennis"
        filmDetailsFromFA.spanishName = "Mi pene"
        filmDetailsFromFA.country = countryService.getCountryBySpanishName("Estados Unidos")
        filmDetailsFromFA.urlSmallPoster = "http://pics.filmaffinity.com/Interstellar-366875261-large.jpg"
        filmDetailsFromFA.urlBigPoster = "http://pics.filmaffinity.com/Interstellar-366875261-large.jpg"

        return filmDetailsFromFA
    }







}
