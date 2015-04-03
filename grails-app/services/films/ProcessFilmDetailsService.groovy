package films

import films.Model.CountryModel
import films.Model.FilmDetailsFromFA
import films.Model.FilmDetailsLanguageModel
import films.Model.GenreModel
import films.Model.GenreNameLanguageModel
import films.Model.LanguageModel
import films.Model.PersonModel
import films.database.CountryService
import films.database.GenreService
import films.database.LanguageService
import films.database.PersonService
import grails.transaction.Transactional

@Transactional
class ProcessFilmDetailsService {

    def spanishSet = ["originalName" : "Título original", "duration" : "Duración", "year" : "Año", "country" : "País", "director" : "Director",
                     "actors" : "Reparto", "genre" : "Género", "synopsis" : "Sinopsis"]

    def englishSet = ["originalName" : "Original title", "duration" : "Running Time", "year" : "Year", "country" : "Country", "director" : "Director",
                      "actors" : "Cast", "genre" : "Genre", "synopsis" : "Synopsis / Plot"]

    def wordsLanguageSet = ["es" : spanishSet, "en" : englishSet]

    CountryService countryService
    GenreService genreService
    PersonService personService
    LanguageService languageService

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
        int startPosition = word.indexOf("\">") + 2
        int endPosition = word.indexOf("</a>") - 1
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

            if (person[0] == ' ')
                person = new String(person[1 .. person.size() - 1])

            person = person.replace("&quot;", "\"")

            if (!persons.contains(person))
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


