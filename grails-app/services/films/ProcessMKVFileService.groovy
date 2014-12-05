package films

import films.Model.AudioTrack
import films.Model.FilmDetailsFromMKVInfo
import films.Model.LanguageModel
import films.Model.SubtitleTrack
import films.database.LanguageService
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils

@Transactional

class ProcessMKVFileService {

    def getDataService

    LanguageService languageService




    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getNumberOfTracks(String mkvStringFile)
    {
        return StringUtils.countMatches(mkvStringFile, "Una pista")
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getFilmDuration(String mkvStringFile)
    {
        def posDuration = mkvStringFile.indexOf("Duración:") + 10
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

    def getSubtitleTracks(String mkvStringFile)
    {
        int NumberOfTracks = getNumberOfTracks(mkvStringFile)


        def subtitleTracks = []

        def iteratorTracks = mkvStringFile.indexOf("Una pista")

        for (int i=0; i<NumberOfTracks; i++)
        {
            def positionOfNextLang = mkvStringFile.indexOf("Idioma", iteratorTracks)
            def positionOfNextTrack = mkvStringFile.indexOf("Una pista", iteratorTracks+1)
            def language

            def indexOfTrackType = mkvStringFile.indexOf("Tipo de pista:", iteratorTracks) + 15

            if (mkvStringFile[indexOfTrackType..mkvStringFile.indexOf("\n", indexOfTrackType)-1].equals("subtitles"))
            {
                log.info "Encontrada pista de subtítulos"

                //Vamos a localizar el idioma
                if ((positionOfNextLang == -1) || (positionOfNextLang > positionOfNextTrack))
                {
                    log.info "No encontrado idioma de la pista"
                    log.info "Asumiremos que es inglés"
                    language = "eng"
                }
                else
                {
                    language = mkvStringFile[mkvStringFile.indexOf("Idioma", iteratorTracks)+8 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Idioma", iteratorTracks))-1]
                    log.info "Idioma de la pista: " + language
                }

                //Tenemos que buscar el language en la tabla
                LanguageModel languageOfTrack = languageService.getLanguageByCode(language)

                if ((languageOfTrack == null) && (language != "Unknown"))
                {
                    log.info "El idioma de la pista no está en la tabla. Se procede a crearlo "

                }

                SubtitleTrack subtitleTrack = new SubtitleTrack()
                subtitleTrack.type = 0
                subtitleTrack.languageName = language
                subtitleTrack.language = languageOfTrack
                subtitleTracks.add(subtitleTrack)
            }
            iteratorTracks = positionOfNextTrack
        }

        return subtitleTracks
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************


    def getAudioTracks (String mkvStringFile)
    {
        int NumberOfTracks = getNumberOfTracks(mkvStringFile)


        def audioTracks = []

        def iteratorTracks = mkvStringFile.indexOf("Una pista")

        for (int i=0; i<NumberOfTracks; i++)
        {
            def positionOfNextLang = mkvStringFile.indexOf("Idioma", iteratorTracks)
            def positionOfNextTrack = mkvStringFile.indexOf("Una pista", iteratorTracks+1)
            def positionOfNextCodecId = mkvStringFile.indexOf("ID del códec", iteratorTracks+1)
            def positionOfNextChannels = mkvStringFile.indexOf("Canales:", iteratorTracks+1)
            def language = "Unknown"
            def codecId = "Unknown"
            int codec = 3
            int channels

            def indexOfTrackType = mkvStringFile.indexOf("Tipo de pista:", iteratorTracks) + 15

            if (mkvStringFile[indexOfTrackType..mkvStringFile.indexOf("\n", indexOfTrackType)-1].equals("audio"))
            {
                log.info "Encontrada pista de audio"

                //Vamos a localizar el idioma
                if ((positionOfNextLang == -1) || (positionOfNextLang > positionOfNextTrack))
                {
                    log.info "No encontrado idioma de la pista"
                    //log.info "Asumiremos que es inglés"
                    //language = "eng"
                }
                else
                {
                    language = mkvStringFile[mkvStringFile.indexOf("Idioma", iteratorTracks)+8 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Idioma", iteratorTracks))-1]
                    log.info "Idioma de la pista: " + language
                }

                //Vamos a localizar el CodecId
                if ((positionOfNextCodecId == -1) || (positionOfNextCodecId > positionOfNextTrack))
                {
                    log.info "No encontrado el CodecId de la pista"
                }
                else
                {
                    codecId = mkvStringFile[mkvStringFile.indexOf("ID del códec:", iteratorTracks)+14 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("ID del códec:", iteratorTracks))-1]
                    log.info codecId
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
                    def channelsString = mkvStringFile[mkvStringFile.indexOf("Canales", iteratorTracks)+9 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Canales", iteratorTracks))]
                    channels = channelsString.toInteger()
                }

                //Tenemos que buscar el language en la tabla
                LanguageModel languageOfTrack = languageService.getLanguageByCode(language)

                if ((languageOfTrack == null) && (language != "Unknown"))
                {
                    log.info "El idioma de la pista no está en la tabla. Se procede a crearlo "
                }
                /*audioTrack = new AudioTrack(language: languageOfTrack, codecId: codecId, compression : codec, audioType : channels)
                audioTracks.add(audioTrack)*/

                AudioTrack audioTrack = new AudioTrack()
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

    def getXResolution(String mkvStringFile)
    {
        def posXResolution = mkvStringFile.indexOf("Anchura en píxeles")
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

    def getYResolution(String mkvStringFile)
    {
        def posYResolution = mkvStringFile.indexOf("Altura en píxeles")
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

    def getVideoCodec (String mkvStringFile)
    {
         def posCodec = mkvStringFile.indexOf("ID del códec:")
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

    def getFilmDetails(String mkvStringFile)
    {

        FilmDetailsFromMKVInfo filmProcessed = new FilmDetailsFromMKVInfo()

        filmProcessed.filmVersion = "Versión cinematográfica"
        filmProcessed.videoCodec = getVideoCodec(mkvStringFile)
        filmProcessed.xResolution = getXResolution(mkvStringFile)
        filmProcessed.yResolution = getYResolution(mkvStringFile)
        filmProcessed.container = "mkv"
        filmProcessed.duration = getFilmDuration(mkvStringFile)
        filmProcessed.audioTracks = getAudioTracks(mkvStringFile)
        filmProcessed.subtitleTracks = getSubtitleTracks(mkvStringFile)

        return filmProcessed
        /*
        SavedFilm filmToReturn = new SavedFilm()


        filmToReturn.audioTracks = getAudioTracks(mkvStringFile)
        filmToReturn.subtitleTracks = getSubtitleTracks(mkvStringFile)
        filmToReturn.filmVersion = "Versión cinematográfica"
        filmToReturn.videoCodec = getVideoCodec(mkvStringFile)
        filmToReturn.xResolution = getXResolution(mkvStringFile)
        filmToReturn.yResolution = getYResolution(mkvStringFile)
        filmToReturn.container = "mkv"
        filmToReturn.duration = getFilmDuration(mkvStringFile)

        return filmToReturn*/
    }

}
