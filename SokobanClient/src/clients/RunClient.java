package clients;
/**
 * 
 * @author Tal Sheinfeld
 * Main for CLIClient class. Will try to connect to a working Sokoban server to play on the CLI ATM.
 *
 */
public class RunClient {

	public static void main(String[] args) {
		
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		CLIClient client = new CLIClient();
		client.start(ip,port);
	}
}
