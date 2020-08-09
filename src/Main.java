import java.util.ArrayList;

//Server
public class Main {
	
	
	public static void main(String[] args) {
		Conversation.conversations = new ArrayList<Conversation>();
		DB.Init();
		Receiver r = new Receiver();
		Thread rec = new Thread(r);
		Network.connect();
		rec.start();
		System.out.println("Server Connected: Name: "+Network.name+ " IP: "+Network.ip+" Port: "+Network.port);

	}

}
