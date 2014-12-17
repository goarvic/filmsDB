package films.Model.ViewCollection

import films.Model.PersonModel

/**
 * Created by vickop on 13/12/14.
 */
class Results {
    List<FilmBasicInfo> allResults
    int pageNumber = 1
    int pageSize
    int order = 0

    Results(List<FilmBasicInfo> allResults, int pageSize)
    {
        this.allResults = allResults
        this.pageSize = pageSize
    }

    int getNumberOfPages ()
    {
        return (allResults.size() / pageSize)
    }

    List<FilmBasicInfo> getResultsPerPage()
    {
        List<FilmBasicInfo> results = new ArrayList<FilmBasicInfo>()

        for (int i = (pageNumber - 1)*pageSize; (i<allResults.size() && i<(pageNumber*pageSize));i++)
        {
            results.add(allResults.get(i))
        }

        return results
    }

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

    void changeOrderToLocalName ()
    {
        Collections.sort(allResults,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.spanishName.compareTo(film2.spanishName))
            }
        })
        order = 3
        pageNumber = 1
    }

    void changeOrderToYear ()
    {
        Collections.sort(allResults,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film1.year.compareTo(film2.year))
            }
        })
        order = 2
        pageNumber = 1
    }

    void changeOrderToDateCreated ()
    {
        Collections.sort(allResults,  new Comparator<FilmBasicInfo>() {
            @Override
            int compare(FilmBasicInfo film1, FilmBasicInfo film2) {
                return new Integer(film2.dateCreated.compareTo(film1.dateCreated))
            }
        })
        order = 0
        pageNumber = 1
    }


    SearchResults search(String search)
    {
        SearchResults searchResults = new SearchResults()
        searchResults.resultsByActors = new ArrayList<FilmBasicInfo>()
        searchResults.resultsByDirector = new ArrayList<FilmBasicInfo>()
        searchResults.resultsByName = new ArrayList<FilmBasicInfo>()

        searchResults.search = search

        if (search == null)
            return searchResults

        for (FilmBasicInfo filmBasicInfo : allResults)
        {
            if ((filmBasicInfo.spanishName.toLowerCase().indexOf(search.toLowerCase()) >= 0) || (filmBasicInfo.originalName.toLowerCase().indexOf(search.toLowerCase()) >= 0))
                searchResults.resultsByName.add(filmBasicInfo)
            for (PersonModel actor : filmBasicInfo.actors)
            {
                if (actor.name.toLowerCase().indexOf(search.toLowerCase())>=0)
                {
                    searchResults.resultsByActors.add(filmBasicInfo)
                    break
                }

            }

            for (PersonModel director : filmBasicInfo.director)
            {
                if (director.name.toLowerCase().indexOf(search.toLowerCase())>=0)
                {
                    searchResults.resultsByDirector.add(filmBasicInfo)
                    break
                }
            }
        }

        return searchResults
    }


}

