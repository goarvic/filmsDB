package films.Model

import films.LanguageName

/**
 * Created with IntelliJ IDEA.
 * User: vickop
 * Date: 27/01/14
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
class LanguageModel {
    long id = -1
    String name
    String localName
    List<LanguageNameModel> languageNames = new ArrayList<LanguageNameModel>()
    String code
}
