import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Conversation {
	
	public int id;
	public String sender;
	public String receiver;
	public String subject;
	public String body;
	
	public Conversation(int id, String sender, String receiver, String subject, String body) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
	}
	
	
	public void transmitToDestinationServer() {
		String domain = receiver.split("@")[1].split(".")[0];
		String ip = DB.getIpOf(domain);
		int port = DB.getPortOf(domain);
		try {
			Socket socket = new Socket(ip,port);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String toString() {
		return "ID: "+id+", Sender: "+sender+", Receiver: "+receiver+"\nSubject: "+subject+"\nBody: "+body;
	}

}
