package films.Model

/**
 * Created with IntelliJ IDEA.
 * User: vickop
 * Date: 23/01/14
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
class FilmDetailsFromMKVInfo {

    String filmVersion
    AudioTrack[] audioTracks
    SubtitleTrack[] subtitleTracks


    //*************************************
    String videoCodec
    int yResolution
    int xResolution
    String container
    //*************************************

    int duration //En segundos

}
