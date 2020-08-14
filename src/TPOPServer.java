import java.util.ArrayList;

public class TPOPServer {
	
	public String serverName;
	public ArrayList<Inbox> inboxes;

	public TPOPServer(String name) {
		serverName = name;
		inboxes = new ArrayList<>();
	}
	
	public ArrayList<Message> getMessagesOf(String mail){
		for(Inbox box : inboxes) {
			if(box.owner.compareTo(mail) == 0) {
				return box.messages;
			}
		}
		return null;
	}
	
	public void addMessage(String to,Message m) {
		for(Inbox box : inboxes) {
			if(box.owner.compareTo(to) == 0) {
				box.addMessage(m);
			}
		}
	}
	
	public Inbox getInbox(String mail) {
		for(Inbox box : inboxes) {
			if(box.owner.compareTo(mail) == 0) {
				return box;
			}
		}
		return null;
	}
}
