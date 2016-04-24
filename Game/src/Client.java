import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.ImageIcon;
import javax.swing.Timer;


public class Client implements Runnable {

	static Socket clientSocket = null;
	ObjectInputStream in = null;
	String serverMsg;
	static PrintStream os = null;
	static DataInputStream is = null;
	static boolean closed = false;
	static String s = "";
	static String cardType = "";
	static GameUI gameUI;
	int countdown = 3;
	Timer timer = new Timer(1000,null);
	String response = null;
	boolean  responded = false;

	public static void main(String args[]) {
		try {
			clientSocket = new Socket("localhost", 5550);
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		gameUI = new GameUI();

		gameUI.cardOne.addMouseListener(new MouseAdapter()  
		{  
			public void mouseClicked(MouseEvent e)  
			{  
				s = gameUI.cardOne.getText();

			}  
		}); 

		if (clientSocket != null && os != null && is != null) {
			try {
				new Thread(new Client()).start();
				while (!closed) {
					os.println(s.trim());
				}

				os.close();
				is.close();
				clientSocket.close();
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}

	}

	public void run() {


		//while(true) {
		try {
			while (true) {
				response = is.readLine();
				if(!response.equals("")) {
					System.out.println(response);
					responded = true;
				}
				// no break here
			}
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
		//}

		if(responded) {
			timer = new Timer(1000,new TimerC());
			timer.start();

		}
	}

	class TimerC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(countdown == 0) { 
				timer.stop();
				gameUI.cardOne.setIcon(new ImageIcon(GameUI.revealCard("blank.png")));
				countdown = 3;
				responded = false;
			}

			else {
				gameUI.cardOne.setIcon(new ImageIcon(GameUI.revealCard(response)));
				countdown--;
				System.out.println(countdown);

			}  
		}
	}
}



