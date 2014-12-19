package films

import films.Model.PersonModel
import films.Model.ViewCollection.FilmBasicInfo

import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class FilmTagLib {
    static defaultEncodeAs = [taglib:'text']
    LinkGenerator grailsLinkGenerator

    def film = {attrs, body ->
        FilmBasicInfo filmData = attrs.filmData
        String linkPoster = grailsLinkGenerator.link([controller: "viewMovies", action: "getFilmPoster", params:["posterName" : filmData.posterName]])
        String linkFilm = grailsLinkGenerator.link([controller: "filmData", action: "index", params:["id" : filmData.idFilm]])
        out << '<div class="row rowFilm">' + '\n'
        out << '    <div class="col-md-2 text-center">' + '\n'
        out << '        <img class="text-center posterMin" src="' + linkPoster + '"/>' + '\n'
        out << '    </div>' + '\n'
        out << '    <div class="col-md-10">' + '\n'
        out << '        <h4><a href="'+ linkFilm + '"> '  + filmData.localName + " (" + filmData.year + ")"
        if (!filmData.filmVersion.equals("Versión cinematográfica"))
            out << " - " + filmData.filmVersion
        out << '</a></h4>' + '\n'
        out << '    </div>' + '\n'
        out << '    <div class="col-md-10 rowDirector">' + '\n'
        out << '        '
        int i = 0
        for (PersonModel person : filmData.director)
        {
            String linkDirector = grailsLinkGenerator.link([controller: "viewMovies", action: "searchMovies", params:["search" : person.name]])
            if (i!=0)
                out << " / "
            out << '<a href="' + linkDirector + '">' + person.name + '</a>' + '\n'
            i++
        }
        out << '    </div>' + '\n'
        out << '    <div class="col-md-10">' + '\n'
        out << '        '
        i = 0
        for (PersonModel person : filmData.actors)
        {
            String linkActor = grailsLinkGenerator.link([controller: "viewMovies", action: "searchMovies", params:["search" : person.name]])
            if (i!=0)
                out << ", "
            out << '<a href="' + linkActor + '">' + person.name + '</a>'
            i++
        }
        out << '    </div>' + '\n'
        out << '</div>' + '\n'
    }
}
