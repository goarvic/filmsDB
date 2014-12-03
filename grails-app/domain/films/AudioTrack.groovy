package films

class AudioTrack {

    Language language
    int audioType // 0 = Mono | 1 = Stereo | 2 = 6 channels | 3 = Unknown...
    int compression // 0 = mp3 | 1 = AC3 | 2 = DTS | 3 = unknown
    String codecId

    static belongsTo = [savedFilm : SavedFilm]

    String comments





    static constraints = {

        comments nullable : true
        language nullable : true
    }
}
