package films.Model

/**
 * Created by vickop on 29/11/14.
 */
class SavedFilmModel {
    long id = -1

    String filmVersion //Puede ser versión cinematográfica, versión del director, etc...

    List<AudioTrackModel> audioTracks


    List<SubtitleTrackModel> subtitleTracks

    int duration //En segundos
    long size    //En bytes

    String videoCodec
    int yResolution
    int xResolution
    String container

    int discReference
    String fileName
}
