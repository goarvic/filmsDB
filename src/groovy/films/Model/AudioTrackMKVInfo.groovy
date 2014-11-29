package films.Model

/**
 * Created with IntelliJ IDEA.
 * User: vickop
 * Date: 23/01/14
 * Time: 22:23
 * To change this template use File | Settings | File Templates.
 */
class AudioTrackMKVInfo {

    LanguageModel language
    String languageName
    int audioType // 0 = Mono | 1 = Stereo | 2 = 6 channels | 3 = Unknown...
    int compression // 0 = mp3 | 1 = AC3 | 2 = DTS | 3 = unknown
    String codecId

    String comments

}
