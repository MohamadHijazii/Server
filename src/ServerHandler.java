import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ServerHandler extends Thread {

	public static Socket otherServer;
	public static DataInputStream sin;
	public static DataOutputStream sout;
	public static ArrayList<Conversation> conversations;

	public ServerHandler(Socket socket) {
		otherServer = socket;
		try {
			sout = new DataOutputStream(otherServer.getOutputStream());
			sin = new DataInputStream(otherServer.getInputStream());
			conversations = new ArrayList<>();
			System.err.println("Server input and output streams opened successfully");

		} catch (IOException e) {

			System.out.println("Error while opening input and output streams");
		}
	}

	public void run() {

		while (true) {
			char c = 0;
			try {
				c = (char) sin.readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				try {
					otherServer.close();
					break;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}
			switch (c) {
			case 'T':
				T();
				break;
			case 'P':P();break;
			case 'R':R();break;
			case 'F':F();break;
			}
		}
	}
	
	
	private void F() {
		try {
			int id = sin.readInt();
			boolean result = sin.readBoolean();
			int len = sin.readInt();
			StringBuilder s = new StringBuilder();
			while(len -- !=0) {
				s.append((char)sin.readByte());
			}
			ClientHandler c = Main.getClientWithID(id);
			System.out.println("(7) Received the result ..");
			c.sendFinalResultToTheClient(result,s.toString());
		}catch(Exception e) {
			
		}
		
	}

	private void P() {
		int n = 36,i=0;
		int id = 0;
		byte [] puzzle = new byte[36];
		try {
			id = sin.readInt();
			while(n-- != 0) {
				puzzle[i++] = sin.readByte();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("(3) Receiving the puzzle ..");
		//transmit the puzzle to the correspondent client
		ClientHandler c = Main.getClientWithID(id);
		c.sendPuzzleToClient(puzzle);
	}

	private void T() {
		int n = 0;
		StringBuilder s = new StringBuilder();
		String sender, receiver, subject, body;
		try {
			int id = sin.readInt();
			n = sin.readInt();
			while (n-- != 0) {
				s.append((char) sin.readByte());
			}
			sender = s.toString();
			s = new StringBuilder();
			n = sin.readInt();

			while (n-- != 0) {
				s.append((char) sin.readByte());
			}
			receiver = s.toString();
			s = new StringBuilder();

			n = sin.readInt();
			while (n-- != 0) {
				s.append((char) sin.readByte());
			}
			subject = s.toString();
			s = new StringBuilder();

			n = sin.readInt();
			while (n-- != 0) {
				s.append((char) sin.readByte());
			}
			body = s.toString();
			System.out.println("(2) Message transmited for me successfully");
			Conversation c = new Conversation(id, sender, receiver, subject, body);
			conversations.add(c);
			//generate the puzzle
			Puzzle puzzle = new Puzzle();
			puzzle.generate();
			puzzle.mix();
			c.puzzle = puzzle;
			TransmitPuzzleToServer(id, puzzle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void R() {
		int n = 36,i=0;
		int id = 0;
		byte [] puzzle = new byte[36];
		try {
			id = sin.readInt();
			while(n-- != 0) {
				puzzle[i++] = sin.readByte();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		Puzzle solution = new Puzzle(puzzle);
		Conversation con = getConversation(id);
		System.out.println("(6) Received Solution and verifying it");
		boolean result = con.puzzle.verify(solution);
		System.out.println("Result: " +con.puzzle.verify(solution));
		//save the ape in the inbox if true and return the result anyway
		try {
			sout.write('F');
			sout.writeInt(id);
			String correct = "Email is sent Successfully";
			String wrong = "Wrong solution";
		if(result) {
			sout.writeBoolean(true);
			sout.writeInt(correct.length());
			sout.write(correct.getBytes());
			//store the message in TPOP server
			Message m = new Message(con.sender, con.subject, con.body);
			Main.Tpop.addMessage(con.receiver, m);
		}
		else {
			sout.writeBoolean(false);
			sout.writeInt(wrong.length());
			sout.write(wrong.getBytes());
		}
		System.out.println("(7) Sending the result to the server ..");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void TransmitMessageToServer(int id, String sender, String receiver, String subject, String body) {
		try {
			sout.writeByte('T');
			sout.writeInt(id);
			sout.writeInt(sender.length());
			sout.write(sender.getBytes(StandardCharsets.UTF_8));
			sout.writeInt(receiver.length());
			sout.write(receiver.getBytes(StandardCharsets.UTF_8));
			sout.writeInt(subject.length());
			sout.write(subject.getBytes(StandardCharsets.UTF_8));
			sout.writeInt(body.length());
			sout.write(body.getBytes(StandardCharsets.UTF_8));
			System.out.println("(2) Transmitting mail to other server ..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void TransmitPuzzleToServer(int id,Puzzle p) {
		try {
			sout.writeByte('P');
			sout.writeInt(id);
			//sout.writeInt(36);
			sout.write(p.getByteArray());
			System.out.println("(3) Transmitting the puzzle ..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void TransmitSolutionToServer(int id,byte []sol) {
		try {
			sout.writeByte('R');
			sout.writeInt(id);
			//sout.writeInt(36);
			sout.write(sol);
			System.out.println("(6) Transmitting Solution to server ..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Conversation getConversation(int id) {
		for (Conversation c : conversations) {
			if (c.id == id) {
				return c;
			}
		}
		return null;
	}
}
