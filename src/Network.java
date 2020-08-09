import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Network {

	public static ServerSocket socket;
	public static String name;
	public static String ip;
	public static int port;
	
	public static void connect() {
		connect(0);
	}
	
	private static void connect(int index) {
		if(index >= DB.domains.size()) {
			System.out.println("Sorry no more available servers :(");
			System.exit(0);
		}
		try {
			
			Network.port = DB.domains.get(index).port;
			socket = new ServerSocket(port);
			Network.name = DB.domains.get(index).name;
			
		} catch (IOException e) {
			connect(index +1);
		}
	}

	
	
	public static void TransmitMessageToServer() {
		
	}
	
	public static void TransmitPuzzleToServer() {
		
	}
	
	public static void TransmitPuzzleToClient() {
		
	}
	
	
}
