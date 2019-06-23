package unical.libreria.serial;

import java.io.IOException;

import javax.jms.MessageListener;

import com.sun.org.apache.xml.internal.security.Init;

import net.tinyos.message.Message;
import net.tinyos.message.MoteIF;
import net.tinyos.message.Receiver;
import net.tinyos.message.Sender;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.Messenger;
import net.tinyos.util.PrintStreamMessenger;

public class SerialCommunicator {
	protected PhoenixSource source;
	protected Sender sender;
	protected Receiver receiver;

	
	

	public static void main(String[] args) {
		//		 String source = args[1];
		 //PhoenixSource phoenix = BuildSource.makePhoenix(net.tinyos.util.PrintStreamMessenger.err);
		Messenger bell = new Messenger() {
			public void message(String s) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
	MoteIF mif = new MoteIF(bell);
//		String mes = "prova";
//
//		byte[] bit = mes.getBytes();
//		Message messaggio = new Message(bit);


//		try {
//			mif.send(0, messaggio);
//		} catch (IOException e) {
//			System.out.println("Non mandato");
//			e.printStackTrace();
//		}
	}


}
