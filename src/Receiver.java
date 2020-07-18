import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Receiver implements Runnable{
	
	String message;
	DataInputStream in;
	@Override
	public void run() {
		ReceiveMessage();
		
	}

	private void ReceiveMessage() {
		while(true) {
			try {
				Socket s = Network.socket.accept();
				System.out.println("Accept socket");
				in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
	
				System.out.println("Read");
				//start reading the packet
				byte type = in.readByte();
				
				System.out.println("We received "+type);
				switch(type) {
					case (byte)'M':sendMail(); break;
					case (byte)'P': break;
					case (byte)'S': break;
					case (byte)'O': break;
				}
				out.writeByte('N');
				System.out.println("END");
				
			} catch (IOException e) {
				
			}
		}
		
	}

	private void sendMail() {
		int n=0;
		StringBuilder s = new StringBuilder();
		String sender,receiver,subject,body;
		try {
			int id = in.readInt();
			n = in.readInt();
			while(n-- !=0) {
				s.append((char)in.readByte());
			}
			sender = s.toString();
			s = new StringBuilder();
			n = in.readInt();
			
			while(n-- !=0) {
				s.append((char)in.readByte());
			}
			receiver = s.toString();
			s = new StringBuilder();
			
			n = in.readInt();
			while(n-- !=0) {
				s.append((char)in.readByte());
			}
			subject = s.toString();
			s = new StringBuilder();
			
			n = in.readInt();
			while(n-- !=0) {
				s.append((char)in.readByte());
			}
			body = s.toString();
			Conversation c = new Conversation(id, sender, receiver, subject, body);
			Main.addConversation(c);
			System.out.println(c);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
