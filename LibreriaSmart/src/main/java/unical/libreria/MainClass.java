package unical.libreria;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import unical.libreria.telegram.SpongyBot;

public class MainClass {

    public static void main(String[] args) {


    	//Inizializzazione Modulo Telegram e avvio
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new SpongyBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
s
        
        

    }
}
