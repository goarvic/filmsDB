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

    HashMap spanishSet = ["track" : "Pista", "duration" : "Duración", "language" : "Idioma", "name" : "Nombre",
                      "trackType" : "Tipo de pista", "codecId" : "ID del códec", "channels" : "Canales",
                      "pixelsWidth" : "Anchura en píxels", "pixelsHeight" : "Altura en píxels",
                      "segmentSize":"Segmento: tamaño", "trackAudioType" : "Audio", "trackSubtitlesType" : "Subtítulos"]

    HashMap wordsLanguageSet = ["spanishSet" : spanishSet]

    LanguageService languageService

    String mkvStringFile


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    private int getNumberOfTracks(HashMap<String, String> establishedLanguage)
    {
        return StringUtils.countMatches(mkvStringFile, establishedLanguage.get("track")+"\n");
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    private int getFilmDurationFromFormattedTime(String durationFormatted) {
        String[] splittedDuration = durationFormatted.split(":");
        int hours = Integer.valueOf(splittedDuration[0]);
        int minutes = Integer.valueOf(splittedDuration[1]);
        String[] splittedSeconds = splittedDuration[2].split("\\.");
        int seconds = Integer.valueOf(splittedSeconds[0]);

        return seconds + minutes * 60 + hours * 3600;
    }


    private int getFilmDuration(HashMap<String, String> establishedLanguage)
    {
        int posDuration = mkvStringFile.indexOf(establishedLanguage.get("duration")+":") + 10;
        String durationString = new String(mkvStringFile[posDuration .. mkvStringFile.indexOf("s",posDuration)-1]);

        final int durationSeconds;
        if (durationString.contains(":")){
            durationSeconds = getFilmDurationFromFormattedTime(durationString);
        } else {
            if (durationString.indexOf(".") != -1)
            {
                durationString = durationString[0 .. durationString.indexOf(".")-1]
            }
            durationSeconds = Integer.parseInt(durationSegsString);
        }

        return durationSeconds;
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getSubtitleTracks(HashMap<String, String> establishedLanguage)
    {
        int NumberOfTracks = getNumberOfTracks(establishedLanguage)
        List<SubtitleTrackModel> subtitleTracks = new ArrayList<>();
        int iteratorTracks = mkvStringFile.indexOf(establishedLanguage.get("track") + "\n");

        for (int i=0; i<NumberOfTracks; i++) {
            int trackType = 2;
            int positionOfNextLang = mkvStringFile.indexOf(establishedLanguage.get("language"), iteratorTracks);
            int positionOfNextTrack = mkvStringFile.indexOf(establishedLanguage.get("track")+"\n", iteratorTracks+1);
            int positionOfNextName = mkvStringFile.indexOf(establishedLanguage.get("name"), iteratorTracks);
            String language = "Unknown"

            def indexOfTrackType = mkvStringFile.indexOf(establishedLanguage.get("trackType"), iteratorTracks) + 15;
            String trackTypeStr = mkvStringFile[indexOfTrackType..mkvStringFile.indexOf("\n", indexOfTrackType)-1];

            if (trackTypeStr.equals(establishedLanguage.get("trackSubtitlesType")))
            {
                log.info "Encontrada pista de subtítulos"

                //Vamos a localizar el idioma
                if ((positionOfNextLang == -1) || ((positionOfNextLang > positionOfNextTrack) && (positionOfNextTrack != -1)))
                {
                    log.info "No encontrado idioma de la pista"
                }
                else
                {
                    language = mkvStringFile[mkvStringFile.indexOf(establishedLanguage.get("language"), iteratorTracks)+8 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Idioma", iteratorTracks))-1]
                    log.info "Idioma de la pista: " + language
                }

                if (((positionOfNextName != -1) && (positionOfNextName <= positionOfNextTrack)) || ((positionOfNextName != -1) && (positionOfNextTrack < 0)))
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
                subtitleTracks.add(subtitleTrack);
            }
            iteratorTracks = positionOfNextTrack;
        }
        return subtitleTracks;
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************


    def getAudioTracks (HashMap<String, String> establishedLanguage)
    {
        int NumberOfTracks = getNumberOfTracks(establishedLanguage);
        List<AudioTrackModel> audioTracks = new ArrayList<>();
        int iteratorTracks = mkvStringFile.indexOf(establishedLanguage.get("track") + "\n");
        String keyTrackAudioType = establishedLanguage.get("trackAudioType");

        for (int i=0; i<NumberOfTracks; i++)
        {
            int positionOfNextLang = mkvStringFile.indexOf(establishedLanguage.get("language"), iteratorTracks);
            int positionOfNextTrack = mkvStringFile.indexOf(establishedLanguage.get("track")+"\n", iteratorTracks+1);
            int positionOfNextCodecId = mkvStringFile.indexOf(establishedLanguage.get("codecId"), iteratorTracks+1);
            int positionOfNextChannels = mkvStringFile.indexOf(establishedLanguage.get("channels")+":", iteratorTracks+1);
            String language = "Unknown"
            def codecId = "Unknown"
            int codec = 3
            int channels = 0

            int indexOfTrackType = mkvStringFile.indexOf(establishedLanguage.get("trackType")+":", iteratorTracks) + 15;
            String trackType = mkvStringFile[indexOfTrackType..mkvStringFile.indexOf("\n", indexOfTrackType)-1];

            if (trackType.equals(keyTrackAudioType))
            {
                log.info "Encontrada pista de audio"

                //Vamos a localizar el idioma
                if ((positionOfNextLang == -1) || ((positionOfNextLang > positionOfNextTrack) && (positionOfNextTrack != -1)))
                {
                    log.info "No encontrado idioma de la pista"
                }
                else
                {
                    language = mkvStringFile[mkvStringFile.indexOf(establishedLanguage.get("language"), iteratorTracks)+8 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Idioma", iteratorTracks))-1]
                    log.info "Idioma de la pista: " + language
                }

                //Vamos a localizar el CodecId
                if ((positionOfNextCodecId == -1) || ((positionOfNextCodecId > positionOfNextTrack) && (positionOfNextTrack != -1)))
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
                if ((positionOfNextChannels == -1) || ((positionOfNextChannels > positionOfNextTrack)&& (positionOfNextTrack != -1)))
                {
                    log.info "No encontrado el número de canales de la pista"
                }
                else
                {
                    String channelsString = mkvStringFile[mkvStringFile.indexOf(establishedLanguage.get("channels"), iteratorTracks)+9 .. mkvStringFile.indexOf("\n",mkvStringFile.indexOf("Canales", iteratorTracks))]
                    channels = channelsString.toInteger();
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
                audioTracks.add(audioTrack);
            }
            iteratorTracks = positionOfNextTrack;
        }

        return audioTracks;
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    private int getXResolution(HashMap<String, String> establishedLanguage)
    {
        String key = establishedLanguage.get("pixelsWidth");
        final int xResolution = -1;
        if (mkvStringFile.contains(key)) {
            int posXResolution = mkvStringFile.indexOf(key)
            posXResolution += key.size() + ": ".size();
            xResolution = mkvStringFile[posXResolution .. mkvStringFile.indexOf("\n", posXResolution)-1].toInteger();
        }
        return xResolution;
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    int getYResolution(HashMap<String, String> establishedLanguage)
    {
        String key = establishedLanguage.get("pixelsHeight");
        int yResolution = -1;
        def posYResolution = mkvStringFile.indexOf(key)
        if (mkvStringFile.contains(key)) {
            posYResolution += key.size() + ": ".size();
            yResolution = mkvStringFile[posYResolution .. mkvStringFile.indexOf("\n", posYResolution)-1].toInteger();
        }
        return yResolution;
    }

    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    def getVideoCodec (HashMap<String, String> establishedLanguage)
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

    private Long getSize (HashMap<String, String> establishedLanguage)
    {
        int posSize = mkvStringFile.indexOf("+ " + establishedLanguage.get("segmentSize") + " ")
        if (posSize == -1)
            return -1
        else
        {
            posSize += 19;
            return Long.parseLong(mkvStringFile[posSize .. mkvStringFile.indexOf("\n", posSize)-1]);
        }
    }


    //*******************************************************************************
    //*******************************************************************************
    //*******************************************************************************

    private HashMap<String, String> getLanguageHashMap()
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

    public FilmDetailsFromMKVInfo getFilmDetails(String mkvStringFileIn)
    {

        if (mkvStringFileIn == null) {
            log.error "Error parsing null mkv file"
            return null
        }
        else {
            mkvStringFile = mkvStringFileIn;
        }

        FilmDetailsFromMKVInfo filmProcessed = new FilmDetailsFromMKVInfo();

        HashMap<String, String> establishedLanguage = getLanguageHashMap();

        if (establishedLanguage == null)
        {
            return null
        }

        filmProcessed.size = getSize(establishedLanguage);
        filmProcessed.videoCodec = getVideoCodec(establishedLanguage);
        filmProcessed.setxResolution(getXResolution(establishedLanguage));
        filmProcessed.setyResolution(getYResolution(establishedLanguage));

        filmProcessed.container = "mkv";
        filmProcessed.duration = getFilmDuration(establishedLanguage);
        filmProcessed.audioTracks = getAudioTracks(establishedLanguage);
        filmProcessed.subtitleTracks = getSubtitleTracks(establishedLanguage);

        return filmProcessed
    }

}
