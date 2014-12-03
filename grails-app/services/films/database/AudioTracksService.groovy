package films.database

import films.AudioTrack
import films.Model.LanguageModel
import grails.transaction.Transactional

@Transactional
class AudioTracksService {

    LanguageService languageService


    films.AudioTrack getUpdatedAudioTrackDomainInstance(films.Model.AudioTrack audioTrackModel)
    {
        films.AudioTrack audioTrackDomain
        if (audioTrackModel == null)
        {
            log.error "Error binding null AudioTrackModel"
            return null
        }

        if (audioTrackModel.id == -1)
        {
            audioTrackDomain = new films.AudioTrack()
        }
        else
        {
            audioTrackDomain = films.AudioTrack.findById(audioTrackModel.id)
        }
        if (audioTrackModel.language != null)
        {
            audioTrackDomain.language = languageService.getLanguageByCode(audioTrackModel.language.code)
        }

        audioTrackModel.properties.each{propertyName, propertyValue ->
            if (!propertyName.equals("class")&&!propertyName.equals("language"))
                audioTrackDomain.setProperty(propertyName, audioTrackModel.getProperty(propertyName))
        }

        return audioTrackDomain
    }



    films.Model.AudioTrack bindAudioTrackFromDomain(films.AudioTrack audioTrackDomain)
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
            audioTrackModel.language = language
        }
        return audioTrackModel
    }


    List<films.Model.AudioTrack> bindAudioTracksFromDomain(List<films.AudioTrack> audioTracksDomain)
    {
        if (audioTracksDomain == null)
            return null

        List<AudioTrack> audioTracksModel = new ArrayList<AudioTrack>()

        for (films.AudioTrack audioTrackDomain : audioTracksDomain)
        {
            audioTracksModel.add(bindAudioTrackFromDomain(audioTrackDomain))
        }
        return audioTracksModel
    }



}
