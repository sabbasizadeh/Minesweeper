package boardBuilder;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileView;

import settings.Configuration;
import settings.SignIn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Board extends JFrame implements MouseMotionListener,
		MouseListener, ActionListener {

	final int MENU = 15;
	final int UPPER_BORDER = 40 + MENU;
	final int MINES_AND_TIMER = 15;
	final int LOWER_BORDER = 8 + MINES_AND_TIMER;
	final int LEFT_BORDER = 8;
	final int ICON_SIZE = 16;

	String username;
	private int width;
	private int height;
	private int mines;
	static int remainingMines;
	boolean isFirstClick = true;
	boolean startTimer = true;// for running the timer
	boolean isTimerRunning = false;// it shows that if timer is stopped or not
	int timerNumber = 0;// will change to show the timer
	private int[][] imagesState; // for the image of each block
	private int[][] mineState; // declare that if each block has mine or not
	private int[][] blockNumber;// number that each block's image should show
	private boolean[][] changabaleState; // for confirmation of changing the
											// image state
	String loadName;// just for the time that we use the loading constructor

	Image[] images; // for loading the minesweeper images

	MenuBar menuBar;
	Menu game, help;
	MenuItem newGame, restart, options, save, load, logout, exit;
	MenuItem ViewHelp, about;

	Label Mines;
	Label Timer;

	public Board(int width, int height, int mines, String username) {
		setSize(width * ICON_SIZE + 2 * LEFT_BORDER, height * ICON_SIZE
				+ UPPER_BORDER + LOWER_BORDER);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Minesweeper");

		addMouseMotionListener(this);
		addMouseListener(this);

		this.username = username;
		this.width = width;
		this.height = height;
		this.mines = mines;

		remainingMines = mines;

		changabaleState = new boolean[width][height];
		mineState = new int[width][height];
		imagesState = new int[width][height];
		blockNumber = new int[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				changabaleState[i][j] = true;
			}
		}

		images = new Image[16 + 10];

		imagesLoading: { // for loading each image in images[]

			images[0] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sqt0.gif"));
			images[1] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sqt1.gif"));
			images[2] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sqt2.gif"));
			images[3] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq0.gif"));
			images[4] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq1.gif"));
			images[5] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq2.gif"));
			images[6] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq3.gif"));
			images[7] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq4.gif"));
			images[8] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq5.gif"));
			images[9] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq6.gif"));
			images[10] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq7.gif"));
			images[11] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq8.gif"));
			images[12] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/mine.gif"));
			images[13] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/minered.gif"));
			images[14] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/mineErr.gif"));
			images[15] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/mineicon.gif"));
		}

		setIconImage(images[15]);

		/*
		 * ADDING LABEL FOR SHOWING REMAINING MINES
		 * ====================================================================
		 */

		Mines = new Label("MINES : " + String.valueOf(remainingMines));
		Mines.setBounds(5, height * ICON_SIZE + UPPER_BORDER + LOWER_BORDER
				- 65, 70, 15);
		Mines.setVisible(true);
		add(Mines);

		/*
		 * ADDING LABEL FOR SHOWING TIMER
		 * ====================================================================
		 */
		Timer = new Label("0");
		Timer.setBounds(90, height * ICON_SIZE + UPPER_BORDER + LOWER_BORDER
				- 65, 60, 15);
		Timer.setVisible(true);
		add(Timer);

		/*
		 * ADDING MENUBAR
		 * ====================================================================
		 */
		menuBar = new MenuBar();
		setMenuBar(menuBar);

		/*
		 * ADDING GAME MENU
		 * ====================================================================
		 */
		game = new Menu("Game");
		menuBar.add(game);

		newGame = new MenuItem("new game");
		newGame.addActionListener(this);

		restart = new MenuItem("restart");
		restart.addActionListener(this);

		options = new MenuItem("options");
		options.addActionListener(this);

		save = new MenuItem("save");
		save.addActionListener(this);

		load = new MenuItem("load");
		load.addActionListener(this);

		logout = new MenuItem("logout");
		logout.addActionListener(this);

		exit = new MenuItem("Exit Game");
		exit.addActionListener(this);

		game.add(newGame);
		game.add(restart);
		game.addSeparator();
		game.add(options);
		game.addSeparator();
		game.add(save);
		game.add(load);
		game.add(logout);
		game.addSeparator();
		game.add(exit);
		/*
		 * ADDING HELP MENU
		 * ====================================================================
		 */

		help = new Menu("Help");
		menuBar.add(help);

		ViewHelp = new MenuItem("View Help");
		ViewHelp.addActionListener(this);

		about = new MenuItem("About Minesweeper");
		about.addActionListener(this);

		help.add(ViewHelp);
		help.addSeparator();
		help.add(about);

	}

	public Board(int width, int height, int mines, String username,
			String loadName) {
		isFirstClick = false;
		setSize(width * ICON_SIZE + 2 * LEFT_BORDER, height * ICON_SIZE
				+ UPPER_BORDER + LOWER_BORDER);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Minesweeper");

		addMouseMotionListener(this);
		addMouseListener(this);

		this.username = username;
		this.width = width;
		this.height = height;
		this.mines = mines;
		this.loadName = loadName;

		isTimerRunning = true;

		changabaleState = new boolean[width][height];
		mineState = new int[width][height];
		imagesState = new int[width][height];
		blockNumber = new int[width][height];

		getLoadedData();

		images = new Image[16 + 10];

		blockNumber = blocksNumberGetter();

		imagesLoading: { // for loading each image in images[]
			String S = "C:/Users/sabbasizadeh/workspace/MineSweeper/src/minesweeperIcons";
			images[0] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sqt0.gif"));
			images[1] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sqt1.gif"));
			images[2] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sqt2.gif"));
			images[3] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq0.gif"));
			images[4] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq1.gif"));
			images[5] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq2.gif"));
			images[6] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq3.gif"));
			images[7] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq4.gif"));
			images[8] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq5.gif"));
			images[9] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq6.gif"));
			images[10] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq7.gif"));
			images[11] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/sq8.gif"));
			images[12] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/mine.gif"));
			images[13] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/minered.gif"));
			images[14] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/mineErr.gif"));
			images[15] = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/minesweeperIcons/mineicon.gif"));

		}
		setIconImage(images[15]);

		/*
		 * ADDING LABEL FOR SHOWING REMAINING MINES
		 * ====================================================================
		 */
		Mines = new Label("MINES : " + String.valueOf(remainingMines));
		Mines.setBounds(5, height * ICON_SIZE + UPPER_BORDER + LOWER_BORDER
				- 65, 60, 15);
		Mines.setVisible(true);
		add(Mines);

		/*
		 * ADDING LABEL FOR SHOWING TIMER
		 * ====================================================================
		 */

		Timer = new Label("0");
		Timer.setBounds(90, height * ICON_SIZE + UPPER_BORDER + LOWER_BORDER
				- 65, 60, 15);
		Timer.setVisible(true);
		add(Timer);

		/*
		 * ADDING MENUBAR
		 * ====================================================================
		 */
		menuBar = new MenuBar();
		setMenuBar(menuBar);

		/*
		 * ADDING GAME MENU
		 * ====================================================================
		 */
		game = new Menu("Game");
		menuBar.add(game);

		newGame = new MenuItem("new game");
		newGame.addActionListener(this);

		restart = new MenuItem("restart");
		restart.addActionListener(this);

		options = new MenuItem("options");
		options.addActionListener(this);

		save = new MenuItem("save");
		save.addActionListener(this);

		load = new MenuItem("load");
		load.addActionListener(this);

		logout = new MenuItem("logout");
		logout.addActionListener(this);

		exit = new MenuItem("Exit Game");
		exit.addActionListener(this);

		game.add(newGame);
		game.add(restart);
		game.addSeparator();
		game.add(options);
		game.addSeparator();
		game.add(save);
		game.add(load);
		game.add(logout);
		game.addSeparator();
		game.add(exit);

		/*
		 * ADDING HELP MENU
		 * ====================================================================
		 */

		help = new Menu("Help");
		menuBar.add(help);

		ViewHelp = new MenuItem("View Help");
		ViewHelp.addActionListener(this);

		about = new MenuItem("About Minesweeper");
		about.addActionListener(this);

		help.add(ViewHelp);
		help.addSeparator();
		help.add(about);

	}

	/**
	 * METHODS FOR MAKING BOARD
	 * ========================================================================
	 */

	public void MineLayer(int x, int y) {

		int minesActivated = 0;
		Random rand = new Random();
		int[][] toExamine = new int[width][height];
		try {
			toExamine[x - 1][y - 1] = 1;
		} catch (Exception e) {
		}
		try {
			toExamine[x - 1][y] = 1;
		} catch (Exception e) {
		}
		try {
			toExamine[x - 1][y + 1] = 1;
		} catch (Exception e) {
		}
		try {
			toExamine[x][y - 1] = 1;
		} catch (Exception e) {
		}
		try {
			toExamine[x][y] = 1;
		} catch (Exception e) {
		}
		try {
			toExamine[x][y + 1] = 1;
		} catch (Exception e) {
		}
		try {
			toExamine[x + 1][y - 1] = 1;
		} catch (Exception e) {
		}
		try {
			toExamine[x + 1][y] = 1;
		} catch (Exception e) {
		}
		try {
			toExamine[x + 1][y + 1] = 1;
		} catch (Exception e) {
		}

		do {
			int a = rand.nextInt(width);
			int b = rand.nextInt(height);
			if ((mineState[a][b] == 0)) {
				if (toExamine[a][b] != 1) {
					mineState[a][b]++;
					minesActivated++;

				}
			}
		} while (minesActivated != mines);
	}

	public int checkMine(int x, int y) {
		int m = 0;
		try {
			m = mineState[x][y];
		} catch (Exception e) {
		}
		return m;
	}

	public int[][] blocksNumberGetter() {
		int[][] block = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int sum = 0;
				if (mineState[i][j] != 1) {
					for (int k1 = -1; k1 <= 1; k1++) {
						for (int k2 = -1; k2 <= 1; k2++) {
							sum += checkMine(i - k1, j - k2);
						}
					}
				} else {
					sum = -1;
				}
				block[i][j] = sum;
			}
		}
		return block;
	}

	/**
	 * MouseListener & MouseMotionListener implemented methods AND METHODS FOR
	 * CHANGING IMAGESSTATE AFTER MOUSECLICK & TIMER method
	 * ========================================================================
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX() - LEFT_BORDER;
		int y = e.getY() - UPPER_BORDER;

		if (y >= 0) {
			x = x / 16;
			y = y / 16;

			if (startTimer == true) {
				isTimerRunning = true;
				runTimer();
				startTimer = false;
			}
			if (e.getButton() == 1 && changabaleState[x][y] == true) {
				if (isFirstClick == true) {
					MineLayer(x, y);
					isFirstClick = false;
					blockNumber = blocksNumberGetter();
				}
				if (imagesState[x][y] == 1)
					remainingMines++;
				reveal(x, y);
			} else if (e.getButton() == 3 && changabaleState[x][y] == true) {
				imagesState[x][y]++;
				imagesState[x][y] = imagesState[x][y] % 3;
				if (imagesState[x][y] == 1) {
					remainingMines--;
				} else if (imagesState[x][y] == 2) {
					remainingMines++;
				}
			}
		}
		Mines.setText("MINES : " + String.valueOf(remainingMines));
		draw(getGraphics());
		win();
	}

	private void runTimer() {
		new Thread() {
			public void run() {
				String S = null;
				try {
					while (isTimerRunning == true) {
						timerNumber++;
						Thread.sleep(1000);
						S = String.valueOf(timerNumber);
						Timer.setText(S);
					}
				} catch (InterruptedException e) {
				}
			}
		}.start();
	}

	private void reveal(int x, int y) {
		if (mineState[x][y] == 1 && changabaleState[x][y] == true) {
			lose(x, y);
		} else if (blockNumber[x][y] > 0) {
			if (imagesState[x][y] == 1)
				remainingMines++;
			imagesState[x][y] = blockNumber[x][y] + 3;
			changabaleState[x][y] = false;
		} else { // it means that the blocknumber is exactly 0 !!!
			revealZero(x, y);
		}
	}

	private void revealZero(int x, int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				catcher(i, j);
			}
		}

	}

	private void catcher(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		else {
			if (blockNumber[x][y] == 0 && changabaleState[x][y] == true) {
				if (imagesState[x][y] == 1)
					remainingMines++;
				imagesState[x][y] = 3;
				changabaleState[x][y] = false;
				revealZero(x, y);
			} else if (mineState[x][y] == 0 && changabaleState[x][y] == true) {
				// means that (blockNumber[x][y] > 0)
				if (imagesState[x][y] == 1)
					remainingMines++;
				imagesState[x][y] = blockNumber[x][y] + 3;
				changabaleState[x][y] = false;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
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

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * Painting methods
	 * ========================================================================
	 */

	public void paint(Graphics g) {
		// super.paint(g);
		draw(g);
	}

	public void draw(Graphics g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				g.drawImage(images[imagesState[i][j]], LEFT_BORDER + i * 16, j
						* 16 + UPPER_BORDER, this);
			}
		}

	}

	/**
	 * ActionListener implemented methods (for MenuBar items)
	 * ========================================================================
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			int answer;
			Object[] options = { "YES", "CANCEL", "SAVE" };
			answer = JOptionPane
					.showOptionDialog(
							null,
							"<html>Are you sure that you want to start a new game?<br>You can save current game before starting a new game</html>",
							"START NEW GAME", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options,
							options[2]);
			// answer : 0:YES/1:CANCEL/2:SAVE
			if (answer == 0) {
				dispose();
				Board newBoard = new Board(width, height, mines, username);
			} else if (answer == 2) {
				try {
					String input = inputReader();
					while (input.isEmpty())
						input = inputReader();
					getSaveNameAndCheckAvailablity(input);
				} catch (IOException e1) {
				}
				dispose();
				Board newBoard = new Board(width, height, mines, username);
			}
		}
		if (e.getSource() == restart) {
			restart();
		}
		if (e.getSource() == options) {
			dispose();
			Configuration cnfg = new Configuration(username);
		}
		if (e.getSource() == save) {
			try {
				String input = inputReader();
				while (input.isEmpty())
					input = inputReader();
				getSaveNameAndCheckAvailablity(input);
			} catch (IOException e1) {
			}
		}
		if (e.getSource() == load) {
			final File dir = new File("./users/" + username
					+ "/Saves");
			JFileChooser fc = new JFileChooser(dir);
			fc.setFileView(new FileView() {

				@Override
				public Boolean isTraversable(File f) {
					return dir.equals(f);
				}
			});
			fc.showOpenDialog(this);
			File selFile = fc.getSelectedFile();
			load(selFile.getName().substring(0, selFile.getName().length() - 4));
		}
		if (e.getSource() == logout) {
			int answer;
			Object[] options = { "YES", "NO" };
			answer = JOptionPane
					.showOptionDialog(
							null,
							"Do you want to save current game before leaving minesweeper?",
							"SAVE", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options,
							options[0]);
			// answer : 0:YES/1:NO
			if (answer == 0) {
				try {
					String input = inputReader();
					while (input.isEmpty())
						input = inputReader();
					getSaveNameAndCheckAvailablity(input);
					SignIn sgn = new SignIn();
				} catch (IOException e1) {
				}
				dispose();

			} else {
				dispose();
				SignIn sgn = new SignIn();
			}
		}
		if (e.getSource() == exit) {
			int answer;
			Object[] options = { "YES", "NO", "SAVE" };
			answer = JOptionPane
					.showOptionDialog(
							null,
							"<html>Are you sure that you want to exit the game?<br>You can save current game before leaving</html>",
							"START NEW GAME", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options,
							options[2]);
			// answer : 0:YES/1:CANCEL/2:SAVE
			if (answer == 0) {
				dispose();
			} else if (answer == 2) {
				try {
					String input = inputReader();
					while (input.isEmpty())
						input = inputReader();
					getSaveNameAndCheckAvailablity(input);
				} catch (IOException e1) {
				}
			}
			System.exit(0);
		}
		if (e.getSource() == about) {
			String aboutMessage = ("Version 1.0\nCopyright 2012\nAll rights reserved. :D\nMinesweeper developed by Sepehr Abbasi zadeh\nThis product is licensed to : " + username);
			JOptionPane.showMessageDialog(null, aboutMessage,
					"About Minesweeper", 1);
		}
		if (e.getSource() == ViewHelp) {
			String help = (" To start a game\n    Double-click Minesweeper.\n    Sign in with your account\n    Choose a difficulty level.\n    Click on the START button."
					+ "\n\nMinesweeper: rules and basics\n  The object\n      Find the empty squares while avoiding the mines. The faster you clear the board, the better your score.\n  The board"
					+ "\n      Minesweeper has six standard boards to choose from, each progressively more difficult.\n      Beginner(easy & hard)\n      Intermediate(easy & hard)\n      Expert(easy & hard)"
					+ "\n      (You can also create a custom board by clicking the Game menu, and then clicking Options.)"
					+ "\n\nHow to play\n    The rules in Minesweeper are simple:\n      Uncover a mine, and the game ends.\n      Uncover an empty square, and you keep playing."
					+ "\n      Uncover a number, and it tells you how many mines lay hidden in the eight surrounding squares�information you use to deduce which nearby squares are safe to click."
					+ "\n\nHints and tips\n    Mark the mines.\n    If you suspect a square conceals a mine, right-click it.\n    This puts a flag on the square. (If you're not sure, right-click again to make it a question mark.)"
					+ "\n    Study the patterns. If three squares in a row display 2-3-2, then you know three mines are probably lined up beside that row. \n    If a square says 8, every surrounding square is mined."
					+ "\n    Explore the unexplored. \n    Not sure where to click next? Try clearing some unexplored territory. You're better off clicking in the middle of unmarked squares than in an area you suspect is mined.                  ");
			JOptionPane.showMessageDialog(null, help, "Help", 1);

		}
	}

	public String inputReader() {
		String input = JOptionPane.showInputDialog("Your save name : ");
		return input;
	}

	public void getSaveNameAndCheckAvailablity(String saveName)
			throws IOException {

		File saveDir = new File("./users/" + username + "/Saves");
		saveDir.mkdir();

		File save = new File(saveDir.getPath() + "/" + saveName + ".txt");

		if (save.exists() == false) {
			save(saveName);
		}

		else {
			int answer;
			Object[] options = { "YES", "CANCEL", "TRY AGAIN" };
			answer = JOptionPane
					.showOptionDialog(
							null,
							"<html>There exists a save file with the same name in your profile<br>Do you really want to save with this name?<br>(This action removes your previously saved data on this save file)</html>",
							"WARNING", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options,
							options[2]);
			// answer : 0:YES/1:CANCEL/2:TRY AGAIN
			if (answer == 0) {
				save(saveName);
			} else if (answer == 2) {
				String s = inputReader();
				while (s.isEmpty())
					s = inputReader();
				getSaveNameAndCheckAvailablity(s);
			}
		}
	}

	/**
	 * LOSING AND WINNING ACTIONS
	 * ========================================================================
	 */
	private void lose(int x, int y) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (mineState[i][j] == 1 && imagesState[i][j] == 1) {
					imagesState[i][j] = 1;
				} else if (mineState[i][j] == 1) {
					imagesState[i][j] = 12;
				} else if (imagesState[i][j] == 1) {
					imagesState[i][j] = 14;
				}
				changabaleState[i][j] = false;
			}
		}
		imagesState[x][y] = 13;
		isTimerRunning = false;
		draw(getGraphics());
		JOptionPane.showMessageDialog(null, "Sorry, you lost this game",
				"GAME LOST", 1);

		int answer;
		Object[] options = { "YES", "NEW GAME", "EXIT GAME" };
		answer = JOptionPane.showOptionDialog(null,
				"Do you want to restart this game?", "RESTART",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[1]);
		// answer : 0:YES/1:NEW GAME/2:EXIT GAME
		if (answer == 0) {
			restart();
		} else if (answer == 1) {
			dispose();
			Board brd = new Board(width, height, mines, username);
		} else if (answer == 2) {
			dispose();
			System.exit(0);
		}
	}

	private void win() {
		int trueBlocks = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (changabaleState[i][j] == true)
					trueBlocks++;
			}
		}
		int trueFlagged = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (imagesState[i][j] == 1 && mineState[i][j] == 1)
					trueFlagged++;
			}
		}

		if (trueBlocks == this.mines || trueFlagged == this.mines) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					changabaleState[i][j] = false;
				}
			}
			isTimerRunning = false;
			setHighScore();
			JOptionPane.showMessageDialog(null,
					"Congratulations, you won this game", "GAME WON", 1);
			int answer;
			Object[] options = { "Play Again", "Exit Game" };
			answer = JOptionPane.showOptionDialog(null,
					"Do you want to start a new game?", "START",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[1]);
			// answer : 0:Play again/1:Exit Game
			if (answer == 0) {
				dispose();
				Board brd = new Board(width, height, mines, username);
			} else if (answer == 1) {
				dispose();
				System.exit(0);
			}
		}
	}

	public void setHighScore() {
		// File root = new File("/D:/minesweeper");
		// File hs = new File(root.getPath() + "HighScores.txt");
		// try {
		// hs.createNewFile();
		// } catch (IOException e1) {
		// }
		// try {
		// writeToFile(hs, String.valueOf(timerNumber));
		// } catch (IOException e) {
		// }
	}

	/**
	 * saving and loading processes and restart method
	 * ========================================================================
	 */
	private static void writeToFile(File save, String dataForSave)
			throws IOException {
		FileWriter writer = new FileWriter(save, false);
		writer.write(dataForSave);
		writer.close();
	}

	public void restart() {
		startTimer = true;
		isTimerRunning = false;
		remainingMines = mines;
		Mines.setText(String.valueOf(mines));
		timerNumber = 0;
		Timer.setText("0");

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				changabaleState[i][j] = true;
				imagesState[i][j] = 0;
			}
		}
		draw(getGraphics());
	}

	public void save(String saveName) {

		File saveDir = new File("./users/" + username + "/Saves");
		saveDir.mkdir();
		File save = new File("./users/" + username + "/Saves/"
				+ saveName + ".txt");

		/*
		 * data that we need to know the elapsed time
		 * ====================================================
		 */

		String elapsedTime = this.Timer.getText() + "\r\n";

		/*
		 * data that we need to know the board size
		 * ====================================================
		 */

		String constructors = this.width + "\r\n" + this.height + "\r\n"
				+ this.mines + "\r\n";

		/*
		 * data that we need to know which blocks are mines
		 * ====================================================
		 */
		String mines = "";
		for (int i = 0; i < width; i++) {
			int j;
			for (j = 0; j < height; j++) {
				mines += checkMine(i, j);
			}
		}
		mines += Mines.getText().substring(Mines.getText().length() - 1);
		mines += "\r\n";

		/*
		 * data that we need to know each block's imageState
		 * ====================================================
		 */

		String images = "";
		for (int i = 0; i < width; i++) {
			int j;
			for (j = 0; j < height; j++) {
				images += imagesState[i][j] + "\r\n";
			}
		}
		/*
		 * data that we need to know which blocks are disabled
		 * ====================================================
		 */

		String states = "";
		for (int i = 0; i < width; i++) {
			int j;
			for (j = 0; j < height; j++) {
				states += changabaleState[i][j] + "\r\n";
			}
		}

		/*
		 * complete gamedata
		 * ====================================================
		 */

		String gameData = constructors + mines + images + states + elapsedTime;

		try {
			save.createNewFile();
			writeToFile(save, gameData);
		} catch (IOException e) {
		}
	}

	public void load(String savedName) {
		File saveDir = new File("./users/" + username + "/Saves");
		saveDir.mkdir();
		File saved = new File("./users/" + username + "/Saves/"
				+ savedName + ".txt");
		Scanner sc;
		ArrayList<String> data = new ArrayList<String>();
		try {
			sc = new Scanner(saved);
			while (sc.hasNextLine()) {
				data.add(sc.nextLine());
			}

			int width = Integer.parseInt(data.get(0));
			int height = Integer.parseInt(data.get(1));
			int mines = Integer.parseInt(data.get(2));

			dispose();
			Board loaded = new Board(width, height, mines, username, savedName);
			loaded.loadName = savedName;
			loaded.remainingMines = Integer.parseInt(data.get(3).substring(
					width * height));
			loaded.Mines.setText("MINES : "
					+ data.get(3).substring(width * height));
			loaded.Timer.setText(data.get(2 * width * height + 4));
			loaded.timerNumber = Integer.parseInt(data.get(2 * width * height
					+ 4));
			loaded.startTimer = false;

		} catch (FileNotFoundException e) {
		}
	}

	public void getLoadedData() {

		File saveDir = new File("./users/" + username + "/Saves");
		saveDir.mkdir();
		File saved = new File("./users/" + username + "/Saves/"
				+ loadName + ".txt");

		Scanner sc;
		ArrayList<String> data = new ArrayList<String>();

		try {
			sc = new Scanner(saved);
			while (sc.hasNextLine()) {
				data.add(sc.nextLine());
			}
			int width = Integer.parseInt(data.get(0));
			int height = Integer.parseInt(data.get(1));
			String mineBlocks = data.get(3);

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					mineState[i][j] = mineBlocks.charAt(height * i + j) - 48;
					changabaleState[i][j] = Boolean.parseBoolean(data
							.get((height * i) + j + (width * height) + 4));
					imagesState[i][j] = Integer.parseInt(data.get((height * i)
							+ j + 4));
				}
			}
		} catch (Exception e) {
		}
		runTimer();
	}
}
