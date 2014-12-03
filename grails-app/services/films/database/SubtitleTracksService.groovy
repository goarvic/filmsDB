package films.database

import films.Model.LanguageModel
import grails.transaction.Transactional

@Transactional
class SubtitleTracksService {

    private List<films.Model.SubtitleTrack> bindSubtitleTracks(List<films.SubtitleTrack> subtitleTracksDomain)
    {
        if (subtitleTracksDomain == null)
            return null
        List<films.Model.SubtitleTrack> subtitleTracksModel = new ArrayList<films.Model.SubtitleTrack>()

        for (films.SubtitleTrack subtitleTrackDomain : subtitleTracksDomain)
        {
            films.Model.SubtitleTrack subtitleTrackModel = new films.Model.SubtitleTrack()
            subtitleTrackModel.properties.each{propertyName, propertyValue ->
                if (!propertyName.equals("class")&&!propertyName.equals("language"))
                    subtitleTrackModel.setProperty(propertyName, subtitleTrackDomain.getProperty(propertyName))
            }
            if (subtitleTrackDomain.language != null)
            {
                LanguageModel language = new LanguageModel()
                language.properties.each{propertyName, propertyValue ->
                    if (!propertyName.equals("class"))
                        language.setProperty(propertyName, subtitleTrackDomain.language.getProperty(propertyName))
                }
            }
            subtitleTracksModel.add(subtitleTrackModel)
        }
        return subtitleTracksModel
    }
}
