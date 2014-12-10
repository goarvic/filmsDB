package films

import films.Model.AJAXCalls.AvailableSpaceOnDisk
import films.Model.AJAXCalls.AvailableSpaceOnDiskResponse
import films.Model.AudioTrackModel
import films.Model.CommandObjects.InfoForSaveFilm
import films.Model.FilmDetailsFromFA
import films.Model.FilmDetailsFromMKVInfo
import films.Model.FilmModel
import films.Model.LanguageModel
import films.Model.SettingModel
import films.Model.SubtitleTrackModel
import films.database.CountryService
import films.database.FilmService
import films.database.LanguageService
import films.database.SavedFilmService
import films.database.SettingService
import grails.converters.JSON
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class CreateNewFilmController {

    def processMKVFileService
    def processFilmDetailsService

    LanguageService languageService
    CountryService countryService
    FilmService filmService
    SavedFilmService savedFilmService
    InfoForSaveFilmService infoForSaveFilmService
    SystemService systemService

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
                    filmToSave.audioTracks = new ArrayList<AudioTrackModel>()
                    filmToSave.subtitleTracks = new ArrayList<SubtitleTrackModel>()
                }
                session.setAttribute("filmDetailsFromMKVInfo", filmToSave)
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
                filmDetailsFromFA = processFilmDetailsService.getTestFilmDetails()
            }
            session.setAttribute("filmDetailsFromFA", filmDetailsFromFA)

            List<LanguageModel> languages = languageService.getAllLanguages()
            session.setAttribute("languages", languages)

            String warningDuplicate = null
            if (filmService.getFilmByOriginalName(filmDetailsFromFA.originalName)!= null)
                warningDuplicate = "Warning! There is an existing instance of this film already saved in database. Pay attention on the version of the film"

            render(view: "createdFilmProcessedInfo/createdFilmProcessedInfo", model : [filmToSave : filmToSave, languages : languages, warningDuplicate:warningDuplicate])
        }
        else
        {
            flash.error = "Error procesando petición http"
            redirect(view: "createFilmFormulary", controller: "createNewFilm")
        }
    }

    def getAudioTracksFormulary()
    {
        FilmDetailsFromMKVInfo filmDetailsFromMKVInfo =  session.getAttribute("filmDetailsFromMKVInfo")
        List<LanguageModel> languages =  session.getAttribute("languages")
        if (filmDetailsFromMKVInfo == null || languages == null)
        {
            request.error = "Error processing FilmModel. No data on session"
            render "Error"
        }
        else
            render (view: "createdFilmProcessedInfo/filmDetailsAudio", model: [audioTracks: filmDetailsFromMKVInfo.audioTracks, languages : languages])
    }

    def getSubtitleTracksFormulary()
    {
        FilmDetailsFromMKVInfo filmDetailsFromMKVInfo =  session.getAttribute("filmDetailsFromMKVInfo")
        List<LanguageModel> languages =  session.getAttribute("languages")
        if (filmDetailsFromMKVInfo == null || languages == null)
        {
            request.error = "Error processing FilmModel. No data on session"
            render "Error"
        }
        else
            render (view: "createdFilmProcessedInfo/filmDetailsSubtitles", model: [subtitleTracks: filmDetailsFromMKVInfo.subtitleTracks, languages : languages])


    }


    def getVideoInfoFormulary()
    {
        FilmDetailsFromMKVInfo filmDetailsFromMKVInfo =  session.getAttribute("filmDetailsFromMKVInfo")
        if (filmDetailsFromMKVInfo == null)
        {
            request.error = "Error processing FilmModel. No data on session"
            render "Error"
        }
        else
            render (view: "createdFilmProcessedInfo/filmDetailsVideo", model: [filmDetailsFromMKVInfo: filmDetailsFromMKVInfo])
    }


    def getFilmProcessedInfoFromFA()
    {
        FilmDetailsFromFA filmDetailsFromFA =  session.getAttribute("filmDetailsFromFA")
        int nextDiscReference = savedFilmService.getNextDisk()

        if (filmDetailsFromFA == null)
        {
            request.error = "Error processing FilmModel. No data on session"
            render "Error"
        }
        else
            render (view: "createdFilmProcessedInfo/filmDetailsFA", model: [filmDetailsFromFA: filmDetailsFromFA, nextDiscReference : nextDiscReference])
    }



    def isAvailableSpaceOnDisk()
    {
        AvailableSpaceOnDisk availableSpaceParams
        AvailableSpaceOnDiskResponse availableSpaceOnDiskResponse
        try
        {
            availableSpaceParams = new AvailableSpaceOnDisk(request.JSON)
        }
        catch(Exception e)
        {
            log.warn "Error binding param object from AJAX request isAvailableSpaceOnDisk"
            availableSpaceOnDiskResponse = new AvailableSpaceOnDiskResponse(enoughSpace: false, discReference: 212, sizeFreeAvailable: 14)
            render availableSpaceOnDiskResponse as JSON
            return
        }

        availableSpaceOnDiskResponse = savedFilmService.enoughSpaceForFilmInDisc(availableSpaceParams)
        render availableSpaceOnDiskResponse as JSON
    }





    def saveFilm(InfoForSaveFilm infoForSaveFilm)
    {
        FilmDetailsFromFA filmDetailsFromFA =  session.getAttribute("filmDetailsFromFA")
        FilmDetailsFromMKVInfo filmDetailsFromMKVInfo =  session.getAttribute("filmDetailsFromMKVInfo")
        if ((filmDetailsFromFA == null) || (filmDetailsFromMKVInfo == null))
        {
            flash.error = "Error saving FilmModel. No data on session"
            redirect(controller: "createNewFilm", action: "index")
            return
        }
        //Vamos a recuperar la carátula
        if (infoForSaveFilmService.processAllInfoAndSaveNewFilm(infoForSaveFilm, filmDetailsFromMKVInfo, filmDetailsFromFA) == null)
        {
            flash.error = "Error saving film"
            redirect(controller: "createNewFilm", action: "index")
            return
        }

        flash.message = "Film saved successful"
        redirect(controller: "createNewFilm", action: "index")

    }
}
