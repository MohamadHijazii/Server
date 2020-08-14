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
	String mail;

	String sender, receiver, subject, body;// info about the mail

	// communication between servers

	public ClientHandler(Socket link) {
		this.link = link;
		try {
			in = new DataInputStream(link.getInputStream());

			out = new DataOutputStream(link.getOutputStream());

			System.err.println("input and output streams opened successfully");

		} catch (IOException e) {

			System.out.println("Error while opening input and output streams");
		}

	}

	public void SetMail(String mail) {
		this.mail = mail;
		System.out.println("Mail: " + mail);
	}

	public void run() {

		while (true) {
			// read the first message
			char c = 0;
			try {
				c = (char) in.readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				try {
					link.close();
					break;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}

			switch (c) {
			case 'M':
				M();
				break;
			case 'S':
				S();
				break;
			case 'B':
				B();
				break;
			case 'L':
				L();
				break;

			}
		}

	}

	public void L() {
		try {
			int n = in.readInt();
			String s = "";
			while (n-- != 0) {
				s += (char) in.readByte();
			}
			SetMail(s);
		} catch (Exception e) {

		}
	}

	public void B() {
		try {
			Inbox inbox = Main.Tpop.getInbox(mail);
			int nb = inbox.getMessagesSize();
			out.writeInt(nb);
			int n;
			for (int i = 0; i < nb; i++) {
				Message m = inbox.messages.get(i);
				out.writeInt(m.from.length());
				out.write(m.from.getBytes());
				out.writeInt(m.subject.length());
				out.write(m.subject.getBytes());
				out.writeInt(m.body.length());
				out.write(m.body.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void M() {
		int n = 0;
		StringBuilder s = new StringBuilder();
		try {
			id = in.readInt();
			n = in.readInt();
			while (n-- != 0) {
				s.append((char) in.readByte());
			}
			sender = s.toString();
			s = new StringBuilder();
			n = in.readInt();

			while (n-- != 0) {
				s.append((char) in.readByte());
			}
			receiver = s.toString();
			s = new StringBuilder();

			n = in.readInt();
			while (n-- != 0) {
				s.append((char) in.readByte());
			}
			subject = s.toString();
			s = new StringBuilder();

			n = in.readInt();
			while (n-- != 0) {
				s.append((char) in.readByte());
			}
			body = s.toString();
			Random r = new Random();
			id = r.nextInt(Integer.MAX_VALUE);
			System.out.println("(1) Receiving mail to send ..");
			ServerHandler.TransmitMessageToServer(id, sender, receiver, subject, body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPuzzleToClient(byte[] puzzle) {
		try {
			out.writeInt(id);
			out.write(puzzle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("(4) Sending puzzle to client ..");
	}

	private void S() {
		int n = 36;
		int i = 0;
		byte[] p = new byte[36];
		try {
			id = in.readInt();
			while (n-- != 0) {
				p[i++] = in.readByte();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("(5) Received solution from client ..");
		ServerHandler.TransmitSolutionToServer(id, p);
	}

	public void sendFinalResultToTheClient(boolean result, String s) {
		try {
			out.writeBoolean(result);
			out.writeInt(s.length());
			out.write(s.getBytes());
			System.out.println("(8) Sending result to the client ..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
