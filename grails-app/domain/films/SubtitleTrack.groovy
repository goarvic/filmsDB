package films

class SubtitleTrack {

    Language language
    int type //0 = Completo | 1 = Forzado | 2 = Otros

    String comments

    static belongsTo = [savedFilm : SavedFilm]

    static constraints = {
        savedFilm bindable : false
        language bindable : false
        comments nullable : true
        language nullable : true
    }
}
