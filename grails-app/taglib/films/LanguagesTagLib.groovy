package films

import films.Model.AudioTrackModel
import films.Model.SubtitleTrackModel

class LanguagesTagLib {
    static defaultEncodeAs = [taglib:'text']

    def languagesAudio = { attrs, body ->
        List<AudioTrackModel> audioTracks = attrs.audioTracks
        String languageCode = attrs.displayLanguage
        List<String> languagesFound = new ArrayList<String>()
        Boolean firstLanguage = true

        for (AudioTrackModel audioTrack : audioTracks)
        {
            if (!languagesFound.contains(audioTrack.language.code))
            {
                languagesFound.add(audioTrack.language.code)
                if (!firstLanguage)
                {
                    out << ', '
                }
                else
                    firstLanguage = false
                out << audioTrack.language.languageNames.find {it-> it.languageCodeOfName.equals(languageCode)}.name
            }
        }
    }

    def languagesSubtitle = { attrs, body ->
        List<SubtitleTrackModel> subtitleTracks = attrs.subtitleTracks
        String languageCode = attrs.displayLanguage
        List<String> languagesFound = new ArrayList<String>()
        Boolean firstLanguage = true

        for (SubtitleTrackModel subtitleTrack : subtitleTracks)
        {
            if ((!languagesFound.contains(subtitleTrack.language.code))
                    && (subtitleTrack.type == 1))
            {
                languagesFound.add(subtitleTrack.language.code)
                if (!firstLanguage)
                {
                    out << ', '
                }
                else
                    firstLanguage = false
                out << subtitleTrack.language.languageNames.find {it-> it.languageCodeOfName.equals(languageCode)}.name
            }
        }
    }
}
