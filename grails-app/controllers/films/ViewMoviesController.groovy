package films

import films.Model.ViewCollection.FilmBasicInfo
import films.Model.ViewCollection.Results
import films.database.SavedFilmService

class ViewMoviesController {

    SavedFilmService savedFilmService
    SystemService systemService

    def index() {

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if (sessionObject == null)
        {
            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated()
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
        }
        else
        {
            if (!(sessionObject instanceof Results))
            {
                render(view: "error.gsp", model : [])
                return
            }
            else
            {

                allResults = (Results) sessionObject
            }
        }
        render (view: "index", model : [resultsPaginated : allResults.getResultsPerPage()])
    }

    def getFilmPoster(String posterName)
    {
        String imagePath = systemService.getSmallPostersFolder()
        if (imagePath == null)
        {
            render null
            return
        }
        imagePath += posterName

        File imagePoster = new File(imagePath)

        byte[] img = imagePoster.getBytes()
        response.setIntHeader('Content-length', img.length)
        response.contentType = 'image/png' // or the appropriate image content type
        response.outputStream << img
        response.outputStream.flush()



    }
}
