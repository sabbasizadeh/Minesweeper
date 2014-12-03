package settings;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SignIn extends JFrame implements MouseListener, KeyListener {

	static JFrame jf;
	static File root = new File("./");
	static File usersDir = new File(root.getPath() + "/users");
	static File users = new File(usersDir.getPath() + "/users.txt");

	static int anyKeyPressed = 0;
	JTextField username;
	JButton signIn;
	JButton clear;
	JLabel signUpText;
	String userInput;
	boolean signInInput;

	Image icon = Toolkit.getDefaultToolkit().getImage(
			"/minesweeperIcons/login.png");

	public SignIn() {

		jf = new JFrame();
		jf.setLayout(null);
		jf.setSize(340, 140);
		jf.setVisible(true);
		jf.setTitle("Login");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.setLocationRelativeTo(this);
		jf.addMouseListener(this);
		jf.setIconImage(icon);

		root.mkdir();
		usersDir.mkdir();
		try {
			users.createNewFile();
		} catch (IOException e) {
		}

		username = new JTextField("Please enter your username...", 30);
		username.addMouseListener(this);
		username.addKeyListener(this);
		username.setLocation(20, 70);
		username.setSize(210, 21);
		username.setVisible(true);

		signIn = new JButton("Login");
		signIn.setToolTipText("enter your username and press this button to login");
		signIn.setLocation(230, 70);
		signIn.setSize(77, 20);
		signIn.addMouseListener(this);
		signIn.addKeyListener(this);
		signIn.setMnemonic(KeyEvent.VK_ENTER);
		signIn.setVisible(true);

		clear = new JButton("Delete accounts");
		clear.setToolTipText("this action will remove all accounts");
		clear.setLocation(100, 91);
		clear.setSize(127, 20);
		clear.addMouseListener(this);
		clear.setVisible(true);

		signUpText = new JLabel(
				"<html>New to minesweeper?<br>Don't worry!<br>Enter your desired username here:</html>");
		signUpText.setLocation(20, 0);
		signUpText.setSize(200, 80);
		signUpText.setVisible(true);

		jf.add(username);
		jf.add(signIn);
		jf.add(signUpText);
		jf.add(clear);

		username.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					signIn.doClick();
					userInput = username.getText();
					if (userInput.trim().isEmpty()
							|| userInput
									.equals("Please enter your username...")) {
						signInInput = false;
					} else {
						signInInput = true;
					}

					if (signInInput == false)
						showMessage(1, "");
					else
						try {
							checkUsernameValidity(users, userInput);
						} catch (FileNotFoundException e1) {
						}
				}
			}
		});
	}

	/**
	 * METHODS
	 * ========================================================================
	 */

	private static void writeToFile(File text, String username)
			throws IOException {
		boolean thereIsInText = false;
		Scanner sc = new Scanner(text);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals(username)) {
				thereIsInText = true;
				break;
			}
		}
		if (thereIsInText == false) {
			FileWriter writer = new FileWriter(text, true);
			writer.write(username + "\r\n");
			writer.close();
		}
	}

	private static void checkUsernameValidity(File text, String username)
			throws FileNotFoundException {
		boolean thereIsInText = false;
		Scanner sc = new Scanner(text);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals(username)) {
				thereIsInText = true;
				break;
			}
		}
		sc.close();
		if (thereIsInText == true) {
			showMessage(2, username);
			createProfile(username);
			startConfiguration(username);
		} else {
			showMessage(3, username);
		}
	}

	private static void showMessage(int i, String username) {

		if (i == 1)
			JOptionPane.showMessageDialog(null, "Please enter your username",
					"NO INPUT", 2);

		if (i == 2)
			JOptionPane.showMessageDialog(null,
					"Welcome to the Sepehr's minesweeper game dear \""
							+ username + "\"", "WELCOME", 1);

		int answer;
		if (i == 3) {
			answer = JOptionPane
					.showConfirmDialog(
							null,
							"<html>This account is new to minesweeper<br>Do you want to create this account?");
			// answer: 0:Yes / 1:No / 2:Cancel
			if (answer == 0) {
				createProfile(username);
				showMessage(2, username);
				startConfiguration(username);
			} else if (answer == 2) {
				jf.dispose();
			}
		}
		anyKeyPressed = 0;
	}

	/**
	 * MouseListener implemented methods
	 * ========================================================================
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 1 && e.getSource() == signIn) {
			userInput = username.getText();
			if (userInput.trim().isEmpty()
					|| userInput.equals("Please enter your username...")) {
				signInInput = false;
			} else {
				signInInput = true;
			}

			if (signInInput == false)
				showMessage(1, "");
			else
				try {
					checkUsernameValidity(users, userInput);
				} catch (FileNotFoundException e1) {
				}
		}
		if (e.getButton() == 1 && e.getSource() == clear) {
			deleteDir(usersDir);
			usersDir.mkdir();
			try {
				users.createNewFile();
			} catch (IOException e1) {
			}
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if ((e.getSource() == username)
				&& (username.getText().equals("Please enter your username..."))) {
			username.setText(null);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * KeyListener implemented methods
	 * ========================================================================
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (!e.equals(null) && anyKeyPressed == 0) {
			username.setText(null);
			anyKeyPressed++;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.equals(KeyEvent.VK_ENTER)) {
			userInput = username.getText();
			if (userInput.trim().isEmpty()
					|| userInput.equals("Please enter your username...")) {
				signInInput = false;
			} else {
				signInInput = true;
			}

			if (signInInput == false)
				showMessage(1, "");
			else
				try {
					checkUsernameValidity(users, userInput);
				} catch (FileNotFoundException e1) {
				}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Creating profile and start the configuration page
	 * ========================================================================
	 */
	private static void createProfile(String username) {
		try {
			writeToFile(users, username);
		} catch (IOException e1) {
		}
		File newProfile = new File(usersDir.getPath() + "/" + username);
		newProfile.mkdir();
		File dirForSaves = new File(newProfile.getPath() + "/Saves");
		dirForSaves.mkdir();
		File textForPreferences = new File(newProfile.getPath()
				+ "/Preferences.txt");
		try {
			textForPreferences.createNewFile();
		} catch (IOException e) {
		}
	}

	private static void startConfiguration(String username) {
		Configuration cnf = new Configuration(username);
		jf.dispose();
	}
}
