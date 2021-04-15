package films.database

import films.AudioTrack
import films.Country
import films.Film
import films.FilmDetailsLanguage
import films.Genre
import films.GenreNameLanguage
import films.Language
import films.Model.AJAXCalls.AvailableSpaceOnDisk
import films.Model.AJAXCalls.AvailableSpaceOnDiskResponse
import films.Model.AudioTrackModel
import films.Model.CountryModel
import films.Model.FilmDetailsLanguageModel
import films.Model.GenreModel
import films.Model.GenreNameLanguageModel
import films.Model.PersonModel
import films.Model.SavedFilmModel
import films.Model.SubtitleTrackModel
import films.Model.ViewCollection.FilmBasicInfo
import films.Model.ViewCollection.SearchResults
import films.Person
import films.SavedFilm
import films.SubtitleTrack
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import org.apache.commons.lang3.StringUtils
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class SavedFilmService {

    AudioTracksService audioTracksService
    SubtitleTracksService subtitleTracksService
    CountryService countryService
    LanguageService languageService
    FilmDetailsLanguageService filmDetailsLanguageService
    GenreNameService genreNameService

    SavedFilmModel bindSavedFilmFromDomainToModel (SavedFilm savedFilmDomain)
    {
        if (savedFilmDomain == null)
        {
            log.error "Error binding null SavedFilmModel domain instance"
            return null
        }
        SavedFilmModel savedFilmModel = new SavedFilmModel()
        DataBindingUtils.bindObjectToInstance(savedFilmModel,savedFilmDomain)

        savedFilmModel.audioTracks = new ArrayList<AudioTrackModel>()
        for(AudioTrack audioTrackDomain : savedFilmDomain.audioTracks)
        {
            AudioTrackModel audioTrackModel = audioTracksService.bindAudioTrackFromDomain(audioTrackDomain)
            savedFilmModel.audioTracks.add(audioTrackModel)
        }

        savedFilmModel.subtitleTracks = new ArrayList<SubtitleTrackModel>()
        for(SubtitleTrack subtitleTrackDomain : savedFilmDomain.subtitleTracks)
        {
            SubtitleTrackModel subtitleTrackModel = subtitleTracksService.bindSubtitleTrack(subtitleTrackDomain)
            savedFilmModel.subtitleTracks.add(subtitleTrackModel)
        }
        return savedFilmModel
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    SavedFilm getAndUpdateDomainInstance (SavedFilmModel savedFilmModel)
    {
        if (savedFilmModel == null)
        {
            log.error "Error saving null SavedFilmModel"
            return null
        }

        SavedFilm savedFilmDomain

        if (savedFilmModel.id >= 0)
        {
            savedFilmDomain = SavedFilm.findById(savedFilmModel.id)
            if (savedFilmDomain == null)
            {
                log.error "Error retrieving domains instance on database"
                return null
            }
        }
        else
            savedFilmDomain = new SavedFilm()

        DataBindingUtils.bindObjectToInstance(savedFilmDomain,savedFilmModel)

        if (savedFilmDomain.audioTracks != null)
            savedFilmDomain.audioTracks.removeAll(savedFilmDomain.audioTracks)
        else
            savedFilmDomain.audioTracks = new ArrayList<AudioTrack>()

        for (AudioTrackModel audioTrackModel : savedFilmModel.audioTracks)
        {
            AudioTrack audioTrackDomain = audioTracksService.getUpdatedAudioTrackDomainInstance(audioTrackModel)
            audioTrackDomain.savedFilm = savedFilmDomain
            savedFilmDomain.audioTracks.add(audioTrackDomain)
        }

        if (savedFilmDomain.subtitleTracks != null)
            savedFilmDomain.subtitleTracks.removeAll(savedFilmDomain.subtitleTracks)
        else
            savedFilmDomain.subtitleTracks = new ArrayList<SubtitleTrack>()

        for (SubtitleTrackModel subtitleTrackModel : savedFilmModel.subtitleTracks)
        {
            SubtitleTrack subtitleTrackDomain = subtitleTracksService.getAndUpdateSubtitleTrackDomainInstance(subtitleTrackModel)
            subtitleTrackDomain.savedFilm = savedFilmDomain
            savedFilmDomain.subtitleTracks.add(subtitleTrackDomain)
        }



        return savedFilmDomain
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    AvailableSpaceOnDiskResponse enoughSpaceForFilmInDisc(AvailableSpaceOnDisk paramsObject)
    {
        AvailableSpaceOnDiskResponse response = new AvailableSpaceOnDiskResponse()
        response.discReference = paramsObject.discReference
        long sizeOfDisk = 25050000000
        List<SavedFilm> savedFilmsCurrentlyInDisc = SavedFilm.findAllByDiscReference(paramsObject.discReference)

        long currentFreeSize = sizeOfDisk
        for (SavedFilm savedFilm : savedFilmsCurrentlyInDisc)
        {
            currentFreeSize = currentFreeSize - savedFilm.size
        }
        response.sizeFreeAvailable = currentFreeSize

        if (paramsObject.size > currentFreeSize)
            response.enoughSpace = false
        else
            response.enoughSpace = true

        return response
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    int getNextDisk()
    {
        List<SavedFilm> listLastSavedFilm = SavedFilm.list(sort:"discReference", order:"desc", max:1)
        if (listLastSavedFilm.size() == 0)
            return 1

        SavedFilm lastSavedFilm = listLastSavedFilm.get(0)
        AvailableSpaceOnDisk availableSpaceOnDiskParam = new AvailableSpaceOnDisk(discReference: lastSavedFilm.discReference, size: 6000000000)
        if (enoughSpaceForFilmInDisc(availableSpaceOnDiskParam))
            return lastSavedFilm.discReference
        else
            return lastSavedFilm.discReference + 1
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    FilmBasicInfo bindFromDomainToBasicInfo (SavedFilm savedFilmDomain, Locale locale)
    {
        FilmBasicInfo filmToBind = new FilmBasicInfo()

        Language language = Language.findByCode(locale.getISO3Language())

        FilmDetailsLanguageModel filmDetailsLanguageModel = filmDetailsLanguageService.getByFilmIdAndLanguage(savedFilmDomain.film, language)
        if (filmDetailsLanguageModel == null)
        {
            if (savedFilmDomain.film.filmDetailsLanguage.size() == 0)
            {
                log.error "No details language on film"
                return null
            }
            else
                filmDetailsLanguageModel = filmDetailsLanguageService.bindFilmDetailsLanguageDomainToModel(savedFilmDomain.film.filmDetailsLanguage.getAt(0))
        }

        DataBindingUtils.bindObjectToInstance(filmToBind,savedFilmDomain.film)
        DataBindingUtils.bindObjectToInstance(filmToBind,savedFilmDomain)
        DataBindingUtils.bindObjectToInstance(filmToBind,filmDetailsLanguageModel)

        if (filmDetailsLanguageModel.posterName == null || filmDetailsLanguageModel.posterName == "A")
        {
            filmToBind.posterName =
                    savedFilmDomain.film.filmDetailsLanguage.find{(it.posterName != null && it.posterName != "A")}.posterName
        }

        filmToBind.idFilm = savedFilmDomain.film.id
        filmToBind.idSavedFilm = savedFilmDomain.id
        filmToBind.actors = new ArrayList<PersonModel>()
        filmToBind.country = countryService.bindFromDomainToModel(savedFilmDomain.film.country)
        for(Person person : savedFilmDomain.film.actors)
        {
            PersonModel personToAdd = new PersonModel()
            DataBindingUtils.bindObjectToInstance(personToAdd, person)
            filmToBind.actors.add(personToAdd)
        }
        filmToBind.director = new ArrayList<PersonModel>()
        for(Person person : savedFilmDomain.film.director)
        {
            PersonModel personToAdd = new PersonModel()
            DataBindingUtils.bindObjectToInstance(personToAdd, person)
            filmToBind.director.add(personToAdd)
        }
        filmToBind.genresLanguage = new ArrayList<>()

        for(Genre genre : savedFilmDomain.film.genres)
        {
            GenreModel genreToAdd = new GenreModel()
            DataBindingUtils.bindObjectToInstance(genreToAdd, genre)
            genreToAdd.genreNameLanguage = new ArrayList<GenreNameLanguageModel>()
            for (GenreNameLanguage genreNameLanguage : genre.genreNameLanguage)
            {
                if (genreNameLanguage.language.code == language.code){
                    filmToBind.genresLanguage.add(genreNameLanguage)
                }
            }

        }

        return filmToBind
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    @Cacheable('listActorsPerFilm')
    Map<Long, List<PersonModel>> getActorsPerFilm () {
        def sqlResultActorsFilms = Film.executeQuery("select f.id, a.name, a.id " +
                "from Film f " +
                "left join f.actors a ")

        return mapResultQueryToPersonPerFilm(sqlResultActorsFilms)
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    @Cacheable('listDirectorsPerFilm')
    Map<Long, List<PersonModel>> getDirectorsPerFilm () {
        def sqlResultDirectorsFilms = Film.executeQuery("select f.id, d.name, d.id " +
                "from Film f " +
                "left join f.director d ")
        return mapResultQueryToPersonPerFilm(sqlResultDirectorsFilms)
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    Map<Long, List<PersonModel>> mapResultQueryToPersonPerFilm(sqlResultDirectorsFilms){
        Map<Long, List<PersonModel>> filmPersons = new HashMap<>()
        sqlResultDirectorsFilms.each {row ->
            List<PersonModel> persons
            boolean containsKey = filmPersons.containsKey(row[0])
            if (!containsKey) {
                persons = new ArrayList<>()
            } else {
                persons = filmPersons.get(row[0])
            }
            PersonModel person = new PersonModel()
            person.name = row[1]
            person.id = row[2]
            person.normalizedName = StringUtils.stripAccents(person.name)
            persons.add(person)
            if(!containsKey){
                filmPersons.put(row[0], persons)
            }
        }
        return filmPersons
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    @Cacheable('listFilms')
    List<FilmBasicInfo> getAllFilmsSortedByDateCreatedDesc(Locale locale)
    {
        Date timeStart = new Date()


        List<FilmBasicInfo> filmListToReturn = new ArrayList<FilmBasicInfo>()

        def sqlResultFilms = SavedFilm.executeQuery( "select fdl.localName, f.originalName, sf.filmVersion, fdl.posterName, " +
                                                        "f.year, sf.dateCreated, sf.id, c.countryCode, f.id " +
                "from SavedFilm sf " +
                "inner join sf.film f " +
                "left join f.filmDetailsLanguage fdl " +
                "inner join fdl.language fl " +
                "left join f.country c " +
                "where fl.code = ?" +
                "order by sf.dateCreated desc",[locale.getISO3Language()])

        def sqlResultGenresFilms = Film.executeQuery("select f.id, gnl.id, gnl.name " +
                "from Film f " +
                "left join f.genres g " +
                "left join g.genreNameLanguage gnl " +
                "inner join gnl.language l " +
                "where l.code = ? ", [locale.getISO3Language()])
        Map<Long, List<GenreNameLanguageModel>> filmGenres = new HashMap<>()
        sqlResultGenresFilms.each {row ->
            List<GenreNameLanguageModel> genresLanguage
            boolean containsKey = filmGenres.containsKey(row[0])
            if (!containsKey) {
                genresLanguage = new ArrayList<>()
            } else {
                genresLanguage = filmGenres.get(row[0])
            }
            GenreNameLanguageModel genreNameLanguageModel = new GenreNameLanguageModel()
            genreNameLanguageModel.id = row[1]
            genreNameLanguageModel.name = row[2]
            genresLanguage.add(genreNameLanguageModel)
            if(!containsKey){
                filmGenres.put(row[0], genresLanguage)
            }
        }

        Map<Long, List<PersonModel>> filmActors = getActorsPerFilm()
        Map<Long, List<PersonModel>> filmDirectors = getDirectorsPerFilm()

        sqlResultFilms.each { row ->
            FilmBasicInfo filmBasicInfo = new FilmBasicInfo()
            filmBasicInfo.localName = row[0]
            filmBasicInfo.originalName = row[1]
            filmBasicInfo.filmVersion = row[2]
            filmBasicInfo.idFilm = row[8]
            filmBasicInfo.posterName = row[3]

            if (filmBasicInfo.posterName == null || filmBasicInfo.posterName == "A")
            {
                def sqlResultFDL = FilmDetailsLanguage.executeQuery("select fdl.posterName from FilmDetailsLanguage fdl " +
                                                                        "left join fdl.film f " +
                                                                        "where f.id=? and fdl.posterName is not NULL and fdl.posterName!='A'",[Long.valueOf(filmBasicInfo.idFilm)])


                filmBasicInfo.posterName = (!sqlResultFDL.isEmpty()) ? sqlResultFDL.get(0) : null
            }

            filmBasicInfo.year = row[4]
            filmBasicInfo.dateCreated = row[5]
            filmBasicInfo.idSavedFilm = row[6]
            filmBasicInfo.country = new CountryModel()
            filmBasicInfo.country.countryCode = row[7]

            filmListToReturn.add(filmBasicInfo);

            filmBasicInfo.setGenresLanguage(new ArrayList<>())
            filmBasicInfo.setActors(new ArrayList<PersonModel>())

            Long filmId = row[8]
            List<GenreNameLanguageModel> genres = filmGenres.get(filmId)
            genres = (genres == null) ? new ArrayList<>() : genres

            List<PersonModel> actors = filmActors.get(filmId)
            List<PersonModel> directors = filmDirectors.get(filmId)
            filmBasicInfo.setActors(actors)
            filmBasicInfo.setDirector(directors)
            filmBasicInfo.setGenresLanguage(genres)

        }

        Date timeEnd = new Date()

        Long timeMs = timeEnd.getTime() - timeStart.getTime()

        log.info "Total time retrieving getAllFilms " + timeMs + " ms"

        return  filmListToReturn

    }

    List<FilmBasicInfo> getFilms(Locale locale, Integer sortBy, String order, Integer filter) {
        List<FilmBasicInfo> allFilms = getAllFilmsSortedByDateCreatedDesc(locale);

        if (sortBy != null){
            if (sortBy == 0)
            {
                changeOrderToDateCreated(allFilms);
            }
            else if (sortBy == 1)
            {
                changeOrderToOriginalName(allFilms);
            }
            else if (sortBy == 2)
            {
                changeOrderToYear(allFilms);
            }
            else
            {
                changeOrderToLocalName(allFilms);
            }
        }

        if (order != null & order.equals("desc")){
            allFilms = allFilms.reverse();
        }
        if (filter != null && filter != 0) {
            List<FilmBasicInfo> allResultsFiltered = new ArrayList<FilmBasicInfo>();
            for (FilmBasicInfo film : allFilms)
            {
                for (GenreNameLanguageModel genreLanguage : film.genresLanguage)
                {
                    if (genreLanguage.id == Long.valueOf(filter))
                    {
                        allResultsFiltered.add(film)
                        break;
                    }
                }
            }
            allFilms = allResultsFiltered;
        }
        return allFilms;
    }


    List<FilmBasicInfo> getFilmsPaginated(Locale locale, Integer pageNumber, Integer pageSize, Integer sortBy, String order, Integer filter)
    {
        List<FilmBasicInfo> allFilms = getFilms(locale, sortBy, order, filter);


        if (pageNumber != null && pageSize != null){
            Integer finalPageNumber = pageNumber;
            if ((pageNumber - 1)*pageSize > allFilms.size()){
                finalPageNumber = lastPage(allFilms.size(), pageSize) + 1;
            }

            allFilms = allFilms.subList((finalPageNumber - 1)*pageSize, Math.min(allFilms.size(), pageNumber * pageSize));
        }

        return allFilms;
    }

    private int lastPage(size, pageSize) {
        return (size / pageSize);
    }

    int getFilmsPages(Locale locale, Integer pageSize, Integer sortBy, String order, Integer filter)
    {
        List<FilmBasicInfo> allFilms = getFilms(locale, sortBy, order, filter);
        int rest = 0;
        if ((allFilms.size() % pageSize) > 0){
            rest = 1;
        }
        return (allFilms.size() / pageSize) + rest;
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void changeOrderToLocalName (List<FilmBasicInfo> allResultsFiltered)
    {
        Collections.sort(allResultsFiltered,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.localName.compareTo(film2.localName))
            }
        })
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void changeOrderToYear (List<FilmBasicInfo> allResultsFiltered)
    {
        Collections.sort(allResultsFiltered,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.year.compareTo(film2.year))
            }
        })
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    void changeOrderToDateCreated (List<FilmBasicInfo> allResultsFiltered)
    {
        Collections.sort(allResultsFiltered,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.dateCreated.compareTo(film2.dateCreated))
            }
        })
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void changeOrderToOriginalName (List<FilmBasicInfo> allResultsFiltered)
    {
        Collections.sort(allResultsFiltered,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.originalName.compareTo(film2.originalName))
            }
        })
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************



    SearchResults searchFilms(Locale locale, String order, Boolean desc, String search)
    {
        List<FilmBasicInfo> allFilms = getFilms(locale, order, desc, null);
        if (order == null){
            changeOrderToYear(allFilms);
            allFilms = allFilms.reverse();
        }


        String searchNormalized = StringUtils.stripAccents(search.toLowerCase())
        SearchResults searchResults = new SearchResults()
        searchResults.resultsByActors = new ArrayList<FilmBasicInfo>()
        searchResults.resultsByDirector = new ArrayList<FilmBasicInfo>()
        searchResults.resultsByName = new ArrayList<FilmBasicInfo>()

        searchResults.search = search

        if (search == null) {
            return searchResults
        }

        for (FilmBasicInfo filmBasicInfo : allFilms)
        {
            if ((StringUtils.stripAccents(filmBasicInfo.localName).toLowerCase().contains(searchNormalized)) || (StringUtils.stripAccents(filmBasicInfo.originalName).toLowerCase().contains(searchNormalized))) {
                searchResults.resultsByName.add(filmBasicInfo);
            }
            for (PersonModel actor : filmBasicInfo.actors)
            {
                if (StringUtils.stripAccents(actor.name.toLowerCase()).contains(searchNormalized))
                {
                    searchResults.resultsByActors.add(filmBasicInfo)
                    break
                }

            }

            for (PersonModel director : filmBasicInfo.director)
            {
                if (StringUtils.stripAccents(director?.name?.toLowerCase()).indexOf(searchNormalized)>=0)
                {
                    searchResults.resultsByDirector.add(filmBasicInfo)
                    break
                }
            }
        }

        return searchResults
    }





    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    @CacheEvict(value=["listGenres", "listFilms", "films", "numberOfFilms", "totalSize",
            "totalActors", "topActor", "topDirector", "topGenre", "listActorsPerFilm", "listDirectorsPerFilm"], allEntries=true)
    int removeSavedFilm(int savedFilmId)
    {

        SavedFilm savedFilmToRemove = SavedFilm.findById(savedFilmId)
        if (savedFilmToRemove == null)
        {
            log.error "Error removing savedFilm. Not found by id: " + savedFilmId
            return -1
        }

        Film filmAssociated = savedFilmToRemove.film
        if (filmAssociated == null)
        {
            log.error "No film associated found for savedFilm id " + savedFilmId + ". Data incoherence!"
            return -2
        }


        if (filmAssociated.savedFilms.size() == 1) //Erase both film and savedFilm
        {
            try{
                filmAssociated.delete(flush:true)
            }
            catch(Exception e)
            {
                log.error "Error deleting film associated to savedfilm id " + savedFilmId + ". Error: " + e
                return -2
            }
            return 0
        }
        else
        {
            filmAssociated.removeFromSavedFilms(savedFilmToRemove)
            try{
                savedFilmToRemove.delete(flush: true)
            }
            catch(Exception e)
            {
                log.error "Error deleting savedFilm id " + savedFilmId + " error: " + e
                return -4
            }

            return 0
        }
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    FilmBasicInfo getRandomFilm(Locale locale)
    {
        int numberOfFilms = SavedFilm.count()
        if (numberOfFilms == 0)
            return null

        SavedFilm savedFilmToBind = null
        while (savedFilmToBind == null)
        {
            Random rand = new Random();
            int randomNum = rand.nextInt(numberOfFilms) + 1;
            savedFilmToBind = SavedFilm.get(randomNum)
        }

        return bindFromDomainToBasicInfo(savedFilmToBind, locale)
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    @CacheEvict(value=["listGenres", "listFilms", "films", "numberOfFilms", "totalSize",
            "totalActors", "topActor", "topDirector", "topGenre", "listActorsPerFilm", "listDirectorsPerFilm"], allEntries=true)
    void removeCaches()
    {
        log.info "Removing Caches"
    }

}
