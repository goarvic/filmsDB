package films.Model

/**
 * Created by vickop on 29/11/14.
 */
class SavedFilm {
    long id = null

    String filmVersion //Puede ser versión cinematográfica, versión del director, etc...

    List<AudioTrack> audioTracks


    List<SubtitleTrack> subtitleTracks

    int duration //En segundos

    String videoCodec
    int yResolution
    int xResolution
    String container

    int discReference
}
