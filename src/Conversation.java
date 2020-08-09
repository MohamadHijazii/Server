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
	public Puzzle puzzle;
	
	public Conversation(int id, String sender, String receiver, String subject, String body) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
	}
	
	public String toString() {
		return "ID: "+id+", Sender: "+sender+", Receiver: "+receiver+"\nSubject: "+subject+"\nBody: "+body;
	}

}
