package films

import films.Model.FilmDetailsLanguageModel
import films.Model.FilmModel
import films.Model.SavedFilmModel
import films.database.FilmService
import org.springframework.web.servlet.support.RequestContextUtils

class FilmDataController {

    FilmService filmService
    SystemService systemService

    private Map<String, Locale> localeMapIso3Language;


    private void iniLanguageCodeMapping() {
        String[] languages = Locale.getISOLanguages();
        localeMapIso3Language = new HashMap<String, Locale>(languages.length);
        for (String language : languages) {
            Locale locale = new Locale(language, "");
            localeMapIso3Language.put(locale.getISO3Language().toUpperCase(), locale);
        }
    }


    private String toIso2Language(String iso3Language){
        if (localeMapIso3Language==null) {
            iniLanguageCodeMapping();
        }
        return localeMapIso3Language.get(iso3Language.toUpperCase()).getLanguage();
    }


    Map<String, String> localesCountries = new HashMap<String, String> () {{
        put("es", "ES");
        put("en", "UK");
    }}



    def viewFilm(int id) {
        int idSavedFilm = id
        Locale locale = RequestContextUtils.getLocale(request)

        FilmModel film = filmService.getFilmBySavedFilmId(idSavedFilm)
        if (film == null)
        {
            log.error "Not found film"
            render "ERROR"
            return
        }
        FilmDetailsLanguageModel filmDetailsLanguage
        SavedFilmModel savedFilm =  film.savedFilms.find{savedFilm-> savedFilm.id == idSavedFilm }
        filmDetailsLanguage = film.filmDetailsLanguage.find{filmDetailsLanguageIt->
                    filmDetailsLanguageIt.language.code == locale.getISO3Language()
                }
        if (filmDetailsLanguage == null)
        {
            filmDetailsLanguage = film.filmDetailsLanguage.getAt(0)
        }

        String ogLocale = getOgLocale(locale);
        List<String> altOgLocales = getAltOgLocales(film.getFilmDetailsLanguage(), locale);



        session.setAttribute("filmDetailsLanguage", filmDetailsLanguage)
        session.setAttribute("filmData", film)
        session.setAttribute("savedFilmData", savedFilm)
        render(view : "filmInfo", model:[film : film, savedFilm: savedFilm,
                                         filmDetailsLanguage: filmDetailsLanguage,
                                         activeLanguageCode : locale.getISO3Language(),
                                         ogLocale : ogLocale,
                                         altOgLocales: altOgLocales])
    }

    private String getOgLocale(Locale locale) {
        return locale.getLanguage() + "_" + localesCountries.get(locale.getLanguage());
    }

    private List<String> getAltOgLocales(ArrayList<FilmDetailsLanguageModel> filmDetailsLanguageModels, Locale locale) {
        List<String> altOgLocales = new ArrayList<>();
        for (FilmDetailsLanguageModel fdl : filmDetailsLanguageModels){
            if (!locale.getISO3Language().equals(fdl.language.code)){
                altOgLocales.add(toIso2Language(fdl.language.code) + "_" + localesCountries.get(toIso2Language(fdl.language.code)));
            }
        }
        return altOgLocales;
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    def getFilmPoster(String id)
    {
        String imagePath = systemService.getPostersFolder()
        if (imagePath == null)
        {
            return
        }
        imagePath += id

        File imagePoster = new File(imagePath)

        byte[] img = imagePoster.getBytes()
        response.setIntHeader('Content-length', img.length)
        response.contentType = 'image/jpg' // or the appropriate image content type
        response.outputStream << img
        response.outputStream.flush()
    }



}
