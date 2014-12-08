package films.database

import films.Model.SubtitleTrackModel
import films.SubtitleTrack
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class SubtitleTracksService {

    LanguageService languageService

    SubtitleTrack getAndUpdateSubtitleTrackDomainInstance(SubtitleTrackModel subtitleTrackModel)
    {
        if (subtitleTrackModel == null)
        {
            log.error "Error binding null SubtitleTrackModel"
            return null
        }

        SubtitleTrack subtitleTrackDomain

        if (subtitleTrackModel.id == -1)
        {
            subtitleTrackDomain = new SubtitleTrack()
        }
        else
        {
            subtitleTrackDomain = SubtitleTrack.findById(subtitleTrackModel.id)
            if (subtitleTrackDomain == null)
            {
                log.error "Error retrieving domain instance from database"
                return null
            }
        }


        if (subtitleTrackModel.language != null)
        {
            subtitleTrackDomain.language = languageService.getUpdateAndSaveDomainInstance(subtitleTrackModel.language)
        }

        DataBindingUtils.bindObjectToInstance(subtitleTrackDomain,subtitleTrackModel)

        return subtitleTrackDomain
    }

    //**************************************************************************************************
    //**************************************************************************************************
    //**************************************************************************************************
    //**************************************************************************************************



     SubtitleTrackModel bindSubtitleTrack(SubtitleTrack subtitleTrackDomain)
    {
        if (subtitleTrackDomain == null)
            return null

        SubtitleTrackModel subtitleTrackModel = new SubtitleTrackModel()
        DataBindingUtils.bindObjectToInstance(subtitleTrackModel,subtitleTrackDomain)

        if (subtitleTrackDomain.language != null)
        {
            subtitleTrackModel.language = languageService.bindFromDomainToModel(subtitleTrackDomain.language)
        }
        return subtitleTrackModel
    }

}
