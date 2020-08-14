import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

//Server
public class Main {
	
	private static ServerSocket socket,serverSocket;
	private static ServerHandler sh;
	private static Vector<ClientHandler> clients;
	public static TPOPServer Tpop;
		
		private static void connect() {
			try {
				socket = new ServerSocket(3103); 
				serverSocket = new ServerSocket(8631);
				Tpop = new TPOPServer("Hotmail");
				Tpop.inboxes.add(new Inbox("mohd.hijazi@hotmail.com"));
				System.out.println("Connected to port 3103");
				
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Socket s = serverSocket.accept();
							sh = new ServerHandler(s);
							sh.start();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				t.start();
				
			}catch(Exception e) {
				try {
					socket = new ServerSocket(1505);
					Socket s = new Socket("127.0.0.1",8631);
					sh = new ServerHandler(s);
					sh.start();
					Tpop = new TPOPServer("Gmail");
					System.out.println("Connected to port 1505");
					Tpop.inboxes.add(new Inbox("hd.saleh@gmail.com"));
				}catch(Exception e1) {
					System.out.println("No more available servers :(");
					System.exit(1);
				}
			}
		}
	
		public static ClientHandler getClientWithID(int id) {
			for(ClientHandler c : clients) {
				if(c.id == id) {
					return c;
				}
			}
			return null;
		}
		
	public static void main(String[] args) {
		connect();

		clients = new Vector<ClientHandler>();
		Conversation.conversations = new ArrayList<>();
		Socket link;
		while (true) {

			try {
				link = socket.accept();

				
				ClientHandler cl = new ClientHandler(link);
				System.out.println("New Socket created " + link.toString()+", mail: "+cl.mail);

				clients.add(cl);
				cl.start();
				

			} catch (IOException e) {
				System.out.println("Failed to accept new socket");
			}

		}

	}

}
