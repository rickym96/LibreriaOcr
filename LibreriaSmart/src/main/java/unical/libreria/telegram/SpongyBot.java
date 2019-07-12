package unical.libreria.telegram;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import unical.libreria.detection.OcrDetection;

public class SpongyBot extends TelegramLongPollingBot{
	Long timeP;
	Long timeN;
	Boolean find=false;
	Boolean founded = false;
	String book=null;
	Boolean answer = false;
	Boolean again = false;
	

	public void onUpdateReceived(Update update) {

		//Stringa test per vedere su console i messaggi
		System.out.println(update.getMessage().getText());


		//        System.out.println(update.getMessage().getFrom().getFirstName() );

		String command=update.getMessage().getText();

		SendMessage message = new SendMessage();
		message.setChatId(update.getMessage().getChatId());
		if(answer) {
			String mex = update.getMessage().getText();
			mex = mex.toUpperCase();
			if(!mex.equals("S") || !mex.equals("N")) {
				message.setText("Scrivi bene ( S or N)");
				sendMessaggio(message);
			}
			else if(mex.equals("S")){
				message.setText("Perfetto! Buona Lettura!");
				sendMessaggio(message);	
				answer = false;
			}
			else {
				message.setText("Vuoi cercare un'altra copia? (S or N)");
				sendMessaggio(message);
				answer = false;
				again = true;
			}
		
		}
		
		else if(again) {
			String mex = update.getMessage().getText();
			mex = mex.toUpperCase();
			if(!mex.equals("S") || !mex.equals("N")) {
				message.setText("Scrivi bene (S or N)");
				sendMessaggio(message);
			}
			else if(mex.equals("S")){
				message.setText("Ok, cerco subito!");
				sendMessaggio(message);	
				again=false;
				find=true;
				
			}
			else {
				message.setText("ciao ciao!");
				sendMessaggio(message);
				again = false;
			}
		}
		
		else if(founded) {
			timeP=System.currentTimeMillis();
			waitTelosb(update, message);

		}
		else if(find==false)	{	
			//Comando Ping
			if(command.equals("/ping")){
				message.setText("Sono pronto");
				sendMessaggio(message);
			}
			//Comando Cerca/Find
			else if(command.equals("/find")){
				message.setText("Ok inserisci il titolo del libro da cercare");
				sendMessaggio(message);

				find=true;

			}
			//Comando Reboot
			else if(command.equals("/reboot")){
				reboot(message);
			}
			//Comando Non riconosciuto
			else {
				message.setText("Comando non Riconosciuto");
				sendMessaggio(message);
			}

		}
		else if(!command.equals("/find")) {
			book=update.getMessage().getText();
			find(message, update);
			find=false;
		}
		else if(find) {
			findAgain(message, book);
			find=false;
		}

		

	}

	public void waitTelosb(Update update, SendMessage message) {
		
		try {
			Process p = Runtime.getRuntime().exec("python3 C:\\Users\\ricky\\git\\LibreriaOcr\\LibreriaSmart\\lib\\seriale.py");
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timeN=System.currentTimeMillis();
		if(timeN-timeP<60000)
			findlast(book, message, update);
	}

	public void findlast(String book, SendMessage message, Update update) {

		//Comando terminale cercalibro
		OcrDetection od = new OcrDetection();

		try {
			if(od.FindText(book)) {
				waitTelosb(update, message);
			}
			else {
				message.setText("Il libro è stato appena prelevato, sei stato tu? (S or N)");
				sendMessaggio(message);
				founded = false;
				answer= true;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void find(SendMessage message, Update update) {

		//Comando terminale cercalibro
		OcrDetection od = new OcrDetection();

		try {
			if(od.FindText(update.getMessage().getText())) {
				message.setText("Libro Trovato!Sarà acceso un led.");
				sendMessaggio(message);
				founded = true;
			}
			else {
				message.setText("Libro NON trovato...");
				sendMessaggio(message);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void findAgain(SendMessage message, String book) {
		//Comando terminale cercalibro
				OcrDetection od = new OcrDetection();

				try {
					if(od.FindText(book)) {
						message.setText("Libro Trovato!Sarà acceso un led.");
						sendMessaggio(message);
						founded = true;
					}
					else {
						message.setText("NON ci sono altre copie del libro cercato...");
						sendMessaggio(message);
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	public void reboot(SendMessage message) {
		message.setText("Il Sistema sarà riavviato ora, ci potranno volere diversi minuti");
		sendMessaggio(message);
		try {
			Process p = Runtime.getRuntime().exec("reboot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessaggio(SendMessage message) {
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public String getBotUsername() {
		return "spongy_bot";
	}

	public String getBotToken() {
		return "779119938:AAFaQE-q1grAVDl4yLVhONGaTGnpL6hbjjk";
	}





}
