package films.database

import films.AudioTrack
import films.Model.AJAXCalls.AvailableSpaceOnDisk
import films.Model.AJAXCalls.AvailableSpaceOnDiskResponse
import films.Model.AudioTrackModel
import films.Model.SavedFilmModel
import films.Model.SubtitleTrackModel
import films.SavedFilm
import films.SubtitleTrack
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

@Transactional
class SavedFilmService {

    AudioTracksService audioTracksService
    SubtitleTracksService subtitleTracksService

    SavedFilmModel bindSavedFilmFromDomainToModel (SavedFilm savedFilmDomain)
    {
        if (savedFilmDomain == null)
        {
            log.error "Error binding null SavedFilmModel domain instance"
            return null
        }
        SavedFilmModel savedFilmModel = new SavedFilmModel()
        DataBindingUtils.bindObjectToInstance(savedFilmModel,savedFilmDomain)

        savedFilmModel.audioTracks = new ArrayList<AudioTrackModel>()
        for(AudioTrack audioTrackDomain : savedFilmDomain.audioTracks)
        {
            AudioTrackModel audioTrackModel = audioTracksService.bindAudioTrackFromDomain(audioTrackDomain)
            savedFilmModel.audioTracks.add(audioTrackModel)
        }

        savedFilmModel.subtitleTracks = new ArrayList<SubtitleTrackModel>()
        for(SubtitleTrack subtitleTrackDomain : savedFilmDomain.subtitleTracks)
        {
            SubtitleTrackModel subtitleTrackModel = subtitleTracksService.bindSubtitleTrack(subtitleTrackDomain)
            savedFilmModel.subtitleTracks.add(subtitleTrackModel)
        }
        return savedFilmModel
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    SavedFilm getAndUpdateDomainInstance (SavedFilmModel savedFilmModel)
    {
        if (savedFilmModel == null)
        {
            log.error "Error saving null SavedFilmModel"
            return null
        }

        SavedFilm savedFilmDomain

        if (savedFilmModel.id >= 0)
        {
            savedFilmDomain = SavedFilm.findById(savedFilmModel.id)
            if (savedFilmDomain == null)
            {
                log.error "Error retrieving domains instance on database"
                return null
            }
        }
        else
            savedFilmDomain = new SavedFilm()

        DataBindingUtils.bindObjectToInstance(savedFilmDomain,savedFilmModel)

        if (savedFilmDomain.audioTracks != null)
            savedFilmDomain.audioTracks.removeAll()
        else
            savedFilmDomain.audioTracks = new ArrayList<AudioTrack>()

        for (AudioTrackModel audioTrackModel : savedFilmModel.audioTracks)
        {
            AudioTrack audioTrackDomain = audioTracksService.getUpdatedAudioTrackDomainInstance(audioTrackModel)
            audioTrackDomain.savedFilm = savedFilmDomain
            savedFilmDomain.audioTracks.add(audioTrackDomain)
        }

        if (savedFilmDomain.subtitleTracks != null)
            savedFilmDomain.subtitleTracks.removeAll()
        else
            savedFilmDomain.subtitleTracks = new ArrayList<SubtitleTrack>()

        for (SubtitleTrackModel subtitleTrackModel : savedFilmModel.subtitleTracks)
        {
            SubtitleTrack subtitleTrackDomain = subtitleTracksService.getAndUpdateSubtitleTrackDomainInstance(subtitleTrackModel)
            subtitleTrackDomain.savedFilm = savedFilmDomain
            savedFilmDomain.subtitleTracks.add(subtitleTrackDomain)
        }



        return savedFilmDomain
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    AvailableSpaceOnDiskResponse enoughSpaceForFilmInDisc(AvailableSpaceOnDisk paramsObject)
    {
        AvailableSpaceOnDiskResponse response = new AvailableSpaceOnDiskResponse()
        response.discReference = paramsObject.discReference
        long sizeOfDisk = 25050000000
        List<SavedFilm> savedFilmsCurrentlyInDisc = SavedFilm.findAllById(paramsObject.discReference)

        long currentFreeSize = sizeOfDisk
        for (SavedFilm savedFilm : savedFilmsCurrentlyInDisc)
        {
            currentFreeSize = currentFreeSize - savedFilm.size
        }
        response.sizeFreeAvailable = currentFreeSize

        if (paramsObject.size > currentFreeSize)
            response.enoughSpace = false
        else
            response.enoughSpace = true

        return response
    }


    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

    int getNextDisk()
    {
        List<SavedFilm> listLastSavedFilm = SavedFilm.list(sort:"discReference", order:"desc", max:1)
        if (listLastSavedFilm.size() == 0)
            return 1

        SavedFilm lastSavedFilm = listLastSavedFilm.get(0)
        AvailableSpaceOnDisk availableSpaceOnDiskParam = new AvailableSpaceOnDisk(discReference: lastSavedFilm.discReference, size: 6000000000)
        if (enoughSpaceForFilmInDisc(availableSpaceOnDiskParam))
            return lastSavedFilm.discReference
        else
            return lastSavedFilm.discReference + 1
    }

    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************
    //**************************************************************************************

}
