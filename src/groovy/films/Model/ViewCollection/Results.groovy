package films.Model.ViewCollection

import films.Model.GenreModel
import films.Model.GenreNameLanguageModel
import films.Model.PersonModel
import org.apache.commons.lang3.StringUtils

/**
 * Created by vickop on 13/12/14.
 */
class Results {
    List<FilmBasicInfo> allResults
    List<FilmBasicInfo> allResultsFiltered
    int pageNumber = 1
    int pageSize
    int order = 0
    int filterGenre = 0

    String topActor = null
    String topDirector = null
    String topGenre = null

    Results(List<FilmBasicInfo> allResults, int pageSize)
    {
        this.allResults = allResults
        this.allResultsFiltered = (List<FilmBasicInfo>) allResults.clone()
        this.pageSize = pageSize
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    int getNumberOfPages ()
    {
        int rest = 0
        if ((allResultsFiltered.size() % pageSize) > 0)
            rest = 1
        return (allResultsFiltered.size() / pageSize) + rest
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    List<FilmBasicInfo> getResultsPerPage()
    {
        List<FilmBasicInfo> results = new ArrayList<FilmBasicInfo>()

        for (int i = (pageNumber - 1)*pageSize; (i<allResultsFiltered.size() && i<(pageNumber*pageSize));i++)
        {
            results.add(allResultsFiltered.get(i))
        }

        return results
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void changeOrderToOriginalName ()
    {
        Collections.sort(allResults,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.originalName.compareTo(film2.originalName))
            }
        })
        order = 1
        pageNumber = 1
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void changeOrderToLocalName ()
    {
        Collections.sort(allResultsFiltered,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.localName.compareTo(film2.localName))
            }
        })
        order = 3
        pageNumber = 1
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    void changeOrderToYear ()
    {
        Collections.sort(allResultsFiltered,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.year.compareTo(film2.year))
            }
        })
        order = 2
        pageNumber = 1
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    void changeOrderToDateCreated ()
    {
        Collections.sort(allResultsFiltered,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film2.dateCreated.compareTo(film1.dateCreated))
            }
        })
        order = 0
        pageNumber = 1
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************


    void applyFilterGenre (int filterGenreLanguageId)
    {
        this.filterGenre = filterGenreLanguageId
        if (filterGenre == 0)
        {
            allResultsFiltered = (List<FilmBasicInfo>) allResults.clone()
            return
        }


        allResultsFiltered = new ArrayList<FilmBasicInfo>()
        for (FilmBasicInfo film : allResults)
        {
            for (GenreNameLanguageModel genreLanguage : film.genresLanguage)
            {
                if (genreLanguage.id == Long.valueOf(filterGenreLanguageId))
                {
                    allResultsFiltered.add(film)
                    break;
                }
            }
        }
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    SearchResults search(String search)
    {
        String searchNormalized = StringUtils.stripAccents(search.toLowerCase())
        SearchResults searchResults = new SearchResults()
        searchResults.resultsByActors = new ArrayList<FilmBasicInfo>()
        searchResults.resultsByDirector = new ArrayList<FilmBasicInfo>()
        searchResults.resultsByName = new ArrayList<FilmBasicInfo>()

        searchResults.search = search

        if (search == null)
            return searchResults

        for (FilmBasicInfo filmBasicInfo : allResults)
        {
            if ((filmBasicInfo.localName.toLowerCase().indexOf(search.toLowerCase()) >= 0) || (filmBasicInfo.originalName.toLowerCase().indexOf(search.toLowerCase()) >= 0))
                searchResults.resultsByName.add(filmBasicInfo)
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

    void removeFilmFromResults(int idSavedFilm)
    {
        int count = 0
        for (FilmBasicInfo film : allResults)
        {
            if (film.idSavedFilm == idSavedFilm)
            {
                allResults.remove(count)
                break
            }
            count++
        }

        count = 0
        for (FilmBasicInfo film : allResultsFiltered)
        {
            if (film.idSavedFilm == idSavedFilm)
            {
                allResultsFiltered.remove(count)
                break
            }
            count++
        }
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    synchronized void initializeStatics(Map<Long, String> genreLanguage)
    {
        HashMap<String, Integer> actors = new HashMap<String, Integer>();
        HashMap<String, Integer>  directors = new HashMap<String, Integer>();
        HashMap<String, Integer>  genres = new HashMap<String, Integer>();

        //Intialize
        for (FilmBasicInfo film : allResults)
        {
            for (PersonModel actor : film.actors)
            {
                if (actors.get(actor.name) == null)
                    actors.put(actor.name, 1)
                else
                    actors.put(actor.name, actors.get(actor.name)+1)
            }

            for (PersonModel director : film.director)
            {
                if (directors.get(director.name) == null)
                    directors.put(director.name, 1)
                else
                    directors.put(director.name, directors.get(director.name)+1)
            }

            for (GenreNameLanguageModel genre : film.genresLanguage)
            {
                if (genres.get(genre.name ) == null)
                    genres.put(genre.name, 1)
                else
                    genres.put(genre.name, genres.get(genre.name)+1)
            }
        }

        topActor = null
        int times = 0
        Iterator it = actors.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (topActor == null)
            {
                topActor = (String) pair.getKey()
                times = (int) pair.getValue()
            }
            else if (((int) pair.getValue()) > times)
            {
                topActor = (String) pair.getKey()
                times = (int) pair.getValue()
            }
        }

        topDirector = null
        times = 0
        it = directors.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (topDirector == null)
            {
                topDirector = (String) pair.getKey()
                times = (int) pair.getValue()
            }
            else if (((int) pair.getValue()) > times)
            {
                topDirector = (String) pair.getKey()
                times = (int) pair.getValue()
            }
        }

        topGenre = null
        times = 0
        it = genres.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (topGenre == null)
            {
                topGenre = (String) pair.getKey()
                times = (int) pair.getValue()
            }
            else if (((int) pair.getValue()) > times)
            {
                topGenre = (String) pair.getKey()
                times = (int) pair.getValue()
            }
        }
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    synchronized String getTopActorAndInitializeIfNecessary()
    {
        if (topActor != null)
        {
            return topActor
        }

        initializeStatics()
        return topActor
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    synchronized String getTopDirectorAndInitializeIfNecessary()
    {
        if (topDirector != null)
        {
            return topDirector
        }

        initializeStatics()
        return topDirector
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    synchronized String getTopGenreAndInitializeIfNecessary()
    {
        if (topGenre != null)
        {
            return topGenre
        }

        initializeStatics()
        return topGenre
    }



}

