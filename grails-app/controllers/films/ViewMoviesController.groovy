package films

class ViewMoviesController {

    def index() {

        render (view: "index", model : [])
    }
}
