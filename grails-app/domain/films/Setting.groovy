package films

class Setting {

    String settingName
    String value

    static constraints = {
        settingName nullable : false
    }
}
