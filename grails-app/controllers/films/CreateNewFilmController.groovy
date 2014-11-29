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

            if (f.isEmpty())
            {
                flash.error = "Error recibiendo fichero de datos mkv info"
                redirect(view: "createFilmFormulary", controller: "createNewFilm")
                return
            }

            try{
                String mkvString = new String(f.bytes, "UTF-8")
                filmToSave = processMKVFileService.getFilmDetails(mkvString)
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
                flash.error = "Hubo un error procesando los datos procedentes FilmAffinity. Por favor, asegúrese de que la URL es correcta."
                redirect(view: "createFilmFormulary", controller: "createNewFilm")
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
}
