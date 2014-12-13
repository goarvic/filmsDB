package films.Model.ViewCollection

/**
 * Created by vickop on 13/12/14.
 */
class Results {
    List<FilmBasicInfo> allResults
    int pageNumber = 1
    int pageSize

    Results(List<FilmBasicInfo> allResults, int pageSize)
    {
        this.allResults = allResults
        this.pageSize = pageSize
    }

    List<FilmBasicInfo> getResultsPerPage()
    {
        List<FilmBasicInfo> results = null

        for (int i = pageNumber*pageSize; i<((pageNumber+1)*pageSize);i++)
        {
            results.add(allResults.get(i))
        }

        return results
    }
}

