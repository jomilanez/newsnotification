package de.hackfest.es.notifier;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: abaranga Date: 28.05.14 Time: 12:00 To change this template use File | Settings | File Templates.
 */
public class NewsNotifier {

	public void processNotifications(final List<String> notifications) {

		for (String notification : notifications) {
			sendMessage(notification);
		}

	}

	private void sendMessage(final String notification) {
		// open a dialog with a meaningfull message
		// http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#dialogdemo
	}
}
