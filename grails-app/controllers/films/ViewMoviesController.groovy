package films

import films.Model.ViewCollection.FilmBasicInfo
import films.Model.ViewCollection.Results
import films.database.SavedFilmService

class ViewMoviesController {

    SavedFilmService savedFilmService
    SystemService systemService

    def index(){
        Results allResults
        List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated().clone()
        int pageSize = systemService.getPageSize()
        allResults = new Results(listFilms, pageSize)
        session.setAttribute("resultsPaginated", allResults)

        redirect(controller: "viewMovies", action: "viewMovies")
    }


    def viewMovies() {

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if (sessionObject == null) {
            List<FilmBasicInfo> listFilms = savedFilmService.getAllFilmsSortedByDateCreated()
            int pageSize = systemService.getPageSize()
            allResults = new Results(listFilms, pageSize)
            session.setAttribute("resultsPaginated", allResults)
        } else {
            if (!(sessionObject instanceof Results)) {
                render(view: "error.gsp", model: [])
                return
            } else {

                allResults = (Results) sessionObject
            }
        }
        List<FilmBasicInfo> resultsPaginated = allResults.getResultsPerPage()
        render(view: "index", model: [resultsPaginated: resultsPaginated, order : allResults.getOrder()])
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def paginateTab() {
        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if (sessionObject == null) {
            log.error "Session error!"
            render "error"
            return
        } else {
            if (!(sessionObject instanceof Results)) {
                render(view: "error.gsp", model: [])
                return
            } else {

                allResults = (Results) sessionObject
            }
        }
        //render (view: "paginateTab", model : [actualPage : 2, numberOfPages : 16])
        render(view: "paginateTab", model: [actualPage: allResults.pageNumber, numberOfPages: allResults.getNumberOfPages()])
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def changePageNumber(String page) {
        int pageNumber
        try {
            pageNumber = Integer.parseInt(page)
        }
        catch (Exception e) {
            log.error "Error parsing pageNumber requested"
            flash.error = "Error parsing pageNumber requested"
            redirect(controller: "viewMovies", action: "index")
            return
        }


        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results)) {
            log.warn "Session error!"
            redirect(controller: "viewMovies", action: "index")
            return
        }
        allResults = (Results) sessionObject
        allResults.setPageNumber(pageNumber)
        redirect(controller: "viewMovies", action: "viewMovies")
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def changeOrder(int order) {

        if (order == null)
        {
            log.warn "Error on the parammeter order"
            redirect(controller: "viewMovies", action: "index")
            return
        }

        Object sessionObject = session.getAttribute("resultsPaginated")
        Results allResults

        if ((sessionObject == null) || !(sessionObject instanceof Results)) {
            log.warn "Session error!"
            redirect(controller: "viewMovies", action: "index")
            return
        }
        allResults = (Results) sessionObject
        if (order == 0)
        {
            allResults.changeOrderToDateCreated()
        }
        else if (order == 1)
        {
            allResults.changeOrderToOriginalName()
        }
        else if (order == 2)
        {
            allResults.changeOrderToYear()
        }
        else
        {
            allResults.changeOrderToLocalName()
        }

        redirect(controller: "viewMovies", action: "viewMovies")
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def updateFilms()
    {
        session.removeAttribute("resultsPaginated")
        redirect(controller: "viewMovies", action: "index")
    }



    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

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
        response.contentType = 'image/jpg' // or the appropriate image content type
        response.outputStream << img
        response.outputStream.flush()
    }
}
