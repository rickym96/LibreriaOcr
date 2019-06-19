package unical.libreria.serial;

import java.io.IOException;

import javax.jms.MessageListener;

import net.tinyos.message.Message;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

public class SerialCommunicator {
	
	public static void main(String[] args) {
//		 String source = args[1];
//		 PhoenixSource phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
		

		
		 MoteIF mif = new MoteIF();
		 String mes = "prova";
		 byte[] bit = mes.getBytes();
		 Message messaggio = new Message(bit);
		
		 try {
			mif.send(0, messaggio);
		} catch (IOException e) {
			System.out.println("Non mandato");
			e.printStackTrace();
		}
		}

	
}
