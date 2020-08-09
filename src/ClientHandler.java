import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

public class ClientHandler extends Thread {

	public int id;
	private Socket link;
	private DataInputStream in;
	private DataOutputStream out;
	private Vector<ClientHandler> clients;
	
	
	String sender,receiver,subject,body;//info about the mail
	
	
	//communication between servers
	
	
	public ClientHandler(Socket link, Vector<ClientHandler> clients) {
		this.link = link;
		this.clients = clients;
		try {
			in = new DataInputStream(link.getInputStream());

			out = new DataOutputStream(link.getOutputStream());

			System.err.println("input and output streams opened successfully");

		} catch (IOException e) {

			System.out.println("Error while opening input and output streams");
		}

	}

	public void run() {

		while(true) {
			// read the first message
			char c = 0;
			try {
				c = (char)in.readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			switch (c) {
			case 'M':
				M();
				break;
			case 'S':
				S();
				break;
			case 'R':
				R();
				break;
	
			}
		}

	}

	private void M() {
		int n=0;
		StringBuilder s = new StringBuilder();
		try {
			id = in.readInt();
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
			Random r = new Random();
			id = r.nextInt(Integer.MAX_VALUE);
			System.out.println("(1) Receiving mail to send ..");
			ServerHandler.TransmitMessageToServer(id,sender,receiver,subject,body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPuzzleToClient(byte [] puzzle) {
		try {
			out.writeInt(id);
			out.write(puzzle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("(4) Sending puzzle to client ..");
	}
	
	private void R() {
		// TODO Auto-generated method stub

	}

	private void S() {
		int n = 36;
		int i=0;
		byte []p = new byte[36];
		try {
			id = in.readInt();
			while(n--!=0) {
				p[i++] = in.readByte();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("(5) Received solution from client ..");
		ServerHandler.TransmitSolutionToServer(id, p);
	}


	
	
	
	
	

}
