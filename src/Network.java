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
	
	public static void connect(int port) {
		try {
			Network.port = port;
			socket = new ServerSocket(port);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void TransmitMessageToServer() {
		
	}
	
	public static void TransmitPuzzleToServer() {
		
	}
	
	public static void TransmitPuzzleToClient() {
		
	}
	
	
}
