import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private int portNo = 0;

	@SuppressWarnings("resource")
	public Server(int portNo) {
		Socket cSocket = null;
		this.portNo = portNo;
		ServerSocket sSocket = null;
		int maxClient = 2;
		ClientThread[] cThreads = new ClientThread[maxClient];
		try {
			sSocket = new ServerSocket(this.portNo);
			while(true) {
				try {
					cSocket = sSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
				int i = 0;
				for (i = 0; i < 3; i++) {
					if (cThreads[i] == null) {
						(cThreads[i] = new ClientThread(cSocket, cThreads)).start();
						break;
					}
				}
				if (i == maxClient) {
					PrintStream os = new PrintStream(cSocket.getOutputStream());
					os.println("Server is full!");
					os.close();
					cSocket.close();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	class ClientThread extends Thread {

		DataInputStream is = null;
		PrintStream os = null;
		Socket clientSocket = null;
		final ClientThread[] cThreads;
		int maxClientsCount;
		String line;
		
		public ClientThread(Socket clientSocket, ClientThread[] cThreads) {
			this.clientSocket = clientSocket;
			this.cThreads = cThreads;
			maxClientsCount = cThreads.length;
		}

		public void run() {
			try {
				is = new DataInputStream(clientSocket.getInputStream());
				os = new PrintStream(clientSocket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}


			while (true) {
				try {
					line = is.readLine();
					for (int i = 0; i < maxClientsCount; i++) {
						if (cThreads[i] != null && !line.equals("")) {
							cThreads[i].os.println(line);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}		      
			}
		}
	}

	/*class NewClient implements Runnable {

		Socket socket;
		String writeToClient = "";
		ObjectOutputStream out;
		ObjectInputStream in;

		public NewClient(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());	


			} catch(IOException exception) {
				System.out.println("Error: " + exception);
			}

			while(true) {
				for(int i = 0; i < clientSocket.size(); i++) {
					Socket tempSock =  (Socket) clientSocket.get(i);
					ObjectOutputStream tempOut;
					try {
						tempOut = new ObjectOutputStream(tempSock.getOutputStream());
						tempOut.write("gameStarts".getBytes());
						tempOut.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public synchronized void sendData(ObjectOutputStream os, byte[] byteData) {
		if (byteData == null) {
			return;
		}
		try {
			os.write(byteData);
			os.flush();
		}
		catch (Exception e) {
			System.out.println("Client Disconnected");
		}
	}


	public synchronized byte[] receive(ObjectInputStream is) throws Exception {
		try {
			byte[] inputData = new byte[1024];
			is.read(inputData);
			return inputData;
		}
		catch (Exception exception) {
			throw exception;
		}
	}*/


	public static void main(String[] args) {
		System.out.println("Server started");
		new Server(5550);
	}
}
