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
        pageNumber = 1
    }
}

