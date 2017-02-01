package clients;

public class RunClient {

	public static void main(String[] args) {
		
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		CLIClient client = new CLIClient();
		client.start(ip,port);
	}
}
