package films

import films.Model.CountryModel
import films.Model.FilmDetailsFromFA
import films.Model.FilmDetailsFromMKVInfo
import films.Model.LanguageModel
import films.database.CountryService
import films.database.LanguageService
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class CreateNewFilmController {

    def processMKVFileService
    def processFilmDetailsService

    LanguageService languageService
    CountryService countryService

    def index()
    {
        render(view: "createFilmFormulary", model : [])
    }

    def processData()
    {
        FilmDetailsFromMKVInfo filmToSave = null
        FilmDetailsFromFA filmDetailsFromFA

        if(request instanceof MultipartHttpServletRequest)
        {
            MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request;
            CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("mkvinfoFile");

            boolean processMKVInfoFile = true
            if (f.isEmpty())
            {
                flash.error = "Error recibiendo fichero de datos mkv info"
                processMKVInfoFile = false
                /*redirect(view: "createFilmFormulary", controller: "createNewFilm")
                return*/
            }

            try{
                if (processMKVInfoFile)
                {
                    String mkvString = new String(f.bytes, "UTF-8")
                    filmToSave = processMKVFileService.getFilmDetails(mkvString)
                }
                else
                {
                    filmToSave = new FilmDetailsFromMKVInfo()
                    filmToSave.audioTracks = new ArrayList<films.Model.AudioTrack>()
                    filmToSave.subtitleTracks = new ArrayList<films.Model.SubtitleTrack>()
                }
            }
            catch (Exception e)
            {
                flash.error = "Hubo un error procesando los datos procedentes del fichero mkvInfo"
                redirect(view: "createFilmFormulary", controller: "createNewFilm")
            }

            try{
                def urlFilmaffinity = new String(params.filmaffinityURL)
                filmDetailsFromFA = processFilmDetailsService.getFilmDetailsFromURL(urlFilmaffinity)
            }
            catch (Exception e)
            {
                flash.error = "Hubo un error procesando los datos procedentes de FilmAffinity. Por favor, asegúrese de que la URL es correcta.\n Modalidad Debug. Se pasan datos ficticios"
                filmDetailsFromFA = new FilmDetailsFromFA()
                filmDetailsFromFA.actors = new ArrayList<Person>()
                filmDetailsFromFA.director = new ArrayList<Person>()
                filmDetailsFromFA.year = 1915

                films.Model.Person person = new films.Model.Person (name: "RidleyScott")
                filmDetailsFromFA.director.add(person)

                person = new films.Model.Person (name: "Akari Enomoto")
                filmDetailsFromFA.actors.add(person)
                person = new films.Model.Person (name: "Lissete Moscoso León")
                filmDetailsFromFA.actors.add(person)
                person = new films.Model.Person (name: "Coco Loco")
                filmDetailsFromFA.actors.add(person)
                person = new films.Model.Person (name: "Ernesto Alterio")
                filmDetailsFromFA.actors.add(person)

                filmDetailsFromFA.originalName = "My Pennis"
                filmDetailsFromFA.spanishName = "Mi pene"
                filmDetailsFromFA.country = countryService.getCountryBySpanishName("Estados Unidos")
                filmDetailsFromFA.urlSmallPoster = "http://pics.filmaffinity.com/Interstellar-366875261-large.jpg"
                filmDetailsFromFA.urlBigPoster = "http://pics.filmaffinity.com/Interstellar-366875261-large.jpg"


                //redirect(view: "createFilmFormulary", controller: "createNewFilm")
            }

            List<LanguageModel> languages = languageService.getAllLanguages()
            List<CountryModel> countrys = countryService.getAllCountriesModel()
            render(view: "createdFilmPrecessedInfo", model : [filmToSave : filmToSave, filmDetailsFromFA : filmDetailsFromFA, languages : languages, countrys : countrys])
        }
        else
        {
            flash.error = "Error procesando petición http"
            redirect(view: "createFilmFormulary", controller: "createNewFilm")
        }
    }


    def saveFilm(films.Model.Film film/*, films.Model.SavedFilm coco*/)
    {
        log.info film
        redirect(controller: "createNewFilm", action: "index")
    }
}