    String getGenreNameFromGenreDetailsPage(String htmlData)
    {
        int headPartPosBegin = htmlData.indexOf("<head>") + 6
        int headPartPosEnd = htmlData.indexOf("</head>") - 1
        String headPart = new String(htmlData[headPartPosBegin .. headPartPosEnd])

        int titlePartBegin = headPart.indexOf("<title>") + 7
        int titlePartEnd = headPart.indexOf("</title>") - 1
        String titlePart = new String(headPart[titlePartBegin .. titlePartEnd])

        int posOfGenreNameBegin = titlePart.indexOf("-") + 2
        int posOfGenreNameEnd = titlePart.indexOf(" - FilmAffinity") - 1

        String genreName = new String(titlePart[posOfGenreNameBegin .. posOfGenreNameEnd])
        return genreName
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    GenreModel getNewGenre(String genreNameWithHiperLink)
    {
        int hiperLinkPosBegin = genreNameWithHiperLink.indexOf("href=") + 6
        int hiperLinkPosEnd = genreNameWithHiperLink.indexOf("\"", hiperLinkPosBegin+1) - 1
        String hiperLink = new String(genreNameWithHiperLink[hiperLinkPosBegin .. hiperLinkPosEnd])

        List<String> urlsOfGenreDetails = getFAURLsToProcess(hiperLink)

        GenreModel genre = new GenreModel(localName: dropHiperlink(genreNameWithHiperLink))

        for (String urlOfGenreDetails : urlsOfGenreDetails)
        {
            String htmlData = getHTMLFromFilmAffinity(urlOfGenreDetails)
            LanguageModel languageGenreName = getLanguageDetails(urlOfGenreDetails)
            String genreLanguageName = getGenreNameFromGenreDetailsPage(htmlData)
            GenreNameLanguageModel genreNameLanguage = new GenreNameLanguageModel(name: genreLanguageName, language: languageGenreName)
            genre.genreNameLanguage.add(genreNameLanguage)
        }

        return genre
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************



    List<GenreModel> getGenresFromHTML(String HTMLContent, String wordsSetString, LanguageModel language)
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
            String genreNameWithHiperLink
            String genreName

            if (positionOfNextDot <= 0)
            {
                genreNameWithHiperLink = new String(extraInfoOnGenres[iterator .. extraInfoOnGenres.length()-1])
                continueIterating = false
            }
            else
            {
                genreNameWithHiperLink = new String(extraInfoOnGenres[iterator .. positionOfNextDot-1])
            }
            if (genreNameWithHiperLink.indexOf("<a ") != -1)
            {
                genreName = dropHiperlink(genreNameWithHiperLink)
            }
            else
                genreName = genreNameWithHiperLink

            iterator = positionOfNextDot+1

            GenreModel genre = genreService.getGenreByNameAndLanguageCode(genreName, language.code)
            if (genre == null)
            {
                genre = getNewGenre(genreNameWithHiperLink)
            }

            genreModels.add(genre)
        }

        return genreModels
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    String getOriginalNameFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        String originalName = getDataFromHTML(HTMLContent, wordsSet.originalName)

        originalName = originalName.replaceAll("\r", "");
        originalName = originalName.replaceAll("\n", "");
        originalName = originalName.trim();

        if (originalName.indexOf("<span")>=0)
        {
            String pattern = "(?i)(<span.*?>)(.+?)(</span>)";
            originalName = originalName.replaceAll(pattern, "");
            originalName = originalName.trim();
        }

        return originalName

    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    String getLocalNameFromHTML(String HTMLContent)
    {
        def posIterator = HTMLContent.indexOf("main-title")
        posIterator = HTMLContent.indexOf("name", posIterator) + 6
        def localName = new String(HTMLContent[posIterator .. HTMLContent.indexOf("</span>", posIterator) - 1])

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
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    int getYearFromHTML(String HTMLContent, String wordsSetString)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        return getDataFromHTML(HTMLContent, wordsSet.year).toInteger()
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

        synopsis = synopsis.replace("&quot;", "\"")
        return synopsis
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    CountryModel getCountryFromHTML(String HTMLContent, String wordsSetString, String languageCode)
    {
        def wordsSet = wordsLanguageSet.get(wordsSetString)
        String country = getDataFromHTML(HTMLContent, wordsSet.country)
        country = new String(country[country.indexOf("&nbsp;")+6 .. country.size()-1])
        CountryModel countryToReturn = countryService.getCountryByLocalName(country,languageCode)

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

    String getHTMLFromFilmAffinity (String urlFilmaffinity)
    {
        Wget httpGetter = new Wget();
        String htmlData = httpGetter.get(urlFilmaffinity);

        return htmlData
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    LanguageModel getLanguageDetails(String urlFilmaffinity)
    {
        if (urlFilmaffinity.indexOf("/es/") > 0)
        {
            log.info("Spanish language found. Setting detail")
            return languageService.getLanguageByCode("spa")
        }
        else if(urlFilmaffinity.indexOf("/en/") > 0)
        {
            log.info("Spanish language found. Setting detail")
            return languageService.getLanguageByCode("eng")
        }
        else
        {
            log.error "Only spanish language currently supported"
            return null
        }
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    String getWordsSet(String urlFilmaffinity)
    {
        String wordset = null
        wordsLanguageSet.each() { key, value ->
            if (urlFilmaffinity.indexOf("/" + key + "/") > 0)
                wordset = new String(key)
        }
        return wordset
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    List<String> getFAURLsToProcess(String urlFilmaffinity)
    {
        int positionOfBarBeforeLanguage = urlFilmaffinity.indexOf('/', 7)
        String urlFirstPart = new String(urlFilmaffinity[0 .. positionOfBarBeforeLanguage])

        int positionOfBarAfterLanguage = urlFilmaffinity.indexOf('/', positionOfBarBeforeLanguage+1)
        String urlLastPart = new String(urlFilmaffinity[positionOfBarAfterLanguage .. urlFilmaffinity.size()-1 ])

        List<String> urls = new ArrayList<String>()

        wordsLanguageSet.each() { key, value ->
            urls.add(new String(urlFirstPart + key + urlLastPart))
        };

        return urls
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    List<FilmDetailsLanguageModel> getDetailsPerLanguage(String urlFilmaffinity)
    {
        List<String> urlsLanguageFA = getFAURLsToProcess(urlFilmaffinity)

        List<FilmDetailsLanguageModel> filmDetailsLanguageModelList = new ArrayList<FilmDetailsLanguageModel>()
        for (String urlLanguage : urlsLanguageFA)
        {
            LanguageModel languageOfURL = getLanguageDetails(urlLanguage)
            def wordsSet = getWordsSet(urlLanguage)
            String htmlData = getHTMLFromFilmAffinity(urlLanguage)
            FilmDetailsLanguageModel filmDetails = new FilmDetailsLanguageModel()
            filmDetails.localName = getLocalNameFromHTML(htmlData)
            filmDetails.synopsis = getSynopsisFromHTML(htmlData, wordsSet)
            filmDetails.filmAffinityURL = urlLanguage
            filmDetails.language = languageOfURL


            filmDetailsLanguageModelList.add(filmDetails)
        }
        return filmDetailsLanguageModelList
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    String getTrailersURL(String urlFilmaffinity, String dataHTML)
    {
        int positionOfBarBeforeLanguage = urlFilmaffinity.indexOf('/', 7)
        String baseURL = new String(urlFilmaffinity[0 .. positionOfBarBeforeLanguage])

        int positionOfTrailersSection = dataHTML.indexOf("Trailers&nbsp;")
        int positionOfStartTrailersSection = dataHTML.indexOf("href",dataHTML.indexOf("\"ntabs\""))
        int iterator = positionOfStartTrailersSection
        while(iterator < positionOfTrailersSection)
        {
            positionOfStartTrailersSection = iterator
            iterator = dataHTML.indexOf("href", iterator+1)
        }

        String urlOfTrailers = baseURL+ new String(dataHTML[positionOfStartTrailersSection + 7 .. dataHTML.indexOf("\"", positionOfStartTrailersSection + 6)-1])

        return urlOfTrailers
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    FilmDetailsFromFA getFilmDetailsFromURL(String urlFilmaffinity) {
        String wordsSet

        if (urlFilmaffinity.indexOf("filmaffinity") < 0)
        {
            log.error "Error. This url is not from FilmAffinity"
            return null
        }

        List<FilmDetailsLanguageModel> detailsLanguageModelList = getDetailsPerLanguage(urlFilmaffinity)

        FilmDetailsFromFA filmDetails = new FilmDetailsFromFA()
        filmDetails.language = getLanguageDetails(urlFilmaffinity)
        if (filmDetails.language == null)
        {
            log.error "Error getting language details"
            return null
        }
        else
            wordsSet = getWordsSet(urlFilmaffinity)

        def htmlData = getHTMLFromFilmAffinity(urlFilmaffinity)

        filmDetails.filmDetailsLanguageModels = detailsLanguageModelList

        filmDetails.country = getCountryFromHTML(htmlData, wordsSet, filmDetails.language.code)
        filmDetails.countryCode = filmDetails.country.countryCode
        filmDetails.duration = getDurationFromHTML(htmlData, wordsSet)
        filmDetails.originalName = getOriginalNameFromHTML(htmlData, wordsSet)
        filmDetails.year = getYearFromHTML(htmlData, wordsSet)
        filmDetails.director = getDirectorsFromHTML(htmlData, wordsSet)
        filmDetails.actors = getActorsFromHTML(htmlData, wordsSet)
        filmDetails.urlBigPoster = getBigPosterURLFromHTML(htmlData)
        filmDetails.urlSmallPoster = getSmallPosterURLFromHTML(htmlData)
        filmDetails.localName = getLocalNameFromHTML(htmlData)
        filmDetails.genres = getGenresFromHTML(htmlData,wordsSet, filmDetails.language)
        filmDetails.synopsis = getSynopsisFromHTML(htmlData, wordsSet)

        return filmDetails
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

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

        personDomain = Person.findByName("Akari2 Enomoto")
        if (personDomain == null)
            person = new PersonModel(name: "Akari2 Enomoto")
        else
            person = personService.bindPersonToModel(personDomain)

        filmDetailsFromFA.actors.add(person)

        personDomain = Person.findByName("Lissete Moscoso León")
        if (personDomain == null)
            person = new PersonModel(name: "Lissete Moscoso León")
        else
            person = personService.bindPersonToModel(personDomain)
        filmDetailsFromFA.actors.add(person)

        personDomain = Person.findByName("Mónica2 Zori")
        if (personDomain == null)
            person = new PersonModel(name: "Mónica2 Zori")
        else
            person = personService.bindPersonToModel(personDomain)
        filmDetailsFromFA.actors.add(person)

        personDomain = Person.findByName("Ernesto2 Alterio")
        if (personDomain == null)
            person = new PersonModel(name: "Ernesto2 Alterio")
        else
            person = personService.bindPersonToModel(personDomain)
        filmDetailsFromFA.actors.add(person)

        filmDetailsFromFA.originalName = "Cocao"
        filmDetailsFromFA.localName = "Cocao"
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