package clients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CLIClient {

	private void readInputsAndSend(BufferedReader in, PrintWriter out,String exitStr){
		try {
			String line;
			while(!(line=in.readLine()).equals(exitStr)){
				out.println(line);
				out.flush();
			}
			out.println(line);
			out.flush();
		} 
		catch (IOException e) {
			System.out.println("CLIClient: "+e.getMessage());
		}
		catch (NullPointerException e) {
			System.out.println("Lost connection to server.");
		}
	}

	private Thread aSyncReadInputsAndSend(BufferedReader in, PrintWriter out,String exitStr){

		Thread t=new Thread(new Runnable() {
			public void run() { 
				readInputsAndSend(in, out, exitStr); 
			}
		});
		t.start();
		return t;
	}
	
	public void start(String ip, int port) {
		try {			
			Socket theServer=new Socket(ip, port);
			System.out.println("connected to server");
			BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
			BufferedReader serverInput=new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			PrintWriter outToServer=new PrintWriter(theServer.getOutputStream());
			PrintWriter outToScreen=new PrintWriter(System.out);
			Thread t1 = aSyncReadInputsAndSend(userInput,outToServer,"exit"); // different thread
			Thread t2 = aSyncReadInputsAndSend(serverInput,outToScreen,"bye"); // different thread
			t1.join(); t2.join(); // wait for threads to end
			userInput.close();
			serverInput.close();
			outToServer.close();
			outToScreen.close();
			theServer.close();
		} 
		catch (UnknownHostException e) {
			System.out.println("UnknownHost error: "+e.getMessage());
		}
		catch (IOException e) {
			System.out.println("IOException: "+e.getMessage());
		}
		catch (InterruptedException e) {
			System.out.println("InterruptedException: "+e.getMessage());
		}

	}
}

