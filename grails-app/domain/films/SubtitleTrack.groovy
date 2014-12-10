package films

class SubtitleTrack {

    Language language
    int type

    String comments

    static belongsTo = [savedFilm : SavedFilm]

    static constraints = {
        savedFilm bindable : false
        language nullable : true, bindable : false
        comments nullable : true
    }
}
