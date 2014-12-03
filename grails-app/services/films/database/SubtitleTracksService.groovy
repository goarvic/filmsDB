package films.database

import films.Model.LanguageModel
import grails.transaction.Transactional

@Transactional
class SubtitleTracksService {

    LanguageService languageService

    films.SubtitleTrack getAndUpdateSubtitleTrackDomainInstance(films.Model.SubtitleTrack subtitleTrackModel)
    {
        films.SubtitleTrack subtitleTrackDomain
        if (subtitleTrackModel == null)
        {
            log.error "Error binding null SubtitleTrackModel"
            return null
        }

        if (subtitleTrackModel.id == -1)
        {
            subtitleTrackDomain = new films.SubtitleTrack()
        }
        else
        {
            subtitleTrackDomain = films.SubtitleTrack.findById(subtitleTrackModel.id)
        }
        if (subtitleTrackModel.language != null)
        {
            subtitleTrackDomain.language = languageService.getLanguageByCode(subtitleTrackModel.language.code)
        }

        subtitleTrackModel.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class")&&!propertyName.equals("language"))
                subtitleTrackDomain.setProperty(propertyName, subtitleTrackModel.getProperty(propertyName))
        }

        return subtitleTrackDomain
    }

    //**************************************************************************************************
    //**************************************************************************************************
    //**************************************************************************************************
    //**************************************************************************************************



     films.Model.SubtitleTrack bindSubtitleTrack(films.SubtitleTrack subtitleTrackDomain)
    {
        if (subtitleTrackDomain == null)
            return null

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
        return subtitleTrackModel
    }


    //**************************************************************************************************
    //**************************************************************************************************
    //**************************************************************************************************
    //**************************************************************************************************

    List<films.Model.SubtitleTrack> bindSubtitleTracks(List<films.SubtitleTrack> subtitleTracksDomain)
    {
        if (subtitleTracksDomain == null)
            return null
        List<films.Model.SubtitleTrack> subtitleTracksModel = new ArrayList<films.Model.SubtitleTrack>()

        for (films.SubtitleTrack subtitleTrackDomain : subtitleTracksDomain)
        {
            films.Model.SubtitleTrack subtitleTrackModel = bindSubtitleTrack(subtitleTracksDomain)
            subtitleTracksModel.add(subtitleTrackModel)
        }
        return subtitleTracksModel
    }
}
