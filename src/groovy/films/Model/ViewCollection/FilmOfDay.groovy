package films.Model.ViewCollection

import org.apache.commons.lang.time.DateUtils

/**
 * Created by X51104GO on 31/12/2014.
 */
class FilmOfDay {

    FilmBasicInfo filmOfTheDay = null
    Date dateOfFilmOfTheDay = null


    public synchronized void setSecureFilmOfTheDay(FilmBasicInfo filmOfDay)
    {
        this.filmOfTheDay = filmOfDay
        dateOfFilmOfTheDay = new Date()
    }

    public synchronized FilmBasicInfo getSecureFilmOfTheDay()
    {
        Date today = new Date()
        if (this.dateOfFilmOfTheDay == null || !DateUtils.isSameDay(today, dateOfFilmOfTheDay))
            return null
        else
            return filmOfTheDay
    }


}
