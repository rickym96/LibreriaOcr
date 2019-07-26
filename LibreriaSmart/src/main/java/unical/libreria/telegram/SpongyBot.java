package unical.libreria.telegram;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import unical.libreria.detection.OcrDetection;

//Questa classe coordina il funzionamento del nostro bot telegram. Il bot starà continuamente in ascolto di nuovi messaggi
//Appena arriva un nuovo messaggio aggiornerà l'UPDATE

public class SpongyBot extends TelegramLongPollingBot{
	Long timeP;
	Long timeN;
	Boolean find=false;
	Boolean founded = false;
	String book=null;
	Boolean answer = false;
	Boolean again = false;
	int Book_position;


	public void onUpdateReceived(Update update) {

		//Stringa test per vedere su console i messaggi
		System.out.println(update.getMessage().getText());


		//System.out.println(update.getMessage().getFrom().getFirstName() );

		String command=update.getMessage().getText();

		SendMessage message = new SendMessage();
		message.setChatId(update.getMessage().getChatId());
		
		if(answer) {
			String mex = update.getMessage().getText();
			mex = mex.toUpperCase();

			if(!mex.equals("S") && !mex.equals("N")) {
				message.setText("Scrivi bene ( S or N)");
				sendMessaggio(message);
			}
			else if(mex.equals("S")){
				message.setText("Perfetto! Buona Lettura!");
				sendMessaggio(message);	
				answer = false;
			}
			else {
				message.setText("Vuoi cercare una copia del Libro? (S or N)");
				sendMessaggio(message);
				answer = false;
				again = true;
			}

		}

		else if(again) {
			String mex = update.getMessage().getText();
			mex = mex.toUpperCase();
			if(!mex.equals("S") && !mex.equals("N")) {
				message.setText("Scrivi bene (S or N)");
				sendMessaggio(message);
			}
			else if(mex.equals("S")){
				message.setText("Ok lo cerco subito!");
				sendMessaggio(message);	
				again=false;
				find=true;
				findAgain(message, book, update);
				find=false;

			}
			else {
				message.setText("Ok Ciao!");
				sendMessaggio(message);
				again = false;
			}
		}

		else if(find==false)	{	
			//Comando Ping
			if(command.equals("/ping")){
				message.setText("Ciao, sono SpongyBot e sono pronto ad aiutarti!");
				sendMessaggio(message);
			}
			//Comando Help
			else if(command.equals("/help")){
				message.setText("Ciao sono SpongyBot!! Il mio compito è aiutarti nella ricerca di un libro nella tua libreria. Per cercare un libro puoi usare il comando /find inserendo poi il nome del libro da farmi cercare. Se il libro sarà presente nella libreria ti risponderò positivamente ed avrai un minuto di tempo per prelevarlo. In questo minuto io farò dei controlli per assicurarmi che il libro venga prelevato e se ciò non avverrà annullerò l'operazione. In caso noterai alcuni malfunzionamenti in me potrai riavviarmi tramite il comando reboot ma attento perchè richiede alcuni minuti! Sono a tua disposizione! :D");
				sendMessaggio(message);
			}
			
			//Comando Cerca/Find
			else if(command.equals("/find")){
				message.setText("Hai avviato il processo di Ricerca, per poter procedere inserisci il Titolo del Libro da farmi cercare! :) ");
				sendMessaggio(message);

				find=true;

			}
			//Comando Reboot
			else if(command.equals("/reboot")){
				reboot(message);
			}
			//Comando Non riconosciuto
			else {
				message.setText("Attenzione, non ho Riconosciuto il comando inserito");
				sendMessaggio(message);
			}

		}
		else if(!command.equals("/find")) {
			book=update.getMessage().getText();
			message.setText("L'operazione può richiedere alcuni secondi...");
			sendMessaggio(message);
			find(message, update);
			find=false;
			if(founded){
				
				timeP=System.currentTimeMillis();
				waitTelosb(update, message);
			}

		}



	}

	public void waitTelosb(Update update, SendMessage message) {
		System.out.println("wait");
		try {
			Process p = Runtime.getRuntime().exec("python3 /home/pi/Desktop/seriale.py");
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ricevuto");
		timeN=System.currentTimeMillis();
		if(timeN-timeP<60000)
			findlast(book, message, update);
		else{
			message.setText("Tempo scaduto! Ci hai messo troppo tempo per prelevare il libro ed ho quindi annullato l'operazione. Puoi ricominciare la ricerca se desideri tramite il comando find");
			sendMessaggio(message);
		}
	}
	

	public void findlast(String book, SendMessage message, Update update) {

		//Comando terminale cercalibro
		OcrDetection od = new OcrDetection();

		try {
			int indice=od.FindText(book);
			if(indice!=-1 && indice==Book_position) {
				waitTelosb(update, message);
			}
			else {
				message.setText("Ho appena rilevato che il libro da te cercato è stato prelevato, sei stato tu? (S or N)");
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
			int indice = od.FindText(update.getMessage().getText());
			if(indice >=0 && indice <=4) {
				message.setText("Il Libro cercato è stato trovato nella sezione A" );
				sendMessaggio(message);
				founded = true;
				Book_position=indice;
			}
			else if(indice >=5 && indice <10){
				message.setText("Il Libro cercato è stato trovato nella sezione B" );
				sendMessaggio(message);
				founded = true;
				Book_position=indice;
			}
			else {
				message.setText("Scusa ma purtroppo non sono riuscito a trovare il libro da te inserito :(");
				sendMessaggio(message);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void findAgain(SendMessage message, String book, Update update) {
		//Comando terminale cercalibro
		OcrDetection od = new OcrDetection();

		
		try {
			int indice = od.FindWithOutGetPhoto(book);
			
			if(indice >=0 && indice <=4) {
				message.setText("Il Libro cercato è stato trovato nella sezione A" );
				sendMessaggio(message);
				Book_position=indice;
				timeP=System.currentTimeMillis();
				waitTelosb(update, message);
			}
			else if(indice >=5 && indice <10){
				message.setText("Il Libro cercato è stato trovato nella sezione B" );
				sendMessaggio(message);
				Book_position=indice;
				timeP=System.currentTimeMillis();
				waitTelosb(update, message);
			}
			else {
				message.setText("NON ci sono altre copie del libro cercato...");
				sendMessaggio(message);
			}


		}
		catch (FileNotFoundException e) {
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
