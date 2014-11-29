package films

class SavedFilm {

    films.Film film    //Clave foranea. Relacion many to one

    String filmVersion //Puede ser versión cinematográfica, versión del director, etc...

    static hasMany = [audioTracks: AudioTrack, subtitleTracks: SubtitleTrack]

    int duration //En segundos

    //*************************************
    String videoCodec
    int yResolution
    int xResolution
    String container
    //*************************************

    int discReference

    static constraints = {
        videoCodec nullable : false
        container nullable : false
        filmVersion nullable : false

    }
}
