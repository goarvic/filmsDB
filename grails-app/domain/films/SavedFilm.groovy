package films

class SavedFilm {

    //FilmModel film    //Clave foranea. Relacion many to one

    String filmVersion //Puede ser versión cinematográfica, versión del director, etc...

    static hasMany = [audioTracks: AudioTrack, subtitleTracks: SubtitleTrack]

    static belongsTo = [film : Film]

    int duration //En segundos

    Date dateCreated

    //*************************************
    String videoCodec
    int yResolution
    int xResolution
    String container
    //*************************************

    def beforeInsert() {
        dateCreated = new Date()
    }

    int discReference

    static constraints = {
        film bindable : false
        audioTracks bindable : false
        subtitleTracks bindable : false
        videoCodec nullable : false
        container nullable : false
        filmVersion nullable : false

    }
}
