package settings;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import boardBuilder.Board;

public class Configuration extends JFrame implements ItemListener,
		ActionListener {

	String username;
	JFrame config;

	JButton setPreferences;
	JButton startNewGame;
	JButton startByPreferences;
	JButton logOff;

	int widthForConstructor = 8;
	int heightForConstructor = 8;
	int minesForConstructor = 9;
	int validMines;

	CheckboxGroup sizeAndDifficulty;
	Checkbox easyBegginer;
	Checkbox easyIntermediate;
	Checkbox easyAdvanced;
	Checkbox hardBegginer;
	Checkbox hardIntermediate;
	Checkbox hardAdvanced;
	Checkbox custom;

	TextField width;
	TextField height;
	TextField mines;

	JLabel jl1;
	JLabel jl2;
	JLabel jl3;
	JLabel jl4;
	JLabel jl5;
	JLabel jl6;
	JLabel jl7;
	JLabel jl8;
	JLabel jl9;

	File root = new File("./users");
	File user;

	Image icon = Toolkit
			.getDefaultToolkit()
			.getImage(
					"/minesweeperIcons/setIcon.png");

	public Configuration(String username) {

		this.username = username;

		config = new JFrame();
		config.setSize(310, 500);
		config.setLayout(null);
		config.setVisible(true);
		config.setTitle("Configuration (" + username + "'s profile)");
		config.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		config.setResizable(false);
		config.setLocationRelativeTo(this);
		config.setIconImage(icon);

		root.mkdir();
		user = new File(root.getPath() + "/" + username);
		user.mkdir();

		sizeAndDifficulty = new CheckboxGroup();
		easyBegginer = new Checkbox("Begginer(easy)", sizeAndDifficulty, true);
		hardBegginer = new Checkbox("Begginer(hard)", sizeAndDifficulty, false);
		easyIntermediate = new Checkbox("Intermediate(easy)",
				sizeAndDifficulty, false);
		hardIntermediate = new Checkbox("Intermediate(hard)",
				sizeAndDifficulty, false);
		easyAdvanced = new Checkbox("Advanced(easy)", sizeAndDifficulty, false);
		hardAdvanced = new Checkbox("Advanced(hard)", sizeAndDifficulty, false);
		jl1 = new JLabel("<html>8 x 8<br>9 mines</html>");
		jl2 = new JLabel("<html>8 x 8<br>15 mines</html>");
		jl3 = new JLabel("<html>14 x 10<br>15 mines</html>");
		jl4 = new JLabel("<html>14 x 10<br>35 mines</html>");
		jl5 = new JLabel("<html>20 x 16<br>35 mines</html>");
		jl6 = new JLabel("<html>20 x 16<br>99 mines</html>");
		jl7 = new JLabel("<html>width: <br>(8 - 30)</html>");
		jl8 = new JLabel("<html>height: <br>(8 - 24)</html>");
		jl9 = new JLabel("<html>mines: <br>(9 - 503)</html>");

		easyBegginer.setBounds(20, 30, 120, 25);
		jl1.setBounds(33, 52, 70, 40);
		easyIntermediate.setBounds(20, 90, 125, 25);
		jl3.setBounds(33, 112, 70, 40);
		easyAdvanced.setBounds(20, 150, 120, 25);
		jl5.setBounds(33, 172, 70, 40);
		hardBegginer.setBounds(170, 30, 120, 25);
		jl2.setBounds(183, 52, 70, 40);
		hardIntermediate.setBounds(170, 90, 125, 25);
		jl4.setBounds(183, 112, 70, 40);
		hardAdvanced.setBounds(170, 150, 120, 25);
		jl6.setBounds(183, 172, 70, 40);
		custom = new Checkbox("Custom", sizeAndDifficulty, false);
		custom.setBounds(20, 250, 120, 25);

		width = new TextField("8");
		height = new TextField("8");
		mines = new TextField("9");
		width.setBounds(80, 285, 40, 25);
		jl7.setBounds(30, 280, 50, 40);
		height.setBounds(80, 330, 40, 25);
		jl8.setBounds(30, 325, 50, 40);
		mines.setBounds(80, 375, 40, 25);
		jl9.setBounds(30, 370, 50, 40);
		width.setEditable(false);
		height.setEditable(false);
		mines.setEditable(false);

		setPreferences = new JButton(
				"<html>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;set<br>preferences</html>");
		setPreferences.setBounds(150, 320, 110, 35);
		setPreferences.setVisible(true);
		setPreferences.setMnemonic(KeyEvent.VK_F);
		setPreferences.setActionCommand("sP");
		setPreferences
				.setToolTipText("<html>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;By clicking this button you set these <br>customizations as your default minesweeper board</html>");

		startNewGame = new JButton("START");
		startNewGame.setBounds(150, 280, 110, 35);
		startNewGame.setVisible(true);
		startNewGame.setMnemonic(KeyEvent.VK_S);
		startNewGame.setActionCommand("sNG");
		startNewGame.setToolTipText("This will launch the selected game");

		startByPreferences = new JButton(
				"<html>START your<br>preferred ;)</html>");
		startByPreferences.setBounds(150, 360, 110, 35);
		startByPreferences.setVisible(true);
		startByPreferences.setMnemonic(KeyEvent.VK_P);
		startByPreferences.setActionCommand("sBP");
		startByPreferences
				.setToolTipText("<html>This will launch your preferred<br> game that have been set before</html>");

		logOff = new JButton("Log off");
		logOff.setBounds(150, 400, 110, 35);
		logOff.setVisible(true);
		logOff.setMnemonic(KeyEvent.VK_L);
		logOff.setActionCommand("lO");
		logOff.setToolTipText("You can log off then login with another account");

		setPreferences.addActionListener(this);
		startNewGame.addActionListener(this);
		startByPreferences.addActionListener(this);
		logOff.addActionListener(this);

		config.add(easyBegginer);
		config.add(easyIntermediate);
		config.add(easyAdvanced);
		config.add(hardBegginer);
		config.add(hardIntermediate);
		config.add(hardAdvanced);
		config.add(custom);
		config.add(jl1);
		config.add(jl2);
		config.add(jl3);
		config.add(jl4);
		config.add(jl5);
		config.add(jl6);
		config.add(jl7);
		config.add(jl8);
		config.add(jl9);
		config.add(width);
		config.add(height);
		config.add(mines);
		config.add(setPreferences);
		config.add(startNewGame);
		config.add(startByPreferences);
		config.add(logOff);

		custom.addItemListener(this);
		easyBegginer.addItemListener(this);
		easyIntermediate.addItemListener(this);
		easyAdvanced.addItemListener(this);
		hardBegginer.addItemListener(this);
		hardIntermediate.addItemListener(this);
		hardAdvanced.addItemListener(this);

	}

	/**
	 * METHODS
	 * ========================================================================
	 */

	private static void writeToFile(File pref, String preferences)
			throws IOException {
		FileWriter writer = new FileWriter(pref, false);
		writer.write(preferences);
		writer.close();
	}

	public void boardCounstructorsSetter(int width, int height, int mines) {
		widthForConstructor = width;
		heightForConstructor = height;
		minesForConstructor = mines;
	}

	public void rangeControlMessage(int i) {
		if (i == 1)
			JOptionPane.showMessageDialog(null,
					"The width should not be more than 30 or less than 8 !!!",
					"WIDTH OUT OF RANGE", 2);
		if (i == 2)
			JOptionPane.showMessageDialog(null,
					"The height should not be more than 24 or less than 8 !!!",
					"HEIGHT OUT OF RANGE", 2);
		if (i == 3)
			JOptionPane.showMessageDialog(null,
					"The mines should not be more than " + validMines
							+ " or less than 8 for this customisation!!!",
					"MINES OUT OF RANGE", 2);
	}

	private int customise() {
		int errors = 0;
		int notValid = 0;
		try {
			widthForConstructor = Integer.parseInt(width.getText());
			if (widthForConstructor > 30 || widthForConstructor < 8) {
				width.setText("8");
				widthForConstructor = 8;
				rangeControlMessage(1);
				notValid++;
			}
		} catch (Exception e) {
			width.setText("8");
			errors++;
		}
		try {
			heightForConstructor = Integer.parseInt(height.getText());
			if (heightForConstructor > 30 || heightForConstructor < 8) {
				height.setText("8");
				heightForConstructor = 8;
				rangeControlMessage(2);
				notValid++;
			}
		} catch (Exception e) {
			height.setText("8");
			errors++;
		}
		try {
			minesForConstructor = Integer.parseInt(mines.getText());
			validMines = (int) (widthForConstructor * heightForConstructor * .7);
			if (minesForConstructor > validMines || minesForConstructor < 9) {
				mines.setText("9");
				minesForConstructor = 9;
				rangeControlMessage(3);
				notValid++;
			}
		} catch (Exception e) {
			mines.setText("9");
			errors++;
		}
		if (errors != 0) {
			JOptionPane.showMessageDialog(null, "Please just enter numbers!",
					"INCORRECT INPUT", 0);
		}
		return errors + notValid;
	}

	/**
	 * ItemListener implemented methods
	 * ========================================================================
	 */

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object src = e.getSource();
		if (src == custom) {
			width.setEditable(true);
			height.setEditable(true);
			mines.setEditable(true);
		} else {
			width.setEditable(false);
			height.setEditable(false);
			mines.setEditable(false);
		}
		if (src == easyBegginer)
			boardCounstructorsSetter(8, 8, 9);
		else if (src == easyIntermediate)
			boardCounstructorsSetter(14, 10, 15);
		else if (src == easyAdvanced)
			boardCounstructorsSetter(20, 16, 35);
		else if (src == hardBegginer)
			boardCounstructorsSetter(8, 8, 15);
		else if (src == hardIntermediate)
			boardCounstructorsSetter(14, 10, 35);
		else if (src == hardAdvanced)
			boardCounstructorsSetter(20, 16, 99);
	}

	/**
	 * ActionListener implemented methods
	 * ========================================================================
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("sP")) {
			
			File preferred = new File(user.getPath() + "/Preferences.txt");
			try {
				preferred.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				if (customise() == 0)
					writeToFile(preferred, widthForConstructor + "\r\n"
							+ heightForConstructor + "\r\n"
							+ minesForConstructor);
			} catch (IOException e1) {
				JOptionPane
						.showMessageDialog(
								null,
								"<html>You have deleted your minesweeper root file!<br>Please restart the minesweeper game<html>",
								"FILE DELETED!!!", 0);
			}
			int[] pref = new int[3 + 10];
			try {
				Scanner sc = new Scanner(preferred);
				for (int i = 0; i < 3; i++) {
					pref[i] = Integer.parseInt(sc.nextLine());
				}
			} catch (Exception ex) {
			}

			String set = "Your preferences are now set to:\n    width : "
					+ pref[0] + "\n   height : " + pref[1] + "\n   mines : "
					+ pref[2];
			width.setText(pref[0] + "");
			height.setText(pref[1] + "");
			mines.setText(pref[2] + "");

			JOptionPane.showMessageDialog(null, set, "NEW SETTINGS", 2);
		}
		if (e.getActionCommand().equals("sBP")) {
			File preferred = new File(user.getPath() + "/Preferences.txt");
			int[] pref = new int[3 + 10];
			try {
				Scanner sc = new Scanner(preferred);
				for (int i = 0; i < 3; i++) {
					try {
						pref[i] = Integer.parseInt(sc.nextLine());
					} catch (Exception EE) {
						pref[0] = 0;
					}
				}

				if (customise() == 0) {
					if (pref[0] != 0) {
						callBoardBuilder(pref[0], pref[1], pref[2]);
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"You have not set anything as your preferences!",
										"NO PREFERENCES", 2);

					}
				}
			} catch (FileNotFoundException e1) {
			}

		}
		if (e.getActionCommand().equals("sNG")) {
			if (custom.getState() == true) {
				if (customise() == 0)// IMPORTANT : in customise method we
										// change some data, so it must be
										// called!
					callBoardBuilder(widthForConstructor, heightForConstructor,
							minesForConstructor);
			} else
				callBoardBuilder(widthForConstructor, heightForConstructor,
						minesForConstructor);
		}
		int ans;
		if (e.getActionCommand().equals("lO")) {
			ans = JOptionPane.showConfirmDialog(null,
					"Are you sure that you want to log off this account?");
			// ans: 0:Yes / 1:No / 2:Cancel
			if (ans == 0) {
				config.dispose();
				SignIn si = new SignIn();
			}
		}
	}

	/**
	 * at last this method will create the board
	 * ========================================================================
	 */

	private void callBoardBuilder(int width, int height, int mines) {

		Board newBoard = new Board(width, height, mines, username);
		config.setVisible(false);
	}
}