package de.hackfest.es;

import de.hackfest.es.indexer.NewsIndexer;
import de.hackfest.es.notifier.NewsNotifier;
import de.hackfest.es.percolator.NewsPercolate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: abaranga Date: 28.05.14 Time: 11:57 To change this template use File | Settings | File Templates.
 */
public class NewsMain {
	private final NewsIndexer newsIndexer;
	private final NewsPercolate newsPercolate;
	private final NewsNotifier newsNotifier;

	public NewsMain() {
		newsIndexer = new NewsIndexer();
		newsPercolate = new NewsPercolate();
		newsNotifier = new NewsNotifier();
	}

	public static void main(String... args) {
		// resd file with bulk data

		List<String> newsJsonList = new ArrayList<String>();
		// TODO read data from the file

		NewsMain main = new NewsMain();
		main.processNews(newsJsonList);

		// stay alive loop
		for (;;) {
		}
	}

	private void processNews(final List<String> newsJsonList) {
		// every 10 seconds send news

		for (String newsJson : newsJsonList) {
			// index document
			newsIndexer.index(newsJson);
			List<String> notificationResults = newsPercolate.percolate(newsJson);
			newsNotifier.processNotifications(notificationResults);
		}
	}
}
