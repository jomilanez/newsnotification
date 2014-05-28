package de.hackfest.es.notifier;

import java.util.List;

import javax.swing.JOptionPane;

/**
 * Created with IntelliJ IDEA. User: abaranga Date: 28.05.14 Time: 12:00 To
 * change this template use File | Settings | File Templates.
 */
public class NewsNotifier {
	private JOptionPane optionPane;

	public NewsNotifier() {
		Object[] arrayList = { "Ok", "Quit" };
		optionPane = new JOptionPane("", JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, arrayList);
		optionPane.setVisible(true);

	}

	public void processNotifications(final List<String> notifications) {
		for (String notification : notifications) {
			sendMessage(notification);
		}
	}

	private void sendMessage(final String notification) {
		System.out.println(notification);
		String message = "default";
		switch (notification) {
		case "1":
			message = "Police Alert";
			break;
		case "2":
			message = "Happy Kids";
			break;
		}

		displayMessage(message);
	}

	private void displayMessage(final String notification) {
		optionPane.setMessage(notification);
		optionPane.createDialog(null, "alert").setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		NewsNotifier notifier = new NewsNotifier();
		while (true) {
			notifier.sendMessage("test " + Math.random());
			Thread.sleep(1000);
		}
	}
}
