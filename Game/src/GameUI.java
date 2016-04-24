import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class GameUI extends JFrame {

	static JLabel blankCardLabel;
	static JLabel cardOne;
	JLabel cardTwo;
	JLabel cardThree;
	JLabel cardFour;
	JLabel cardFive;
	JLabel cardSix;
	JLabel cardSeven;
	JLabel cardEight;
	JLabel cardNine;
	JLabel cardTen;
	JLabel cardEleven;
	JLabel cardTwelve;
	JLabel cardThirteen;
	JLabel cardFourteen;
	static BufferedImage blankCardPic;
	JLabel cardsLeftLabel;
	static BufferedImage revealCardPic;

	public GameUI() {
		setLayout(null);

		cardOne = new JLabel(new ImageIcon(blankCard()));
		cardOne.setBounds(170,10,80,80);
		add(cardOne);
		cardOne.setText("ClubsAce.png");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setSize(800,600);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new GameUI();
	}

	public BufferedImage blankCard() {
		try {
			blankCardPic = ImageIO.read(new File("resources/images/blank.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blankCardPic;
	}
	
	public static BufferedImage revealCard(String s) {
		try {
			revealCardPic = ImageIO.read(new File("resources/images/"+s));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return revealCardPic;
	}
}
