import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class DB {

	public static String path="C:\\Users\\Mohamad\\Desktop\\servers.txt";
	
	public static ArrayList<Domain> domains;
	
	public static void Init() {
		domains = new ArrayList<Domain>();
		read();
	}
	
	public static String getIpOf(String name) {
		for(int i=0;i<domains.size();i++) {
			if(name.equals(domains.get(i).name)) {
				return domains.get(i).ip;
			}
		}
		return null;
	}
	
	public static int getPortOf(String name) {
		for(int i=0;i<domains.size();i++) {
			if(name.equals(domains.get(i).name)) {
				return domains.get(i).port;
			}
		}
		return 0;
	}
	
	static void read() {
		try {
			BufferedReader rb = new BufferedReader(new FileReader(path));
			String s = rb.readLine();
			while(s != null) {
				Domain d = new Domain(s);
				domains.add(d);
				s = rb.readLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printAllDomains() {
		System.out.println("Domains: "+domains);
	}
	
	public static class Domain{
		public String name;
		public String ip;
		public int port;
		public Domain(String name, String ip, int port) {
			this.name = name;
			this.ip = ip;
			this.port = port;
		}
		
		public Domain(String s) {
			String []t = s.split(" ");
			name = t[0];
			ip = t[1];
			port = Integer.parseInt(t[2]);
		}
		
		public String toString() {
			return "Name: "+name+", IP: "+ip+", Port: "+port;
		}
	}
}
