package boardBuilder;

import settings.Configuration;
import settings.SignIn;

public class Main {

	public static void main(String[] arg) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SignIn si = new SignIn();
				// Board b = new Board(9, 9, 9, "sepehr");
				// Configuration config = new Configuration("ali");
			}
		});
	}
}
