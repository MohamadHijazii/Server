import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Conversation {
	
	static ArrayList<Conversation> conversations;
	
	public void add() {
		conversations.add(this);
	}
	
	public static Conversation getConversation(int id) {
		for(Conversation c : conversations) {
			if(c.id == id) {
				return c;
			}
		}
		return null;
	}
	
	public int id;
	public String sender;
	public String receiver;
	public String subject;
	public String body;
	public Socket socket;
	public Puzzle puzzle;
	
	public Conversation(int id, String sender, String receiver, String subject, String body,Socket socket) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
		this.socket = socket;
	}
	
	
	public void TransmitMessageToServer() {
		String domain = receiver.split("@")[1].split(".")[0];
		String ip = DB.getIpOf(domain);
		int port = DB.getPortOf(domain);
		try {
			Socket socket = new Socket(ip,port);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeByte('M');
			out.writeInt(0);
			out.writeInt(sender.length());
			out.write(sender.getBytes(StandardCharsets.UTF_8));
			out.writeInt(receiver.length());
			out.write(receiver.getBytes(StandardCharsets.UTF_8));
			out.writeInt(subject.length());
			out.write(subject.getBytes(StandardCharsets.UTF_8));
			out.writeInt(body.length());
			out.write(body.getBytes(StandardCharsets.UTF_8));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void TransmitPuzzleToServer() {
		
	}
	
	public void TransmitPuzzleToClient() {
		
	}
	
	public void addMessageToClient() {
		
	}
	
	public String toString() {
		return "ID: "+id+", Sender: "+sender+", Receiver: "+receiver+"\nSubject: "+subject+"\nBody: "+body;
	}

}
