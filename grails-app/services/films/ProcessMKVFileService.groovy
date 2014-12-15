package films

import films.Model.AudioTrackModel
import films.Model.FilmDetailsFromMKVInfo
import films.Model.LanguageModel
import films.Model.SubtitleTrackModel
import films.database.LanguageService
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils

@Transactional

class ProcessMKVFileService {

    HashMap spanishSet = ["track" : "Una pista", "duration" : "Duración", "language" : "Idioma", "name" : "Nombre",
                      "trackType" : "Tipo de pista", "codecId" : "ID del códec", "channels" : "Canales",
                      "pixelsWidth" : "Anchura en píxeles", "pixelsHeight" : "Altura en píxeles",
                      "segmentSize":"Segmento, tamaño"]

    HashMap wordsLanguageSet = ["spanishSet" : spanishSet]

    LanguageService languageService

    HashMap establishedLanguage = null
    String mkvStringFile


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getNumberOfTracks()
    {
        return StringUtils.countMatches(mkvStringFile, establishedLanguage.get("track"))
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getFilmDuration()
    {
        def posDuration = mkvStringFile.indexOf(establishedLanguage.get("duration")+":") + 10
        def durationSegs = new String(mkvStringFile[posDuration .. mkvStringFile.indexOf("s",posDuration)-1])
        if (durationSegs.indexOf(".") != -1)
        {
            durationSegs = durationSegs[0 .. durationSegs.indexOf(".")-1]
        }
        return Integer.parseInt(durationSegs)
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getSubtitleTracks()
    {
        int NumberOfTracks = getNumberOfTracks()
        def subtitleTracks = []
        def iteratorTracks = mkvStringFile.indexOf(establishedLanguage.get("track"))

        for (int i=0; i<NumberOfTracks; i++)
        {
            int trackType = 2
            def positionOfNextLang = mkvStringFile.indexOf(establishedLanguage.get("language"), iteratorTracks)
            def positionOfNextTrack = mkvStringFile.indexOf(establishedLanguage.get("track"), iteratorTracks+1)
            def positionOfNextName = mkvStringFile.indexOf(establishedLanguage.get("name"), iteratorTracks)
            def language = "Unknown"

            def indexOfTrackType = mkvStringFile.indexOf(establishedLanguage.get("trackType"), iteratorTracks) + 15

            if (mkvStringFile[indexOfTrackType..mkvStringFile.indexOf("\n", indexOfTrackType)-1].equals("subtitles"))
            {
                log.info "Encontrada pista de subtítulos"

                //Vamos a localizar el idioma
                if ((positionOfNextLang == -1) || (positionOfNextLang > positionOfNextTrack))
                {
                    log.info "No encontrado idioma de la pista"
                }
                else
                {
                    language = mkvStringFile[mkvStringFile.indexOf(establishedLanguage.get("language"), iteratorTracks)+8 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Idioma", iteratorTracks))-1]
                    log.info "Idioma de la pista: " + language
                }

                if ((positionOfNextName != -1) && (positionOfNextName <= positionOfNextTrack))
                {
                    String nameOfTrack = new String(mkvStringFile[positionOfNextName+8 .. mkvStringFile.indexOf("\n", positionOfNextName) - 1])
                    log.info "Encontrado nombre de la pista: " + nameOfTrack

                    if (nameOfTrack.toLowerCase().indexOf("for") != -1)
                    {
                        log.info "Parece pista de subtitulos forzados. Se marca como tal"
                        trackType = 0
                    }
                    else if (nameOfTrack.toLowerCase().indexOf("comp") != -1)
                    {
                        log.info "Parece pista de subtitulos completos. Se marca como tal"
                        trackType = 1
                    }
                }

                //Tenemos que buscar el language en la tabla
                LanguageModel languageOfTrack = null
                if (!language.equals("Unknown"))
                    languageOfTrack = languageService.getLanguageByCode(language)

                if ((languageOfTrack == null) && (language != "Unknown"))
                {
                    log.warn "El idioma de la pista no está en la tabla."
                }

                SubtitleTrackModel subtitleTrack = new SubtitleTrackModel()
                subtitleTrack.type = 0
                subtitleTrack.languageName = language
                subtitleTrack.language = languageOfTrack
                subtitleTrack.type = trackType
                subtitleTracks.add(subtitleTrack)
            }
            iteratorTracks = positionOfNextTrack
        }
        return subtitleTracks
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************


    def getAudioTracks ()
    {
        int NumberOfTracks = getNumberOfTracks()
        def audioTracks = []
        def iteratorTracks = mkvStringFile.indexOf(establishedLanguage.get("track"))

        for (int i=0; i<NumberOfTracks; i++)
        {
            def positionOfNextLang = mkvStringFile.indexOf(establishedLanguage.get("language"), iteratorTracks)
            def positionOfNextTrack = mkvStringFile.indexOf(establishedLanguage.get("track"), iteratorTracks+1)
            def positionOfNextCodecId = mkvStringFile.indexOf(establishedLanguage.get("codecId"), iteratorTracks+1)
            def positionOfNextChannels = mkvStringFile.indexOf(establishedLanguage.get("channels")+":", iteratorTracks+1)
            def language = "Unknown"
            def codecId = "Unknown"
            int codec = 3
            int channels = 0

            def indexOfTrackType = mkvStringFile.indexOf(establishedLanguage.get("trackType")+":", iteratorTracks) + 15

            if (mkvStringFile[indexOfTrackType..mkvStringFile.indexOf("\n", indexOfTrackType)-1].equals("audio"))
            {
                log.info "Encontrada pista de audio"

                //Vamos a localizar el idioma
                if ((positionOfNextLang == -1) || (positionOfNextLang > positionOfNextTrack))
                {
                    log.info "No encontrado idioma de la pista"
                }
                else
                {
                    language = mkvStringFile[mkvStringFile.indexOf(establishedLanguage.get("language"), iteratorTracks)+8 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Idioma", iteratorTracks))-1]
                    log.info "Idioma de la pista: " + language
                }

                //Vamos a localizar el CodecId
                if ((positionOfNextCodecId == -1) || (positionOfNextCodecId > positionOfNextTrack))
                {
                    log.info "No encontrado el CodecId de la pista"
                }
                else
                {
                    codecId = mkvStringFile[mkvStringFile.indexOf(establishedLanguage.get("codecId") + ":", iteratorTracks) + 14 .. mkvStringFile.indexOf("\n", mkvStringFile.indexOf(establishedLanguage.get("codecId")+":", iteratorTracks))-1]
                    log.info "Códec de la pista: " + codecId
                    if (codecId.equals("A_DTS"))
                        codec = 2
                    else if (codecId.equals("A_AC3"))
                        codec = 1
                    else
                        codec = 3
                }

                //Vamos a localizar el número de canales
                if ((positionOfNextChannels == -1) || (positionOfNextChannels > positionOfNextTrack))
                {
                    log.info "No encontrado el número de canales de la pista"
                }
                else
                {
                    def channelsString = mkvStringFile[mkvStringFile.indexOf(establishedLanguage.get("channels"), iteratorTracks)+9 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Canales", iteratorTracks))]
                    channels = channelsString.toInteger()
                }

                //Tenemos que buscar el language en la tabla
                LanguageModel languageOfTrack
                if (!language.equals("Unknown"))
                    languageOfTrack = languageService.getLanguageByCode(language)
                else
                    languageOfTrack = null

                if ((languageOfTrack == null) && (language != "Unknown"))
                {
                    log.info "El idioma de la pista no está en la tabla. Se procede a crearlo "
                }

                AudioTrackModel audioTrack = new AudioTrackModel()
                audioTrack.audioType = channels
                audioTrack.languageName = language
                audioTrack.codecId = codecId
                audioTrack.compression = codec
                audioTrack.language = languageOfTrack
                audioTracks.add(audioTrack)
            }
            iteratorTracks = positionOfNextTrack
        }

        return audioTracks
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getXResolution()
    {
        def posXResolution = mkvStringFile.indexOf(establishedLanguage.get("pixelsWidth"))
        if (posXResolution == -1)
        {
            log.warn "No encontrada la resolución del eje X"
            return -1
        }
        else
        {
            posXResolution += 20
            return mkvStringFile[posXResolution .. mkvStringFile.indexOf("\n", posXResolution)-1].toInteger()
        }
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getYResolution()
    {
        def posYResolution = mkvStringFile.indexOf(establishedLanguage.get("pixelsHeight"))
        if (posYResolution == -1)
        {
            log.warn "No encontrada la resolución del eje X"
            return -1
        }
        else
        {
            posYResolution += 19
            return mkvStringFile[posYResolution .. mkvStringFile.indexOf("\n", posYResolution)-1].toInteger()
        }
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getVideoCodec ()
    {
         def posCodec = mkvStringFile.indexOf(establishedLanguage.get("codecId")+":")
         if (posCodec == -1)
             return -1
         else
         {
             posCodec += 14
             return mkvStringFile[posCodec .. mkvStringFile.indexOf("\n", posCodec)-1]
         }
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getSize ()
    {
        def posSize = mkvStringFile.indexOf("+ " + establishedLanguage.get("segmentSize") + " ")
        if (posSize == -1)
            return -1
        else
        {
            posSize += 19
            return Long.parseLong(mkvStringFile[posSize .. mkvStringFile.indexOf("\n", posSize)-1])
        }
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    HashMap defineLanguage()
    {
        for (e in wordsLanguageSet)
        {
            HashMap languageSet = e.value
            if (mkvStringFile.indexOf(languageSet.get("track")) != -1)
            {
                log.info "Language Set Found"
                return languageSet
            }
        }
        log.error "Language set not found"
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    FilmDetailsFromMKVInfo getFilmDetails(String mkvStringFileIn)
    {

        if (mkvStringFileIn == null)
        {
            log.error "Error parsing null mkv file"
            return null
        }
        else
            mkvStringFile = mkvStringFileIn

        FilmDetailsFromMKVInfo filmProcessed = new FilmDetailsFromMKVInfo()

        establishedLanguage = defineLanguage()

        if (establishedLanguage == null)
        {
            return null
        }
        filmProcessed.size = getSize()
        filmProcessed.videoCodec = getVideoCodec()
        filmProcessed.xResolution = getXResolution()
        filmProcessed.yResolution = getYResolution()
        filmProcessed.container = "mkv"
        filmProcessed.duration = getFilmDuration()
        filmProcessed.audioTracks = getAudioTracks()
        filmProcessed.subtitleTracks = getSubtitleTracks()

        return filmProcessed
    }

}
