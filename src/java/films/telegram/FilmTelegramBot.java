package films.telegram;

import org.apache.commons.lang.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class FilmTelegramBot extends TelegramLongPollingBot {

    private String botUserName;
    private String botToken;



    public FilmTelegramBot (String botUserName, String botToken) {
        this.botUserName = botUserName;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        // Esta función se invocará cuando nuestro bot reciba un mensaje

        // Se obtiene el mensaje escrito por el usuario
        final String messageTextReceived = update.getMessage().getText();

        // Se obtiene el id de chat del usuario
        final long chatId = update.getMessage().getChatId();

        // Se crea un objeto mensaje
        SendMessage message = new SendMessage();
        message.setChatId(Long.toString(chatId));
        message.setText(messageTextReceived);

        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // Se devuelve el nombre que dimos al bot al crearlo con el BotFather
        return botUserName;
    }

    @Override
    public String getBotToken() {
        // Se devuelve el token que nos generó el BotFather de nuestro bot
        return botToken;
    }

    public void sendMessageToChannel(String message, String channelId) throws TelegramApiException {
        if (StringUtils.isNotEmpty(botUserName) && StringUtils.isNotEmpty(botToken) && StringUtils.isNotEmpty(channelId)){
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(channelId);
            sendMessage.setText(message);
            sendMessage.enableMarkdown(true);

            execute(sendMessage);
        }
    }

}
