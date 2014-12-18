package films

import films.Model.PersonModel
import films.Model.ViewCollection.FilmBasicInfo
import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib

class FilmTagLib {
    //static defaultEncodeAs = [taglib:'html']
    static defaultEncodeAs = [taglib:'text']


        //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def film = {attrs, body ->
        FilmBasicInfo filmData = attrs.filmData
        out << '<div class="row rowFilm">'
        out << '    <div class="col-md-2 text-center">'
        out << '        <img class="text-center posterMin" src="' + new ApplicationTagLib().createLink([controller :"viewMovies", action: "getFilmPoster", params : ["posterName" : filmData.posterName]]) + '"/>'
        out << '    </div>'
        out << '    <div class="col-md-10">'
        out << '        <h4>' + filmData.localName + " (" + filmData.year + ")"
        if (!filmData.filmVersion.equals("Versión cinematográfica"))
            out << " - " + filmData.filmVersion
        out << '</h4>'
        out << '    </div>'
        out << '    <div class="col-md-10 rowDirector">'
        out << '        '
        int i = 0
        for (PersonModel person : filmData.director)
        {
            if (i!=0)
                out << " / "
            out << person.name
            i++
        }
        out << '    </div>'
        out << '    <div class="col-md-10">'
        out << '        '
        i = 0
        for (PersonModel person : filmData.actors)
        {
            if (i!=0)
                out << ", "
            out << person.name
            i++
        }
        out << '    </div>'
        out << '</div>'
    }
}
