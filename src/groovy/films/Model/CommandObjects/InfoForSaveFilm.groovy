package films.Model.CommandObjects

import films.Model.AudioTrackModel
import films.Model.SubtitleTrackModel
import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile

/**
 * Created by vickop on 8/12/14.
 */
@Validateable
class InfoForSaveFilm {

    MultipartFile poster
    int discReference
    List<AudioTrackModel> audioTracks
    List<SubtitleTrackModel> subtitleTracks
    String filmVersion
    String fileName

}
