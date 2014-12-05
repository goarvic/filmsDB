package films.database

import films.AudioTrack
import films.Model.AudioTrackModel
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class AudioTracksService {

    LanguageService languageService


    AudioTrack getUpdatedAudioTrackDomainInstance(AudioTrackModel audioTrackModel)
    {
        AudioTrack audioTrackDomain
        if (audioTrackModel == null)
        {
            log.error "Error binding null AudioTrackModel"
            return null
        }

        if (audioTrackModel.id == -1)
        {
            audioTrackDomain = new AudioTrack()
        }
        else
        {
            audioTrackDomain = AudioTrack.findById(audioTrackModel.id)
            if (audioTrackDomain == null)
            {
                log.error "Error retrieving domain instance from database"
                return null
            }
        }
        if (audioTrackModel.language != null)
        {
            audioTrackDomain.language = languageService.getUpdateAndSaveDomainInstance(audioTrackModel.language)
        }
        DataBindingUtils.bindObjectToInstance(audioTrackDomain,audioTrackModel)
        return audioTrackDomain
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    AudioTrackModel bindAudioTrackFromDomain(AudioTrack audioTrackDomain)
    {
        AudioTrackModel audioTrackModel = new AudioTrackModel()
        DataBindingUtils.bindObjectToInstance(audioTrackModel,audioTrackDomain)
        if (audioTrackDomain.language != null)
        {
            audioTrackModel.language = languageService.bindFromDomainToModel(audioTrackDomain.language)
        }
        return audioTrackModel
    }

    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************
    //***********************************************************************************************************

    List<AudioTrackModel> bindAudioTracksFromDomain(List<AudioTrack> audioTracksDomain)
    {
        if (audioTracksDomain == null)
            return null

        List<AudioTrackModel> audioTracksModel = new ArrayList<AudioTrackModel>()

        for (AudioTrack audioTrackDomain : audioTracksDomain)
        {
            audioTracksModel.add(bindAudioTrackFromDomain(audioTrackDomain))
        }
        return audioTracksModel
    }

}
