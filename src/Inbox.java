import java.util.ArrayList;

public class Inbox {
	public String owner;
	public ArrayList<Message> messages;
	
	public Inbox(String owner) {
		this.owner = owner;
		messages = new ArrayList<>();
	}
	
	public void addMessage(Message m) {
		messages.add(m);
	}
	
	public int getMessagesSize() {
		return messages.size();
	}
		
}
