package unical.libreria.serial;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Communicator comunicatore = new Communicator();

		comunicatore.searchForPorts();
		comunicatore.connect("COM4");
		comunicatore.initIOStream();
		comunicatore.disconnect();
	}

}
