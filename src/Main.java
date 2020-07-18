import java.util.ArrayList;

//Server
public class Main {
	
	static ArrayList<Conversation> conversations;

	public static void addConversation(Conversation c) {
		conversations.add(c);
	}
	
	public static void main(String[] args) {
		conversations = new ArrayList<Conversation>();
		DB.Init();
		DB.printAllDomains();
		Receiver r = new Receiver();
		Thread rec = new Thread(r);
		Network.connect(4225);
		rec.start();
		System.out.println("Server Connected: Name: ----"+ " IP: "+Network.ip+" Port: "+Network.port);

	}

}
