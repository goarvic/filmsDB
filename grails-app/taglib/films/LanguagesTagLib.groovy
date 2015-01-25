package films

import films.Model.AudioTrackModel
import films.Model.SubtitleTrackModel

class LanguagesTagLib {
    static defaultEncodeAs = [taglib:'text']

    def languagesAudio = { attrs, body ->
        List<AudioTrackModel> audioTracks = attrs.audioTracks
        List<String> languagesFound = new ArrayList<String>()
        Boolean firstLanguage = true

        for (AudioTrackModel audioTrack : audioTracks)
        {
            if (!languagesFound.contains(audioTrack.language.localName))
            {
                languagesFound.add(audioTrack.language.localName)
                if (!firstLanguage)
                {
                    out << ', '
                }
                else
                    firstLanguage = false
                out << audioTrack.language.localName
            }
        }
    }

    def languagesSubtitle = { attrs, body ->
        List<SubtitleTrackModel> subtitleTracks = attrs.subtitleTracks
        List<String> languagesFound = new ArrayList<String>()
        Boolean firstLanguage = true

        for (SubtitleTrackModel subtitleTrack : subtitleTracks)
        {
            if ((!languagesFound.contains(subtitleTrack.language.localName))
                    && (subtitleTrack.type == 1))
            {
                languagesFound.add(subtitleTrack.language.localName)
                if (!firstLanguage)
                {
                    out << ', '
                }
                else
                    firstLanguage = false
                out << subtitleTrack.language.localName
            }
        }
    }
}
