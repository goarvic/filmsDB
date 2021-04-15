package films

import films.Model.SettingModel
import films.database.SettingService
import films.telegram.FilmTelegramBot
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.BotSession
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Transactional
public class TelegramService {

    private FilmTelegramBot filmTelegramBot
    SettingService settingService


    void initializeBot() {
        SettingModel telegramUserNameBot = settingService.getSettingByName("telegramBotName");
        SettingModel telegramBotToken = settingService.getSettingByName("telegramBotToken");

        if (telegramUserNameBot != null && telegramBotToken != null) {
            try {
                // Se registra el bot
                final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                filmTelegramBot = new FilmTelegramBot(telegramUserNameBot.getValue(), telegramBotToken.getValue());
                telegramBotsApi.registerBot(filmTelegramBot);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    void notifyNewFilm(String urlFilm) {
        String channelId = settingService.getSettingByName("telegramPostNewFilmChannelId").getValue();
        try{
            filmTelegramBot.sendMessageToChannel(urlFilm, channelId)
        } catch(TelegramApiException exception) {
            log.info("Exception sending telegram notification: {}", exception.getMessage())
        }

    }
}
