 package de.hackfest.es;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import de.hackfest.es.indexer.NewsIndexer;
import de.hackfest.es.notifier.NewsNotifier;
import de.hackfest.es.percolator.NewsPercolate;
import de.hackfest.es.utils.NewsUtils;

/**
 * Created with IntelliJ IDEA. User: abaranga Date: 28.05.14 Time: 11:57 To change this template use File | Settings | File Templates.
 */
public class NewsMain {
	private NewsIndexer newsIndexer;
	private final NewsPercolate newsPercolate;
	private final NewsNotifier newsNotifier;
	private Client esClient;
	private NewsConfig config;

	public NewsMain() {
		createESClient();
		newsIndexer = new NewsIndexer(esClient, config.getIndexName());
		newsPercolate = new NewsPercolate(esClient, config.getIndexName());
		newsNotifier = new NewsNotifier();
	}

	public static void main(String... args) throws IOException {
		// resd file with bulk data

		List<String> newsJsonList = readJsonFile();
		// TODO read data from the file

		NewsMain main = new NewsMain();
		main.processNews(newsJsonList);
	}

	private void processNews(final List<String> newsJsonList) {
		// every 10 seconds send news
		
		for (String newsJson : newsJsonList) {
			// index document
			newsIndexer.index(newsJson);
			List<String> notificationResults = newsPercolate.percolate(newsJson);
			newsNotifier.processNotifications(notificationResults);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List<String> readJsonFile() throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(NewsMain.class.getClassLoader().getResource("localnews.bulk").getPath()));

        List<String> result = new ArrayList<>();
		while (in.ready()) {
			String s = in.readLine();
			if (s.startsWith("{\"category\"")) {
                result.add(s);
			}
		}
		in.close();
        return result;
	}
	
	private void createESClient(){
		Properties properties = NewsUtils.loadResourceProperties("es.properties");
		this.config = new NewsConfig(properties);
		final Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", config.getEsClusterName()).build();
		TransportClient client = new TransportClient(settings);

		for (String hostPort : config.getEsZenDiscoveryHosts().split(",")) {
			final String[] parsed = hostPort.split(":");
			Integer port = 9300;
			if (parsed.length == 2) {
				port = Integer.parseInt(parsed[1]);
			}
			client.addTransportAddress(new InetSocketTransportAddress(parsed[0], port));
		}
		esClient = client;
	}
	
	

}