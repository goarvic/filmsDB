package films.database

import films.AudioTrack
import films.Model.LanguageModel
import grails.transaction.Transactional

@Transactional
class AudioTracksService {

    private List<films.Model.AudioTrack> bindAudioTracksFromDomain(List<films.AudioTrack> audioTracksDomain)
    {
        if (audioTracksDomain == null)
            return null
        List<AudioTrack> audioTracksModel = new ArrayList<AudioTrack>()

        for (films.AudioTrack audioTrackDomain : audioTracksDomain)
        {
            AudioTrack audioTrackModel = new AudioTrack()
            audioTrackModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class")&&!propertyName.equals("language"))
                    audioTrackModel.setProperty(propertyName, audioTrackDomain.getProperty(propertyName))
            }
            if (audioTrackDomain.language != null)
            {
                LanguageModel language = new LanguageModel()
                language.properties.each{propertyName, propertyValue ->
                    if (!propertyName.equals("class"))
                        language.setProperty(propertyName, audioTrackDomain.language.getProperty(propertyName))
                }
            }
            audioTracksModel.add(audioTrackModel)
        }
        return audioTracksModel
    }
}
